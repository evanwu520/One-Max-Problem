package homework.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import homework.bean.Point;
import homework.util.CommonUtil;

public class PSOwithAckleyFunction {

	private int iterCount = 5;//�|�N��
	private int particleCount = 10;	//�ɤl�ƶq 
	private float c1 = 1.5f;	//�ӤH�v��1
	private float c2 = 1.5f;	//�s���v��2
	private float boundMax = 30;	//�̤j���
	private float boundMin = 0;	//�̤p���
	
	Point socialOptimal = new Point();	//�s��̦n����m�έ�
	Point[] indivialOptimal= new Point[particleCount];	//�ӤH�̦n����m�έ�
	Point[] currentPostion = new Point[particleCount];	//�ثe��m�έ�	
	
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
			
			for(int i=1; i<iterCount; i++){
				writer.println("--------------------------------"+"�|�N"+i+"------------------------------------------");
				System.out.println("--------------------------------"+"�|�N"+i+"------------------------------------------");
				
				fitness();
				updateVelocityAndPosition();
				print(currentPostion, writer);
				
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
	 */
	public void init(){
		
		for(int i=0; i<particleCount; i++){
			
			Point p = new Point();
			Random rand = new Random();
			//random start point and random velocity
			p.setX(rand.nextFloat() * (boundMax - boundMin) + boundMin);	
			p.setY(rand.nextFloat() * (boundMax - boundMin) + boundMin);
			p.setVelocity((rand.nextFloat() * (boundMax/20) - boundMin/20) +boundMin/20);
			currentPostion[i] = p; 
		}
		
	}
	
	/**
	 * fitness
	 */
	public void fitness(){
		
		for(int i=0; i<particleCount; i++){
			
			Point p = currentPostion[i];
			//evaluate
			p.setValue(CommonUtil.AckleyFuncion(p.getX(), p.getY()));
			
			if(indivialOptimal[i] == null){
				indivialOptimal[i] = new Point();
			}
			
			//replace indivail best value and location
			if(p.getValue() > indivialOptimal[i].getValue()){
				indivialOptimal[i].setValue(p.getValue());
				indivialOptimal[i].setX(p.getX());
				indivialOptimal[i].setY(p.getY());
				indivialOptimal[i].setVelocity(p.getVelocity());
			}
			
			//replace social best value and location
			if(indivialOptimal[i].getValue() > socialOptimal.getValue()){
				socialOptimal.setValue(indivialOptimal[i].getValue());
				socialOptimal.setX(indivialOptimal[i].getX());
				socialOptimal.setY(indivialOptimal[i].getY());
				socialOptimal.setVelocity(indivialOptimal[i].getVelocity());
			}
			
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
			
			
			//�W�X���
			if(newX > boundMax){
				newX = boundMax;
			}else if(newX < boundMin){
				newX = boundMin;
			}
			//�W�X���
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
		// random -2~2
		velocity = current.getVelocity() +  c1*(rand.nextInt(2+1+2)- 2) * (individual.getValue() - current.getValue()) 
		+  c2*(rand.nextInt(2+1+2)- 2) * (social.getValue() - current.getValue()); 
		
		return (float)velocity;
	}
	
}


