package homework.bean;

public class Point implements Cloneable {

	private float x; // x�y��
	private float y; // y�y��
	private float velocityX; // x�t�v
	private float velocityY; // y�t�v
	private double value; // �A����

	public Point clone() throws CloneNotSupportedException {
		return (Point) super.clone();
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
	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", value=" + value + "]";
	}

}
