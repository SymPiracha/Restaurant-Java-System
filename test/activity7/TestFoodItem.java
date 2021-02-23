package activity7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pertaining to {@link FoodItem}. Trivial getters and setters are
 * ignored.
 * Important testing points: Flyweight instantiation, equality, dietary cat
 *                           management.
 */
public class TestFoodItem {

	@BeforeEach
	public void init() {

	}

	/**
	 * Tests that the only flyweight criteria is the name.
	 */
	@Test
	public void testConstructor() {
		double price = 100D;
		FoodType type = FoodType.MAIN_MEAL;

		FoodItem fi1 = FoodItem.get("coffee", 100D, type, DietaryCategory.get("mushroom"));
		FoodItem fi2 = FoodItem.get("coffee", 123D, FoodType.DRINK, DietaryCategory.get("raviolis"));
		assertSame(fi1, fi2);

		//Make sure coffee fields not overwritten
		assertEquals(fi2.getName(), "coffee");
		assertTrue(Util.closeEnough(fi2.getPrice(), price));
		assertEquals(fi2.getFoodType(), type);
	}

	/**
	 * Tests that dietary categories are properly added (both constructor
	 * and methods)
	 */
	@Test
	public void testDietaryCatConstructor() {
		Set<DietaryCategory> catsToAdd = new HashSet<>(Arrays.asList(
				DietaryCategory.get("1"),
				DietaryCategory.get("2"),
				DietaryCategory.get("lOwErCaMeLcAsE"),
				DietaryCategory.get("testing2")));

		//Constructor
		FoodItem fi = FoodItem.get("kangaroo", 11111D, FoodType.DRINK, new ArrayList<>(catsToAdd));

		Set<DietaryCategory> gottenCats = Util.getDietCats(fi);

		//Test that all were converted to lowercase
		assertTrue(gottenCats.contains(DietaryCategory.get("lowercamelcase")));

		//Test that all are present
		for (DietaryCategory s : catsToAdd) {
			assertTrue(gottenCats.contains(s));
		}

		//Test duplicates
		DietaryCategory[] list = new DietaryCategory[100];
		for (int i = 0; i < 100; i++) {
			list[i] = DietaryCategory.get("Somecat");
		}
		FoodItem fi2 = FoodItem.get("testing2", 11111D, FoodType.DRINK, list);

		gottenCats = Util.getDietCats(fi2);

		int count = 0;
		for (DietaryCategory s : gottenCats) {
			if (s.equals(DietaryCategory.get("somecat"))) {
				count++;
			}
		}
		assertEquals(1, count);

	}

	@Test
	public void testDietaryCatMethod() {
		FoodItem fi = FoodItem.get("wegpoh9iua", 11111D, FoodType.DRINK);

		Set<DietaryCategory> gottenCats = Util.getDietCats(fi);

		//Test add method
		DietaryCategory[] list = new DietaryCategory[]{
				DietaryCategory.get("UpperCamelCase"),
				DietaryCategory.get("3"),
				DietaryCategory.get("4"),
				DietaryCategory.get("5")};

		fi.addDietaryCategories(list);

		assertEquals(list.length, gottenCats.size()); //Same number added
		for (DietaryCategory s : list) {
			assertTrue(gottenCats.contains(s)); //All added
		}

		//Test duplicate check.
		for (int i = 0; i < 100; i++) {
			fi.addDietaryCategories(DietaryCategory.get("Somecat"));
		}

		int count = 0;
		for (DietaryCategory s : gottenCats) {
			if (s.equals(DietaryCategory.get("somecat"))) {
				count++;
			}
		}
		assertEquals(1, count);
	}

	@Test
	public void testRemoveDietCat() {
		FoodItem fi = FoodItem.get("desgrbfniujb", 1999D, FoodType.SNACK);
		Set<DietaryCategory> gottenCats = Util.getDietCats(fi);
		assertTrue(gottenCats.isEmpty());

		//Try removing while nothing is there
		assertDoesNotThrow(() -> fi.removeDietaryCategories(DietaryCategory.get("gnwueroi"), DietaryCategory.get("gagw"), DietaryCategory.get("esriophgoi")));

		gottenCats.addAll(Arrays.asList(
				DietaryCategory.get("123"),
				DietaryCategory.get("456"),
				DietaryCategory.get("house")));
		//Try removing something that was there.
		fi.removeDietaryCategories(DietaryCategory.get("123"));
		assertFalse(gottenCats.contains(DietaryCategory.get("123")));

		//Try removing with different caps
		fi.removeDietaryCategories(DietaryCategory.get("HOUSE"));
		assertFalse(gottenCats.contains(DietaryCategory.get("house")));
	}

	/**
	 * Tests that equals works for two different instances with the same name
	 * (illegal configuration).
	 */
	@Test
	public void testEquals() {
		FoodItem fi1 = FoodItem.get("nintendo switch", 1000D, FoodType.get("mango"), DietaryCategory.get("void"));
		FoodItem fi2;
		try {
			Constructor<FoodItem> constr = FoodItem.class.getDeclaredConstructor(String.class, double.class, FoodType.class, Collection.class);
			fi2 = constr.newInstance("nintendo switch", 123123D, FoodType.get("orange"), Collections.emptyList());
			//Make sure the illegally procured instance is not the same.
			assertNotSame(fi1, fi2);
			//Make sure the equality check works for two separate instances.
			assertEquals(fi1, fi2);
		} catch (Exception ignored) { }
	}

	/**
	 * Tests hashcode functionality.
	 */
	@Test
	public void testHashCode() throws Exception {
		Random rand = new Random();
		Constructor<FoodItem> constructor = FoodItem.class.getDeclaredConstructor(String.class, double.class, FoodType.class, Collection.class);

		for (int i = 0; i < 20; i++) {
			double name = rand.nextDouble();
			FoodItem fi1 = FoodItem.get(Double.toString(name), 1999D, FoodType.get("mango"), DietaryCategory.get("helicopter"));
			FoodItem fi2 = constructor.newInstance(Double.toString(name), 10000D, FoodType.get("something"), Collections.emptyList());
			assertEquals(fi1, fi2);
			assertEquals(fi1.hashCode(), fi2.hashCode());
		}
	}
}
