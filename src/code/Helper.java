package activity7;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Helper class containing some common methods.
 */
public final class Helper {

	private Helper() {
		throw new IllegalArgumentException("Don't instantiate this");
	}


	public static boolean does_a_contain_b(Collection<DietaryCategory> a, DietaryCategory...b) {
		boolean contains;
		for (DietaryCategory s:b) {
			contains = a.contains(s);
			if (!contains) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return A predicate that filters for some food type.
	 */
	public static Predicate<MenuItem> isFoodType(FoodType pCategory){
		return item -> item.getFoodType().equals(pCategory);
	}

	/**
	 * @return A predicate that filters for some categories.
	 */
	public static Predicate<MenuItem> isDietaryCategory(DietaryCategory ... pCategories){
		return c -> does_a_contain_b(c.getDietaryCategories(), pCategories);
	}

	/**
	 * A comparator method to sort menu items by price in increasing order.
	 * @return sorted list of menu items by price.
	 */
	public static Comparator<MenuItem> sortByPrice() {
		return comparing(MenuItem::getPrice);
	}

	/**
	 * A comparator method to sort menu items alphabetically by description.
	 * @return sorted list of menu items by description.
	 */
	public static Comparator<MenuItem> sortByDescription() {
		return comparing(MenuItem::toString);
	}

	/**
	 * Call this to populate the menu with dummy items for the purpose of either
	 * testing or showcase.
	 */
	public static void populateWithDummies() {
		Menu menu = Menu.instance();
		//Add drinks
		FoodItem coffee = FoodItem.get("Coffee", 2D, FoodType.DRINK, DietaryCategory.VEGETARIAN);
		FoodItem tea = FoodItem.get("Tea", 1.75D, FoodType.DRINK, DietaryCategory.VEGAN, DietaryCategory.VEGETARIAN, DietaryCategory.HALAL);
		FoodItem beer = FoodItem.get("Beer", 2.5D, FoodType.DRINK);
		SizeableItem smallSoda = new SizeableItem(Size.SMALL, "Soda", 2.5D, FoodType.DRINK);
		SizeableItem mediumSoda = new SizeableItem(Size.MEDIUM, "Soda", 2.5D, FoodType.DRINK);
		SizeableItem largeSoda = new SizeableItem(Size.LARGE, "Soda", 2.5D, FoodType.DRINK);
		menu.addItem(coffee); menu.addItem(tea); menu.addItem(beer); menu.addItem(smallSoda); menu.addItem(mediumSoda); menu.addItem(largeSoda);

		//Add main meals
		FoodItem poutine = FoodItem.get("Poutine", 8D, FoodType.MAIN_MEAL);
		FoodItem cheeseburger = FoodItem.get("Cheeseburger", 5D, FoodType.MAIN_MEAL);
		FoodItem spaghetti = FoodItem.get("Spaghetti", 5.75D, FoodType.MAIN_MEAL);
		FoodItem soup = FoodItem.get("Soup", 6D, FoodType.MAIN_MEAL);
		FoodItem okonomiyaki = FoodItem.get("Okonomiyaki", 15D, FoodType.MAIN_MEAL);
		FoodItem salad = FoodItem.get("Salad", 100D, FoodType.MAIN_MEAL);
		menu.addItem(poutine); menu.addItem(cheeseburger); menu.addItem(spaghetti); menu.addItem(soup); menu.addItem(okonomiyaki); menu.addItem(salad);

		//Add snacks
		FoodItem chips = FoodItem.get("Chips", 1D, FoodType.SNACK);
		FoodItem peanuts = FoodItem.get("Peanuts", 1D, FoodType.SNACK);
		FoodItem chocobar = FoodItem.get("Chocolate Bar", 1.75D, FoodType.SNACK);
		FoodItem fries = FoodItem.get("Fries", 1.75D, FoodType.SNACK);
		menu.addItem(chips); menu.addItem(peanuts); menu.addItem(chocobar); menu.addItem(fries);

		//Add combos
		ComboItem burgerCombo = new ComboItem("Number 9 Large", 0.1D, FoodType.MAIN_MEAL, cheeseburger, fries, mediumSoda);
		ComboItem poutineCombo = new ComboItem("Poutine Combo", 0.15D, FoodType.MAIN_MEAL, poutine, largeSoda, chips);
		ComboItem smallCombo = new ComboItem("Snack Combo", 0.5D, FoodType.SNACK, chips, smallSoda, chocobar);
		ComboItem morningCombo = new ComboItem("Morning Trio", 0.05, FoodType.MAIN_MEAL, coffee, chips, peanuts);
		menu.addItem(burgerCombo); menu.addItem(poutineCombo); menu.addItem(smallCombo); menu.addItem(morningCombo);

		//Add special items
		SpecialItem specialTea = new SpecialItem("Special Tea", 0.15, tea);
		SpecialItem specialBurger = new SpecialItem("Expired Burger", 0.90, cheeseburger);
		SpecialItem specialCombo = new SpecialItem("Unsold Number 9", 0.50, burgerCombo);
		menu.addItem(specialTea); menu.addItem(specialBurger); menu.addItem(specialCombo);
	}

	public static Predicate<MenuItem> isPrice(double pPrice) {
		return c -> {
			System.out.println(c.getPrice());
			System.out.println(pPrice);
			return c.getPrice() == pPrice;
		};
	}

	public static Predicate<MenuItem> isInRange(double pLower, double pUpper){
		assert pLower <= pUpper;
		return c -> (pLower<=c.getPrice())&&(pUpper>=c.getPrice());
	}
}
