
public class EdgeDetection33Filter extends Filter {
	
	private static final float[][] matrix = {
		{-1, -1, -1},
		{-1, 8, -1},
		{-1, -1, -1}};
	
	private static final int divisor = 1;
	private static final int offset = 0;
	private static final int height = 3;
	private static final int width = 3;
	
	EdgeDetection33Filter() {
		super(matrix, divisor, offset, height, width);
	}
}

