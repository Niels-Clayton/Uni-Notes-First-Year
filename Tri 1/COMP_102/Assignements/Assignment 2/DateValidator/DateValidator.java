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
 * Reads a date from the user as three integers, and then checks that the date is valid
 */

public class DateValidator {

    /**
     * Asks user for three integer and then checks if it is a valid date.
     */
    public void doCore(){
        int day = UI.askInt("Day: ");
        int month = UI.askInt("Month: ");
        int year = UI.askInt("Year: ");
        this.validateDateCore(day, month, year);
    }

    /** CORE
     * Determines whether the date specified by the three integers is a valid date.
     * For the Core, you may assume that
     * - All months have 31 days, numbered 1 to 31
     * - The months run from 1 to 12
     * - Years start from 1
     */
    public void validateDateCore(int day, int month, int year){
        if((day>=1&&day<=31)&&(month>=1&&month<=12)&&(year>=1)){
          UI.println("WOW! Welldone! "+day+"/"+month+"/"+year+ " is a valid date! You are a valid human that can tell time :^ )");
        }else{
          UI.println("That date is incorrect, you must go re-evaluate your life choices.");
        }

    }

    /**
     * Asks user for three integer and then checks if it is a valid date.
     */
    public void doCompletion(){
        int day = UI.askInt("Day: ");
        int month = UI.askInt("Month: ");
        int year = UI.askInt("Year (4 digits): ");
        this.validateDateCompletion(day, month, year);
    }

    /** COMPLETION
     * Determines whether the date specified by the three integers is a valid date.
     * For the Completion, you should check that
     * - Months have the correct number of days
     * - Years have 4 digits (between 1000 and 9999)
     * - On leap years February should have 29 days.
     *    A year is a leap year if:
     *       - The year can be evenly divided by 4 but not 100,  O
     *       - The year can be evenly divided by 400
     */
    public void validateDateCompletion(int day, int month, int year){

      /* for statments list the months with the same length, then if statment sa ys for those months what the int 'day' must be between*/

          for (month=month; (month==1||month==3||month==5||month==7||month==8||month==10||month==12);){
            if((day>=1&&day<=31)&&(year>=1000&&year<=9999&&(month>=1&&month<=12))){
            UI.println("WOW! Welldone! "+day+"/"+month+"/"+year+ " is a valid date! You are a valid human that can tell time :^ )");
            }
            else{
              UI.println("That date is incorrect, you must go re-evaluate your life choices.");
            }
            return;
        }
          for (month=month; (month==4||month==6||month==9||month==11);){
            if((day>=1&&day<=30)&&(year>=1000&&year<=9999&&(month>=1&&month<=12))){
            UI.println("WOW! Welldone! "+day+"/"+month+"/"+year+ " is a valid date! You are a valid human that can tell time :^ )");
            }else{
            UI.println("That date is incorrect, you must go re-evaluate your life choices.");
            }
            return;
        }
        for (month=month; (month==2);){
            if((day>=1&&day<=29)&&((year%4==0&&year%100!=0)||year%400==0)&&(year>=1000&&year<=9999&&(month>=1&&month<=12))){
            UI.println("WOW! Welldone! "+day+"/"+month+"/"+year+ " is a valid date! You are a valid human that can tell time :^ )");
            }
            else if((day>=1&&day<=28)&&((year%4!=0&&year%100==0)||year%400!=0)&&(year>=1000&&year<=9999&&(month>=1&&month<=12))){
            UI.println("WOW! Welldone! "+day+"/"+month+"/"+year+ " is a valid date! You are a valid human that can tell time :^ )");
            }
            else{
            UI.println("That date is incorrect, you must go re-evaluate your life choices.");
            }
            return;
            }
        }
    }
