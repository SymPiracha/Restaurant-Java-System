package activity7;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Enum representing common dietary categories/types. Similar to FoodType.
 * See it for more info.
 */
public class DietaryCategory
{
    private static final Map<String, DietaryCategory> aInstances = new HashMap<>();

    public static DietaryCategory HALAL = get("Halal");
    public static DietaryCategory KOSHER = get("Kosher");
    public static DietaryCategory PALEO = get("Paleo");
    public static DietaryCategory VEGAN = get("Vegan");
    public static DietaryCategory VEGETARIAN = get("Vegetarian");
    public static DietaryCategory GLUTEN_FREE = get("Gluten-Free");

    private final String name;

    public static DietaryCategory get(String pName)
    {
        assert pName != null && !"".equals(pName);
        if (aInstances.containsKey(pName.toLowerCase()))
        {
            return aInstances.get(pName.toLowerCase());
        }
        DietaryCategory dietaryCategory = new DietaryCategory(pName);
        aInstances.put(dietaryCategory.name.toLowerCase(), dietaryCategory);
        return dietaryCategory;
    }

    private DietaryCategory(String pName)
    {
        name = pName;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals(Object pObject)
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
        DietaryCategory dietaryCategory = (DietaryCategory) pObject;
        return this.name.equalsIgnoreCase(dietaryCategory.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name.toLowerCase());
    }
}
