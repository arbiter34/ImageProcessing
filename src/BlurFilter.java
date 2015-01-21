
public class BlurFilter extends Filter {
	
	private static final float[][] matrix = {
		{1, 1, 1},
		{1, 1, 1},
		{1, 1, 1}};
	
	private static final int divisor = 9;
	private static final int offset = 0;
	private static final int height = 3;
	private static final int width = 3;
	
	BlurFilter() {
		super(matrix, divisor, offset, height, width);
	}
}
