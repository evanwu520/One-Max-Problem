package homework.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Knapsack implements Cloneable, Comparator<Knapsack> {
	
	private int weightBearing;	//背包負重
	
	private List<Item> items = new ArrayList<>();
	private int weight;
	private int value;
	private String s;
	private float ev;
	
	public Knapsack(){
		
	}
	
	public Knapsack(int weight){
		this.weightBearing = weight/2; //背包承重為全重量的一半
	}
	
    public Knapsack clone() throws CloneNotSupportedException {  
        return (Knapsack) super.clone();  
    }  

	public int getWeightBearing() {
		return weightBearing;
	}

	public int getWeight() {
		
		return weight;
	}

	public int getValue() {
		
		return value;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	
	public float getEv() {
		return ev;
	}

	public void setEv(float ev) {
		this.ev = ev;
	}

	public boolean isValid(){
		return this.getWeight() <= this.weightBearing;
	}
	
	public void calculation(){
		
		this.weight = 0;
		this.value = 0;
		
		for(Item item:this.items){
			this.value += item.getValue();
			this.weight += item.getWeight();
		}
		
		this.ev = value;
	}

	@Override
	public int compare(Knapsack o1, Knapsack o2) {
		
		float change1 = o1.getEv();
		float change2 = o2.getEv();
		
		
		if (change1 < change2) return 1;
		if (change1 > change2) return -1;
		
		return 0;
	}

	@Override
	public String toString() {
		return  "總價值:"+ev+"	價值:"+ value+"	重量:"+weight+"	限重:" +weightBearing +"	包背內容物:"+getS();
	}
	
	
	
}
