package activity7;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Testing utility class. Ignore this.
 */
@SuppressWarnings("checkstyle")
public class Util {

	private Util() {
		throw new IllegalArgumentException("Non-instantiable class.");
	}

	/**
	 * Compares two double floating point numbers representing a price
	 * and returns true if they are close enough (to the cent). This method
	 * is to account for floating point inaccuracies.
	 * @param d1 A price.
	 * @param d2 Another price.
	 * @return Whether the two prices are within one centile of each other.
	 */
	public static boolean closeEnough(double d1, double d2) {
		boolean okay = (Math.abs(d1 - d2) < 0.01D);
		if (!okay) {
			System.err.printf("Expected: <%2f> but was: <%2f>", d1, d2);
		}
		return okay;
	}

	/**
	 * General helper to get fields with reflection.
	 * @param source Source object.
	 * @param name Name of the field.
	 * @return Field, needs to be cast down.
	 */
	public static Object getField(Object source, String name) {
		try {
			Field field = source.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(source);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * General helper to get methods with reflection.
	 * @param source Source object.
	 * @param name Name of the method.
	 * @return Method.
	 */
	public static Method getMethod(Class source, String name) {
		try {
			Method method = source.getDeclaredMethod(name);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get all items contained within a menu.
	 * @param pMenu Menu to get from.
	 * @return List of all MenuItem in said Menu.
	 */
	@SuppressWarnings("unchecked")
	public static List<MenuItem> getMenuItems(Menu pMenu) {
		return (List<MenuItem>) getField(pMenu, "aItems");
	}

	public static Set<DietaryCategory> getDietCats(FoodItem foodItem) {
		return (Set<DietaryCategory>) getField(foodItem, "aDietaryCategories");
	}

	/**
	 * Get all MenuItems contained within a ComboItem.
	 * @param pComboItem ComboItem to get from.
	 * @return List of all MenuItems in said ComboItem.
	 */
	@SuppressWarnings("unchecked")
	public static List<MenuItem> getMenuItems(ComboItem pComboItem) {
		return (List<MenuItem>) getField(pComboItem, "aItems");
	}

	/**
	 * Checks that two lists have the exact same elements.
	 * @param list1 List to check.
	 * @param list2 Another list to check.
	 * @return True if all elements are == (not just equals) to one another.
	 *         False otherwise.
	 */
	public static boolean refEquals(List list1, List list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i) != list2.get(i)) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Checks that a list contains a specific reference. (Not just equals).
	 * @param list List to search.
	 * @param search Reference to search.
	 * @return True if reference contained, false otherwise.
	 */
	public static boolean containsRef(List list, Object search) {
		for (Object o : list) {
			if (o == search) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints a menu "as an array".
	 * @param menu Menu to print.
	 */
	public static void printMenu(Menu menu) {
		System.out.print("[");
		for (MenuItem menuItem : menu) {
			try {
				System.out.print(menuItem.getName());
			} catch (Exception e) {
				System.out.print(menuItem.description()); //Fallback
			}
			System.out.print(", ");
		}
		System.out.println("]");
	}

}