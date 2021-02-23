package activity7;

/**
 * Represents all possible sizes and the default modifiers items of this
 * size receive to their base price.
 * Recommend using Size.toString() while testing to compare outputs.
 */
public enum Size
{
	SMALL(0.7, "Small"),
	MEDIUM(1.0, "Medium"),
	LARGE(1.3, "Large"),
	EXTRA_LARGE(1.5, "Extra Large");

	public final double defaultModifier;
	public final String name;

	Size(double pDefaultModifier, String pName)
	{
		defaultModifier = pDefaultModifier;
		name = pName;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
