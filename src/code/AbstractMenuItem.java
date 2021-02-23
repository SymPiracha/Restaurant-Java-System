package activity7;

import java.util.Collection;

/**
 * Menu items with a name and a category. Represents an element (composite
 * or unique) that has a name rather than than one singular item.
 * @see ComboItem
 * @see FoodItem
 * @see SizeableItem
 */
public abstract class AbstractMenuItem implements MenuItem
{
	/** Price of the item. */
	protected double aPrice;
	/** Name of the menu item. */
	protected String aName;
	/** Food type of the menu item. */
	protected FoodType aFoodType;

	/**
	 * Constructor for a MenuItem. Only used by subclasses to enforce
	 * instantiation of aName and aFoodType.
	 */
	public AbstractMenuItem(String pName, FoodType pFoodType, double pPrice)
	{
		assert pName != null && pFoodType != null && pPrice >= 0D;
		assert !"".equals(pName);
		aPrice = pPrice;
		aName = pName;
		aFoodType = pFoodType;
	}

	/**
	 * @return Name of this item.
	 */
	@Override
	public final String getName()
	{
		return aName;
	}

	/**
	 * @return The food category this item belongs to.
	 */
	@Override
	public final FoodType getFoodType()
	{
		return aFoodType;
	}

	public final void setFoodType(FoodType pFoodType)
	{
		assert pFoodType != null;
		aFoodType = pFoodType;
		Menu.instance().updateMenu();
	}

	/**
	 * @return The total price of this item.
	 */
	@Override
	public double getPrice()
	{
		return aPrice;
	}
	

	/**
	 * @return All dietary categories contained by this MenuItem.
	 *         This method makes no guarantee as to the order the categories
	 *         will be presented in.
	 */
	@Override
	abstract public Collection<DietaryCategory> getDietaryCategories();

	/**
	 * @return A short string to be displayed by the GUI.
	 */
	@Override
	public final String description()
	{
		return String.format("[%s %s $%.2f]", getName(), extraInformation(), getPrice());
	}
	
	abstract String extraInformation();
	
	@Override
	public final String toString()
	{
		return description();
	}
}
