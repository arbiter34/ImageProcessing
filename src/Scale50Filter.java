
public class Scale50Filter extends Filter {
	
	private static final float[][] matrix = {
		{ 0, -.125f,  0},
		{-.125f,  .5f, -.125f},
		{ 0, -.125f,  0}};
	
	private static final int divisor = 1;
	private static final int offset = 0;
	private static final int height = 3;
	private static final int width = 3;
	
	Scale50Filter() {
		super(matrix, divisor, offset, height, width);
	}
}
