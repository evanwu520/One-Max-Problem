package homework.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import homework.util.CommonUtil;

public class ExhaustiveSearch{
	
	static long count = 0;//record count
	static long maxStrNo = 0;//record one max str No.
	static int isOpenConsoleLog = 1;//0:close, 1:open
	static String maxStr = "";//record one max str
	static HashMap<String, Object> hasMap;
	
	public ExhaustiveSearch(int bitCount, int openLog) throws Exception{
		int n = 0;
		n = Integer.valueOf(bitCount);
		isOpenConsoleLog = Integer.valueOf(openLog);
		ArrayList list = new ArrayList<>();		
		long lStartTime = System.nanoTime();
		search(n, "");
		long lEndTime = System.nanoTime();
		long milliseconds = (lEndTime-lStartTime)/1000000;
		CommonUtil.log("總花費毫秒數:"+milliseconds);
		CommonUtil.log(maxStrNo, maxStr);
	}
	
	/**
	 * fullSerach
	 * @param n
	 * @param findedStr
	 * @throws Exception
	 */
	public static void search(int n, String findedStr) throws Exception{
		
		for(int i=0; i<=1; i++){
			if(n>1){
				search(n-1, findedStr + String.valueOf(i));//recursion
			}else if(n==1){
				//find max str
				 hasMap = CommonUtil.compareStr(maxStr, findedStr + String.valueOf(i), ++count);
				 if(hasMap != null){
					 maxStrNo = (long) hasMap.get("maxStrNo");
					 maxStr = (String) hasMap.get("maxStr");
				 }
				 //log
				if(isOpenConsoleLog == 1){
					CommonUtil.log(count, findedStr + String.valueOf(i));
				}
			}
		}
	}
	
}
