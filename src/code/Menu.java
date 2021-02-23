package activity7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a menu that can be displayed in the menu display.
 */
public class Menu implements Iterable<MenuItem>
{
	private final List<MenuItem> aItems = new ArrayList<>();
	private final List<Observer> aObservers = new ArrayList<>();


	// Global variable to hold single instance of Menu
	private static final Menu INSTANCE = new Menu();


	/**
	 * An accessor method that returns an instance of Menu.
	 *
	 * @return instance of Menu
	 */
	public static Menu instance()
	{
		return INSTANCE;
	}


	/**
	 * Adds an item to the menu. Does not add if the item is already contained.
	 * @param pItem Item to add.
	 * @pre pItem != null
	 */
	public void addItem(MenuItem pItem)
	{
		assert pItem != null;
		if(!aItems.contains(pItem))
		{
			aItems.add(pItem);
			// Inform observers that MenuItem item is added
			updateObservers();
		}
	}

	/**
	 * Removes an item from menu.
	 * @param pItem Item to remove.
	 * @pre pItem != null
	 */
	public void removeItem(MenuItem pItem)
	{
		assert pItem != null;
		// Inform observers that MenuItem item is removed if it was removed
		if (aItems.remove(pItem)) {
			updateObservers();
		}
	}

	/**
	 * Stream from all items in the menu.
	 * @return stream of Menu items
	 */
	public Stream<MenuItem> stream()
	{
		return new ArrayList<>(aItems).stream();
	}

	@Override
	public Iterator<MenuItem> iterator()
	{
		return new ArrayList<>(aItems).iterator();
	}

	/**
	 * @param pObserver Observer to register with the menu.
	 */
	public void addObserver(Observer pObserver)
	{
		assert pObserver != null;
		aObservers.add(pObserver);
	}

	/**
	 * @param pObserver Observer to deregister with the menu.
	 */
	public void removeObserver(Observer pObserver)
	{
		assert pObserver != null;
		aObservers.remove(pObserver);
	}

	//Reinstated helper methods

	public List<MenuItem> filterMenuItems(List<Predicate<MenuItem>> allPredicates)
	{
		return aItems.stream()
				.filter(allPredicates.stream()
						//Create composite predicate
						//Initial predicate unconditionally true //pred1.and(pred2)
						.reduce( (anyVar) -> true, Predicate::and) )
				.collect(Collectors.toList());
	}

	public List<MenuItem> filterMenuItems(Predicate<MenuItem> pre)
	{
		return aItems.stream()
				.filter(pre)
				.collect(Collectors.toList());
	}

	private void updateObservers()
	{
		aObservers.forEach(Observer::menuUpdated);
	}

	public void updateMenu()
	{
		updateObservers();
	}


}
