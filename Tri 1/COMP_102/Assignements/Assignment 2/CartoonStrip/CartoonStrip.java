// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Program to create simple animated cartoon strips using the
 *  CartoonCharacter class.
 */

public class CartoonStrip{

    /** animate creates two cartoon characters on the window.
     *  Then animates them according to a fixed script by calling a series
     *  of methods on the characters.
     */
    public void animate(){
        CartoonCharacter Geoff = new CartoonCharacter(50, 50, "yellow");
        for (int i=0; i<=650 ;i=i+1 ) {
        Geoff.walk(i-(i-1));
        }
        Geoff.lookLeft();
        Geoff.think("....?....");
        Geoff.frown();
        Geoff.speak("Hey Arnold! hurry up!");
        CartoonCharacter Arnie = new CartoonCharacter(-50, 50, "blue");
        for (int i=0; i<=350 ;i=i+1 ) {
        Arnie.walk(i-(i-1));
        }
        Arnie.speak("....*huff*....*huff*...");
        Arnie.think("GET TO THE CHOPPER!");
        for (int i=0; i<=200 ;i=i+1 ) {
        Arnie.walk(i-(i-1));
        }
        Geoff.lookRight();
        Geoff.think("We went the wrong way!");
        Geoff.lookLeft();
        Geoff.speak("we need to turn back");
        Geoff.speak("we went the wrong way");
        Arnie.frown();
        Arnie.speak("I am the Terminator");
        Arnie.speak("I must not be late");
        Arnie.lookLeft();
        for (int i=0; i<=650 ;i=i+1 ) {
        Arnie.walk(i-(i-1));
        }
        Geoff.speak("wait for me!");
        for (int i=0; i<=700;i=i+1 ) {
            Geoff.walk(i-(i-1));
        }
    }


    /** threeDancers creates three cartoon characters on the window.
     *  Then makes each character do the same little dance in turn,
     *  by calling the dance method.
     */
    public void threeDancers(){
        CartoonCharacter Geoff = new CartoonCharacter(-50, 50, "yellow");
        CartoonCharacter Jill = new CartoonCharacter(-50, 50, "green");
        CartoonCharacter Hue = new CartoonCharacter(-50, 50, "blue");
        for (int i=0; i<=250 ;i=i+1 ) {
        Geoff.walk(i-(i-1));
        }
        for (int i=0; i<=150 ;i=i+1 ) {
        Jill.walk(i-(i-1));
        }
        for (int i=0; i<=50 ;i=i+1 ) {
        Hue.walk(i-(i-1));
        }
        this.dance(Geoff);
        this.dance(Jill);
        this.dance(Hue);
    }

    /** Makes a character do a little dance.
     * Has one parameter - a CartoonCharacter object
     */
    public void dance(CartoonCharacter face){
        for (int i=0; i<=400 ;i=i+1 ) {
        face.walk(i-(i-1));
        }
        face.lookLeft();
        face.lookRight();
        face.speak("   *clap*  *clap*");
        face.lookLeft();
        for (int i=0; i<=100 ;i=i+1 ) {
        face.walk(i-(i-1));
        }
        face.speak("   *tap*  *tap*");
        face.lookRight();
        for (int i=0; i<=100 ;i=i+1 ) {
        face.walk(i-(i-1));
        }
        face.speak("*bows*");
    }

    /** Challenge story book
     * Interactive, asking for a string and then with if/else ststments chose actions
     */
     public void storybook(){
         CartoonCharacter Geoff = new CartoonCharacter(-50, 50, "yellow");
         for (int i=0; i<=350 ;i=i+1 ) {
         Geoff.walk(i-(i-1));
        }
        Geoff.speak("Hi!, I'm Geoff");
        Geoff.speak("what is your name?");
        String name = UI.askString("what is your name:");
        Geoff.speak(name+("?"));
        Geoff.speak("That is a cool name");
        Geoff.speak("Where do i walk "+name+("?"));
        String direction = UI.askString("turn left or right?");
        if(direction.equalsIgnoreCase("left")){
            Geoff.lookLeft();
            Geoff.speak("Are you sure?");
            Geoff.speak("that way looks scary");
            String usure = UI.askString ("Still go left? Yes/No:");
            if(usure.equalsIgnoreCase("Yes")){
                Geoff.speak("Ok, I trust you");
                for (int i=0; i<=350 ;i=i+1 ) {
                Geoff.walk(i-(i-1));
               }
               UI.println("you helped Geoff escape the maze, welldone");
            }
            else if(usure.equalsIgnoreCase("No")){
                Geoff.speak("Guess its right then?");
                Geoff.lookRight();
                Geoff.speak("Here goes nothing!");
                for (int i=0; i<=800 ;i=i+1 ) {
                Geoff.walk(i-(i-1));
                Geoff.frown();
               }
               UI.println("You led Geoff to Sweenie Tods shop");
               UI.println("He was swiftly decapateted and turned into pies");
        }
     }
     else if(direction.equalsIgnoreCase("Right")){
         Geoff.speak("That sounds like a good choice");
         for (int i=0; i<=150 ;i=i+1 ) {
         Geoff.walk(i-(i-1));
        }
         Geoff.think("actually... this looks sketchy");
         Geoff.speak("It's not too late to turn back");
         Geoff.speak("are you sure this is the way?");
         String Goback = UI.askString("Do you want to go back?");
         if(Goback.equalsIgnoreCase("no")){
        Geoff.speak("Guess you know best");
         for (int i=0; i<=800 ;i=i+1 ) {
         Geoff.walk(i-(i-1));
         Geoff.frown();
        }
        UI.println("You led Geoff to Sweenie Tods shop");
        UI.println("He was swiftly decapateted and turned into pies");
        }
        else if(Goback.equalsIgnoreCase("yes")){
            Geoff.speak("Ok, I trust you");
            Geoff.lookLeft();
            for (int i=0; i<=500 ;i=i+1 ) {
            Geoff.walk(i-(i-1));
           }
           UI.println("you helped Geoff escape the maze, welldone");
        }
    }
}
}
