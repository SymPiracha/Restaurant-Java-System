package activity7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ListView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPanel
{
	private Menu menu = Menu.instance();
	private Panel panel;
	private List<DietaryCategory> diet_one = new ArrayList<>(List.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE));
	private MenuItem juice = FoodItem.get("Orange Juice", 5, FoodType.DRINK, diet_one);
	private MenuItem pepsi = new SizeableItem(Size.MEDIUM, "pepsi", 6, FoodType.DRINK, diet_one);
	private List<MenuItem> items;
	

	final JFXPanel fxPanel = new JFXPanel();

	@BeforeEach
	public void init()
	{
		panel = new Panel("label", Helper.isFoodType(FoodType.DRINK), Helper.sortByPrice());
	}
	/**
	 * Tests that when a panel 
	 */
	@Test
	public void test_addedToObservers()
	{
		List<Observer> listofObservers = new ArrayList<>();
		try
		{
			java.lang.reflect.Field privatePanels = Menu.class.getDeclaredField("aObservers");
			privatePanels.setAccessible(true);
			listofObservers = (List<Observer>) privatePanels.get(Menu.instance());
			assertTrue(listofObservers.contains(panel));
		}
		catch (ReflectiveOperationException e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * Tests that when an item is added to a menu, the panel's listview gets updated.
	 */
	@Test
	public void test_updateMenuPanel() {
		menu.addItem(juice);
		
		try {
			java.lang.reflect.Field privateItems = Panel.class.getDeclaredField("aListView");
			privateItems.setAccessible(true);
			items = ((ListView<MenuItem>) privateItems.get(panel)).getItems();
			} catch(ReflectiveOperationException e) {
			e.printStackTrace();
			}
		assertTrue(items.contains(juice));
	}
	/**
	 * Tests when an item is removed, the panel's listview gets updated.
	 */
	 @Test
	 public void test_itemRemoved() {
		 menu.addItem(juice);
		 menu.removeItem(juice);
		 try {
				java.lang.reflect.Field privateItems = Panel.class.getDeclaredField("aListView");
				privateItems.setAccessible(true);
				items = ((ListView<MenuItem>) privateItems.get(panel)).getItems();
				} catch(ReflectiveOperationException e) {
				e.printStackTrace();
				}
		 assertFalse(items.contains(juice));
	 }
	 /**
	  * Testing that sorting is happening correctly
	  */
	 @Test
	 public void test_sort(){
		 menu.addItem(juice);
		 menu.addItem(pepsi);
		 
		 
		 try {
				java.lang.reflect.Field privateItems = Panel.class.getDeclaredField("aListView");
				privateItems.setAccessible(true);
				items = ((ListView<MenuItem>) privateItems.get(panel)).getItems();
				} catch(ReflectiveOperationException e) {
				e.printStackTrace();
				}
		 try {
			 Comparator<MenuItem> priceCompare;
			 java.lang.reflect.Field privateCompare = Panel.class.getDeclaredField("aSortingStrategy");
			 privateCompare.setAccessible(true);
			 priceCompare = (Comparator<MenuItem>) privateCompare.get(panel);
			 Collections.sort(items, priceCompare);
			 boolean worked = false;
			 if(items.get(0)== juice && items.get(1)==pepsi) {
				 worked=true;
			 }
			 assertTrue(worked);
			 } catch(ReflectiveOperationException e) {
			 e.printStackTrace();
			 }
	
		
		 
	 }
}
