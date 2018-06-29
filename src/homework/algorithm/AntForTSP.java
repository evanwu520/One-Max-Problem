package homework.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import homework.bean.Ant;
import homework.bean.City;
import homework.bean.Knapsack;

public class AntForTSP implements ACOsolution {
	//http://elib.zib.de/pub/mp-testdata/tsp/tsplib/tsp/
	//http://elib.zib.de/pub/mp-testdata/tsp/tsplib/tsp/eil51.opt.tour
	//http://www.cwa.mdx.ac.uk/tsp/tspeil51opt.html
	//http://www.tgis.org.tw/files/recruit/92_4e19050d.pdf
	//http://www.baeldung.com/java-ant-colony-optimization
	
	private int antCount = 100;	//螞蟻數量
	private int iterCount = 100;//疊代次數	
	private int Q = 100;//增強費洛費系數
	private double alpha = 1d;	//係數
	private double beta = 3d;	//係數
	private double evaporation = 0.6d;//蒸發率
	public HashMap<Integer, City> allCity = new HashMap<>();//城市坐座資訊
	private HashMap<String, Double> distanceTable = new HashMap<>();//城市間的距離
	private HashMap<String, Double> phonemeTable = new HashMap<>();//城市間的費洛蒙
	private Ant[] ants = new Ant[antCount];//螞蟻物件
	//tsp 最佳
	public List<Integer> bestRouteList = new ArrayList<>();
	//最少步數
	private double bestTotal = 999999;
	//記錄每次罍代最好的
	public List<Ant>  recordBest = new ArrayList<>();
	
	
	/**
	 * constructor 
	 * @param settingMap
	 */
	public AntForTSP(HashMap<String, String> settingMap){
		
		iterCount = Integer.valueOf(settingMap.get("iterCount"));
		Q = Integer.valueOf(settingMap.get("Q"));
		alpha = Double.valueOf(settingMap.get("alpha"));
		beta = Double.valueOf(settingMap.get("beta"));
		evaporation = Double.valueOf(settingMap.get("evaporation"));
		
	}
	
	/**
	 * constructor
	 */
	public AntForTSP(){
		
	}

	/**
	 * main
	 */
	public void main() throws IOException, CloneNotSupportedException {

		readTSPfile();//讀取城市資料
		calculateDistance();//計算各城市間的距離
		
		//疊代數
		for(int i = 1; i <= iterCount; i++){
			
			initAnt();//隨機起點
			antExplore();//開始尋找
			calaculateAntRoutePath();//計算總路徑
			updatePheromone();//更新費洛蒙
			
			//排序
			Arrays.sort(ants, new Ant());
			
			//記綠最佳解
			if (bestTotal > ants[0].getTotalDistance()) {
				bestTotal = ants[0].getTotalDistance();
				recordBest.add(ants[0].clone());
			}else{
				int index = recordBest.size()-1 < 0 ? 0 : recordBest.size()-1;
				recordBest.add(recordBest.get(index).clone());
			}
			
		}
		printResult();
		// best result
		readTSPbest();

	}
	
	/**
	 * printResult
	 */
	 private void printResult(){
		
		int i=1;
		
		System.out.println("距離");
		
		for(Ant ant:recordBest){
			
			System.out.println("iter:"+i++ +" " +ant.getTotalDistance());
		}
		
		System.out.println("路徑");

		i=1;
		for(Ant ant:recordBest){
			
			System.out.println("iter:"+i++ +" " +ant.getVisit());
		}
		
	}
	 
	/**
	 * initAnt
	 */
	@Override
	public void initAnt() {

		for (int i = 0; i < antCount; i++) {

			ants[i] = new Ant();
			// 隨機起點
			int index = (int) (Math.random() * allCity.size()) + 1;
			City city = allCity.get(index);

			ants[i].setCurrent(city);
			ants[i].getVisit().add(index);
		}
	}
		
	
	/**
	 * antExplore
	 */
	 @Override
	 public void antExplore() {

		for (int i = 1; i <= allCity.size(); i++) {

			for (int j = 0; j < antCount; j++) {
				//計算目前所在城市到下一個可能所有的城市
				HashMap<String, Double> probability  = calculateProbability(ants[j]);
				//決定下一個城市
				moveNextCity(ants[j], probability);

			}
		}
	}
	 
		
	/**
	 * calaculateAntRoutePath
	 */
	@Override
	public void calaculateAntRoutePath() {

		for (int i = 0; i < ants.length; i++) {

			List<Integer> routePath = ants[i].getVisit();

			double totalPath = 0d;
			String key = "";

			// 回到原點
			routePath.add(routePath.get(0));

			for (int j = 0; j < routePath.size() - 1; j++) {

				key = String.valueOf(routePath.get(j)) + "to" + String.valueOf(routePath.get(j + 1));
				totalPath += distanceTable.get(key);
			}

			ants[i].setTotalDistance(totalPath);
		}
	}

	/**
	 * moveNextCity
	 * 
	 * @param ant
	 * @param probability
	 */
	@Override
	public void moveNextCity(Ant ant, HashMap<String, Double> probability) {

		// 輪盤法
		String key = "";
		double r = new Random().nextDouble();
		double total = 0;

		for (int i = 1; i <= allCity.size(); i++) {

			key = String.valueOf(ant.getCurrent().getNumber()) + "to" + String.valueOf(i);
			total += probability.get(key);
			if (total >= r) {
				ant.setCurrent(allCity.get(i));
				ant.getVisit().add(i);
				break;
			}
		}
	}

	/**
	 * updatePheromone
	 */
	@Override
	public void updatePheromone() {

		// evaporation 費洛蒙
		for (String key : phonemeTable.keySet()) {
			double newPheromone = phonemeTable.get(key) * evaporation;
			phonemeTable.put(key, newPheromone);
		}

		Arrays.sort(ants, new Ant());
		double rate = 0.95;

		double contribution;
		// enforce
		for (Ant ant : ants) {

			contribution = (Q / ant.getTotalDistance()) * rate;

			rate *= rate;

			for (int j = 0; j < ant.getVisit().size() - 1; j++) {

				// from to dest
				String fromToDest = String.valueOf(ant.getVisit().get(j)) + "to" + String.valueOf(ant.getVisit().get(j + 1));
				String DestToFrom = String.valueOf(ant.getVisit().get(j + 1)) + "to" + String.valueOf(ant.getVisit().get(j));
				double newPheromone = phonemeTable.get(fromToDest) + contribution;

				// A TO B
				phonemeTable.put(fromToDest, newPheromone);
				// B TO A
				phonemeTable.put(DestToFrom, newPheromone);
			}
		}
	}

	
	/**
	 * calculateProbability
	 * @param ant
	 * @return
	 */
	 public HashMap<String, Double> calculateProbability(Ant ant) {

		float sum = 0;
		HashMap<String, Double> probability = new HashMap<>();
		
		
		//計算還沒走完的總和
		for (int j = 1; j <= allCity.size(); j++) {
			
			if(ant.getVisit().contains(j)){
				continue;
			}

			String key = String.valueOf(ant.getCurrent().getNumber()) + "to" + String.valueOf(j);

			sum += (Math.pow(phonemeTable.get(key), alpha) * Math.pow((1 / distanceTable.get(key)), beta));
		}
		
		//各別計算還沒走完的機率
		for (int j = 1; j <= allCity.size(); j++) {

			String key = String.valueOf(ant.getCurrent().getNumber()) + "to" + String.valueOf(j);
			//走過的設為0
			double probabilities = 0d;
			
			if(!ant.getVisit().contains(j)){
				probabilities = (Math.pow(phonemeTable.get(key), alpha) * Math.pow((1 / distanceTable.get(key)), beta)) / sum;
			}
			probability.put(key, probabilities);
		}
		
		return probability;
	}

	/**
	 * caculate distance between city and city init phoneme between city and city
	 */
	 @Override
	 public void calculateDistance() {

		for (int i = 1; i <= allCity.size(); i++) {

			for (int j = 1; j <= allCity.size(); j++) {

				// 自已不會走到自已
				if (i == j) {
					continue;
				}

				String key = String.valueOf(i) +"to"+ String.valueOf(j);

				City a = allCity.get(i);
				City b = allCity.get(j);

				double distance = (float) Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
				distanceTable.put(key, distance);
				// 費洛蒙初始值
				phonemeTable.put(key, 1d);
			}
		}
	}

	/**
	 * loding city data
	 * @throws IOException
	 */
	 @Override
	 public void readTSPfile() throws IOException {

		InputStream fr = null;
		BufferedReader buf =null;
		String line;

		try{
			
			ClassLoader CLDR = this.getClass().getClassLoader();
			fr = CLDR.getResourceAsStream("homework/config/tsp51");
			buf = new BufferedReader(new InputStreamReader(fr));
			while ((line = buf.readLine()) != null) {

				String splitLine[] = line.split(" ");
				Integer number = Integer.valueOf(splitLine[0]);
				float x = Float.valueOf(splitLine[1]);
				float y = Float.valueOf(splitLine[2]);

				City city = new City(number, x, y);
				allCity.put(number, city);
			}
		}catch(IOException ex){
			throw ex;
		}finally{
			if(fr != null){
				fr.close();
			}
			if(buf != null){
				buf.close();
			}
		}
	}
	
	/**
	 * readTSPbest
	 * @throws IOException
	 */
	 private void readTSPbest() throws IOException {

		InputStream fr = null;
		BufferedReader buf =null;
		String line;

		try{
			ClassLoader CLDR = this.getClass().getClassLoader();
			fr = CLDR.getResourceAsStream("homework/config/tspBest");
			buf = new BufferedReader(new InputStreamReader(fr));
			while ((line = buf.readLine()) != null) {
				
				bestRouteList.add(Integer.valueOf(line.trim()));
			}
		}catch(IOException ex){
			throw ex;
		}finally{
			if(fr != null){
				fr.close();
			}
			if(buf != null){
				buf.close();
			}
		}
	}




}