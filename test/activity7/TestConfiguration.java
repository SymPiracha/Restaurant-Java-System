package activity7;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestConfiguration {

    private Configuration defaultConfig;
    final JFXPanel fxPanel = new JFXPanel();
    private Panel leftPanel;
    private Panel centrePanel;
    private Panel rightPanel;
    private List<MenuItem> menuItems;

    @BeforeEach
    public void init() {
        Helper.populateWithDummies();
        leftPanel = new Panel("Drinks",
                FoodType.DRINK.getPredicate(),
                Helper.sortByDescription());
        centrePanel = new Panel("Snacks",
                FoodType.SNACK.getPredicate(),
                Helper.sortByDescription());
        rightPanel = new Panel("Main meal",
                FoodType.MAIN_MEAL.getPredicate(),
                Helper.sortByPrice());
        defaultConfig = new Configuration(
                "Default Configuration",
                leftPanel,
                centrePanel,
                rightPanel
        );
    }

    /**
     * Tests user's ability to differentiate configurations by name.
     */
    @Test
    public void testDefaultConfigName() {
        assertEquals(defaultConfig.getName(), "Default Configuration");
    }

    /**
     * Tests that no panel is selected in a default configuration.
     */
    @Test
    public void testDefaultNotSelected() {
        Configuration defaultConfig = new Configuration(
                "Default Configuration",
                leftPanel,
                centrePanel,
                rightPanel
        );
        assertEquals(defaultConfig.getSelected(), Optional.empty());
    }

    /**
     * Tests that items are successfully added back to the list view
     * after aListView.getItems().clear() has been called.
     */
    @Test
    public void testRegeneratePanel() {
        try {
            centrePanel.menuUpdated();
            java.lang.reflect.Field privateView = centrePanel.getClass().getDeclaredField("aListView");
            privateView.setAccessible(true);
            menuItems = ((ListView<MenuItem>) privateView.get(centrePanel)).getItems();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(menuItems);
    }

    /**
     * Tests that the items in a panel are successfully in order of
     * increasing price in the sortByPrice configuration.
     */
    @Test
    public void testSortByPrice() {
        try {
            rightPanel.menuUpdated();
            java.lang.reflect.Field privateView = rightPanel.getClass().getDeclaredField("aListView");
            privateView.setAccessible(true);
            menuItems = ((ListView<MenuItem>) privateView.get(rightPanel)).getItems();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assertFalse(menuItems.get(0).getPrice() > menuItems.get(1).getPrice());
    }
}
