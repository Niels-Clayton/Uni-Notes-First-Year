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

/** Run SymbolDrawer methods  */
public class RunSymbolDrawer{
    public static void main(String[] arguments){
        SymbolDrawer sd = new SymbolDrawer();
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Core: Flag of France",
            ()->sd.drawFranceFlag(100,100,UI.askDouble("How wide:")));
        UI.addButton("Core: Hospital Sign",
            ()->sd.drawHospitalSign(100,100,UI.askDouble("How wide:")));
        UI.addButton("Core: Flag of Laos",
            ()->sd.drawLaosFlag(100,100,UI.askDouble("How wide:")));
        // COMPLETION
        UI.addButton("Completion: Flag of UAE",
            ()->sd.drawUaeFlag(100,100,UI.askDouble("How wide:")));
        UI.addButton("Completion: Flag of Greenland",
            ()->sd.drawGreenlandFlag(100,100,UI.askDouble("How wide:")));
        // CHALLENGE
        UI.addButton("Challenge: Mitsubishi",
            ()->sd.drawMitsubishiLogo(100,100,UI.askDouble("How wide:")));
        UI.addButton("Challenge: Koru Flag",
            ()->sd.drawKoruFlag(100,100,UI.askDouble("How wide:")));
        UI.addButton("Quit", UI::quit);

        UI.setDivider(0.3);
    }
}
