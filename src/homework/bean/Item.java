package homework.bean;

public class Item {
	
	private int value;	//»ù­È
	private int weight;  //­«¶q
	/**
	 * constructor
	 * @param value
	 * @param weight
	 */
	public Item(int value, int weight){
		this.value = value;
		this.weight = weight;
	}
	
	public double getValue() {
		return value;
	}
	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "Item [value=" + value + ", weight=" + weight + "]";
	}
	

}
