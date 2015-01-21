
public class FilterFactory {
	
	public Filter getFilter(String name) {
		if (name == "EdgeDetection55") {
			return new EdgeDetection55Filter();
		} else if (name == "EdgeDetection33") {
			return new EdgeDetection33Filter();
		} else if (name == "Blur") {
			return new BlurFilter();
		} else if (name == "Sharpen33") {
			return new Sharpen33Filter();
		} else if (name == "Scale50") {
			return new Scale50Filter();
		}
		return null;
	}
}
