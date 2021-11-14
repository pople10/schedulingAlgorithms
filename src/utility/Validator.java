package utility;

public class Validator {
	public static boolean isInteger(String e)
	{
		try 
		{
			Integer.parseInt(e);
		}
		catch(Exception e1)
		{
			return false;
		}
		return true;
	}
}
