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

	private int itemCount = 30; //���~����
	private int populatonSize = 20;
	private int tournamentGrop = 4;
	private float crossOverRate = 0.6f;
	private float mutationRate = 0.1f;
	private Item[] table = new Item[itemCount];
	private int weightBearing; // �I�]�t��
	private int iterCount = 30;//�c�ȥN��
	private float deduct = 5.f; //������
	
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

		// ���� index ���ȭ��q����
		weightBearing = genKPtable(table);

		for(Item it:table){
			System.out.println(it.toString());
		}
		
		// �üƲ���population
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
				System.out.printf("��%d�N  %s\n", i, k.toString());
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
			
			//�W�L���q������
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
				// �������q�λ���,��i�I�]
				knapsack.getItems().add(table[i]);
			}
		}
		// �p�⭫�q���`�M�λ���
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
			// �ƧǡA����/���q�ȶV�j�ƧǶV�e��
			Collections.sort(bagGroup, new Knapsack());
			// ���Ĥ@�W
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
		
		//CrossOver���v
		int crossOverCount = (int) (list.size() * crossOverRate);
		
		//�H�����CrossOver index
		List<Integer> crossOverGroup= CommonUtil.getRandomIndex(list.size(), crossOverCount, false);
		
		// sort the list
		Collections.sort(crossOverGroup);
		
		//�����n��t���A�N�n��t����icrossOverList
		for(int i=crossOverGroup.size()-1; i>=0; i--){
			crossOverList.add(list.get(crossOverGroup.get(i).intValue()).clone());
			list.remove(crossOverGroup.get(i).intValue());
		}
		
		//�ƧǦn�줣�n
		Collections.sort(crossOverList, new Knapsack());
		
		int pairCount = (crossOverCount - (crossOverCount % 2))/2;
		//�Y����t
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
		//mutation���v
		int mutationCount = (int) (list.size() * mutationRate);
		
		// �H�����CrossOver index
		List<Integer> mutationGroup = CommonUtil.getRandomIndex(list.size(), mutationCount, false);

		// sort the list
		Collections.sort(mutationGroup);

		// �����n���ܪ��A�N�n���ܪ���imutationList
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
	 * ���͹�����
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
