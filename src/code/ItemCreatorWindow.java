package activity7;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Self contained window that allows adding a {@link FoodItem}. Asks the user
 * to enter fields, converts them into strings, and creates a food item with
 * it.
 * @see FoodItem
 */
public class ItemCreatorWindow extends VBox {

	private ItemCreatorWindow() {
		//Slot 0: Item to create selector.
		//Slot 1: Creation window (ItemBuilder).

		List<ItemBuilder> builders = Arrays.asList(
				new FoodItemBuilder(),
				new SizeableItemBuilder(),
				new SpecialItemBuilder(),
				new ComboItemBuilder());

		this.setPadding(new Insets(20, 20, 20, 20));

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().add("FoodItem");
		comboBox.getItems().add("SizeableItem");
		comboBox.getItems().add("SpecialItem");
		comboBox.getItems().add("ComboItem");

		comboBox.setOnAction((event) -> {
			String selected = comboBox.getSelectionModel().getSelectedItem();
			System.out.println(selected);
			if ("FoodItem".equalsIgnoreCase(selected)) {
				getChildren().set(1, builders.get(0));
			}
			if ("SizeableItem".equalsIgnoreCase(selected)) {
				getChildren().set(1, builders.get(1));
			}
			if ("SpecialItem".equalsIgnoreCase(selected)) {
				getChildren().set(1, builders.get(2));
			}
			if ("ComboItem".equalsIgnoreCase(selected)) {
				getChildren().set(1, builders.get(3));
			}
		});
		comboBox.getSelectionModel().selectFirst();

		this.getChildren().add(comboBox);
		this.getChildren().add(builders.get(0));

	}

	public void close() {
		Stage stage = (Stage) this.getScene().getWindow();
		stage.close();
	}

	public static void createNewWindow() {
		Stage stage = new Stage();
		stage.setTitle("Create an item");
		stage.setScene(new Scene(new ItemCreatorWindow()));
		stage.show();
	}

	public static TextField createTextField(String label) {
		TextField nameField = new TextField();
		nameField.setPrefWidth(400);
		nameField.setPromptText(label);
		return nameField;
	}

	/**
	 * Represents a GUI element responsible of letting the client build an item.
	 */
	private abstract class ItemBuilder extends VBox {
		/** Create item and close window. */
		abstract void create();
		protected Button createAddButton() {
			Button addButton = new Button("Add");
			addButton.setOnMouseClicked((e) -> create());
			return addButton;
		}
		protected Button createRemoveButton() {
			Button removeButton = new Button("Cancel");
			removeButton.setOnMouseClicked((e) -> close());
			return removeButton;
		}
	}

	/** Generic {@link FoodItem} builder. */
	private class FoodItemBuilder extends ItemBuilder {
		private final TextField nameField;
		private final TextField priceField;
		private final TextField typeField;
		private final TextField categoryField;

		public FoodItemBuilder() {
			this.setPadding(new Insets(20, 20, 20, 20));
			this.setSpacing(10);

			//Enter name
			nameField = new TextField();
			nameField.setPrefWidth(400);
			nameField.setPromptText("Name");
			this.getChildren().add(nameField);

			//Enter price
			priceField = new TextField();
			priceField.setPromptText("Price");
			this.getChildren().add(priceField);

			//Enter type
			typeField = new TextField();
			typeField.setPromptText("Food Type");
			this.getChildren().add(typeField);

			//Enter categories
			categoryField = new TextField();
			categoryField.setPromptText("Categories (Separated by commas)");
			this.getChildren().add(categoryField);

			//Choice (Add and remove side-by-side)
			HBox decisionBox = new HBox();
			this.getChildren().add(decisionBox);

			//Add button
			decisionBox.getChildren().add(createAddButton());

			//Cancel button
			decisionBox.getChildren().add(createRemoveButton());
		}

		@Override
		public void create() {
			try {
				//Get fields
				String name = nameField.getText();
				double price = Double.parseDouble(priceField.getText());
				String type = typeField.getText();
				String[] categories = categoryField.getText().split(",");

				//Abort if empty arguments.
				if (name.equals("") || type.equals("")) {
					System.out.println("Name or food type invalid");
					throw new IllegalArgumentException();
				}

				//Create item
				FoodItem item;
				if (categories.length == 1 && categories[0].equals("")) {
					item = FoodItem.get(name, price, FoodType.get(type)); //No categories
				}
				else {
					List<DietaryCategory> cat = Arrays.stream(categories).
							map(DietaryCategory::get)
							.collect(Collectors.toList());
					item = FoodItem.get(name, price, FoodType.get(type), cat);
				}

				Menu.instance().addItem(item);
			} catch (Exception e) {
				e.printStackTrace();
				/* Just don't add */
				System.out.println("Something went wrong and the item was" +
						" not added.");
			}
			close();
		}
	}

	/** Builds an {@link SizeableItem}. */
	private class SizeableItemBuilder extends ItemBuilder {
		private final ComboBox<Size> sizeBox = new ComboBox<>();
		private final TextField nameField;
		private final TextField priceField;
		private final TextField typeField;
		private final TextField categoryField;

		public SizeableItemBuilder() {
			this.setPadding(new Insets(20, 20, 20, 20));
			this.setSpacing(10);

			//Enter size
			sizeBox.getItems().addAll(Size.values());
			sizeBox.getSelectionModel().selectFirst();
			this.getChildren().add(sizeBox);
			//Enter name
			nameField = createTextField("Name");
			this.getChildren().add(nameField);
			//Enter price
			priceField = createTextField("Price");
			this.getChildren().add(priceField);
			//Enter type
			typeField = createTextField("FoodType");
			this.getChildren().add(typeField);
			//Enter categories
			categoryField = createTextField("Categories (Separated by commas)");
			this.getChildren().add(categoryField);

			//Choice (Add and remove side-by-side)
			HBox decisionBox = new HBox();
			this.getChildren().add(decisionBox);

			//Add button
			Button addButton = new Button("Add");
			addButton.setOnMouseClicked(mouseEvent -> create());
			decisionBox.getChildren().add(addButton);

			//Cancel button
			Button cancelButton = new Button("Cancel");
			cancelButton.setOnMouseClicked(mouseEvent -> close());
			decisionBox.getChildren().add(cancelButton);
		}

		public void create() {
			try {
				//Get fields
				Size size = sizeBox.getSelectionModel().getSelectedItem();
				String name = nameField.getText();
				double price = Double.parseDouble(priceField.getText());
				String type = typeField.getText();
				String[] categories = categoryField.getText().split(",");

				//Create item
				List<DietaryCategory> cat = Arrays.stream(categories).
						map(DietaryCategory::get)
						.collect(Collectors.toList());
				SizeableItem item = new SizeableItem(size, name, price, FoodType.get(type), cat);
				Menu.instance().addItem(item);
			} catch (Exception e) {
				e.printStackTrace();
				/* Just don't add */
				System.out.println("Something went wrong and the item was" +
						" not added.");
			}
			close();
		}
	}

	/** Builds a {@link SpecialItem}. */
	private class SpecialItemBuilder extends ItemBuilder {
		private final ComboBox<MenuItem> itemPicker = new ComboBox<>();
		private final TextField nameField;
		private final TextField discountField;

		public SpecialItemBuilder() {
			this.setPadding(new Insets(20, 20, 20, 20));
			this.setSpacing(10);

			//Enter size
			itemPicker.getItems().addAll(Menu.instance().stream().collect(Collectors.toList()));
			itemPicker.getSelectionModel().selectFirst();
			this.getChildren().add(itemPicker);

			//Enter name
			nameField = new TextField();
			nameField.setPrefWidth(400);
			nameField.setPromptText("Name");
			this.getChildren().add(nameField);

			//Enter price
			discountField = new TextField();
			discountField.setPromptText("Discount");
			this.getChildren().add(discountField);

			//Choice (Add and remove side-by-side)
			HBox decisionBox = new HBox();
			this.getChildren().add(decisionBox);

			//Add button
			Button addButton = new Button("Add");
			addButton.setOnMouseClicked(mouseEvent -> create());
			decisionBox.getChildren().add(addButton);

			//Cancel button
			decisionBox.getChildren().add(createRemoveButton());
		}

		@Override
		public void create() {
			try {
				//Get fields
				MenuItem itemToAdd = itemPicker.getSelectionModel().getSelectedItem();
				String name = nameField.getText();
				double discount = Double.parseDouble(discountField.getText());

				//Create item
				SpecialItem item = new SpecialItem(name, discount, itemToAdd);
				Menu.instance().addItem(item);
			} catch (Exception e) {
				e.printStackTrace();
				/* Just don't add */
				System.out.println("Something went wrong and the item was" +
						" not added.");
			}
			close();
		}
	}

	/** Builds a {@link ComboItem}. */
	private class ComboItemBuilder extends ItemBuilder {
		private final ComboBox<MenuItem> allBox = new ComboBox<>();
		private final ListView<MenuItem> stagedItems =  new ListView<>();
		private final TextField nameField;
		private final TextField discountField;
		private final TextField typeField;

		public ComboItemBuilder() {
			this.setPadding(new Insets(20, 20, 20, 20));
			this.setSpacing(10);

			//Slot 0: Select item box
			allBox.getItems().addAll(Menu.instance().stream().collect(Collectors.toList()));
			allBox.setOnAction((event) -> {
				MenuItem selected = allBox.getSelectionModel().getSelectedItem();
				stagedItems.getItems().add(selected);
			});
			this.getChildren().add(allBox);

			//Slot 1: Staging area
			stagedItems.setMinHeight(100);
			this.getChildren().add(stagedItems);

			//Slot 2: Enter name
			nameField = new TextField();
			nameField.setPrefWidth(400);
			nameField.setPromptText("Name");
			this.getChildren().add(nameField);

			//Slot 3: Enter discount
			discountField = new TextField();
			discountField.setPromptText("Discount");
			this.getChildren().add(discountField);

			//Slot 4: Enter type
			typeField = new TextField();
			typeField.setPromptText("Food Type");
			this.getChildren().add(typeField);

			//Choice (Add and remove side-by-side)
			HBox decisionBox = new HBox();
			this.getChildren().add(decisionBox);

			//Add button
			decisionBox.getChildren().add(createAddButton());

			//Remove button
			Button removeButton = new Button("Remove");
			removeButton.setOnMouseClicked((e) -> {
				int selected = stagedItems.getSelectionModel().getSelectedIndex();
				stagedItems.getItems().remove(selected);
			});
			decisionBox.getChildren().add(removeButton);

			//Cancel button
			decisionBox.getChildren().add(createRemoveButton());
		}

		@Override
		public void create() {
			try {
				//Get fields
				MenuItem[] itemsToAdd = new MenuItem[stagedItems.getItems().size()];
				stagedItems.getItems().toArray(itemsToAdd);
				String name = nameField.getText();
				double discount = Double.parseDouble(discountField.getText());
				String type = typeField.getText();

				//Create item
				ComboItem item = new ComboItem(name, discount, FoodType.get(type), itemsToAdd);
				Menu.instance().addItem(item);
			} catch (Exception e) {
				e.printStackTrace();
				/* Just don't add */
				System.out.println("Something went wrong and the item was" +
						" not added.");
			}
			close();
		}
	}
}
