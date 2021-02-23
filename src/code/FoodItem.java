package activity7;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing a food item.
 *
 * Acknowledged drawbacks.
 *
 * FoodCategory could be made into a class (see FoodType for more details).
 * FoodCategory could be an optional field.
 *
 * List arguments and returns could be replaced with Collections, allowing
 * other methods to just chuck in whatever categories they have on hand and not
 * having to convert to a list. At this point, changing the method signature
 * would cause more refactoring trouble than it is worth so it is left as List.
 *
 * extraInformation slightly suboptimal, see !4 for more details.
 */
public class FoodItem extends AbstractMenuItem
{
	private final Set<DietaryCategory> aDietaryCategories = new HashSet<>();
	/** Not storing as Map in case we change the uniqueness criteria. */
	private final static List<FoodItem> aInstances = new ArrayList<>();

	/* --------------------------[Instantiation]---------------------------- */

	/** Constructor for a FoodItem. */
	protected FoodItem(String pName, double pPrice, FoodType pFoodType,
					   Collection<DietaryCategory> pDietaryCategories)
	{
		super(pName, pFoodType, pPrice);
		aName = pName;
		aDietaryCategories.addAll(pDietaryCategories);
	}

	/**
	 * Flyweight getter method. The current uniqueness criteria is that two
	 * items have the same name.
	 * To change this criteria, modify {@link #willBeEqual}
	 */
	public static FoodItem get(String pName, double pPrice, FoodType pFoodType,
							   Collection<DietaryCategory> pDietaryCategories)
	{
		assert pName != null;
		for(FoodItem item: aInstances)
		{
			if(item.willBeEqual(pName, pPrice, pFoodType, pDietaryCategories)){
				return item;
			}
		} 
		FoodItem item = new FoodItem(pName, pPrice, pFoodType,
				pDietaryCategories);
		aInstances.add(item);
		return item;
	}
	
	public static FoodItem get(String pName, double pPrice, FoodType pFoodType)
	{
		return get(pName, pPrice, pFoodType, Collections.emptyList());
	}

	/** 
	 * Alternate Flyweight getter method.
	 * @see #get(String, double, FoodType, Collection)
	 */
	public static FoodItem get(String pName, double pPrice, FoodType pFoodType,
							   DietaryCategory... pDietaryCategories)
	{
		assert pDietaryCategories != null;
		Arrays.stream(pDietaryCategories)
				.forEach( (dietCat) -> {assert dietCat != null;} );
		return get(pName, pPrice, pFoodType, Arrays.asList(pDietaryCategories));
	}
	


	/* -------------------------[Specific methods]-------------------------- */

	/**
	 * Adds some dietary categories to this item's list. Appends them in
	 * lowercase.
	 * @param pCategories Dietary categories to append onto this item. */
	public void addDietaryCategories(DietaryCategory ... pCategories)
	{
		Arrays.stream(pCategories).forEach( (cat) -> {assert cat != null;} );
		//ToLower then add all.
		aDietaryCategories.addAll(
				Arrays.stream(pCategories)
						.collect(Collectors.toSet()));
		Menu.instance().updateMenu();
	}

	/** @param pCategories Dietary categories to remove from this item. */
	public void removeDietaryCategories(DietaryCategory ... pCategories)
	{
		Arrays.stream(pCategories).forEach( (cat) -> {assert cat != null;} );
		for (DietaryCategory s : pCategories) {
			aDietaryCategories.remove(s);
		}
		Menu.instance().updateMenu();
	}

	/** @param pPrice New price to set. */
	public void setPrice(double pPrice)
	{
		aPrice=pPrice;
		Menu.instance().updateMenu();
	}

	/* ------------------------[Inherited methods]-------------------------- */

	@Override
	String extraInformation()
	{
		String conditionsString = "with dietary categories (";
		if (aDietaryCategories.isEmpty()) {
			conditionsString = "";
		}
		else {
			int commaMgr = 0;
			for (DietaryCategory category: aDietaryCategories) {
				if (commaMgr == 0)
				{
					conditionsString += category.toString();
				}
				else
					{
					conditionsString += ", " + category.toString();
				}
				commaMgr++;
			}
			conditionsString += ") and a price of";
		}
		return conditionsString;
	}

	/**
	 * @return A list of all dietary categories of this food item. This method
	 *         makes no guarantees on the order in which the categories will be
	 *         presented in the list.
	 */
	@Override
	public Collection<DietaryCategory> getDietaryCategories()
	{
		//Return unmodifiable list of cats.
		return List.copyOf(aDietaryCategories);
	}

	/**
	 * @return Whether the item is considered equal to this.
	 *         The current definition of equality is that the two items share
	 *         the same type, and same name. This can be changed.
	 */
	@Override
	public boolean equals(Object pObject)
	{
		if (pObject == null)
		{
			return false;
		}
		else if (pObject == this)
		{
			return true;
		}
		else if (pObject.getClass() != this.getClass())
		{
			return false;
		}

		FoodItem fi = (FoodItem) pObject;
		//return willBeEqual(fi.aName, fi.aPrice, fi.aFoodType, fi.getDietaryCategories());
		return this.aName.equals(fi.aName);
	}

	/**
	 * Currently unused.
	 * @return Whether the object is totally-equals to this.
	 */
	public boolean totallyEquals(Object pObject)
	{
		if (pObject == null)
		{
			return false;
		}
		if (pObject == this)
		{
			return true;
		}
		if (pObject.getClass() != this.getClass())
		{
			return false;
		}

		FoodItem foodItem = (FoodItem) pObject;
		return foodItem.aName.equals(this.aName)
				&& foodItem.aFoodType.equals(this.aFoodType)
				&& foodItem.aPrice == this.aPrice
				&& this.aDietaryCategories.equals(foodItem.aDietaryCategories);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(aName);
	}

	/**
	 * Checks if a new item created with the given arguments will be
	 * equal to this one as far as the uniqueness criteria is concerned.
	 * Mirrors the equals check and the arguments of the constructor.
	 * Some arguments will be unused to allow easy refactoring in case the
	 * equality criteria are changed.
	 * @return Whether an object created with the given arguments are equal
	 *         as far as the flyweight creation method is concerned.
	 * @see #equals(Object)
	 */
	private boolean willBeEqual(String pName, double pPrice, FoodType pFoodType,
								Collection<DietaryCategory> pDietaryCategories)
	{
		return aName.equals(pName);
	}
}
