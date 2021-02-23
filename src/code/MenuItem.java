package activity7;

import java.util.Collection;
import java.util.List;

/**
 * Represents an element that can be displayed in a menu.
 */
public interface MenuItem
{
	/**
	 * @return The total price of the item.
	 */
	double getPrice();

	/**
	 * @return The name of the item. NOT synonymous with description().
	 */
	String getName();

	/**
	 * @return A short description to display in the GUI:
	 *         [Name] [modifiers...] [$Price]
	 */
	String description();

	/**
	 * @return The food category this item belongs to.
	 */
	FoodType getFoodType();

	/**
	 * @return A list of all unique dietary categories for this item.
	 */
	Collection<DietaryCategory> getDietaryCategories();
}
