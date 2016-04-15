package alpvax.waroflight.emotions;

import net.minecraft.util.IStringSerializable;

public enum EnumEmotion implements IStringSerializable
{
	RAGE("red", "\u00A74"),
	GREED("orange", "\u00A76"),
	FEAR("yellow", "\u00A7E"),
	WILLPOWER("green", "\u00A7A"),
	HOPE("blue", "\u00A79"),
	COMPASSION("indigo", "\u00A75"),
	LOVE("violet", "\u00A7D"),
	DEATH("black", "\u00A70"),
	LIFE("white", "\u00A7F");
	public static EnumEmotion[] values = values();

	/**
	 * Gets the emotion by either name or colour.<br>
	 * Returns null if no match found.
	 */
	public static EnumEmotion get(String s)
	{
		s = s.toLowerCase();
		for(EnumEmotion e : values)
		{
			if(e.name().toLowerCase() == s || e.colour == s)
			{
				return e;
			}
		}
		return null;
	}

	private String colour;
	private String chatColour;

	private EnumEmotion(String spectrumColour, String chatColourChar)
	{
		colour = spectrumColour;
		chatColour = chatColourChar;
	}

	public String colour()
	{
		return colour;
	}

	public String chatColourChar()
	{
		return chatColour;
	}

	@Override
	public String getName()
	{
		return colour;
	}
}