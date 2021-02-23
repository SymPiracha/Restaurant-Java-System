package activity7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests relating to {@link ComboItem}.
 * Important testing points: Construction, price calculation (and recalculation)
 */
public class TestComboItem {

	private FoodItem foodItem1;
	private final List<DietaryCategory> cats1 = Collections.emptyList();
	private FoodItem foodItem2;
	private final List<DietaryCategory> cats2 = List.of(
			DietaryCategory.get("black"),
			DietaryCategory.get("white"),
			DietaryCategory.get("green"));
	private FoodItem foodItem3;
	private final List<DietaryCategory> cats3 = List.of(
			DietaryCategory.get("green"),
			DietaryCategory.get("azure"),
			DietaryCategory.get("beige"));
	private FoodItem foodItem4;
	private final List<DietaryCategory> cats4 = List.of(
			DietaryCategory.get("beige"),
			DietaryCategory.get("crimson"),
			DietaryCategory.get("cyan"));
	private ComboItem comboItem1;
	private final double defaultDiscount = 0.1D;
	private final double defaultPrice = 100D;

	private final DietaryCategory dummyCat = DietaryCategory.get("a");
	private final FoodType dummyType = FoodType.get("a");

	private final Predicate<MenuItem> blueChooser = menuItem -> {
		List<DietaryCategory> list = Arrays.asList(
				DietaryCategory.get("azure"),
				DietaryCategory.get("teal"),
				DietaryCategory.get("cyan"));
		for (DietaryCategory s : menuItem.getDietaryCategories()) {
			if (list.contains(s)) {
				return true;
			}
		}
		return false;
	};

	@BeforeEach
	public void init() {
		//Illegally reinitialize fresh FoodItem instances for each test.
		try {
			Constructor<FoodItem> cstr =
					FoodItem.class.getDeclaredConstructor(String.class,
							double.class, FoodType.class, Collection.class);

			foodItem1 = cstr.newInstance("Item1", defaultPrice, FoodType.DRINK, cats1);
			foodItem2 = cstr.newInstance("Item2", defaultPrice, FoodType.SNACK, cats2);
			foodItem3 = cstr.newInstance("Item3", defaultPrice, FoodType.SNACK, cats3);
			foodItem4 = cstr.newInstance("Item4", defaultPrice, FoodType.MAIN_MEAL, cats4);
			comboItem1 = new ComboItem("TestCombo", 0.1D, dummyType, foodItem1, foodItem2, foodItem3, foodItem4);
		} catch (Exception ignored) {}
	}

	/**
	 * Tests that the constructor rejects any and all null values.
	 */
	@Test
	public void testConstructorNull() {
		//Completely Null varargs
		assertThrows(AssertionError.class, () -> new ComboItem("a", 0D, dummyType, null));
		//One of the varargs null
		assertThrows(AssertionError.class, () -> new ComboItem("a", 0D, dummyType, foodItem1, null, foodItem2));
		//Null name
		assertThrows(AssertionError.class, () -> new ComboItem(null, 0D, dummyType, foodItem1));
		//Null food type
		assertThrows(AssertionError.class, () -> new ComboItem("a", 0D, null, foodItem1));
		//Invalid discount bound
		assertThrows(AssertionError.class, () -> new ComboItem("a", -0.1D, dummyType, foodItem1));
		//Invalid discount bound
		assertThrows(AssertionError.class, () -> new ComboItem("a", 1.1D, dummyType, foodItem1));
	}

	/**
	 * Test if changing the discount changes the price.
	 */
	@Test
	public void testDiscount() {
		double totalPrice = foodItem1.getPrice() + foodItem2.getPrice() + foodItem3.getPrice() + foodItem4.getPrice();

		assertTrue(Util.closeEnough(totalPrice * (1D - defaultDiscount), comboItem1.getPrice()));

		comboItem1.setDiscount(0.99D);
		assertTrue(Util.closeEnough(totalPrice * (1D - 0.99D), comboItem1.getPrice()));

	}

	/**
	 * Test getting an aggregate list of diet cats.
	 */
	@Test
	public void testDietCats() {
		Set<DietaryCategory> dietcats = new HashSet<>();
		dietcats.addAll(cats1);
		dietcats.addAll(cats2);
		dietcats.addAll(cats3);
		dietcats.addAll(cats4);

		Collection<DietaryCategory> gottenCats = comboItem1.getDietaryCategories();

		//All present and only present once.
		for (DietaryCategory s : dietcats) {
			int count = 0;
			for (DietaryCategory s1 : gottenCats) {
				if (s1.equals(s)) {
					count++;
				}
			}
			assertEquals(1, count);
		}
	}

	/**
	 * Tests bulk remover predicate.
	 */
	@Test
	public void testBulkRemover() {
		comboItem1.removeItem(blueChooser);
		List<MenuItem> items = new ArrayList<>(comboItem1.getItems());

		assertTrue(items.contains(foodItem1));
		assertTrue(items.contains(foodItem2));
		assertFalse(items.contains(foodItem3));
		assertFalse(items.contains(foodItem4));
	}

	/**
	 * Tests normal remover.
	 */
	@Test
	public void testNormalRemover() {
		comboItem1.removeItem(foodItem1);
		assertFalse(comboItem1.getItems().contains(foodItem1));
	}

	/**
	 * Tests adder.
	 */
	@Test
	public void testAdd() {
		FoodItem f1 = FoodItem.get("toAdd1", 0.5D, dummyType, dummyCat);
		FoodItem f2 = FoodItem.get("toAdd2", 0.5D, dummyType, dummyCat);
		comboItem1.addItem(f1, f2);

		List<MenuItem> items = new ArrayList<>(comboItem1.getItems());
		assertTrue(items.contains(f1));
		assertTrue(items.contains(f2));
	}

	/**
	 * Test the description method of comboItem.
	 */
	@Test
	public void testDescription() {
		double totalPrice = foodItem1.getPrice() + foodItem2.getPrice() + foodItem3.getPrice() + foodItem4.getPrice();
		String test_output = String.format("[TestCombo which includes the following: Item1, Item2, Item3, Item4. Original Price: $ %.2f. Discounted Price: $%.2f]", totalPrice, comboItem1.getPrice());

		assertEquals(test_output, comboItem1.description());
	}

	/**
	 * Tests equality and hashcode.
	 */
	@Test
	public void testEquals() {
		//Exactly the same
		ComboItem toCompare = new ComboItem("TestCombo", 0.1D, dummyType, foodItem1, foodItem2, foodItem3, foodItem4);
		assertEquals(toCompare, comboItem1);
		assertEquals(toCompare.hashCode(), comboItem1.hashCode());

		//Different item order
		ComboItem toCompare2 = new ComboItem("TestCombo", 0.1D, dummyType, foodItem2, foodItem1, foodItem3, foodItem4);
		assertNotEquals(toCompare2, comboItem1);
		assertNotEquals(toCompare2.hashCode(), comboItem1.hashCode());

		//Change totally equal instance slightly
		toCompare.setDiscount(0.0100000001D);
		assertNotEquals(toCompare, comboItem1);
		assertNotEquals(toCompare.hashCode(), comboItem1.hashCode());
	}
	
	
	@Test
	public void testDescription2() {
		ComboItem comboItem = new ComboItem("Combo", 0.1D, FoodType.get("testType"), foodItem1, foodItem2);
		assertEquals("[Combo which includes the following: Item1, Item2. Original Price: $ 200.00. Discounted Price: $180.00]", comboItem.description());
	}

}
