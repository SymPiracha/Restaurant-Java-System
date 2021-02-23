package activity7;

import java.util.*;

/**
 * <h1>SizeableItem</h1>
 * Class representing an item that is sized and can be resized.
 * <h1>Major decisions history</h1>
 * <p> <b>11/22</b>
 *  Initial design outlined in #4.
 *  The inability to have two items with the same name but different sizes is
 *  acknowledged in an effort be both flyweight whilst allowing changing sizes.
 *  This compromise is made to explore the flyweight design pattern while
 *  respecting requirement 2.4.
 * </p>
 * <p> <b>11/25</b>
 *  SizeableItem no longer flyweight. Equality criteria now defined as having
 *  the same name, size and class. To not interfere with FoodItem's flyweight
 *  system, equals separates by class rather than instanceof. Design decision
 *  to allow multiple items of the same name with different sizes and after
 *  drawbacks discussion with Prof. Robillard.
 * </p>
 */
public final class SizeableItem extends FoodItem
{

	/** Current size of the item. */
	private Size aSize;
	/** Price modifier for each size (as multiplier to base price). */
	private final Map<Size, Double> aMultipliers = new HashMap<>();
//	/** Instance list for flyweight instantiation. */
//	private static final List<SizeableItem> aInstances = new LinkedList<>();

	//Initialize price multiplier Map.
	{
		for (Size s : Size.values())
		{
			aMultipliers.put(s, s.defaultModifier);
		}
	}

	/* --------------------------[Instantiation]---------------------------- */

	/**
	 * Constructor for a SizeableItem. Creates a SizeableItem with given size
	 * and default size modifiers.
	 */
	public SizeableItem(Size pSize, String pName, double pPrice, FoodType pFoodType,
						Collection<DietaryCategory> pDietaryCategories)
	{
		super(pName, pPrice, pFoodType, pDietaryCategories);
		aSize = pSize;

	}

	public SizeableItem(Size pSize, String pName, double pPrice,
						FoodType pFoodType, DietaryCategory... pDietaryCategories)
	{
		super(pName, pPrice, pFoodType, List.of(pDietaryCategories));
		aSize = pSize;
	}

	/* -------------------------[Specific methods]-------------------------- */

	/** @param pSize New size. */
	public void setSize(Size pSize)
	{
		assert pSize != null;
		aSize = pSize;
		Menu.instance().updateMenu();
	}

	/**
	 * Changes the price modifier for a given size.
	 * @param pSize Size whose modifier to change.
	 * @param pModifier New modifier for that size.
	 * @pre pSize != null;
	 */
	public void changeSizeModifier(Size pSize, double pModifier)
	{
		assert pSize != null;
		aMultipliers.put(pSize, pModifier);
		Menu.instance().updateMenu();
	}

	/* ------------------------[Inherited methods]-------------------------- */

	/**
	 * @return Price of this item, depending on its current size and the
	 *         multiplier for that size. In other words, the base price
	 *         times the multiplier for its current size.
	 */
	@Override
	public double getPrice()
	{
		return aMultipliers.get(aSize) * super.getPrice();
	}

	@Override
	String extraInformation()
	{
		//return size followed by foodItem extraInformation
		return this.aSize + super.extraInformation();

	}

	/**
	 * @return Whether the item is considered equal to this.
	 *         The current definition of equality is that the two items share
	 *         the same name, size and are of the same class. This definition
	 *         is subject to change.
	 */
	@Override
	public boolean equals(Object pObject)
	{
		if (pObject == null) return false;
		if (pObject == this) return true;
		if (pObject.getClass() != this.getClass()) return false;
		SizeableItem sizeableItem = (SizeableItem) pObject;
		return this.aName.equals(sizeableItem.aName)
				&& this.aSize == sizeableItem.aSize;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(aName, aSize);
	}
}
