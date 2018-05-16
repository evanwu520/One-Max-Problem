package homework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {

	/**
	 * compareStr
	 * 
	 * @param currentMaxStr
	 * @param inputStr
	 * @param long
	 * @throws Exception
	 */
	public static HashMap<String, Object> compareStr(String currentMaxStr, String inputStr, long strNo)
			throws Exception {

		HashMap<String, Object> hashMap = null;

		if (accumulateOneCount(inputStr) > accumulateOneCount(currentMaxStr)) {
			hashMap = new HashMap<>();
			hashMap.put("maxStr", inputStr);
			hashMap.put("maxStrNo", strNo);
		}

		return hashMap;

	}

	/**
	 * compareStr
	 * 
	 * @param currentMaxStr
	 * @param inputStr
	 * @throws Exception
	 */
	public static String compareStr(String currentMaxStr, String inputStr) {

		if (accumulateOneCount(inputStr) > accumulateOneCount(currentMaxStr)) {
			return inputStr;
		}

		return currentMaxStr;

	}

	/**
	 * accumulateOneCount
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int accumulateOneCount(String str) {

		int count = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}

	/**
	 * log
	 * 
	 * @param number
	 * @param str
	 */
	public static void log(long number, String str) {
		System.out.printf("²Ä%d²Õ: %s\n", number, str);
	}

	/**
	 * log
	 * 
	 * @param str
	 */
	public static void log(String str) {
		System.out.printf("%s\n", str);
	}

	/**
	 * genRandomPoint
	 * 
	 * @param length
	 * @return
	 */
	public static String genRandomPoint(int length) {

		String point = "";

		while (length > 0) {
			int value = (int) (Math.random() * 10);
			point += value > 4 ? "0" : "1";
			length--;
		}
		return point;
	}

	/**
	 * findNeighbor
	 * 
	 * @param point
	 * @return
	 */
	public static List<String> findNeighbor(String point) {

		List<String> list = new ArrayList<>();
		String neigborPoint = "";
		int length = point.length();

		for (int i = 0; i < length; i++) {

			String temp = "0".equals(point.substring(i, i + 1)) ? "1" : "0";

			if (i == 0) {
				neigborPoint = temp + point.substring(i + 1);
			} else {
				neigborPoint = point.substring(0, i) + temp + point.substring(i + 1);
			}

			list.add(neigborPoint);
		}

		return list;
	}

	/**
	 * getRandomNeighbor
	 * 
	 * @param point
	 * @return
	 */
	public static String getRandomNeighbor(String point) {

		List<String> list = findNeighbor(point);
		int size = list.size();
		int index = (int) (Math.random() * size);

		return list.get(index);
	}
	
	/**
	 * getRandomIndex
	 * @param
	 * @param getCount
	 * @param repeat
	 * @return
	 */
	public static List<Integer> getRandomIndex(int randomSize, int getCount, boolean repeat){
		
		List<Integer> list = new ArrayList<>();
		
		while(getCount > 0){
			
			int index = (int) (Math.random() * randomSize);
			
			
			if(repeat){
				list.add(index);
				getCount--;
			}else{
				if(!list.contains(index)){
					list.add(index);
					getCount--;
				}
			}
			
		}
		
		return list;
		
	}
	/**
	 * AckleyFuncion
	 * @param x
	 * @param y
	 * @return
	 */
	public static double AckleyFuncion(float x, float y){
		
		double v1 = -0.2 * Math.sqrt(0.5 * (x*x + y*y));
		double v2 = 0.5 * (Math.cos(2*Math.PI*x) + Math.cos(2*Math.PI*y));
		double value = -20 * Math.exp(v1) - Math.exp(v2)+Math.E+20;
		
		return value;
	}
	
}
