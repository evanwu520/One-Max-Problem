package homework.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import homework.bean.Point;
import homework.util.CommonUtil;

public class PSOwithAckleyFunction {

	private int iterCount = 0;//舼计
	private int particleCount = 1;	//采计秖
	private int boundMax = 30;	//程娩
	private int boundMin = -30;	//程娩
	private float w = 1f;	//篋┦舦
	private float c1 = 1f;	//ō舦
	private float c2 = 1f;	//竤砰舦3
	private float velocityMax = 2.5f;
	private float velocityMin = -2.5f;
	
	Point socialOptimal = new Point();	//竤砰程竚の
	Point[] indivialOptimal= new Point[particleCount];	//程竚の
	Point[] currentPostion = new Point[particleCount];	//ヘ玡竚の	
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		new PSOwithAckleyFunction().run();
	}
	
	public void run(){

		PrintWriter writer = null;
		
		try{
			
			File f = new File(System.getProperty("user.dir")+"\\pos.result");
			
			if(f.exists()){
				f.delete();
			}
			
			writer = new PrintWriter(f , "UTF-8");
			init();
			
			for(int i=1; i<=iterCount; i++){
				writer.println("--------------------------------"+"舼"+i+"------------------------------------------");
				System.out.println("--------------------------------"+"舼"+i+"------------------------------------------");
				
				fitness();
				print(currentPostion, writer);
				updateVelocityAndPosition();
				
				
				writer.println("----------------------------------------------------------------------------");
				System.out.println("----------------------------------------------------------------------------");
			}
			
			writer.println("------------------------------indivial optimal------------------------------");
			System.out.println("------------------------------indivial optimal------------------------------");

			print(indivialOptimal, writer);
			
			writer.println("------------------------------social optimal------------------------------");
			System.out.println("------------------------------social optimal------------------------------");
			writer.println(socialOptimal.toString());
			System.out.println(socialOptimal.toString());
			
			System.out.printf("reuslt file path:%s", f.getPath());
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}

		}
//		Random rand = new Random();
//		System.out.println(((float)(rand.nextInt(10)))/10);
		
//		for(int i=0; i<=1000; i++){
//			System.out.println(CommonUtil.AckleyFuncion(i, i));
//			System.out.println(CommonUtil.AckleyFuncion(i*-1, i*-1));
//			
//			if(CommonUtil.AckleyFuncion(i, i) == 20 && CommonUtil.AckleyFuncion(i*-1, i*-1) ==20){
//				System.out.println(i);
//				break;
//			}
//		}
	}
	
	/**
	 * print
	 * @param points
	 * @param writer
	 */
	public void print(Point[] points, PrintWriter writer){
		
		for(Point p:points){
			writer.println(p.toString());
			System.out.println(p.toString());
		}
	}
	
	/**
	 * init
	 * @throws CloneNotSupportedException 
	 */
	public void init() throws CloneNotSupportedException{
		
		for(int i=0; i<particleCount; i++){
			
			Point p = new Point();
			Random rand = new Random();
			//random start point and random velocity
			p.setX(rand.nextInt(30+1+30)- 30);
			p.setY(rand.nextInt(30+1+30)- 30);
			p.setVelocityX(rand.nextFloat());
			p.setVelocityY(rand.nextFloat());
			p.setValue(9999999);
			
			indivialOptimal[i] = p.clone();
			socialOptimal = p.clone();
			currentPostion[i] = p.clone(); 
		}
		
	}
	
	/**
	 * fitness
	 * @throws CloneNotSupportedException 
	 */
	public void fitness() throws CloneNotSupportedException{
		
		for(int i=0; i<particleCount; i++){
			
			Point p = currentPostion[i];
			//evaluate
			p.setValue(CommonUtil.AckleyFuncion(p.getX(), p.getY()));
			
			if(indivialOptimal[i] == null){
				indivialOptimal[i] = new Point();
			}
			
			//replace indivail best value and location
			if(p.getValue() < indivialOptimal[i].getValue()){
				indivialOptimal[i] = p.clone();
			}
			
			//replace social best value and location
			if(indivialOptimal[i].getValue() < socialOptimal.getValue()){
				socialOptimal = indivialOptimal[i].clone();
			}
			
		}
	}
	
	/**
	 * updateVelocityAndPosition
	 */
	public void updateVelocityAndPosition(){
		
		for(int i=0; i<particleCount; i++){
			
			float newVelocityX = this.calculateVelocity(currentPostion[i], indivialOptimal[i], socialOptimal, "X");
			float newVelocityY = this.calculateVelocity(currentPostion[i], indivialOptimal[i], socialOptimal, "Y");
			
			float newX = 0;
			float newY = 0;
			
		    if(currentPostion[i].getX()>0 && newVelocityX>0){
		    	newX = currentPostion[i].getX() - newVelocityX;
		    }else if (currentPostion[i].getX()>0 && newVelocityX<0){
		    	newX = currentPostion[i].getX() + newVelocityX;
		    }else if (currentPostion[i].getX()<0 && newVelocityX>0){
		    	newX = currentPostion[i].getX() + newVelocityX;
		    }else if (currentPostion[i].getX()<0 && newVelocityX<0){
		    	newX = currentPostion[i].getX() - newVelocityX;
		    }
		    
		    if(currentPostion[i].getY()>0 && newVelocityY>0){
		    	newY = currentPostion[i].getY() - newVelocityY;
		    }else if (currentPostion[i].getY()>0 && newVelocityY<0){
		    	newY = currentPostion[i].getY() + newVelocityY;
		    }else if (currentPostion[i].getY()<0 && newVelocityY>0){
		    	newY = currentPostion[i].getY() + newVelocityY;
		    }else if (currentPostion[i].getY()<0 && newVelocityY<0){
		    	newY = currentPostion[i].getY() - newVelocityY;
		    }
		    
			
			
			System.out.println("newVelocityX:"+newVelocityX +" newVelocityY:"+newVelocityY);
			//禬娩
			if(newX > boundMax){
				newX = boundMax;
			}else if(newX <boundMin){
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
			currentPostion[i].setVelocityX(newVelocityX);
			currentPostion[i].setVelocityY(newVelocityY);
		}
	}
	
	/**
	 * calculateVelocity
	 * @param current
	 * @param individual
	 * @param social
	 * @return
	 */
	private float calculateVelocity(Point current, Point individual, Point social, String direct){
		
		float velocity = 0;
		
		Random rand = new Random();
//		((float)(rand.nextInt(10)))/10
		// random 0~1
		
		
		if("X".equalsIgnoreCase(direct)){
			velocity = w*current.getVelocityX() +  c1*(((float)(rand.nextInt(10))+1)/10) * (individual.getX() - current.getX()) 
					+  c2*(((float)(rand.nextInt(10))+1)/10) * (social.getX()- current.getX()); 
		}else if("Y".equalsIgnoreCase(direct)){
			velocity = w*current.getVelocityY() +  c1*(((float)(rand.nextInt(10))+1)/10) * (individual.getY() - current.getY()) 
					+  c2*(((float)(rand.nextInt(10))+1)/10) * (social.getY()- current.getY()); 
		}
		
		
		
		if(velocity> velocityMax){
			velocity = velocityMax;
		}
		
		if(velocity< velocityMin){
			velocity = velocityMin;
		}
//		
		
		return velocity;
	}
	
}


