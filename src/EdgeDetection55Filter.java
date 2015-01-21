
public class EdgeDetection55Filter extends Filter {
	
	private static final float[][] matrix = {
		{-1, -1, -1, -1, -1},
		{-1,  0,  0,  0, -1},
		{-1,  0, 16,  0, -1},
		{-1,  0,  0,  0, -1},
		{-1, -1, -1, -1, -1}};
	
	private static final int divisor = 1;
	private static final int offset = 0;
	private static final int height = 5;
	private static final int width = 5;
	
	EdgeDetection55Filter() {
		super(matrix, divisor, offset, height, width);
	}
}

