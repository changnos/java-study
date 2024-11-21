package prob04;

public class StringUtil {

	public static String concatenate(String[] strArr) {
		String ret = "";
		int len = strArr.length;
		for(int i = 0; i < len; i++) ret += strArr[i];
		return ret;
	}
}
