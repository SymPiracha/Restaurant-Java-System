package activity7;

import java.util.Collection;
import java.util.Objects;

public class SpecialItem extends AbstractMenuItem 
{

	/** MenuItem contained in this combo. */
	private final MenuItem aSpecialItem;
	/** Discount (portion of price) applied to the total price. */
	private double aDiscount;
	
	/**
	 * Constructor for a combo item.
	 * @param pDiscount Discount to apply. Must be between 0 and 1.
	 */
	public SpecialItem(String pName, double pDiscount, MenuItem pItem)
	{
		super(pName, pItem.getFoodType(), pItem.getPrice());
		assert pDiscount >= 0D && pDiscount <= 1D;

		aDiscount = pDiscount;
		aSpecialItem = pItem;
		recalculatePrice();
	}
	
	/**
	 * Calculates the new price by applying dicount to original price
	 */
	private void recalculatePrice()
	{
		aPrice = (1-aDiscount) * aSpecialItem.getPrice();
	}
	
	/**
	 * @param pDiscount is the new discount to be applied
	 * @pre Discount to apply. Must be between 0 and 1.
	 */
	public void setDiscount(double pDiscount)
	{
		assert pDiscount >= 0D && pDiscount <= 1D;
		aDiscount = pDiscount;
		aPrice = (1-pDiscount) * aSpecialItem.getPrice();
		Menu.instance().updateMenu();
	}
	

	@Override
	public Collection<DietaryCategory> getDietaryCategories()
	{
		return aSpecialItem.getDietaryCategories();
	}
	
	/**
	 * @return original price without applying discount
	 */
	private double originalPrice()
	{
		return aSpecialItem.getPrice();
	}

	@Override
	String extraInformation()
	{
		return String.format("has been put on Special. Original Price: $ %.2f, Discounted Price: ", originalPrice());
	}
	
	@Override
	public boolean equals(Object pObject)
	{
		if (pObject == null)
		{
			return false;
		}
		if (pObject == this)
		{
			return true;
		}
		if (pObject.getClass() != this.getClass())
		{
			return false;
		}
		SpecialItem specialItem = (SpecialItem) pObject;
		return this.aName.equals(specialItem.aName)
				&& this.aDiscount == specialItem.aDiscount
				&& this.aFoodType.equals(specialItem.aFoodType)
				&& this.aSpecialItem.equals(specialItem.aSpecialItem);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(aName, aDiscount, aFoodType, aSpecialItem);
	}
}

