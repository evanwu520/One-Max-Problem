package homework.main;

import java.util.HashMap;
import java.util.TreeMap;

import homework.algorithm.ACOsolution;
import homework.algorithm.AI;
import homework.algorithm.AntForTSP;
import homework.algorithm.ExhaustiveSearch;
import homework.algorithm.GAwithKP;
import homework.algorithm.HCsearchV2;
import homework.algorithm.SAsearch;
import homework.draw.DrawLine;
import homework.util.CommonUtil;

public class Excute {

	public static void main(String[] args) {
		try {

			// default
			int n = 100; // default bit
			int iterMax = 1000; // 程舼计
			int perIter = 100; // 丁禯

			String function = "Greedy";
			function = args[0];
			

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
				
				HashMap<String, String> settingMap = new HashMap<>();
				
				if(args.length == 8){
					settingMap.put("itemCount", args[1]);
					settingMap.put("populatonSize", args[2]);
					settingMap.put("tournamentGrop", args[3]);
					settingMap.put("crossOverRate", args[4]);
					settingMap.put("mutationRate", args[5]);
					settingMap.put("iterCount", args[6]);
					settingMap.put("deduct", args[7]);
					
					GAwithKP ai = new GAwithKP(settingMap);
					ai.serach();
				}else{
					GAwithKP ai = new GAwithKP();
					ai.test();
				} 
				

			} else if("ANT".equals(function)) {
				
				HashMap<String, String> settingMap = new HashMap<>();
				
				System.out.println(args.length);
				
				if(args.length == 7){
					
					settingMap.put("iterCount", args[2]);
					settingMap.put("Q", args[3]);
					settingMap.put("alpha", args[4]);
					settingMap.put("beta", args[5]);
					settingMap.put("evaporation", args[6]);
					
					AntForTSP  explore = new AntForTSP(settingMap);
					
					//琌礶瓜
					if("true".equals(args[1])){
						DrawLine drawLine = new DrawLine();
						drawLine.draw(explore);
					}else{
						explore.main();
					}
					
				}else{
					//default //柯闷计秖 100 //舼Ω计100 //糤眏禣禣╰计   Q 100 //alpha 玒计  1d //beta 玒计 3d //籡祇瞯 0.6d //ぃ礶瓜
					ACOsolution explore = new AntForTSP();
					explore.main();
				}
			
				
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
