package homework.algorithm;

import java.util.TreeMap;

import homework.util.CommonUtil;

public class SAsearch implements AI {

	private int n = 100; // default bit
	private int iterMax = 1000; // 最多疊代數
	private int perIter = 100; // 間距

	/**
	 * SAsearch
	 * 
	 * @throws Exception
	 */
	public SAsearch() throws Exception {
	}

	/**
	 * SAsearch
	 * 
	 * @param n
	 * @param iterMax
	 * @param perIter
	 * @throws Exception
	 */
	public SAsearch(int n, int iterMax, int perIter) throws Exception {
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

		TreeMap<Integer, String> resultMap = new TreeMap<>();
		String startPoint = "";// 起點
		int currentValue = 0; // 價值
		int maxValue = 0;// 最佳解值
		float temperature = 0; // 溫度
		String currentMaxStr = "";

		// 執行次數
		for (int i = perIter; i <= iterMax; i += perIter) {

			startPoint = CommonUtil.genRandomPoint(n); // 隨機起點
			currentValue = CommonUtil.accumulateOneCount(startPoint); // 初始價值
			temperature = 100; // 起始溫度
			maxValue = 0;
			currentMaxStr = "";

			if (i == perIter) {
				resultMap.put(0, startPoint);// save start point
			}

			// 每次要執行的次數
			for (int j = 1; j <= i; j++) {
				// random neighbor
				String neighborPoint = CommonUtil.getRandomNeighbor(startPoint);
				// 新解價值
				int newValue = CommonUtil.accumulateOneCount(neighborPoint);
				// 隨機0~1
				float r = (float) ((int) (Math.random() * 11)) / 10;
				double pa = Math.exp((newValue - currentValue) / temperature);

				if (pa > r) {
					currentValue = newValue; // 新解取代舊解
					startPoint = neighborPoint;
				}
				// 區域解與新解比較
				if (maxValue < currentValue) {
					currentMaxStr = neighborPoint;
					maxValue = currentValue;
				}
				// 溫度衰退
				temperature *= 0.99;
			}

			resultMap.put(i, currentMaxStr);// save

		}

		return resultMap;
	}
}
