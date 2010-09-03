package line6;

/**
 * Misc static methods
 * @author Mateusz Szygenda
 *
 */
public class Misc {
	public static String splitByUppercase(String str)
	{
		str = str.replace('_', ' ');
		StringBuffer result = new StringBuffer();
		for(int i = 0, count = str.length(); i < count; i++)
		{
			if(Character.isUpperCase(str.charAt(i)))
				result.append(' ');
			result.append(str.charAt(i));
		}
		return result.toString();
	}
}
