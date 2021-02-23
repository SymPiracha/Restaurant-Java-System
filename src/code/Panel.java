package activity7;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Class representing one of the 3 panels visible at any given time.
 * Contains the logic to filter, then sort all menu items before displaying
 * them.
 *
 * All panels are permanently registered with the Menu even if they are not
 * currently displayed.
 */
public class Panel extends VBox implements Observer
{

    protected static final Panel showAllPanel = new Panel(
            "All Items",
            (x) -> true,
            (x, y) -> 0);

    private static final String STYLE =
            "-fx-pref-height: 300px; -fx-border-width: 1; -fx-border-color: black;"
                    + "-fx-background-color: lightgrey; -fx-padding: 5px; -fx-alignment: top-center";

    // Panel configuration
    private final Comparator<MenuItem> aSortingStrategy;
    private final Predicate<MenuItem> aFilteringStrategy;

    //  List view of menu items in a panel
    private final ListView<MenuItem> aListView = new ListView<>();

    /**
     * Public constructor to create a Menu panel.
     * @param pLabel Label (Name) of this panel.
     * @param pFilteringStrategy A predicate used to filter items to show.
     * @param pSortingStrategy A comparator for sorting items.
     */
    public Panel(String pLabel, Predicate<MenuItem> pFilteringStrategy,
                 Comparator<MenuItem> pSortingStrategy)
    {
        Menu.instance().addObserver(this);

        //Setting up this panel's name and style.
        this.setStyle(STYLE);
        this.setFillWidth(true);
        Label aLabel = new Label(pLabel);
        aLabel.setStyle("-fx-font-weight: bold");

        //Panel configuration
        aSortingStrategy = pSortingStrategy;
        aFilteringStrategy = pFilteringStrategy;

        //Build the ListView
        aListView.setMaxHeight(Double.MAX_VALUE);
        regeneratePanel();

        //Add name and ListView to panel.
        this.getChildren().add(aLabel);
        this.getChildren().add(aListView);

        //Ensure the list resizes correctly.
        VBox.setVgrow(aListView, Priority.ALWAYS);
    }


    /**
     * Updates a panel based on Predicate filter and item display sort.
     */
    private void regeneratePanel()
    {
        // Clear list view items to repopulate from Menu
        aListView.getItems().clear();

        // Populate menu items into GUI
        Menu.instance().stream()
                .sorted(aSortingStrategy)
                .filter(aFilteringStrategy)
                .forEach(menuItem -> aListView.getItems().add(menuItem));
    }

    /**
     * Override of menuUpdated in Observer.
     * Uses the pull data flow strategy.
     */
    @Override
    public void menuUpdated() {
        regeneratePanel();
    }

    /**
     * @return Item that is currently selected. Returns empty if none is
     *         currently selected.
     */
    public Optional<MenuItem> getSelected()
    {
        return Optional.ofNullable(aListView.getSelectionModel().getSelectedItem());
    }
}



