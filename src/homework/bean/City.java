package homework.bean;

public class City {
	
	private int number;
	private float x;
	private float y;
	
	public City(int number, float x, float y){
		this.number = number;
		this.x = x;
		this.y = y;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
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

	@Override
	public String toString() {
		return "City [number=" + number + ", x=" + x + ", y=" + y + "]";
	}
	
}
