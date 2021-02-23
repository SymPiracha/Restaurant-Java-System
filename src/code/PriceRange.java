package activity7;

/**
 * Values in a certain price range. Price ranges include the start and
 * excludes the end.
 */
public enum PriceRange
{
    CHEAP(0D, 5D),
    MEDIUM(5D, 10D),
    EXPENSIVE(10D, Double.MAX_VALUE);

    public final double aLowerBound;
    public final double aUpperBound;

    PriceRange(double lowerBound, double upperBound)
    {
        aLowerBound = lowerBound;
        aUpperBound = upperBound;
    }

    /**
     * Whether the given item's price is in the desired range.
     * @param pItem Item to check.
     * @return True if in bound, false otherwise.
     */
    public boolean isInBound(MenuItem pItem)
    {
        return pItem.getPrice() >= aLowerBound && pItem.getPrice() < aUpperBound;
    }

    /**
     * Get the range the given price falls into.
     * @param pItem Price whose range is desired.
     * @return The price range this price falls into.
     * @throws AssertionError If the given price is negative, or no range
     *                        was found (shouldn't be possible).
     * @pre pPrice >= 0D
     */
    public static PriceRange getRange(MenuItem pItem)
    {
        assert pItem != null;
        for (PriceRange pr : PriceRange.values())
        {
            if (pr.isInBound(pItem))
            {
                return pr;
            }
        }
        assert false; //No range found.
        return EXPENSIVE;
    }

}
