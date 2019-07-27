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

public class ClassTimes {

    File myFile = new File ("classdata.txt");

    /**
    * Reads the class timetable file, printing out each line.
    * This method is very straightforward, and there are very similar
    * examples in the lecture notes.
    */
    public void printAll() {
        try
        {
            Scanner scan = new Scanner(myFile);
            while(scan.hasNextLine())
            {
                UI.println(scan.nextLine());
            }
        }

        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        UI.println("=========================");
    }


    /** Core 1
    * Read the class timetable file, printing out (to the text pane)
    * the class type, day, start time, end time, and room
    * for each class with the target course.
    * Print a message if there are no classes for the course.
    */
    public void doPrintCourse(){
        String course = UI.askString("Enter course code (eg ACCY111):").toUpperCase();
        UI.clearText();
        this.printCourse(course);
    }

    public void printCourse(String targetCourse){
        UI.println("\nClasses for course " + targetCourse);
        UI.println("=========================");
        int i = 0;

        try
        {
            Scanner scan = new Scanner(myFile);
            while(scan.hasNext())
            {
                if (scan.next().equals( targetCourse))
                {
                    UI.println(scan.nextLine());
                    i++;
                }

            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }

        if(i == 0)
        {
            UI.printf("The course %s does not exist.\n",targetCourse);
        }

        UI.println("=========================");
    }


    /** Core 2
    * Print out the name of the target room, and underline it.
    * Then read the class timetable file, printing out (to the text pane)
    *  the course code, class type, day, start time, end time
    *  for each class in the target room.
    * It will be best to read the six tokens on each line individually.
    */
    public void doPrintRoom() {
        String room = UI.askString("Enter room code (eg AM102):").toUpperCase();
        UI.clearText();
        this.printRoom(room);
    }

    public void printRoom(String targetRoom) {
        UI.println("Classes in " + targetRoom);
        UI.println("=======================");
        int i = 0;


        try
        {
            Scanner scan = new Scanner(myFile);

            String courseCode = ("");
            String classType = ("");
            String day = ("");
            String startTime = ("");
            String endTime = ("");
            String room = ("");

            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                startTime = scan.next();
                endTime = scan.next();
                room = scan.next();

                if (room.equals( targetRoom))
                {
                    UI.printf("%10s %10s %10s %10s %10s \n", courseCode, classType, day, startTime, endTime);
                    i++;
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        if(i == 0)
        {
            UI.printf("There are no classes in %s.\n",targetRoom);
        }
        else
        {
            UI.printf("there are %d courses in the room %s\n", i, targetRoom);
        }
        UI.println("=========================");
    }

    /** Core 3
    * Prints out the start time and underlines it, then
    * Reads the class timetable file, printing out (to the text pane)
    * the course code, class type, room, day and end time for
    * each class that starts at the target time.
    * It will be best to read the six tokens on each line individually.
    */
    public void doPrintAtStartTime() {
        int time = UI.askInt("Enter start time (eg 900):");
        UI.clearText();
        this.printAtStartTime(time);
    }

    public void printAtStartTime(int startTime) {
        UI.println("\nClasses starting at " + startTime);
        UI.println("============================");
        int i = 0;

        try
        {
            Scanner scan = new Scanner(myFile);

            String courseCode = ("");
            String classType = ("");
            String day = ("");
            int Time = (0);
            int endTime = (0);
            String room = ("");

            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                Time = scan.nextInt();
                endTime = scan.nextInt();
                room = scan.next();

                if (Time == ( startTime))
                {
                    UI.printf("%10s %10s %10s %10s %10d \n", courseCode, classType, room, day, endTime);
                    i++; //keeps count of the number of times the if has been satisfied
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        if(i == 0)
        {
            UI.printf("There are no classes at %s.\n",startTime);
        }
        else
        {
            UI.printf("there are %d courses at the time %s\n", i, startTime);
        }

        UI.println("=========================");
    }



    /** Completion 1
    * Prints a title containing its arguments, and then
    * Reads the class timetable file, printing out (to the text pane)
    * the course code, class type, day, start and end time
    * for each class that is in targetRoom1 or targetRoom2 and is on targetDay
    * It will be best to read the six tokens on each line individually.
    */
    public void doPrintInRoomsOnDay(){
        String room1 = UI.askString("Enter first room code (eg AM102):").toUpperCase();
        String room2 = UI.askString("Enter second room code (eg AM104):").toUpperCase();
        String day = this.askDay();
        UI.clearText();
        this.printInRoomsOnDay(room1, room2, day);
    }

    public void printInRoomsOnDay(String targetRoom1, String targetRoom2, String targetDay){
        UI.printf("Classes in %s or %s on %s%n", targetRoom1, targetRoom2, targetDay);
        UI.println("==========================================");
        int i = 0;

        try
        {
            Scanner scan = new Scanner(myFile);

            String courseCode = ("");
            String classType = ("");
            String day = ("");
            String startTime = ("");
            String endTime = ("");
            String room = ("");

            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                startTime = scan.next();
                endTime = scan.next();
                room = scan.next();

                if ((room.equals(targetRoom1) || room.equals(targetRoom2)) && day.equals(targetDay))
                {
                    UI.printf("%10s %10s %10s %10s %10s %10s \n", courseCode, classType, day, startTime, endTime, room);
                    i++;
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        if(i == 0)
        {
            UI.printf("There are no classes in %s or %s.\n",targetRoom1, targetRoom2);
        }
        else
        {
            UI.printf("there are %d courses across the rooms %s and %s\n", i, targetRoom1, targetRoom2);
        }

        UI.println("=========================");
    }


    /** Completion 2
    * Writes a new file listing all the class bookings that are in a given room.
    *  The name of the new file should be the room, followed by "_Bookings.txt"
    *  The first line of the file should specify what room the bookings are for:
    *  "Bookings for room <room name>"
    *
    *  Each class booking should be formatted in three lines, with a blank line after.
    *  Course: <Course Code>
    *  Time: <Day> <Start Time>-<End Time>
    *  Session: <Type>
    *
    *  For example, if the targetRoom is VZ515, then the start of the file would be as follows
    *
    *  Bookings for room VZ515
    *  ----------------------------------
    *  Course: ACCY111
    *  Time: Tue 1000-1050
    *  Session: Tutorial
    *
    *  Course: ACCY130
    *  Time: Thu 1310-1400
    *  Session: Tutorial
    *
    *  Course: ACCY130
    *  Time: Tue 1310-1400
    *  Session: Tutorial
    *
    */
    public void doBookingsFileForRoom() {
        String room = UI.askString("Enter room code (eg AM102):").toUpperCase();;
        UI.clearText();
        this.bookingsFileForRoom(room);
    }

    public void bookingsFileForRoom(String targetRoom){
        UI.println("Generating room booking file for " + targetRoom);
        int i = 0;

        try
        {
            String courseCode = ("");
            String classType = ("");
            String day = ("");
            String startTime = ("");
            String endTime = ("");
            String room = ("");

            Scanner scan = new Scanner(myFile);
            PrintStream out = new PrintStream(new File(targetRoom+"_Bookings.txt"));
            out.printf(" Bookings for room %s\n ----------------------------------\n", targetRoom);


            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                startTime = scan.next();
                endTime = scan.next();
                room = scan.next();

                if ((room.equals(targetRoom)))
                {
                    out.printf("Course: %s\n", courseCode);
                    out.printf("Time: %5s %5s-%5s\n", day, startTime, endTime);
                    out.printf("Session: %s\n\n", classType);
                    i++;
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        UI.println("Printed to "+targetRoom+"_Bookings.txt");
        UI.println("=========================");
    }

    /** Completion 3
    * Lists all the classes (just list the course code, type, and room) that are
    *  in the specified building (given by its abbreviation)
    *  on the specified day, and start at or after the specified start time
    * Note, the first part of every room name is an uppercase abbreviation of the building.
    */
    public void doInBuildingAfterTime() {
        String building = UI.askString("Enter building (eg HM):").toUpperCase();
        String day = this.askDay();
        int time = UI.askInt("Enter start time (eg 1315):");
        UI.clearText();
        this.inBuildingAfterTime(building, day, time);
    }

    public void inBuildingAfterTime(String targetBuilding, String targetDay, int targetStart){
        UI.printf("Classes in %s on %s after %d\n", targetBuilding, targetDay, targetStart);
        UI.println("============================");
        int i = 0;


        try
        {
            String courseCode = ("");
            String classType = ("");
            String day = ("");
            int startTime = (0);
            String endTime = ("");
            String room = ("");

            Scanner scan = new Scanner(myFile);

            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                startTime = scan.nextInt();
                endTime = scan.next();
                room = scan.next();

                if ((room.substring(0,2).equals(targetBuilding)) && day.equals(targetDay) && startTime >= targetStart)
                {
                    UI.printf("%10s %10s %10s \n", courseCode, classType, room);
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }
        UI.println("=========================");
    }

    /** Challenge 1
    * Computes and returns the average (mean) duration in minutes of all classes scheduled
    * in a specified room.
    * Hint: be careful with the times
    * Hint: if there are no classes in the room, do not cause an error.
    */
    public void doMeanClassLength() {
        String room = UI.askString("Enter room code (eg AM102):").toUpperCase();
        UI.clearText();
        double mean = this.meanClassLength(room);
        if (mean == 0) {
            UI.printf("There were no classes in  %s%n", room);
        }
        else {
            UI.printf("Average duration in %s = %4.2f mins%n",
            room,  mean);
        }
        UI.println("=========================");
    }

    public double meanClassLength(String targetRoom){
        int i = 0;
        int total = 0;
        double mean = 0;

        try
        {
            String courseCode = ("");
            String classType = ("");
            String day = ("");
            int startTime = (0);
            int endTime = (0);
            String room = ("");

            Scanner scan = new Scanner(myFile);

            while(scan.hasNext())
            {
                courseCode = scan.next();
                classType = scan.next();
                day = scan.next();
                startTime = scan.nextInt();
                endTime = scan.nextInt();
                room = scan.next();

                if (room.equals(targetRoom))
                {
                    total = total + (endTime-startTime);
                    i++;
                }
            }
        }
        catch(IOException e)
        {
            UI.printf("File Failure %s \n", e);
        }

        if (i>0)
        {
            mean = (total/i);
        }
        return mean;
    }

    /** Challenge 2
    * Lists all the courses (just the course code) that have classes in a given building
    * on a given day such that any part of the class is between the given times.
    * Each course involved should only be listed once, even if it has several classes
    * in the building in the time period.  Note, the data file is ordered by the course code.
    * Note that this is similar, but not the same as, one of the completion questions.
    */
    public void doPotentialDisruptions(){
        UI.clearText();
        String building = UI.askString("Enter a building code(eg AM):").toUpperCase();
        String day = this.askDay();
        int start = UI.askInt("What is the start time?");
        int end = UI.askInt("What is the end time?");
        this.potentialDisruptions(building, day, start, end);
    }

    public void potentialDisruptions(String building, String targetDay, int targetStart, int targetEnd){
        UI.printf("\nClasses in %s on %s between %d and %d%n",
        building, targetDay, targetStart, targetEnd);
        UI.println("=================================");
        /*# YOUR CODE HERE */

        UI.println("=========================");
    }

    /** Asks the user for a Day and returns as a capitalised three letter string */
    public String askDay(){
        String day;
        while (true) {
            day = UI.askString("Enter first 3 letters of day (eg Mon):");
            if (day.length()>=3)
            {
                break;
            }
            UI.println("You must enter at least three letters of the day.");
        }
        return day.substring(0,1).toUpperCase() + day.substring(1,3).toLowerCase();
    }


}
