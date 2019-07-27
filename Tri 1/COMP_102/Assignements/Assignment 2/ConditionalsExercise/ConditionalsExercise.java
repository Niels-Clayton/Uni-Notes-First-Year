// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

public class ConditionalsExercise{

    /** Ask user for an integer
     * if the number is a valid hour (1 to 12), then it prints the number in
     * the form  "The time is 6 o'clock" (if they entered 6
     * otherwise it prints  "That number is not a valid time"
     */
    public void validHour(){
      double hour=UI.askDouble ("what is the time in 12 hour format?");
      if(12.59>=hour&&hour>0){
        UI.println("the time is "+hour+" o'colck.");
      }
      else{
        UI.println("That is not a valid time.");
      }

    }

    /** Asks the user to enter a word.
     * Says "Yes, that fits" if the word starts with "p" and is 7 characters long,
     * and "Sorry, that word won't work" otherwise.
     */
    public void wordGame(){
       String words=UI.askString ("enter a word:");
       int wordLength = words.length();
       if(words.contains(" ")){
         UI.println("Sorry, that word won't work.");
         return;
        }

       for(int i=0; i<10; i=i+1){
         if(words.contains(Integer.toString(i))){
         UI.println("Sorry, that word won't work.");
         return;
        }

       }
       if((words.startsWith("p")) && (wordLength == 7)){
           UI.println("Yes, that fits");
       }
        else{
          UI.println("Sorry, that word won't work.");
       }

    }

    /**  Asks the user to enter the name of a country, and
     * draws the appropriate flag (by calling one of the
     * methods below).
     * Recognises japan, indonesia, austria and bangladesh.
     */
    public void drawAFlag(){
        String country=UI.askString ("What countries flag would you like?\nJapan, Indonesia, Austria, or Bangladesh?\nType here:");
        if(country.equalsIgnoreCase("Japan")){
          this.drawJapanFlag();
        }
        else if(country.equalsIgnoreCase("Indonesia")){
          this.drawIndonesiaFlag();
        }
        else if(country.equalsIgnoreCase("Austria")){
          this.drawAustriaFlag();
        }
        else if(country.equalsIgnoreCase("Bangladesh")){
          this.drawBangladeshFlag();
        }
        else{
          UI.println("Please input an avalable flag.");
        }

    }

    /** Asks the user to enter three words and prints out the longest one.
     * (if two words are equally long, it doesn't matter which it prints).
     * You can call the length() method on a string to find out how long it is.
     * Note that there are three possible cases to check for.
     */
    public void longestWord(){
        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/
    // The flag methods called by doDrawAFlag.

    /** Draws the Japanese flag */
    public void drawJapanFlag(){
        double width = 250;
        double height = width*2/3;
        double circleDiam = height*3/5;
        UI.clearGraphics();
        UI.setColor(Color.red);
        UI.fillOval(100+width/2-circleDiam/2, 100+height/2-circleDiam/2, circleDiam, circleDiam);
        UI.setColor(Color.black);
        UI.drawRect(100, 100, width, height);
    }

    /** Draws the Indonesian flag */
    public void drawIndonesiaFlag(){
        double width = 250;
        double height = width*2/3;
        UI.clearGraphics();
        UI.setColor(Color.red);
        UI.fillRect(100,100, width, height/2);
        UI.setColor(Color.black);
        UI.drawRect(100,100, width, height);
    }

    /** Draws the Austrian flag */
    public void drawAustriaFlag(){
        double width = 250;
        double height = width*2/3;
        UI.clearGraphics();
        UI.setColor(Color.red);
        UI.fillRect(100,100, width, height/3);
        UI.fillRect(100,100+height*2/3, width, height/3);
        UI.setColor(Color.black);
        UI.drawRect(100,100, width, height);
    }

    /** Draws the Bangladeshi flag  */
    public void drawBangladeshFlag(){
        double width = 250;
        double height = width*3/5;
        double circle = width*2/5;
        UI.clearGraphics();
        UI.setColor(Color.green);
        UI.fillRect(100,100, width, height);
        UI.setColor(Color.red);
        UI.fillOval(100+width/2-circle/2,100+height/2-circle/2, circle, circle);
        UI.setColor(Color.black);
        UI.drawRect(100,100, width, height);
    }


}
