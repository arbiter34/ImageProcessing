import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;

class IMP implements MouseListener{
   JFrame frame;
   JPanel mp;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   double RED_WEIGHT = 0.299;
   double GREEN_WEIGHT = 0.587;
   double BLUE_WEIGHT = 0.114;

  FilterFactory ff = new FilterFactory();
   //Instance Fields you will be using below
   
   //This will be your height and width of your 2d array
   int height=0, width=0;
   
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Image Processing Software by Hunter");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu functions = getFunctions();
      JMenu filters = getFilters();
      frame.addWindowListener(new WindowAdapter(){
              public void windowClosing(WindowEvent ev){quit();}
            });
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      bar.add(file);
      bar.add(functions);
      bar.add(filters);
      frame.setSize(600, 600);
      mp = new JPanel();
      mp.setBackground(new Color(0, 0, 0));
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      butPanel.setBackground(Color.black);
      start = new JButton("start");
      start.setEnabled(false);
      start.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){ fun2(); }
           });
      butPanel.add(start);
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      frame.setVisible(true);      
   }
   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
  private JMenu getFunctions()
  {
     JMenu fun = new JMenu("Functions");     
      
      JMenuItem secondItem = new JMenuItem("Convert to Grayscale");
      secondItem.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){fun2();}
           });
           
      fun.add(secondItem);
      
      JMenuItem fourthItem = new JMenuItem("Find Orange Object");
      fourthItem.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent evt){fun4();}
           });
           
      fun.add(fourthItem);
      
      return fun; 

  }
  
  private JMenu getFilters() {
	  JMenu filterMenu = new JMenu("Filters");
      JMenuItem Edge33Item = new JMenuItem("3x3 Edge Detection");
      Edge33Item.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent evt){fun3("EdgeDetection33");}
      });
      filterMenu.add(Edge33Item);
      
      JMenuItem Edge55Item = new JMenuItem("5x5 Edge Detection");
      Edge55Item.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent evt){fun3("EdgeDetection55");}
      });
      filterMenu.add(Edge55Item);
      
      JMenuItem SharpenItem = new JMenuItem("3x3 Sharpen");
      SharpenItem.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent evt){fun3("Sharpen33");}
      });
      filterMenu.add(SharpenItem);
      
      JMenuItem BlurItem = new JMenuItem("3x3 Blur");
      BlurItem.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent evt){fun3("Blur");}
      });
      filterMenu.add(BlurItem);
      
      /*JMenuItem ScaleItem = new JMenuItem("50% Scale");
      ScaleItem.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent evt){fun3("Scale50");}
      });
      filterMenu.add(ScaleItem);*/
      
      return filterMenu;
  }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
     int option = chooser.showOpenDialog(frame);
     if(option == JFileChooser.APPROVE_OPTION) {
       pic = chooser.getSelectedFile();
       img = new ImageIcon(pic.getPath());
      } else {
    	  return;
      }
     width = img.getIconWidth();
     height = img.getIconHeight(); 
     
     JLabel label = new JLabel(img);
     label.addMouseListener(this);
     pixels = new int[width*height];
     
     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     mp.add(label);
     
     mp.revalidate();
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
      
     
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {
        for(int i = 0; i<width*height; i++)
             pixels[i] = results[i]; 
       Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));
       
       PixelGrabber pg = new PixelGrabber(img2, 0, 0, width, height, pixels, 0, width );
       try{
           pg.grabPixels();
       }catch(InterruptedException e)
         {
            System.err.println("Interrupted waiting for pixels");
            return;
         }
       for(int i = 0; i<width*height; i++)
          results[i] = pixels[i];  
       turnTwoDimensional();

      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.setBackground(new Color(0, 0, 0));
       mp.add(label2);
     
       mp.revalidate(); 
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
       for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          pixels[i*width+j] = picture[i][j];
      Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
     
       mp.revalidate(); 
   
    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
    }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */

  /*
   * fun2
   * grayscale conversion
   */  
  private void fun2()
  {
	  for (int y = 0; y < height; y++) {
		  for (int x = 0; x < width; x++) {
			  //for each pixel convert grayscale
			  picture[y][x] = convertPixelToGrayscale(picture[y][x]);
		  }
	  }
      resetPicture();
  }
  
  
  private int convertPixelToGrayscale(int pixel) {
	  int rgbArray[] = new int[4];
	  
	  //grab pixels as individual ints
	  rgbArray = getPixelArray(pixel);
	  
	  //calculate luminance average via colors weights(wikipedia)
	  int luminance_average = (int) ((rgbArray[1]*RED_WEIGHT)+(rgbArray[2]*GREEN_WEIGHT)+(rgbArray[3]*BLUE_WEIGHT));
	  
	  //replace RGB with luminance average
	  for (int i = 1; i < 4; i++) {
		  rgbArray[i] = luminance_average;
	  }
	  
	  //convert 4 int array back to single int
	  pixel = getPixels(rgbArray);
	  
	  //return int value
	  return pixel;
  }
  
  /*
   * Clamp rgb values for 0 <= X <= 255
   */
  private int clamp(int val) {
	  if (val < 0) {
		  return 0;
	  }
	  if (val > 255) {
		  return 255;
	  }
	  return val;
  }
  
  /*
   * Image Convolution Function
   * Input: Takes name of convolution kernel to apply
   * Output: None - redraws picture
   */
  private void fun3(String filterName)
  {
	  //init new 2d int array for new image values
	  int[][] temp_pic = new int[height][width];
	  
	  //get filter from FilterFactory
	  Filter filter = ff.getFilter(filterName);
	  
	  //Check if filter is valid - display error and return gracefully if not
	  if (filter == null) {
		  JOptionPane.showMessageDialog(mp, "The requested filter does not exist." ,"Filter Error", JOptionPane.PLAIN_MESSAGE);
		  return;
	  }
	  
	  //unnecessary copy of picture array - could remove
	  for (int y = 0; y < height; y++) {
		  for (int x = 0; x < width; x++) {
			  temp_pic[y][x] = picture[y][x];
		  }
	  }
	  
	  //iterate over every pixel in picture array
	  for (int y = 0; y < height; y++) {
		  for (int x = 0; x < width; x++) {
			  //new rgbArray
			  int[] rgbArray = new int[4];
			  
			  //init rgbArray to 0 due to += in kernel loop
			  for (int i = 0; i < 4; i++) {
				  rgbArray[i] = 0;
			  }
			  
			  //iterate over convolution kernel
			  for (int i = 0; i < filter.getHeight(); i++) {
				  //calc new y value for kernel x, y relative to main pixel
				  int new_y = y + (i-(int)(filter.getHeight()/2));
				  
				  //check if out of bounds, if so skip(easiest edge handling)
				  if (new_y < 0 || new_y >= height) {
					  continue;
				  }
				  for ( int j = 0; j < filter.getWidth(); j++) {
					  //calc new x value for kernel x, y relative to main pixel
					  int new_x = x + (j-(int)(filter.getWidth()/2));
					  //check if out of bounds, if so skip
					  if (new_x < 0 || new_x >= width) {
						  continue;
					  }
					  //add (r|g|b) value to calculated pixel value
					  for (int k = 1; k < 4; k++) {
						  rgbArray[k] += (int)(filter.getKernelValue(i, j) * (float)((picture[new_y][new_x] >> (24-(8*k))) & 0xff));
					  }
				  }
			  }
			  
			  //calculate value to divisor, then clamp
			  for (int i = 1; i < 4; i++) {
				  rgbArray[i] = clamp(rgbArray[i]/filter.getDivisor())+filter.getOffset();
			  }
			  //grab original alpha value
			  rgbArray[0] = (picture[y][x] >> 24) & 0xff;
			  //place new value in new array
			  temp_pic[y][x] = getPixels(rgbArray);
		  }
	  }
	  //change picture reference to temp_pic
	  picture = temp_pic;
	  
	  //redraw picture
      resetPicture();
  }
  
  /*
   * fun4
   */
  private void fun4()
  {
	  // min = (18, 50, 90) and max = (27, 255, 255) - orange
	  //Create new threshold object(used for RGBtoHSV and then threshold check based on creator arguments
	  Threshold t = new Threshold(5f, 27f, 0.3f, 1f, 0.3f, 1f);
	  
	  
	  for (int y = 0; y < height; y++) {
		  for ( int x = 0; x < width; x++) {
			  //if in threshold value, turn white; else turn black
			  if (t.thresholdRgbArray(getPixelArray(picture[y][x]))) {
				  picture[y][x] = 0xFFFFFFFF;
			  } else {
				  picture[y][x] = 0x00000000;
			  }
		  }
	  }
	  //redraw picture
	  resetPicture();
  }
  


  
  
 
  private void quit()
  {  
     System.exit(0);
  }

   public void mouseEntered(MouseEvent m){}
   public void mouseExited(MouseEvent m){}
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
   public void mousePressed(MouseEvent m){}
   public void mouseReleased(MouseEvent m){}
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }
   


 
}
