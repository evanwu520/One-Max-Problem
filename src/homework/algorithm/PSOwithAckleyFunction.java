package homework.algorithm;

import java.util.Random;

import homework.bean.Point;
import homework.util.CommonUtil;

public class PSOwithAckleyFunction {

	//http://ccy.dd.ncu.edu.tw/~chen/resource/pso/pso.htm
	int particleCount = 20;	//采计秖 
	float c1 = 0.1f;	//舦1
	float c2 = 0.2f;	//舦2
	float boundMax = 10;	//程娩
	float boundMin = 0;	//程娩
	
	Point socialOptimal = new Point();	//竤砰程竚の
	Point[] indivialOptimal= new Point[particleCount];	//程竚の
	Point[] currentPostion = new Point[particleCount];		
	
	public static void main(String[] args) {
		
		PSOwithAckleyFunction pso = new PSOwithAckleyFunction();
		
		pso.init();
		
		
		pso.fitness();
		pso.updateVelocityAndPosition();
		
		System.out.println("-----------------------------");
		
		
		pso.fitness();
		pso.updateVelocityAndPosition();
		
		System.out.println(pso.socialOptimal.toString());
//		System.out.println(CommonUtil.AckleyFuncion(10, 10));
//		System.out.println(CommonUtil.AckleyFuncion(8.792031f, 8.412519f));
		//8.792031 8.412519
	}
	
	/**
	 * init
	 */
	public void init(){
		
		for(int i=0; i<particleCount; i++){
			
			Point p = new Point();
			Random rand = new Random();
			//random start point and random velocity
			p.setX(rand.nextFloat() * (boundMax - boundMin) + boundMin);	
			p.setY(rand.nextFloat() * (boundMax - boundMin) + boundMin);
			p.setVelocity((rand.nextFloat() * (boundMax/20 - boundMin/20) +boundMin/20));
			currentPostion[i] = p; 
		}
		
	}
	
	/**
	 * fitness
	 */
	public void fitness(){
		
		for(int i=0; i<particleCount; i++){
			
			Point p = currentPostion[i];
			p.setValue(CommonUtil.AckleyFuncion(p.getX(), p.getY()));
			
			if(indivialOptimal[i] == null){
				indivialOptimal[i] = new Point();
			}
			
			//replace indivail best value and location
			if(p.getValue() > indivialOptimal[i].getValue()){
				indivialOptimal[i].setValue(p.getValue());
				indivialOptimal[i].setX(p.getX());
				indivialOptimal[i].setY(p.getY());
			}
			
			//replace social best value and location
			if(indivialOptimal[i].getValue() > socialOptimal.getValue()){
				socialOptimal.setValue(indivialOptimal[i].getValue());
				socialOptimal.setX(indivialOptimal[i].getX());
				socialOptimal.setY(indivialOptimal[i].getY());
			}
			
			System.out.println(currentPostion[i].toString());
		}
	}
	
	/**
	 * updateVelocityAndPosition
	 */
	public void updateVelocityAndPosition(){
		
		for(int i=0; i<particleCount; i++){
			
			float newVelocity = this.calculateVelocity(currentPostion[i], indivialOptimal[i], socialOptimal);
			
			currentPostion[i].setVelocity(newVelocity);
			
			float newX = currentPostion[i].getX() + newVelocity;
			float newY = currentPostion[i].getY() + newVelocity;
			
			
			//禬娩
			if(newX > boundMax){
				newX = boundMax;
			}else if(newX < boundMin){
				newX = boundMin;
			}
			//禬娩
			if(newY > boundMax){
				newY = boundMax;
			}else if(newY < boundMin){
				newY = boundMin;
			}
			
			currentPostion[i].setX(newX);
			currentPostion[i].setY(newY);
			
		}
	}
	
	/**
	 * calculateVelocity
	 * @param current
	 * @param individual
	 * @param social
	 * @return
	 */
	private float calculateVelocity(Point current, Point individual, Point social){
		
		double velocity = 0;
		
		Random rand = new Random();
		
		velocity = current.getVelocity() +  c1*(rand.nextInt(5+1+5)- 5) * (individual.getValue() - current.getValue()) 
		+  c2*(rand.nextInt(5+1+5)- 5) * (social.getValue() - current.getValue()); 
		
		return (float)velocity;
	}
	
}


