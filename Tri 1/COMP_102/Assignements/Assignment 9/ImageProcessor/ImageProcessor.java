// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
* Name:
* Username:
* ID:
*/

import ecs100.*;
import java.util.*;
import java.awt.Color;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/** ImageProcessor allows the user to display and edit a
*  greyscale image in a number of ways.
*  The program represents the image as a 2D array of integers, which must
*   all be between 0 (black) and 255 (white).
*  The class includes three methods (already written for you) that will
*   - read a png or jpeg image file and store a 2D array of greyscale values
*     into the image field.
*   - render (display) the 2D array of greyscale values in the image field on the graphics pane
*   - write the 2D array of greyscale values in the image field to a png file.
*
*  You are to complete the methods to modify an image:
*   - darken the image
*   - increase the contrast
*   - Rotate the image 180 degrees and 90 degrees
*   - flip the image horizontally or vertically.
*   - merge another image with the current image
*
*/
public class ImageProcessor{
    // the current image (initialised to a very small 3x3 image)
    private int[][] image = new int[][]{{80,80,80},{80, 200, 80},{80,80,80}};

    // current selected point
    private int selectedRow = 0;
    private int selectedCol = 0;

    private final int pixelSize = 1;  // the size of the pixels as drawn on screen


    /**
    * Construct a new ImageProcessor object
    * and set up the GUI
    */
    public ImageProcessor(){
        this.setupGui();
        this.computeGreyColours();   // compute table of grey colours for converting images to greyscale.
    }

    public void setupGui(){
        UI.initialise();
        UI.setMouseListener(this::doMouse);
        UI.addButton("Load",       this::loadImage );
        UI.addButton("Save",       this::saveImage );
        UI.addButton("Darken",     this::darkenImage );
        UI.addButton("Lighten",     this::lightenImage );
        UI.addButton("Contrast",   this::contrastImage );
        UI.addButton("Flip Horiz", this::flipImageHorizontally );
        UI.addButton("Flip Vert",  this::flipImageVertically );
        UI.addButton("Rotate 180", this::rotateImage180 );
        UI.addButton("Rotate 90",  this::rotateImage90 );
        UI.addButton("Expand",     this::expandImage );
        UI.addButton("Merge",      this::mergeImage );
        UI.addButton("Quit", UI::quit );
    }

    /** CORE:
    * Make all pixels in the image darker by 20 greylevels.
    * but make sure that you never go below 0
    */
    public void darkenImage(){

        int height = this.image.length;
        int width = this.image[0].length;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Trace.println("its working");
                if(this.image[i][j] <= 20){
                    this.image[i][j] = 0;
                }
                else{
                    this.image[i][j] = this.image[i][j] - 20;
                }
            }
        }
        this.redisplayImage();
    }

    public void lightenImage(){

        int height = this.image.length;
        int width = this.image[0].length;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Trace.println("its working");
                if(this.image[i][j] >= 235){
                    this.image[i][j] = 255;
                }
                else{
                    this.image[i][j] = this.image[i][j] + 20;
                }
            }
        }
        this.redisplayImage();
    }

    /** CORE:
    * Increase the contrast of the image -
    * make all lighter pixels in the image (above 128) even lighter (by 20%)
    * and make all darker pixels even darker (by 20%)
    * For example, a pixel value of 158 is 30 levels above 128. It should be lightened by 20% of 30 (= 6) to 164.
    *              A pixel value of 48 is 80 levels below 128. It should be darkened by 20% of 80 (= 16) to 32.
    */
    public void contrastImage(){

        int height = this.image.length;
        int width = this.image[0].length;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(this.image[i][j] < 128){
                    int colour = (int)(this.image[i][j]-((128-this.image[i][j])*0.2));
                    if(colour < 0){
                        colour = 0;
                    }
                    else{
                        this.image[i][j] = colour;
                    }
                }
                else{
                    int colour = (int)(this.image[i][j]+((this.image[i][j] - 128)*0.2));
                    if(colour > 255){
                        colour = 255;
                    }
                    else{
                        this.image[i][j] = colour;
                    }
                }
            }
        }
        this.redisplayImage();
    }

    /** CORE:
    * Flip the image horizontally
    *   exchange the values on the left half of the image
    *   with the corresponding values on the right half
    */
    public void flipImageHorizontally(){
        for(int i = 0; i < this.image.length; i++){
            for(int j = 0; j < (this.image[i].length/2); j++){
                int left_pixel = this.image[i][j];
                this.image[i][j] = this.image[i][((this.image[i].length-1)-j)];
                this.image[i][(this.image[i].length -1)-j] = left_pixel;
            }
        }
        this.redisplayImage();
    }

    /** CORE:
    * Flip the image vertically
    *   exchange the values on the top half of the image
    *   with the corresponding values on the bottom half
    */
    public void flipImageVertically(){
        for(int i = 0; i < this.image.length/2; i++){
            for(int j = 0; j < (this.image[i].length); j++){
                int top_pixel = this.image[i][j];
                this.image[i][j] = this.image[(this.image.length-1)-i][j];
                this.image[(this.image.length-1)-i][j] = top_pixel;
            }
        }

        this.redisplayImage();
    }

    /** COMPLETION:
    * Rotate the image 180 degrees
    * Each cell is swapped with the corresponding cell
    *  on the other side of the center of the images.
    * It is easier to make a new array, the same size as image, then
    *   copy each pixel in image to the right place in the new array
    *   and then assign the new array to the image field.
    */
    public void rotateImage180(){
        int height = this.image.length;
        int width = this.image[0].length;

        for(int i = 0; i < height/2; i++){
            for(int j = 0; j < (width); j++){
                int top_row = this.image[i][j];
                this.image[i][j] = this.image[(height-1)-i][(width-1)-j];
                this.image[(height-1)-i][(width-1)-j] = top_row;
            }
        }
        this.redisplayImage();
    }


    /**  COMPLETION:
    * Rotate the image 90 degrees anticlockwise
    * Note, the resulting image will have different dimensions:
    *  the width of the new image will be the height of the old image.
    *  the height of the new image will be the width of the old image.
    * You will need to make a new image array of the new dimensions,
    *  fill it with the correct pixel values from the original array,
    *  and then set the image field to contain the new image.
    */
    public void rotateImage90(){
        int height = this.image.length;
        int width = this.image[0].length;
        int rotated_height = width;
        int rotated_width = height;
        int[][] rotated_image = new int[rotated_height][rotated_width];

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                rotated_image[j][height-1-i] = this.image[i][j];
            }
        }
        this.image = rotated_image;
        this.redisplayImage();
    }

    /** CHALLENGE:
    * Expand the top left quarter of the image to fill the whole image
    * each pixel in the top left quarter will be copied to four pixels
    * in the new image.
    * Be careful not to try to access elements past the edge of the array!
    * Hint: It is actually easier to work backwards from the bottom right corner.
    */
    public void expandImage(){
        int height = this.image.length;
        int width = this.image[0].length;
        int[][] expand_image = new int[height/2][width/2];
        int expand_height = expand_image.length;
        int expand_width = expand_image[0].length;
        for (int i = 0; i < height/2; i++){
            for (int j = 0; j < width/2; j++){
                expand_image[i][j] = image[i][j];
            }
        }
        for (int i = 0; i < height/2; i++){
            for (int j = 0; j < width/2; j++){
                image[height-(i*2)-1][width-(j*2)-1] = expand_image[(expand_height-i)-1][(expand_width-j)-1];
                image[height-((i*2)+1)-1][width-(j*2)-1] = expand_image[(expand_height-i)-1][(expand_width-j)-1];
                image[height-(i*2)-1][width-((j*2)+1)-1] = expand_image[(expand_height-i)-1][(expand_width-j)-1];
                image[height-((i*2)+1)-1][width-((j*2)+1)-1] = expand_image[(expand_height-i)-1][(expand_width-j)-1];
            }
        }
        this.redisplayImage();
    }

    /** CHALLENGE:
    * Merge two images
    * Ask the user to select another image file, and load it into another array.
    *  Work out the rows and columns shared by the images
    *  For each pixel value in the shared region, replace the current pixel value
    *  by the average of the pixel value in current image and the corresponding
    *  pixel value in the other image.
    */
    public void mergeImage(){
        int [][] other = this.loadAnImage(UIFileChooser.open());
        int rows = Math.min(this.image.length, other.length);       // rows and cols
        int cols = Math.min(this.image[0].length, other[0].length); // common to both
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                image[i][j] = (other[i][j] + image[i][j])/2;
            }
        }

        this.redisplayImage();
    }

    //=========================================================================
    // Methods below here are already written for you -
    // for redisplaying the image array on the graphics pane,
    // for loading an image file into the image array,
    // for saving the image array into a file,
    // for setting the mouse position.

    /** field and helper methods to precompute and store all the possible grey colours,
    *  so the redisplay method does not have to constantly construct new color objects
    */
    private Color[] greyColors = new Color[256];

    /** Display the image on the screen with each pixel as a square of size pixelSize.
    *  To speed it up, all the possible colours from 0 - 255 have been precalculated.
    */
    public void redisplayImage(){
        UI.clearGraphics();
        UI.setImmediateRepaint(false);
        for(int row=0; row<this.image.length; row++){
            int y = row * this.pixelSize;
            for(int col=0; col<this.image[0].length; col++){
                int x = col * this.pixelSize;
                UI.setColor(this.greyColor(this.image[row][col]));
                UI.fillRect(x, y, this.pixelSize, this.pixelSize);
            }
        }
        UI.setColor(Color.red);
        UI.drawRect(this.selectedCol*this.pixelSize,this.selectedRow*this.pixelSize,
        this.pixelSize,this.pixelSize);
        UI.repaintGraphics();
    }

    /** Get and return an image as a two-dimensional grey-scale image (from 0-255).
    *  This method will cause the image to be returned as a grey-scale image,
    *  regardless of the original colouration.
    */
    public int[][] loadAnImage(String imageName) {
        int[][] ans = null;
        if (imageName==null) return null;
        try {
            BufferedImage img = ImageIO.read(new File(imageName));
            UI.printMessage("loaded image height(rows)= " + img.getHeight() +
            "  width(cols)= " + img.getWidth());
            ans = new int[img.getHeight()][img.getWidth()];
            for (int row = 0; row < img.getHeight(); row++){
                for (int col = 0; col < img.getWidth(); col++){
                    Color c = new Color(img.getRGB(col, row), true);
                    // Use a common algorithm to move to greyscale
                    ans[row][col] = (int)Math.round((0.3 * c.getRed()) + (0.59 * c.getGreen())
                    + (0.11 * c.getBlue()));
                }
            }
        } catch(IOException e){UI.println("Image reading failed: "+e);}
        return ans;
    }

    /** Ask user for an image file, and load it into the current image */
    public void loadImage(){
        this.image = this.loadAnImage(UIFileChooser.open());
        this.redisplayImage();
    }

    /** Write the current greyscale image to the specified filename */
    public  void saveImage() {
        // For speed, we'll assume every row of the image is the same length!
        int height = this.image.length;
        int width = this.image[0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int greyscaleValue = this.image[row][col];
                Color c = new Color(greyscaleValue, greyscaleValue, greyscaleValue);
                img.setRGB(col, row, c.getRGB());
            }
        }
        try {
            String fname = UIFileChooser.save("save to png image file");
            if (fname==null) return;
            File imageFile = new File(fname);
            ImageIO.write(img, "png", new File(fname));
        } catch(IOException e){UI.println("Image reading failed: "+e);}
    }

    private void computeGreyColours(){
        for (int i=0; i<256; i++){
            this.greyColors[i] = new Color(i, i, i);
        }
    }

    private Color greyColor(int grey){
        if (grey < 0){
            return Color.blue;
        }
        else if (grey > 255){
            return Color.red;
        }
        else {
            return this.greyColors[grey];
        }
    }


    public void doMouse(String a, double x, double y){
        if (a.equals("released")) {
            this.setPos(x, y);}
        }

        /** Set the selected Row and Col to the pixel on the mouse position x, y */
        public void setPos(double x, double y){
            int row = (int)(y/this.pixelSize);
            int col = (int)(x/this.pixelSize);
            if (this.image != null && row < this.image.length && col < this.image[0].length){
                this.selectedRow = row;
                this.selectedCol = col;
                this.redisplayImage();
            }
        }

        // Main
        public static void main(String[] arguments){
            ImageProcessor ob = new ImageProcessor();
        }

    }