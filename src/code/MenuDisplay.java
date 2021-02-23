package activity7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

/**
 * GUI for the menu.
 *
 * Configurations represent a set of 3 panels. New
 * configurations can be instantiated and added to the configurations list to
 * register them.
 *
 * This class will automatically generate a button to switch to each registered
 * configuration.
 *
 * @see Configuration
 * @see Panel
 */
public class MenuDisplay extends Application
{

	private static final int WIDTH = 1000;
	private static final int MIN_WIDTH = 500;
	private static final int HEIGHT = 600;
	private static final int MIN_HEIGHT = 300;


	/**
	 * All registered configurations. ADD CONFIGURATION HERE TO REGISTER IT.
	 * <n>Requirement 4.12<n/>
	 */
	private final List<Configuration> aConfigurations = new ArrayList<>(
			Arrays.asList(
					Configuration.foodTypeConfiguration,
					Configuration.dietaryConfiguration,
					Configuration.priceConfiguration,
					Configuration.compositeConfiguration
			));


	/** The currently selected configuration. */
	private Configuration aInFocusConfig;

	/**
	 * Vertical box containing the configuration, the selection boxes and the
	 * add/remove buttons.
	 * Slot 0: <b>CONFIGURATION</b>.
	 * Slot 1: Configuration choice boxes.
	 * Slot 2: Add/Remove boxes.
	 */
	private VBox vbox;
    
    @Override
    public void start(Stage pStage)
	{

		//Creating the configuration (this will get overwritten)
		aInFocusConfig = aConfigurations.get(0);

		//Adding the ADD/REMOVE buttons along a horizontal box.
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(createAddButton(), createRemoveButton());

		//Add the configuration, controls, and buttons to the vertical box.
		vbox = new VBox(
				aInFocusConfig,
				createControl(aConfigurations),
				buttonBox
		);

		//Ensuring all registered configurations can resize.
		for (Configuration cfg : aConfigurations)
		{
			VBox.setVgrow(cfg, Priority.ALWAYS);
		}

		//Finally displaying.
		Scene scene = new Scene(vbox);
        pStage.setScene(scene);
        pStage.setWidth(WIDTH);
        pStage.setMinWidth(MIN_WIDTH);
        pStage.setHeight(HEIGHT);
        pStage.setMinHeight(MIN_HEIGHT);
        pStage.show();

        //FIXME SET TO FALSE TO REMOVE THE DEMO ITEMS
		Helper.populateWithDummies();
	}

	/**
	 * @return A button that will open a window to create a new FoodItem.
	 */
	private Button createAddButton()
	{
		Button addButton = new Button("Add");
		//Adding button functionality
		addButton.setOnMouseClicked(mouseEvent -> ItemCreatorWindow.createNewWindow());
		return addButton;
	}

	/**
	 * @return A button that will remove whatever item is currently selected
	 *         by the configuration in focus.
	 */
	private Button createRemoveButton()
	{
		Button removeButton = new Button("Remove");
		removeButton.setOnMouseClicked((mouseEvent) -> {
			Optional<MenuItem> tmp = aInFocusConfig.getSelected();
			if (tmp.isEmpty()) {
				return;
			}
			MenuItem subject = aInFocusConfig.getSelected().get();
			Menu.instance().removeItem(subject);
		});
		return removeButton;
	}

	/**
	 * Creates toggleable buttons along an horizontal box that allow switching
	 * between every registered configuration. Upon clicking on them, the
	 * configuration in focus will be changed. The 1st item will be selected
	 * by default at the start.
	 * @param pConfigurations All registered configurations.
	 * @pre pConfigurations.size() > 0;
	 */
    private HBox createControl(Collection<Configuration> pConfigurations)
	{
    	assert pConfigurations != null && pConfigurations.size() > 0;
    	pConfigurations.forEach(x -> { assert x != null; });

    	//Create box
    	HBox control = new HBox();
    	control.setPadding(new Insets(5));
        control.setAlignment(Pos.CENTER);
    	ToggleGroup group = new ToggleGroup();

    	//Create a button for every registered configuration.
        for( Configuration cfg : pConfigurations )
        {
        	RadioButton button = new RadioButton(cfg.getName());
        	button.setPadding(new Insets(5));
        	button.setToggleGroup(group);

        	//Replace the displayed and focussed on configuration.
        	button.setOnMouseClicked(mouseEvent -> {
				vbox.getChildren().set(0, cfg);
				aInFocusConfig = cfg;
			});

            control.getChildren().add(button);
        }
        ((RadioButton) control.getChildren().get(0)).setSelected(true);
        return control;
    }

	/**
	 * Launches the application.
	 * @param pArgs This program takes no argument.
	 */
	public static void main(String[] pArgs)
	{
		launch(pArgs);
	}
}
