package activity7;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Arrays;
import java.util.Optional;

/**
 * Class representing a configuration (Set of 3 panels) to be displayed.
 *
 * Only one configuration is displayed at any given time. Register a
 * configuration with the {@link MenuDisplay} by adding it to the list. A
 * button to choose it will be generated automatically.
 *
 */
public class Configuration extends GridPane
{

	/** Default configuration: 3 types, sorted alphabetically. */
	public static Configuration foodTypeConfiguration = new Configuration(
			"By Food Type",
			new Panel("Drinks",
					FoodType.DRINK.getPredicate(),
					Helper.sortByDescription()),
			new Panel("Main meal",
					FoodType.MAIN_MEAL.getPredicate(),
					Helper.sortByDescription()),
			new Panel("Snacks",
					FoodType.SNACK.getPredicate(),
					Helper.sortByDescription())
	);

	/** 3 price categories, sorted by price. */
	public static Configuration priceConfiguration = new Configuration(
			"By Price",
			new Panel("Cheap",
					PriceRange.CHEAP::isInBound,
					Helper.sortByPrice()),
			new Panel("Moderate",
					PriceRange.MEDIUM::isInBound,
					Helper.sortByPrice()),
			new Panel("Expensive",
					PriceRange.EXPENSIVE::isInBound,
					Helper.sortByPrice())
	);

	/** 3 common dietary categories, sorted by price. */
	public static Configuration dietaryConfiguration = new Configuration(
			"By Dietary Categories",
			new Panel(DietaryCategory.VEGETARIAN.toString(),
					Helper.isDietaryCategory(DietaryCategory.VEGETARIAN),
					Helper.sortByPrice()),
			new Panel(DietaryCategory.VEGAN.toString(),
					Helper.isDietaryCategory(DietaryCategory.VEGAN),
					Helper.sortByPrice()),
			new Panel(DietaryCategory.KOSHER.toString(),
					Helper.isDietaryCategory(DietaryCategory.KOSHER),
					Helper.sortByPrice())
	);

	public static Configuration compositeConfiguration = new Configuration(
			"By Combos",
			new Panel("Combos",
					(x) -> (x instanceof ComboItem),
					Helper.sortByPrice()),
			new Panel("All items",
					(x) -> (true),
					Helper.sortByPrice()),
			new Panel("Items on special",
					(x) -> (x instanceof SpecialItem),
					Helper.sortByPrice())
	);

	private final String aName;
	private final Panel aLeft;
	private final Panel aRight;
	private final Panel aCentre;

	public Configuration(String pName, Panel pLeft, Panel pCentre, Panel pRight)
	{
		assert pName != null && !"".equals(pName) && pLeft != null
				&& pCentre != null && pRight != null;

		aName = pName;
		aLeft = pLeft;
		aRight = pRight;
		aCentre = pCentre;

		//Add the panels.
		this.add(aLeft, 0, 0);
		this.add(aCentre, 1, 0);
		this.add(aRight, 2, 0);

		//Make grow horizontally.
		GridPane.setHgrow(aLeft, Priority.ALWAYS);
		GridPane.setHgrow(aCentre, Priority.ALWAYS);
		GridPane.setHgrow(aRight, Priority.ALWAYS);

		//Make grow vertically.
		GridPane.setVgrow(aLeft, Priority.ALWAYS);
		GridPane.setVgrow(aCentre, Priority.ALWAYS);
		GridPane.setVgrow(aRight, Priority.ALWAYS);

	}

	public String getName()
	{
		return aName;
	}

	/**
	 * @return The MenuItem that is currently selected, if there is one.
	 */
	public Optional<MenuItem> getSelected()
	{
		if (aLeft.getSelected().isPresent())
		{
			return aLeft.getSelected();
		}
		else if (aCentre.getSelected().isPresent())
		{
			return aCentre.getSelected();
		}
		else if (aRight.getSelected().isPresent())
		{
			return aRight.getSelected();
		}
		return Optional.empty();
	}
	
	
}
