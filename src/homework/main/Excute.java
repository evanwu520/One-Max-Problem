package homework.main;

import java.util.HashMap;
import java.util.TreeMap;

import homework.algorithm.AI;
import homework.algorithm.ExhaustiveSearch;
import homework.algorithm.GAwithKP;
import homework.algorithm.HCsearchV2;
import homework.algorithm.SAsearch;
import homework.util.CommonUtil;

public class Excute {

	public static void main(String[] args) {
		try {

			// default
			int n = 100; // default bit
			int iterMax = 1000; // 最多疊代數
			int perIter = 100; // 間距

			String function = "Greedy";

			if (args.length == 4) {
				function = args[0];
				n = Integer.valueOf(args[1]);
				iterMax = Integer.valueOf(args[2]);
				perIter = Integer.valueOf(args[3]);
			}

			AI algorithm = null;

			if ("Greedy".equals(function)) {

				int isOpenLog = iterMax;
				new ExhaustiveSearch(n, isOpenLog);

			} else if ("HC".equals(function)) {

				algorithm = new HCsearchV2(n, iterMax, perIter);

			} else if ("SA".equals(function)) {

				algorithm = new SAsearch(n, iterMax, perIter);

			} else if ("GA".equals(function)) {
				
				HashMap<String, Object> settingMap = new HashMap<>();
				settingMap.put("itemCount", args[0]);
				settingMap.put("populatonSize", args[1]);
				settingMap.put("tournamentGrop", args[2]);
				settingMap.put("crossOverRate", args[3]);
				settingMap.put("mutationRate", args[4]);
				settingMap.put("iterCount", args[5]);
				settingMap.put("deduct", args[6]);
				
				GAwithKP ai = new GAwithKP(settingMap);
				ai.serach();
				

			} else {
				System.out.println("parameter error");
			}

			if (algorithm != null) {
				printResult(algorithm.serach());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printResult(TreeMap<Integer, String> resultMap) {
		for (int iter : resultMap.keySet()) {
			String maxStr = resultMap.get(iter);
			System.out.printf("iter, Max Value : %d,%d\nMax String: %s\n", iter, CommonUtil.accumulateOneCount(maxStr),
					maxStr);
		}
	}

}
