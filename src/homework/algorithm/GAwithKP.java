package homework.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import homework.bean.Item;
import homework.bean.Knapsack;
import homework.util.CommonUtil;

public class GAwithKP implements AI {

	private int itemCount = 30; //物品種類
	private int populatonSize = 20;
	private int tournamentGrop = 4;
	private float crossOverRate = 0.6f;
	private float mutationRate = 0.1f;
	private Item[] table = new Item[itemCount];
	private int weightBearing; // 背包負重
	private int iterCount = 30;//繁值代數
	private float deduct = 5.f; //扣分比重
	
	public GAwithKP(){
		
	}
	
	public GAwithKP(HashMap<String, Object> settingMap){
		
		itemCount = (int) settingMap.get("itemCount");
		populatonSize = (int) settingMap.get("populatonSize");
		tournamentGrop = (int) settingMap.get("tournamentGrop");
		crossOverRate = (float) settingMap.get("crossOverRate");
		mutationRate = (float) settingMap.get("mutationRate");
		iterCount = (int) settingMap.get("iterCount");
	}

	@Override
	public TreeMap<Integer, String> serach() throws Exception {
		
		List<Knapsack> bagList = new ArrayList<>();

		// 產生 index 價值重量應表
		weightBearing = genKPtable(table);

		for(Item it:table){
			System.out.println(it.toString());
		}
		
		// 亂數產生population
		initPopulaton(bagList, populatonSize);

        for(int i=0; i<iterCount; i++){
        	fitness(bagList);
    		selection(bagList);
    		crossOver(bagList);
    		mutation(bagList);
    		print(bagList, i+1);
        }
		return null;
	}
	
//	public static void main(String args []) throws Exception{
//		test();
//	}
	
	
	public static void test() throws Exception{
		GAwithKP ai = new GAwithKP();
		ai.serach();
	}
	
	/**
	 * print
	 * @param bagList
	 * @param i
	 */
	private void print (List<Knapsack> bagList, int i){
		
		Collections.sort(bagList, new Knapsack());
		
		for (Knapsack k : bagList) {
			
			if(k.isValid()){
				System.out.printf("第%d代  %s\n", i, k.toString());
				break;
			}
		}
	}

	/**
	 * initPopulaton
	 * @param list
	 * @param size
	 */
	private void initPopulaton(List<Knapsack> list, int size) {

		while (size > 0) {

			Knapsack knapsack = new Knapsack(weightBearing);
			knapsack.setS(CommonUtil.genRandomPoint(itemCount));
			putItem(knapsack);

			if (knapsack.isValid()) {
				list.add(knapsack);
				size--;
			}
		}
	}

	/**
	 * fitness
	 * @param n
	 */
	private void fitness(List<Knapsack> list) {

		for (Knapsack knapsack : list) {
			
			putItem(knapsack);
			
			//超過重量扣價值
			if(!knapsack.isValid()){
				float newEv =  knapsack.getEv() - ((knapsack.getWeight() - knapsack.getWeightBearing()) * deduct);
				knapsack.setEv(newEv);
			}
		}
	}

	/**
	 * putItem
	 * @param knapsack
	 */
	private void putItem(Knapsack knapsack) {
		
		knapsack.getItems().clear();

		for (int i = 0; i < knapsack.getS().length(); i++) {

			if (knapsack.getS().charAt(i) == '1') {
				// 對應重量及價值,放進背包
				knapsack.getItems().add(table[i]);
			}
		}
		// 計算重量及總和及價值
		knapsack.calculation();
	}

	/**
	 * selection
	 * @param list
	 * @param selectCount
	 * @param perGroup
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private void selection(List<Knapsack> list) throws CloneNotSupportedException {
		
		tournament(list);
	}
	
	/**
	 * tournament
	 * @param list
	 * @param perGroup
	 */
	private void tournament(List<Knapsack> list){

		List<Knapsack> newList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			List<Knapsack> bagGroup = new ArrayList<>();
			List<Integer> indexList = CommonUtil.getRandomIndex(list.size(), tournamentGrop, false);

			for (Integer index : indexList) {
				bagGroup.add(list.get(index.intValue()));
			}
			// 排序，價值/重量值越大排序越前面
			Collections.sort(bagGroup, new Knapsack());
			// 取第一名
			newList.add(bagGroup.get(0));
		}
		list.clear();
		list.addAll(newList);
	}

	/**
	 * crossOver
	 * @param list
	 * @throws CloneNotSupportedException 
	 */
	private void crossOver(List<Knapsack> list) throws CloneNotSupportedException {
		
		List<Knapsack> crossOverList = new ArrayList<>();
		
		//CrossOver機率
		int crossOverCount = (int) (list.size() * crossOverRate);
		
		//隨機選擇CrossOver index
		List<Integer> crossOverGroup= CommonUtil.getRandomIndex(list.size(), crossOverCount, false);
		
		// sort the list
		Collections.sort(crossOverGroup);
		
		//移除要交配的，將要交配的放進crossOverList
		for(int i=crossOverGroup.size()-1; i>=0; i--){
			crossOverList.add(list.get(crossOverGroup.get(i).intValue()).clone());
			list.remove(crossOverGroup.get(i).intValue());
		}
		
		//排序好到不好
		Collections.sort(crossOverList, new Knapsack());
		
		int pairCount = (crossOverCount - (crossOverCount % 2))/2;
		//頭尾交配
		for(int x=0; x <pairCount; x++){
			
			int y = crossOverCount-1-x;
			
			int crossOverIndex = (int) (Math.random() * itemCount) + 1;
			String xValue = crossOverList.get(x).getS().substring(0, crossOverIndex) + crossOverList.get(y).getS().substring(crossOverIndex, itemCount);
			String yValue = crossOverList.get(y).getS().substring(0, crossOverIndex) + crossOverList.get(x).getS().substring(crossOverIndex, itemCount);
			
			String[] sonArray = {xValue, yValue};
			
			for(String son: sonArray){
				Knapsack k = new Knapsack(weightBearing);
				k.setS(son);
				putItem(k);
				list.add(k);
			}
		}
	}

	/**
	 * mutation
	 * @param list
	 * @throws CloneNotSupportedException 
	 */
	private void mutation(List<Knapsack> list) throws CloneNotSupportedException {
		
		List<Knapsack> mutationList = new ArrayList<>();
		//mutation機率
		int mutationCount = (int) (list.size() * mutationRate);
		
		// 隨機選擇CrossOver index
		List<Integer> mutationGroup = CommonUtil.getRandomIndex(list.size(), mutationCount, false);

		// sort the list
		Collections.sort(mutationGroup);

		// 移除要突變的，將要突變的放進mutationList
		for (int i = mutationGroup.size() - 1; i >= 0; i--) {
			mutationList.add(list.get(mutationGroup.get(i).intValue()).clone());
			list.remove(mutationGroup.get(i).intValue());
		}
		
		for(Knapsack mutation:mutationList){
			
			int mutationIndex = (int) (Math.random() * itemCount) ;
			
			String newValue = "";
			
			if(mutation.getS().charAt(mutationIndex) == '1'){
				newValue =  mutation.getS().substring(0, mutationIndex) +"0"+ mutation.getS().substring(mutationIndex+1, itemCount);
			}else{
				newValue =  mutation.getS().substring(0, mutationIndex) +"1"+ mutation.getS().substring(mutationIndex+1, itemCount);
			}
			
			mutation.setS(newValue);
			putItem(mutation);
			list.add(mutation);
		}
	}

	/**
	 * 產生對應表
	 * @param table
	 * @return
	 */
	private int genKPtable(Item[] table) {

		int totalWeight = 0;

		for (int i = 0; i < table.length; i++) {

			int value = (int) (Math.random() * 10) + 1;
			int weight = (int) (Math.random() * 10) + 1;
			table[i] = new Item(value, weight);

			totalWeight += weight;
		}

		return totalWeight;
	}
}
