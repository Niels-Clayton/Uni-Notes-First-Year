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
import javax.swing.JColorChooser;

/** TricolourFlagDrawer: draws a series of tricolour flags */
public class TricolourFlagDrawer
{

    public static final double width = 200;
    public static final double height = 133;

    /**   CORE
     * asks user for a position and three colours, then calls the
     * drawThreeColourFlagCore method, passing the appropriate arguments
     */
    public void doCore()
    {
        double left = UI.askDouble("left of flag");
        double top = UI.askDouble("top of flag");
        UI.println("Now choose the colours");
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeColourFlagCore(left, top, stripe1, stripe2, stripe3 );
    }

    /**   CORE
     * draws a three colour flag consisting of three vertical equal-width
     * stripes at the given position
     */
    public void drawThreeColourFlagCore(double left, double top, Color stripe1, Color stripe2, Color stripe3 )
    {
        UI.setColor(stripe3);
        UI.fillRect(left, top, width, height);
        UI.setColor(stripe2);
        UI.fillRect(left, top, width*2/3, height);
        UI.setColor(stripe1);
        UI.fillRect(left, top, width*1/3, height);
        UI.setColor(Color.black);
        UI.drawRect(left, top, width, height);
    }

     /**   COMPLETION
     * draws multiple flag made up of three equal size stripes by calling the
     * drawThreeColourFlagCompletion method, passing the appropriate arguments
     */

    public void doCompletion()
    {
        this.drawThreeColourFlagCompletion(true,  20,  50,  Color.black, Color.yellow, Color.red);               // Belgium
        this.drawThreeColourFlagCompletion(false, 250, 100, Color.black, Color.red,    Color.yellow);            // Germany
        this.drawThreeColourFlagCompletion(true,  140, 430, Color.blue,  Color.white,  Color.red);               // France
        this.drawThreeColourFlagCompletion(true,  290, 270, Color.red,   Color.yellow, Color.green.darker());    // Guinea
        this.drawThreeColourFlagCompletion(false, 470, 30,  Color.red,   Color.white,  Color.blue);               // The Netherlands
        this.drawThreeColourFlagCompletion(false, 50,  250, Color.white, Color.blue,   Color.red);               // Russia
    }

     /** COMPLETION
     * draws a three colour flag consisting of three equal-size stripes
     * at the given position
     * The stripes can be either vertical or horizontal
     */
    public void drawThreeColourFlagCompletion(boolean type, double left, double top, Color stripe1, Color stripe2, Color stripe3)
    {
        if (type == true)
        {
            UI.setColor(stripe3);
            UI.fillRect(left, top, width, height);
            UI.setColor(stripe2);
            UI.fillRect(left, top, width*2/3, height);
            UI.setColor(stripe1);
            UI.fillRect(left, top, width*1/3, height);
            UI.setColor(Color.black);
            UI.drawRect(left, top, width, height);
        }
        else
        {
            UI.setColor(stripe3);
            UI.fillRect(left, top, width, height);
            UI.setColor(stripe2);
            UI.fillRect(left, top, width, height*2/3);
            UI.setColor(stripe1);
            UI.fillRect(left, top, width, height*1/3);
            UI.setColor(Color.black);
            UI.drawRect(left, top, width, height);
        }
    }

        //Challenge//

    public void doChallenge()
    {
        this.drawThreeColourFlagChallenge(0, "Flag of Mons, Belgium",        true,  20, 25, Color.decode("#da121a"), Color.decode("#ffffff"), Color.decode("#da121a"));
        this.drawThreeColourFlagChallenge(1, "Bandera d'Atlantium",          false, 20, 25, Color.decode("#01bafe"), Color.decode("#ffeb00"), Color.decode("#fe8600"));
        this.drawThreeColourFlagChallenge(2, "The bisexual pride flag",      false, 20, 25, Color.decode("#d60270"), Color.decode("#9b4f96"), Color.decode("#0038a8"));
        this.drawThreeColourFlagChallenge(3, "Flag of Charleville Mezieres", true,  20, 25, Color.decode("#0000ff"), Color.decode("#ffff00"), Color.decode("#ff0000"));
        this.drawThreeColourFlagChallenge(4, "Flag of Gabon",                false, 20, 25, Color.decode("#009e60"), Color.decode("#fcd116"), Color.decode("#3a74c4"));
        this.drawThreeColourFlagChallenge(5, "Flag of Mali",                 true,  20, 25, Color.decode("#14853a"), Color.decode("#fcd116"), Color.decode("#ce1126"));
        this.drawThreeColourFlagChallenge(6, "Flag of Opocno",               false, 20, 25, Color.decode("#0055ff"), Color.decode("#ffd500"), Color.decode("#00aa00"));
        this.drawThreeColourFlagChallenge(7, "Newfoundland",                 true,  20, 25, Color.decode("#009a49"), Color.decode("#ffffff"), Color.decode("#ff99cc"));
        this.drawThreeColourFlagChallenge(8, "Flag of Armenia",              false, 20, 25, Color.decode("#00aa00"), Color.decode("#ffffff"), Color.decode("#ffee00"));
    }

    public void drawThreeColourFlagChallenge(int position, String name, boolean type, double left, double top, Color stripe1, Color stripe2, Color stripe3)
    {
        if (position < 3)
        {
            if (type == true)
            {
                UI.setColor(stripe3);
                UI.fillRect(left, (top+(width*position)+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect(left, (top+(width*position)+30), width*2/3, height);
                UI.setColor(stripe1);
                UI.fillRect(left, (top+(width*position)+30), width*1/3, height);
                UI.setColor(Color.black);
                UI.drawRect(left, (top+(width*position)+30), width, height);
                UI.drawString(name, left, (top+(width*position)+45+height));
            }
            else
            {
                UI.setColor(stripe3);
                UI.fillRect(left, (top+(width*position)+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect(left, (top+(width*position)+30), width, height*2/3);
                UI.setColor(stripe1);
                UI.fillRect(left, (top+(width*position)+30), width, height*1/3);
                UI.setColor(Color.black);
                UI.drawRect(left, (top+(width*position)+30), width, height);
                UI.drawString(name, left, (top+(width*position)+45+height));
            }
        }
        else if (position >= 3 && position < 6)
        {
            if (type == true)
            {
                UI.setColor(stripe3);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width*2/3, height);
                UI.setColor(stripe1);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width*1/3, height);
                UI.setColor(Color.black);
                UI.drawRect((left + width + 30), (top+(width*(position-3))+30), width, height);
                UI.drawString(name, (left+ width + 30), (top+(width*(position-3))+45+height));
            }
            else
            {
                UI.setColor(stripe3);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width, height*2/3);
                UI.setColor(stripe1);
                UI.fillRect((left + width + 30), (top+(width*(position-3))+30), width, height*1/3);
                UI.setColor(Color.black);
                UI.drawRect((left + width + 30), (top+(width*(position-3))+30), width, height);
                UI.drawString(name, (left+ width + 30), (top+(width*(position-3))+45+height));
            }
        }
        else
        {
            if (type == true)
            {
                UI.setColor(stripe3);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width*2/3, height);
                UI.setColor(stripe1);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width*1/3, height);
                UI.setColor(Color.black);
                UI.drawRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height);
                UI.drawString(name, (left+ (2*width) + 60), (top+(width*(position-6))+45+height));
            }
            else
            {
                UI.setColor(stripe3);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height);
                UI.setColor(stripe2);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height*2/3);
                UI.setColor(stripe1);
                UI.fillRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height*1/3);
                UI.setColor(Color.black);
                UI.drawRect((left + (2*width) + 60), (top+(width*(position-6))+30), width, height);
                UI.drawString(name, (left+ (2*width) + 60), (top+(width*(position-6))+45+height));
            }
        }
    }
}
