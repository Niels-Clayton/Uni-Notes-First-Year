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
import java.io.*;
import java.awt.Color;

/** Renders plain ppm images onto the graphics panel
 *  ppm images are the simplest possible colour image format.
 */

public class ImageRenderer
{
    public static final int TOP = 20;   // top edge of the image
    public static final int LEFT = 20;  // left edge of the image
    public static final int PIXEL_SIZE = 2;

    /** Core:
     * Renders a ppm image file.
     * Asks for the name of the file, then calls renderImageHelper.
     */
    public void renderImageCore()
    {
        String filename = UIFileChooser.open();
        File myfile = new File(filename);
        try
        {
        Scanner sc = new Scanner(myfile);
        this.renderImageHelper(sc);
        }
    catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
    }

    /** Core:
     * Renders a ppm image file.
     * Renders the image at position (LEFT, TOP).
     * Each pixel of the image  is rendered by a square of size PIXEL_SIZE
     * Assumes that
     * - the colour depth is 255,
     * - there is just one image in the file (not "animated"), and
     * - there are no comments in the file.
     * The first four tokens are "P3", number of columns, number of rows, 255
     * The remaining tokens are the pixel values (red, green, blue for each pixel)
     */
    public void renderImageHelper(Scanner scan)
    {
        String imgCode = scan.next();
        String columns1 = scan.next();
        String rows1 = scan.next();
        String colourDepth1 = scan.next();

        int columns = Integer.valueOf(columns1);
        int rows = Integer.valueOf(rows1);
        int colourDepth = Integer.valueOf(colourDepth1);

        int row = 0;
        while (row < rows)
        {
            double y = TOP + row*PIXEL_SIZE;
            int col = 0;
            while (col < columns)
            {
                int r = scan.nextInt();
                int g = scan.nextInt();
                int b = scan.nextInt();

                double x = LEFT + col*PIXEL_SIZE;
                UI.setColor(new Color(r,g,b));
                UI.fillRect(x , y , PIXEL_SIZE , PIXEL_SIZE);
                col++;
            }
            row++;
        }
        UI.sleep(100);
    }

    /** Completion
     * Renders a ppm image file which may be animated (multiple images in the file)
     * Asks for the name of the file, then renders the image at position (LEFT, TOP).
     * Each pixel of the image  is rendered by a square of size PIXEL_SIZE
     * Renders each image in the file in turn with 200 mSec delay.
     * Repeats the sequence 3 times.
     */

    public void renderAnimatedImage()
    {
        String filename = UIFileChooser.open();
        File myfile = new File(filename);
        String comment = "";

        for(int i = 0 ; i<3 ; i++)
        {
            try
            {
                Scanner scan = new Scanner(myfile);
                while(scan.hasNext())
                {
                       this.renderImageHelper(scan);
                }
            }
            catch(IOException e)
            {
                UI.printf("File Failure %s \n", e);
            }
        }
    }



/**
    Challenge:
    Render both P3 and P2 images
    P2 images are black and white, so the RGB values will all be equal
*/

    public void renderChallengeImages()
    {
        String filename = UIFileChooser.open();
        File myfile = new File(filename);


        for(int i = 0 ; i<3 ; i++)
        {
            try
            {
                Scanner scan = new Scanner(myfile);
                while(scan.hasNext())
                {
                       this.renderImageChallenge(scan);

                }
            }
            catch(IOException e)
            {
                UI.printf("File Failure %s \n", e);
            }
        }

    }

    public void renderImageChallenge(Scanner scan)
    {
        String imgCode = scan.next();
        while(imgCode.startsWith("#"))
        {
            String comment = scan.nextLine();
            imgCode = scan.next();
        }

        String columns1 = scan.next();
        while(columns1.startsWith("#"))
        {
            String comment = scan.nextLine();
            columns1 = scan.next();
        }
        String rows1 = scan.next();
        while(rows1.startsWith("#"))
        {
            String comment = scan.nextLine();
            rows1 = scan.next();
        }
        String colourDepth1 = scan.next();
        while(colourDepth1.startsWith("#"))
        {
            String comment = scan.nextLine();
            colourDepth1 = scan.next();
        }

        int columns = Integer.valueOf(columns1);
        int rows = Integer.valueOf(rows1);
        int colourDepth = Integer.valueOf(colourDepth1);
        //UI.println(imgCode+"     " +columns+"       " +rows+"   " +colourDepth); //debugging to see what "scan" was reading.

        if(imgCode.equals("P3"))

        {
            int row = 0;
            while (row < rows)
            {
                int col = 0;
                while (col < columns)
                {
                    String r1 = scan.next();
                    while(r1.startsWith("#"))
                    {
                        String comment = scan.nextLine();
                        r1 = scan.next();
                    }
                    String g1 = scan.next();
                    while(g1.startsWith("#"))
                    {
                        String comment = scan.nextLine();
                        g1 = scan.nextLine();
                    }
                    String b1 = scan.next();
                    while(b1.startsWith("#"))
                    {
                        String comment = scan.nextLine();
                        b1 = scan.next();
                    }

                    int r = ((255/colourDepth)*(Integer.valueOf(r1)));
                    int g = ((255/colourDepth)*(Integer.valueOf(g1)));
                    int b = ((255/colourDepth)*(Integer.valueOf(b1)));

                    double y = TOP + row*PIXEL_SIZE;
                    double x = LEFT + col*PIXEL_SIZE;
                    UI.setColor(new Color(r,g,b));
                    UI.fillRect(x , y , PIXEL_SIZE , PIXEL_SIZE);
                    col++;
                }
                row++;
            }
            UI.sleep(100);
        }

        else //if(imgCode.equals("P2"))
        {
            int row = 0;
            while (row < rows)
            {
                double y = TOP + row*PIXEL_SIZE;
                int col = 0;
                while (col < columns)
                {
                    String blackness1 = scan.next();
                    while(blackness1.startsWith("#"))
                    {
                        String comment = scan.nextLine();
                        blackness1 = scan.next();
                    }

                    int blackness = ((255/colourDepth)*(Integer.valueOf(blackness1)));

                    double x = LEFT + col*PIXEL_SIZE;
                    UI.setColor(new Color(blackness,blackness,blackness));
                    UI.fillRect(x , y , PIXEL_SIZE , PIXEL_SIZE);
                    col++;
                }
                row++;
            }
        }
    }
}
