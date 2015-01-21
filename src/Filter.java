
public abstract class Filter {
	private int divisor;
	private int offset;
	private int[][] matrix;
	private int height;
	private int width;
	
	Filter(int[][] matrix, int divisor, int offset, int width, int height) {
		this.matrix = matrix;
		this.divisor = divisor;
		this.offset = offset;
		this.width = width;
		this.height = height;
	}
	
	public int getKernelValue(int y, int x) {
		return this.matrix[y][x];
	}
	
	public int getDivisor() {
		return this.divisor;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
}
