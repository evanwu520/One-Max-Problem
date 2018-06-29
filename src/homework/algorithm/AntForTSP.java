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
	
	private int antCount = 100;	//���Ƽƶq
	private int iterCount = 100;//�|�N����	
	private int Q = 100;//�W�j�O���O�t��
	private double alpha = 1d;	//�Y��
	private double beta = 3d;	//�Y��
	private double evaporation = 0.6d;//�]�o�v
	public HashMap<Integer, City> allCity = new HashMap<>();//�������y��T
	private HashMap<String, Double> distanceTable = new HashMap<>();//���������Z��
	private HashMap<String, Double> phonemeTable = new HashMap<>();//���������O���X
	private Ant[] ants = new Ant[antCount];//���ƪ���
	//tsp �̨�
	public List<Integer> bestRouteList = new ArrayList<>();
	//�̤֨B��
	private double bestTotal = 999999;
	//�O���C����N�̦n��
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

		readTSPfile();//Ū���������
		calculateDistance();//�p��U���������Z��
		
		//�|�N��
		for(int i = 1; i <= iterCount; i++){
			
			initAnt();//�H���_�I
			antExplore();//�}�l�M��
			calaculateAntRoutePath();//�p���`���|
			updatePheromone();//��s�O���X
			
			//�Ƨ�
			Arrays.sort(ants, new Ant());
			
			//�O��̨θ�
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
		
		System.out.println("�Z��");
		
		for(Ant ant:recordBest){
			
			System.out.println("iter:"+i++ +" " +ant.getTotalDistance());
		}
		
		System.out.println("���|");

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
			// �H���_�I
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
				//�p��ثe�Ҧb������U�@�ӥi��Ҧ�������
				HashMap<String, Double> probability  = calculateProbability(ants[j]);
				//�M�w�U�@�ӫ���
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

			// �^����I
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

		// ���L�k
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

		// evaporation �O���X
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
		
		
		//�p���٨S�������`�M
		for (int j = 1; j <= allCity.size(); j++) {
			
			if(ant.getVisit().contains(j)){
				continue;
			}

			String key = String.valueOf(ant.getCurrent().getNumber()) + "to" + String.valueOf(j);

			sum += (Math.pow(phonemeTable.get(key), alpha) * Math.pow((1 / distanceTable.get(key)), beta));
		}
		
		//�U�O�p���٨S���������v
		for (int j = 1; j <= allCity.size(); j++) {

			String key = String.valueOf(ant.getCurrent().getNumber()) + "to" + String.valueOf(j);
			//���L���]��0
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

				// �ۤw���|����ۤw
				if (i == j) {
					continue;
				}

				String key = String.valueOf(i) +"to"+ String.valueOf(j);

				City a = allCity.get(i);
				City b = allCity.get(j);

				double distance = (float) Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
				distanceTable.put(key, distance);
				// �O���X��l��
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