package activity7;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestMenu {
	
	private Menu aMenu = Menu.instance();
	private FoodItem f1 = FoodItem.get("soup", 100D, FoodType.MAIN_MEAL, DietaryCategory.get("mushroom"));
	private FoodItem f2 = FoodItem.get("coffee", 123D, FoodType.DRINK, DietaryCategory.get("raviolis"));
	private FoodItem f4 = FoodItem.get("cake", 110D, FoodType.SNACK, DietaryCategory.get("fave"));
	private FoodItem f3 = FoodItem.get("chips", 10D, FoodType.SNACK, DietaryCategory.VEGAN);
	private ComboItem comboItem = new ComboItem("Combo", 0.1D, FoodType.get("testType"), f1, f2);
	
	
	
	public List<MenuItem> getItems() {
		try {
		    Field field  = Menu.class.getDeclaredField("aItems");
			field.setAccessible(true);
			return (List<MenuItem>) field.get(aMenu);
		}
		catch (ReflectiveOperationException e) {
			fail();
			return null;
			
		}	
	}

    @BeforeEach
    public void init() {

    	getItems().clear();
    }
    
    @Test
    public void testAddFoodItem() {
    	
    	aMenu.addItem(f1);
    	
    	assertEquals(1, getItems().size());
    }
    	
    @Test
    public void testComboItem() {
    	
    	aMenu.addItem(comboItem);
    	
    	assertEquals(1, getItems().size());
    	
    }
    
    @Test	
    public void testAddTwoItemsTwice() {

    	aMenu.addItem(f1);
    	aMenu.addItem(f1);
    	
    	assertEquals(1, getItems().size());
    	
    }
    
    @Test
    public void testRemoveItem() {
    	aMenu.addItem(f1);
    	aMenu.removeItem(f1);
    	
    	assertEquals(0, getItems().size());
    }
    
    
    @Test
    public void testRemoveItemNotInMenu() {
    	
    	aMenu.addItem(f2);
    	
    	assertEquals(1, getItems().size());
    	
    	aMenu.removeItem(comboItem);
   	
    	assertEquals(1, getItems().size());
    	
    }
    
    
    
    @Test
    public void testPriceFilter() {
    	aMenu.addItem(f2);
    	aMenu.addItem(f1);
    	
    	assertEquals(1, aMenu.filterMenuItems(Helper.isPrice(123)).size());
    	assertEquals(0, aMenu.filterMenuItems(Helper.isPrice(200)).size());
    }
    
    @Test
    public void testInRangeFilter() {
 
    	aMenu.addItem(f2);
    	aMenu.addItem(f1);
    	aMenu.addItem(f3);
    	
    	assertEquals(2, aMenu.filterMenuItems(Helper.isInRange(100,123)).size());

    	aMenu.addItem(f4);
    	
    	assertEquals(3, aMenu.filterMenuItems(Helper.isInRange(100,123)).size());
    	
    }
    
    @Test
    public void testIsFoodCategory() {
    	
    	aMenu.addItem(f1);
    	
    	assertEquals(1, aMenu.filterMenuItems(Helper.isFoodType(FoodType.MAIN_MEAL)).size());
    	
    	aMenu.addItem(f3);
    	aMenu.addItem(f4);
    	
    	assertEquals(2, aMenu.filterMenuItems(Helper.isFoodType(FoodType.SNACK)).size());
    	
    }
    
    @Test
    public void testIsDietaryCategory() {
    	
    	assertEquals(0, aMenu.filterMenuItems(Helper.isDietaryCategory(DietaryCategory.get("vegan"))).size());
    	
    	aMenu.addItem(f3);
    	
    	System.out.println(f3.getDietaryCategories());
    	
    	assertEquals(1, aMenu.filterMenuItems(Helper.isDietaryCategory(DietaryCategory.VEGAN)).size());
    	
    	
    }

    @Test 
    public void testMultipleFilters() {
    	
    	aMenu.addItem(f1);
    	aMenu.addItem(f2);
    	aMenu.addItem(f3);
    	
    	List<Predicate<MenuItem>> filters = new ArrayList<Predicate<MenuItem>>();
    	
    	filters.add(Helper.isInRange(100, 123));
    	filters.add(Helper.isFoodType(FoodType.SNACK));
    	
    	
    	aMenu.filterMenuItems(filters);
    	
    }
   
    
    
    

    
    
    
    
    

}
