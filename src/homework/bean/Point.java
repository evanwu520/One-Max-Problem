package homework.bean;

public class Point {
	
	private float x;		//x�y��
	private float y;		//y�y��
	private float velocity; //�t�v
	private double value;	//�A����
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", velocity=" + velocity + ", value=" + value + "]";
	}
	
}
