package model;

public class Shape {

	public static final String REC = "Rectangle";
	public static final String SQR = "Square";
	public static final String CIR = "Circle";

	private String shape = null;
	private Integer width = null;
	private Integer height = null;
	private Integer radius = null;
	private Double area = null;

	public Shape(String shape, Integer size) {
		switch (shape) {
		case Shape.REC:
			System.err.println("Wrong constructor for shape " + shape);
			break;
		case Shape.SQR:
			this.shape = shape;
			this.width = size;
			this.height = size;
			this.area = (double) size * size;
			break;
		case Shape.CIR:
			this.shape = shape;
			this.radius = size;
			this.area = Math.PI * size * size;
			break;
		}
	}

	public Shape(int size1, int size2) {
		this.shape = REC;
		this.width = size1;
		this.height = size2;
		this.area = (double) size1 * size2;
	}

	public String getShape() {
		return shape;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getRadius() {
		return radius;
	}

	public Double getArea() {
		return area;
	}
	
	@Override
	public String toString() {
		return this.shape;
	}
	
}
