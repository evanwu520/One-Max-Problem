package homework.bean;

public class Point implements Cloneable {

	private float x; // x座標
	private float y; // y座標
	private float velocityX; // x速率
	private float velocityY; // y速率
	private double value; // 適應值

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
