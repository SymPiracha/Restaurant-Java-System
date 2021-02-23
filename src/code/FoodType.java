package activity7;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <b>UPDATE</b> In the end, this enum was changed for a flyweight class.
 * Name and variable fields remain public for compatibility with old code (#9).
 *
 * <b>OUTDATED BELOW</b>
 * Enum representing food types. Currently only used to easily access common
 * String keywords rather than riskily retyping "Drink"/"Snack", ... each time
 * (and risking making typos) and allow quickly changing food type names in
 * in case we want to.
 *
 * FoodTypes are still stored as String to allow for custom types. This enum
 * only provides easy access to common keywords.
 *
 * DO NOT HARDCODE ANYTHING USING THIS ENUM. IT IS CURRENTLY ONLY USED AS
 * "GLOBAL VARIABLES" TO GET PRE-DETERMINED STRINGS.
 *
 * Recommend using FoodType.name or FoodType.toString() to instantiate
 * MenuItems' FoodType field or anytime we are referring to one of the 3
 * pre-determined food types.
 *
 * Acknowledged drawbacks: this system is easy to confuse and working with
 * primitive Strings borders on primitive obsession. A flyweight FoodType class
 * with only a name field is more appropriate, but a disproportional amount of
 * work for little functionality gain.
 *
 * TLDR: <b>DO NOT USE AS ARGUMENT.</b>
 *
 */
public class FoodType
{

	private final static Map<String, FoodType> aInstances = new HashMap<>();

	public static FoodType DRINK = get("Drink");
	public static FoodType SNACK = get("Snack");
	public static FoodType MAIN_MEAL = get("Main Meal");

	private final String name;

	private FoodType(String pName)
	{
		name = pName;
	}

	public static FoodType get(String pName)
	{
		assert pName != null && !"".equals(pName);
		if (aInstances.containsKey(pName.toLowerCase()))
		{
			return aInstances.get(pName.toLowerCase());
		}
		FoodType foodType = new FoodType(pName);
		aInstances.put(foodType.name.toLowerCase(), foodType);
		return foodType;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object pObject)
	{
		if (pObject == null) return false;
		if (pObject == this) return true;
		if (pObject.getClass() != this.getClass()) return false;
		FoodType foodType = (FoodType) pObject;
		return this.name.equalsIgnoreCase(foodType.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name.toLowerCase());
	}

	public Predicate<MenuItem> getPredicate()
	{
		return (menuItem) -> this.equals(menuItem.getFoodType());
	}
}
