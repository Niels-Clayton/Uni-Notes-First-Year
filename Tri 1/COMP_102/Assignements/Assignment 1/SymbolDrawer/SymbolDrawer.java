// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/**
 * Draws various symbols: flags, signs, and car logos
 *
 * You can find lots of flag details (including the correct dimensions and colours)
 * from  http://www.crwflags.com/fotw/flags/
 */
public class SymbolDrawer{

    /**   CORE
     * Draw the flag of France.
     * The flag has three vertical stripes;
     * The left is blue, the right is red and the middle is white.
     * The flag is 2/3 as high as it is wide (ratio 2:3).
     */
    public void drawFranceFlag(double left, double top, double width){
      double height= width*2.0/3.0;

      UI.setColor(Color.red);
      UI.fillRect(left, top, width, height);
      UI.setColor(Color.white);
      UI.fillRect(left, top, width*2/3, height);
      UI.setColor(Color.blue);
      UI.fillRect(left, top, width*1/3, height);
      UI.setColor(Color.black);
      UI.drawRect(left, top, width, height);

    }

    /**   CORE
     * Draw the hospital sign - a blue square with a big white centred H.
     * The H is made of 3 rectangular strips
     */
    public void drawHospitalSign(double left, double top, double size) {
UI.setColor(Color.blue);
UI.fillRect(left,top,size,size);
UI.setColor(Color.white);
UI.fillRect(left+size/4, top+size/5, size*1/2, size*3/5);
UI.setColor(Color.blue);
UI.fillRect(left+size/4+size/7.5,top,(size/2)-(size/7.5) -(size/7.5),(size/2)-(size/7.5/2));
UI.fillRect(left+size/4+size/7.5,top+(size/2)+(size/7.5/2),(size/2)-(size/7.5) -(size/7.5),(size/2)-(size/7.5/2));

    }

    /**   CORE
     * Draw the flag of Laos.
     * The flag has three horizontal stripes with a white circle in the centre;
     * See the assignment for the dimensions.
     */
    public void drawLaosFlag(double left, double top, double width) {
    double height = width*2/3;

    UI.setColor(Color.red);
    UI.fillRect(left,top,width,height);
    UI.setColor(Color.blue);
    UI.fillRect(left,top+height/4,width,height/2);
    UI.setColor(Color.white);
    UI.fillOval(left+width/2-height/5,top+height*3/10,height*2/5,height*2/5);


    }

    /**   COMPLETION
     * Draw the flag of the United Arab Emirates.
     * The flag has a vertical red stripe on the left, and
     * three horizontal stripes (green, white, black) on the right.
     * See the assignment for dimensions and details.
     */
    public void drawUaeFlag(double left, double top, double width) {
    double height= width/2;
    UI.setColor(Color.red);
    UI.fillRect(left,top,width,height);
    UI.setColor(Color.green);
    UI.fillRect(left+width/4,top,width*3/4,height*1/3);
    UI.setColor(Color.white);
    UI.fillRect(left+width/4,top+height*1/3,width*3/4,height*2/3);
    UI.setColor(Color.black);
    UI.fillRect(left+width/4,top+height*2/3,width*3/4,height/3);
    UI.drawRect(left,top,width,height);


    }

    /**   COMPLETION
     * Draw the flag of Greenland.
     * The top half of the flag is white, and the bottom half is red.
     * There is a circle in the middle (off-set to left)  which is
     * also half white/red but on the opposite sides.
     * See the assignment for dimensions
     */
    public void drawGreenlandFlag(double left, double top, double width) {
      double height=width*2/3;

    UI.setColor(Color.white);
    UI.fillRect(left,top,width,height);
    UI.setColor(Color.red);
    UI.fillRect(left,top+height/2,width,height/2);
    UI.fillArc(left+width*3.5/18,top+height/6,width*8/18,width*8/18,0,180);
    UI.setColor(Color.decode("#ffffff"));
    UI.fillArc(left+width*3.5/18,top+height/6,width*8/18,width*8/18,180,180);
    UI.setColor(Color.decode("#000000"));
    UI.drawRect(left,top,width,height);
    }

    /**   CHALLENGE
     * Draw the Misubishi Logo.
     */
    public void drawMitsubishiLogo(double left, double top, double size) {
    double height = size*Math.pow(3.0, 0.5)/2;
    UI.setColor(Color.decode("#ff0000"));
    double poly1[]={left+size/2,left+ size*2/3,left+size/2,left+size*1/3};
    double poly2[]={top,top+height/3,top+height*2/3,top+height/3};
    UI.fillPolygon(poly1,poly2,4);
    double poly3[]={left+size/2,left+size*5/6,left+size,left+size*2/3};
    double poly4[]={top+height*2/3,top+height*2/3,top+height,top+height};
    UI.fillPolygon(poly3,poly4,4);
    double poly5[]={left+size/2,left+size*1/6,left,left+size*1/3};
    double poly6[]={top+height*2/3,top+height*2/3,top+height,top+height};
    UI.fillPolygon(poly5,poly6,4);
    }

    /**   CHALLENGE
     * Draw the Koru Flag.
     * It was one of the new flag designs for the 2016 referendum,
     * designed by Sven Baker from Wellington
     * The flag is 1/2 as high as it is wide (ratio 1:2).
     */
    public void drawKoruFlag(double left, double top, double width) {
    double height=width/2;
    UI.setColor(Color.blue);
    UI.fillRect(left,top,width,height);
    UI.setColor(Color.red);
    UI.fillRect(left,top,width/2,height);
    UI.setColor(Color.white);
    UI.fillOval(left+width*0.562/2,top-width*0.062,width*0.562,width*0.562);


    }


}
