package alpvax.wayoflight.core;


public enum EnumLanternColour
{
	RED("\u00A74"),
	ORANGE("\u00A76"),
	YELLOW("\u00A7E"),
	GREEN("\u00A7A"),
	BLUE("\u00A79"),
	INDIGO("\u00A75"),
	VIOLET("\u00A7D"),
	BLACK("\u00A70"),
	WHITE("\u00A7F");
	public static EnumLanternColour[] values = values();

	public static EnumLanternColour get(String name)
	{
		for(EnumLanternColour e : values)
		{
			if(e.name() == name.toUpperCase())
			{
				return e;
			}
		}
		return null;
	}

	private String chatColour;

	private EnumLanternColour(String chatColourChar)
	{
		chatColour = chatColourChar;
	}

	public String chatColour()
	{
		return chatColour;
	}
}