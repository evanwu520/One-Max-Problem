package homework.algorithm;

import java.io.IOException;
import java.util.HashMap;

import homework.bean.Ant;

public interface ACOsolution {
	
	//讀取城市資料
	void readTSPfile() throws IOException;
	
	//計算每個城市間的距離及初始費洛蒙， 建立對應表暫存在記憶體
	void calculateDistance();

	//隨機起點
	void initAnt();
	
	//開始尋找
	void antExplore();
	
	//計算目前所在城市到下一個可能所有的城市
	HashMap<String, Double> calculateProbability(Ant ant);
	
	//決定下一個城市
	void moveNextCity(Ant ant, HashMap<String, Double> probability);
	
	//計算拜訪拜路徑總和
	void calaculateAntRoutePath();
	
	//更新費洛蒙
	void updatePheromone();
	
	//主程式
	void main() throws IOException, CloneNotSupportedException;
	
	
	
}
