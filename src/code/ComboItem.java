package activity7;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <h1>Combo Item</h1>
 * Class representing a combo item.
 * <h1>Major decisions history</h1>
 * <p> <b>11/22</b>
 *  Initial design outlined in #4.
 * </p>
 * <p> <b>11/24</b>
 *  Add bulk remover using predicates to determine what items to remove.
 * </p>
 * <p> <b>11/26</b>
 *  Move price to AbstractMenuItem, rework price calculation to update value.
 * </p>
 */
public class ComboItem extends AbstractMenuItem
{

	/** Discount (portion of price) applied to the total price. */
	private double aDiscount;
	/** MenuItems contained in this combo. */
	private final List<MenuItem> aItems;

	/* --------------------------[Instantiation]---------------------------- */

	/**
	 * Constructor for a combo item.
	 * @param pDiscount Discount to apply. Must be between 0 and 1.
	 * @param pFoodType Food type of the combo as a whole
	 */
	public ComboItem(String pName, double pDiscount, FoodType pFoodType,
					 MenuItem... pMenuItems)
	{
		super(pName, pFoodType, 123D); //Temporary dummy price
		assert pDiscount >= 0D && pDiscount <= 1D && pMenuItems != null;
		Arrays.stream(pMenuItems).forEach(item -> { assert item != null; });

		aDiscount = pDiscount;
		aItems = new ArrayList<>(Arrays.asList(pMenuItems));
		recalculatePrice();
	}

	/* -------------------------[Specific methods]-------------------------- */

	public void setDiscount(Double pDiscount)
	{
		assert pDiscount >= 0D && pDiscount <= 1D;
		aDiscount = pDiscount;
		recalculatePrice();
		Menu.instance().updateMenu();
	}

	public double getDiscount() {
		return aDiscount;
	}

	public Collection<MenuItem> getItems()
	{
		return Collections.unmodifiableList(aItems);
	}

	/**
	 * Adds items to this combo
	 * @param pMenuItems Items to add.
	 */
	public void addItem(MenuItem ... pMenuItems)
	{
		assert pMenuItems != null;
		Arrays.stream(pMenuItems).forEach(menuItem -> { assert menuItem != null; });

		aItems.addAll(Arrays.asList(pMenuItems));
		recalculatePrice();
		Menu.instance().updateMenu();
	}

	/**
	 * Removes items from this combo.
	 * @param pMenuItems Items to remove.
	 */
	public void removeItem(MenuItem ... pMenuItems)
	{
		assert pMenuItems != null;
		Arrays.stream(pMenuItems).forEach(menuItem -> { assert menuItem != null; });

		aItems.removeAll(Arrays.asList(pMenuItems));
		recalculatePrice();
		Menu.instance().updateMenu();
	}

	/**
	 * Removes all items contained within this combo matching the predicate.
	 * @param pFilter Predicate to use.
	 */
	public void removeItem(Predicate<MenuItem> pFilter)
	{
		assert pFilter != null;
		aItems.removeIf(pFilter); //Could be replaced with a for loop
		recalculatePrice();
		Menu.instance().updateMenu();
	}

	private double originalPrice()
	{
		recalculatePrice();
		return aItems.stream()                     //Stream<MenuItem
				.mapToDouble( MenuItem::getPrice ) //Stream<double>
				.sum();                            //double
	}
	
	/* ------------------------[Inherited methods]-------------------------- */

	@Override
	String extraInformation()
	{
		int i = 0;
		String comboInfo = "which includes the following: ";
		for (MenuItem item: aItems)
		{
			if (i == 0) comboInfo += item.getName();
			else comboInfo += ", " + item.getName() + "";
			i++;
		}
		comboInfo += String.format(". Original Price: $ %.2f. Discounted Price:", originalPrice());
		
		return comboInfo;
		
	}
	
	private void recalculatePrice()
	{
		aPrice = (1 - aDiscount) *
				aItems.stream()                            //Stream<MenuItem
						.mapToDouble( MenuItem::getPrice ) //Stream<double>
						.sum();                            //double
	}

	/**
	 * Gets all unique dietary categories from the content of this combo.
	 * This method makes no guarantee on the order the dietary categories
	 * will be presented in.
	 * @return Unmodifiable list of unique dietary categories.
	 */
	@Override
	public Collection<DietaryCategory> getDietaryCategories()
	{
		return List.copyOf(aItems.stream()           //Stream<MenuItem>
				.map(MenuItem::getDietaryCategories) //Stream<Collection<DietaryCategory>>
				.flatMap(Collection::stream)         //Stream<DietaryCategory>
				.collect(Collectors.toSet())         //Set<DietaryCategory>
		);
	}

	/**
	 * @return Whether the object is totally equal to this. Note: total
	 *         equality is defined as each item being equal and in the same
	 *         order, how that equality is defined lies with each item's
	 *         implementation.
	 */
	@Override
	public boolean equals(Object pObject)
	{
		if (pObject == null) return false;
		if (pObject == this) return true;
		if (pObject.getClass() != this.getClass()) return false;
		ComboItem comboItem = (ComboItem) pObject;
		return this.aName.equals(comboItem.aName)
				&& this.aDiscount == comboItem.aDiscount
				&& this.aFoodType.equals(comboItem.aFoodType)
				&& this.aItems.equals(comboItem.aItems);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(aName, aDiscount, aFoodType, aItems);
	}
}
