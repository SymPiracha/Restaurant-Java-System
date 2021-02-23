package activity7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class TestSpecialItem
{
	
	private final List<DietaryCategory> diet_one = new ArrayList<>();
	private FoodItem foodItem1;
	private FoodItem foodItem2;
	
	@BeforeEach
	public void init() 
	{
		diet_one.add(DietaryCategory.VEGAN);
		diet_one.add(DietaryCategory.GLUTEN_FREE);
		foodItem1 = FoodItem.get("Orange Juice", 5, FoodType.DRINK, diet_one);
		foodItem2 = new SizeableItem(Size.MEDIUM, "pepsi", 6, FoodType.DRINK, diet_one);
	}
	
	@Test
	public void test_special_fooditem_desciption()
	{
		String expected = "[Special's juice has been put on Special. Original Price: $ 5.00, Discounted Price:  $4.50]";
		SpecialItem special = new SpecialItem("Special's juice",0.1D,foodItem1);
		assertEquals(expected, special.description());
	}
	
	@Test
	public void test_new_price()
	{
		SpecialItem special = new SpecialItem("Special's juice",0.1D,foodItem1);
		assertEquals(special.getPrice(), 4.5);
	}
	
	@Test
	public void testEquals() {
		//Exactly the same
		SpecialItem special1 = new SpecialItem("Special's juice",0.1D,foodItem1);
		SpecialItem special2 = new SpecialItem("Special's juice",0.1D,foodItem1); 
		assertEquals(special1.hashCode(), special2.hashCode());
	}
	
	@Test
	public void test_original_price()
	{
		SpecialItem s1 = new SpecialItem("Special's juice",0.1D,foodItem1);
		try {
			java.lang.reflect.Method method = SpecialItem.class.getDeclaredMethod("originalPrice");
			method.setAccessible(true);
			double originalPrice = (double) method.invoke(s1);
			assertEquals(originalPrice, 5);
		}
		catch(ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void test_combo_speical_price()
	{
		ComboItem combo = new ComboItem("Drinking Set", 0.1D, FoodType.get("Combo"), foodItem1, foodItem2);
		SpecialItem special = new SpecialItem("Special Kid and Parent Drink Set", 0.1D, combo);

		assertEquals(combo.getPrice(), 9.9);
		assertEquals(special.getPrice(), 8.91);
	}

	@Test
	public void test_combo_special_message()
	{
		String test_output = "[Special Kid and Parent Drink Set has been put on Special. Original Price: $ 9.90, Discounted Price:  $8.91]";
		ComboItem combo = new ComboItem("Drinking Set", 0.1D, FoodType.get("Combo"), foodItem1, foodItem2);
		SpecialItem special = new SpecialItem("Special Kid and Parent Drink Set", 0.1D, combo);

		assertEquals(special.description(), test_output);
	}
	
}
