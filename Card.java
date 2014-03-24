
public class Card
{
	final int SKIP = 10;
	final int REVERSE = 11;
	final int DRAW2 = 12;
	final int WILD = 13;
	final int WILDDRAW4 = 14;
	
	int value;
	String color;
	
	public Card(int inValue, String inColor)
	{
		value = inValue;
		color = inColor;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public String toString()
	{
		if(value < 10)
		{
			return color + " " + value;
		}
		switch(value)
		{
		case SKIP:
			return color + " skip";
		case REVERSE:
			return color + " reverse";
		case DRAW2:
			return color + " draw 2";
		case WILD:
			return color + " wild";
		case WILDDRAW4:
			return color + " wild draw 4";
		default:
			return "null";
		}
		
	}
}
