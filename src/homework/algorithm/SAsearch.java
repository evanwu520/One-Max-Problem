package homework.algorithm;

import java.util.TreeMap;

import homework.util.CommonUtil;

public class SAsearch implements AI {

	private int n = 100; // default bit
	private int iterMax = 1000; // �̦h�|�N��
	private int perIter = 100; // ���Z

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
		String startPoint = "";// �_�I
		int currentValue = 0; // ����
		int maxValue = 0;// �̨θѭ�
		float temperature = 0; // �ū�
		String currentMaxStr = "";

		// ���榸��
		for (int i = perIter; i <= iterMax; i += perIter) {

			startPoint = CommonUtil.genRandomPoint(n); // �H���_�I
			currentValue = CommonUtil.accumulateOneCount(startPoint); // ��l����
			temperature = 100; // �_�l�ū�
			maxValue = 0;
			currentMaxStr = "";

			if (i == perIter) {
				resultMap.put(0, startPoint);// save start point
			}

			// �C���n���檺����
			for (int j = 1; j <= i; j++) {
				// random neighbor
				String neighborPoint = CommonUtil.getRandomNeighbor(startPoint);
				// �s�ѻ���
				int newValue = CommonUtil.accumulateOneCount(neighborPoint);
				// �H��0~1
				float r = (float) ((int) (Math.random() * 11)) / 10;
				double pa = Math.exp((newValue - currentValue) / temperature);

				if (pa > r) {
					currentValue = newValue; // �s�Ѩ��N�¸�
					startPoint = neighborPoint;
				}
				// �ϰ�ѻP�s�Ѥ��
				if (maxValue < currentValue) {
					currentMaxStr = neighborPoint;
					maxValue = currentValue;
				}
				// �ūװI�h
				temperature *= 0.99;
			}

			resultMap.put(i, currentMaxStr);// save

		}

		return resultMap;
	}
}
