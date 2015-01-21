
public class Threshold {
	
	private float hMin, hMax, sMin, sMax, vMin, vMax;
	
	Threshold(float hMin, float hMax, float sMin, float sMax, float vMin, float vMax) {
		this.hMin = hMin;
		this.hMax = hMax;
		this.sMin = sMin;
		this.sMax = sMax;
		this.vMin = vMin;
		this.vMax = vMax;
	}
	
	
	public boolean thresholdRgbArray(int[] rgbArray) {
		  float[] hsvArray = new float[4];
		  
		  hsvArray = RGBtoHSV(rgbArray);

		  if (hsvArray[1] > 27 || hsvArray[1] < 18) {
			  return false;
		  } else if (hsvArray[2] > 1 || hsvArray[2] < 0.1569f) {
			  return false;
		  } else if (hsvArray[3] > 1 || hsvArray[3] < .3530f) {
			  return false;
		  } else {
			  return true;
		  } 
	}
	
	 //algorithm derived from wikipedia info
	 private float[] RGBtoHSV(int[] rgbArray) {
	 	float[] hsvArray = new float[4];
	 	float[] fRgbArray = new float[4];
	 	for (int i = 1; i < 4; i++) {
	 		fRgbArray[i] = rgbArray[i] / 255.0f;
	 	}

	 	float min, max, delta;

	 	min = Math.min(fRgbArray[1], Math.min(fRgbArray[2], fRgbArray[3]));
	 	max = Math.max(fRgbArray[1], Math.max(fRgbArray[2], fRgbArray[3]));
	 	hsvArray[3] = max;
	 	delta = max - min;
	 	//grey black white
	 	if (min == max) {
	 		hsvArray[1] = 0;
	 		hsvArray[2] = 0;
	 		hsvArray[3] = min;
	 		return hsvArray;
	 	}
	 	
	 	float d = (fRgbArray[1] == min) ? fRgbArray[2]-fRgbArray[3] : ((fRgbArray[3] == min) ? fRgbArray[1]-fRgbArray[2] : fRgbArray[3]-fRgbArray[1]);
	 	float h = (fRgbArray[1] == min) ? 3 : ((fRgbArray[3] == min) ? 1 : 5);
	 	hsvArray[1] = 60*(h - d/delta);
	 	hsvArray[2] = delta/max;
	 	hsvArray[3] = max;
	 	
	 	return hsvArray;
	 }

	 //algorithm from http://www.cs.rit.edu/~ncs/color/t_convert.html
	 private int[] HSVtoRGB(int [] hsvArray) {
		int[] rgbArray = new int[4];
	 	int i;
	 	float f, p, q, t;
		rgbArray[0] = hsvArray[0];
	 	if( hsvArray[2] == 0 ) {
	 		// achromatic (grey)
	 		rgbArray[1] = hsvArray[3];
	 		rgbArray[2] = hsvArray[3];
	 		rgbArray[3] = hsvArray[3];
	 		return rgbArray;
	 	}
	 	hsvArray[1] /= 60;			// sector 0 to 5
	 	i = (int)Math.floor( hsvArray[1] );
	 	f = hsvArray[1] - i;	// factorial part of h
	 	p = hsvArray[3] * ( 1 - hsvArray[2] );
	 	q = hsvArray[3] * ( 1 - hsvArray[2] * f );
	 	t = hsvArray[3] * ( 1 - hsvArray[2] * ( 1 - f ) );
	 	switch( i ) {
	 		case 0:
	 			rgbArray[1] = hsvArray[3];
	 			rgbArray[2] = (int)t;
	 			rgbArray[3] = (int)p;
	 			break;
	 		case 1:
	 			rgbArray[1] = (int)q;
	 			rgbArray[2] = hsvArray[3];
	 			rgbArray[3] = (int)p;
	 			break;
	 		case 2:
	 			rgbArray[1] = (int)p;
	 			rgbArray[2] = hsvArray[3];
	 			rgbArray[3] = (int)t;
	 			break;
	 		case 3:
	 			rgbArray[1] = (int)p;
	 			rgbArray[2] = (int)q;
	 			rgbArray[3] = hsvArray[3];
	 			break;
	 		case 4:
	 			rgbArray[1] = (int)t;
	 			rgbArray[2] = (int)p;
	 			rgbArray[3] = hsvArray[3];
	 			break;
	 		default:		// case 5:
	 			rgbArray[1] = hsvArray[3];
	 			rgbArray[2] = (int)p;
	 			rgbArray[3] = (int)q;
	 			break;
	 	}
	 	return rgbArray;
	 }
}
