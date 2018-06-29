package homework.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ant implements Cloneable, Comparator<Ant>{
	
	private City current;
	private List<Integer> visit = new ArrayList<>();
	private double totalDistance;
	
    public Ant clone() throws CloneNotSupportedException {  
        return (Ant) super.clone();  
    }  

	public City getCurrent() {
		return current;
	}

	public void setCurrent(City current) {
		this.current = current;
	}
	public List<Integer> getVisit() {
		return visit;
	}

	public void setVisit(List<Integer> visit) {
		this.visit = visit;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	@Override
	public int compare(Ant o1, Ant o2) {
		
		
		if (o1.getTotalDistance() > o2.getTotalDistance()) return 1;
		if (o1.getTotalDistance() < o2.getTotalDistance()) return -1;
		
		return 0;
	}
	
//	public void clean(){
//		current = null;
//		visit = new ArrayList<>();
//		totalDistance = 0f;
//	}

}
