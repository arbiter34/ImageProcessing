
public class Sharpen33Filter extends Filter {
	
	private static final int[][] matrix = {
		{ 0, -1,  0},
		{-1,  5, -1},
		{ 0, -1,  0}};
	
	private static final int divisor = 1;
	private static final int offset = 0;
	private static final int height = 3;
	private static final int width = 3;
	
	Sharpen33Filter() {
		super(matrix, divisor, offset, height, width);
	}
}
