package activity7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pertaining to {@link SizeableItem}. Trivial getters are ignored.
 * Important testing points: Construction, price calculation, changing size.
 */
public class TestSizeableItem {

//	@BeforeEach
//	public void reset() {
//		try {
//			Field field = SizeableItem.class.getDeclaredField("aInstances");
//			field.setAccessible(true);
//			List<SizeableItem> instanceList = (List<SizeableItem>) field.get(null);
//			instanceList.clear();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testConstructorFlyweight() {
//		SizeableItem si1 = SizeableItem.get(Size.MEDIUM, "Coke", 100D, FoodType.DRINK.name, new ArrayList<>());
//		SizeableItem si2 = SizeableItem.get(Size.MEDIUM, "Coke", 100D, FoodType.DRINK.name, new ArrayList<>());
//		assertSame(si1, si2);
//		assertEquals(si1, si2);
//		assertEquals(si1.hashCode(), si2.hashCode());
//
//		SizeableItem si3 = SizeableItem.get(Size.MEDIUM, "Pepsi", 100D, FoodType.DRINK.name, new ArrayList<>());
//		assertNotSame(si1, si3);
//		assertNotEquals(si1, si3);
//		assertNotEquals(si1.hashCode(), si3.hashCode());
//	}

//	@Test
//	public void testConstructorFlyweightCriteria() {
//		//Different different fields, same name
//		SizeableItem si1 = SizeableItem.get(Size.MEDIUM, "Coke1", 200D, FoodType.SNACK.name, new ArrayList<>());
//		SizeableItem si2 = SizeableItem.get(Size.SMALL, "Coke1", 100D, FoodType.SNACK.name, Arrays.asList("a", "b", "c"));
//		assertSame(si1, si2);
//	}

	/**
	 * Tests that the item was initialized with proper default values.
	 */
	@Test
	public void testPrice() {
		double basePrice = 100D;
		SizeableItem item = new SizeableItem(Size.MEDIUM, "Coke", basePrice, FoodType.DRINK, new ArrayList<>());
		//Test default prices
		for (Size size : Size.values()) {
			item.setSize(size);
			assertTrue(Util.closeEnough(size.defaultModifier * basePrice, item.getPrice()));
		}
	}

	/**
	 * Tests whether changing a price modifier actually changes the price of the
	 * item if it is that size.
	 */
	@Test
	public void testChangePrice() {
		double basePrice = 100D;
		SizeableItem item = new SizeableItem(Size.MEDIUM, "Coke", basePrice, FoodType.DRINK, new ArrayList<>());

		//Custom price modifiers
		double customSmall = 0.5D;
		double customLarge = 3.0D;
		item.changeSizeModifier(Size.SMALL, customSmall);
		item.changeSizeModifier(Size.LARGE, customLarge);

		item.setSize(Size.SMALL);
		assertTrue(Util.closeEnough(basePrice * customSmall, item.getPrice()));
		item.setSize(Size.LARGE);
		assertTrue(Util.closeEnough(basePrice * customLarge, item.getPrice()));
	}

	@Test
	public void testEquals() {
		SizeableItem sizeableItem = new SizeableItem(Size.MEDIUM, "Coke", 100D, FoodType.DRINK, new ArrayList<>());
		FoodItem foodItem = FoodItem.get("Coke", 100D, FoodType.DRINK, new ArrayList<>());
		assertNotEquals(sizeableItem, foodItem);
	}

	//TODO
	@Test
	public void testDescription() {
		SizeableItem sizeableItem = new SizeableItem(Size.LARGE, "Coke", 100D, FoodType.DRINK, new ArrayList<>());
		assertEquals("[Coke Large $130.00]", sizeableItem.description());
		sizeableItem.setSize(Size.SMALL);
		assertEquals("[Coke Small $70.00]", sizeableItem.description());

	}

}
