package homework.algorithm;

import java.util.TreeMap;

import homework.util.CommonUtil;

public class HCsearchV2 implements AI {

	private int n = 100; // default bit
	private int iterMax = 1000; // 最多疊代數
	private int perIter = 100; // 間距

	/**
	 * HCsearchV2
	 */
	public HCsearchV2() {

	}

	/**
	 * HCsearchV2
	 * 
	 * @param n
	 * @param iterMax
	 * @param perIter
	 * @throws Exception
	 */
	public HCsearchV2(int n, int iterMax, int perIter) throws Exception {
		this.n = n;
		this.iterMax = iterMax;
		this.perIter = perIter;
	}

	/**
	 * serach
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public TreeMap<Integer, String> serach() throws Exception {

		// initialize
		TreeMap<Integer, String> resultMap = new TreeMap<>();
		String startPoint = "";
		String tempPoint = "";
		String currentMaxStr = "";

		for (int i = perIter; i <= iterMax; i += perIter) {

			startPoint = CommonUtil.genRandomPoint(n);// random start point
			tempPoint = "";
			currentMaxStr = "";

			if (i == perIter) {
				resultMap.put(0, startPoint);// save start point
			}
			currentMaxStr = CommonUtil.compareStr(currentMaxStr, startPoint);

			for (int j = 1; j <= i; j++) {
				tempPoint = CommonUtil.getRandomNeighbor(currentMaxStr);// random
																		// neighbor
				currentMaxStr = CommonUtil.compareStr(currentMaxStr, tempPoint);// evaluate
			}

			resultMap.put(i, currentMaxStr);// save
		}

		return resultMap;

	}
}
