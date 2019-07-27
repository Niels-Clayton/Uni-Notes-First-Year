// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 4
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.*;


/** 
 *  Measures the performance of different ways of doing a priority queue of Patients
 *  Uses a cutdown version of Patient that has nothing in it but a priority (so it takes
 *   minimal time to construct a new Patient).
 *  The Patient constructor doesn't need any arguments
 *  Remember that small priority values are the highest priority - 1 is higher priority than 10.
 *  Algorithms to test and measure:
 *      Use a built-in PriorityQueue
 *      Use an ArrayList, with queue's head at 0,   sorting when you add an item.
 *      Use an ArrayList, with queue's head at end, sorting when you add an item.
 *  Each method should have an items parameter, which is a collection of Patients
 *    that should be initially added to the queue (eg  new PriorityQueue<Patient>(items); or
 *    new ArrayList<Patient>(items))
 *    It should then repeatedly dequeue a patient from the queue, and enqueue a new Patient(). 
 *    It should do this 100,000 times.
 *    (the number of times can be changed using the textField)
 *  To test your methods, you should have debugging statements such as UI.println(queue)
 *   in the loop to print out the state of the queue. You could comment them out before measuring.
 */

public class MeasuringQueues{

    private static final int TIMES=100000;  //Number of times to repeatedly dequeue and enqueue item

    /**
    * Construct a new MeasuringQueues object
    */
    public MeasuringQueues(){
       setupGUI();
    }

    /**
    * Setup the GUI
    */
    public void setupGUI(){
       UI.addButton("Test", this::test);
       UI.addButton("Measure", this::measure);
       UI.addButton("Quit", UI::quit);
       UI.setDivider(1.0);
    }


    /**
     * Create a priority queue using a PriorityQueue, 
     * adding all the patients in the collection to the queue.
     * (n will be the size of the the collection in the patients parameter).
     * Then, dequeue and enqueue TIMES times.
     */
    public double useQueuesPQ(Collection<Patient> patients){
        double startTime = System.currentTimeMillis();
        Queue<Patient> priorityQueue = new PriorityQueue<>(patients);  //create a priority queue with the patients collection in it
        //UI.println(priorityQueue);
        for (int i = 0; i < TIMES; i++){
            priorityQueue.poll();                                      //poll from the priority queue
            priorityQueue.offer(new Patient());                        //add a new patient to the queue
            //UI.println(priorityQueue);
        }
        return (System.currentTimeMillis() - startTime);               //return the total time to complete
        
    }
    
    

    /**
     * Create a queue using an ArrayList with the head at the end.
     * Make a new ArrayList using all the patients in the collection,
     * and then sorting the list.
     * Then, dequeue and enqueue TIMES times.
     * (n will be the size of the the collection in the patients parameter).
     * Note: Head of queue is at the end of the list, 
     * so we need to sort in the reverse order of Patients (so the smallest value comes at the end)
     */
    public double useQueuesALEnd(Collection<Patient> patients){

    
        double startTime = System.currentTimeMillis();
        List<Patient> headAtEnd = new ArrayList<>(patients); //create a new arraylist with the patients collection in it
        headAtEnd.sort(Collections.reverseOrder());          //do an initial sort, in reverse order
    
        for (int i = 0; i < TIMES ; i++){
            headAtEnd.sort(Collections.reverseOrder());         // sort using a comparator that puts the lowest priority first
                                                                // because objects are always removed from the end of this
                                                                // the Patients with height est priority will be removed first.
            headAtEnd.remove((headAtEnd.size()-1)); //remove from the end of the list
            headAtEnd.add(new Patient());  //add a new patient
        }
        return (System.currentTimeMillis() - startTime); //return the completion time
    }

    /**
     * Create a queue using an ArrayList with the head at the start.
     * Head of queue is at the start of the list
     * Make a new ArrayList using all the patients in the collection,
     * and then sorting the list.
     * Then, dequeue and enqueue TIMES times.
     * (n will be the size of the the collection in the patients parameter).
     */
    public double useQueuesALStart(Collection<Patient> patients){
        
        double startTime = System.currentTimeMillis();   //take the start time
        List<Patient> headAtStart = new ArrayList<>(patients);  //create a new arraylist and pass it the patients collection
        headAtStart.sort(Patient::compareTo);          //complete an initial sort

        for (int i = 0; i < TIMES ; i++){
            headAtStart.remove(0);           //remove the patient from the front of the list
            headAtStart.add(new Patient());  //add a new patient to the list
            Collections.sort(headAtStart);   //sort the list
        }
        return (System.currentTimeMillis() - startTime);  //return the total time to complete
    }



    /**
     * For a sequence of values of n, from 1000 to 1024000,
     *  - Construct a collection of n Patients (This step shouldn't be included in the time measurement)
     *  - call each of the methods, passing the collection, and measuring
     *    how long each method takes to dequeue and enqueue a Patient 100,000 times
     */
    public void measure(){
        UI.printf("Measuring methods using %d repeitions, on queues of size 1000 up to 1,024,000\n",TIMES);
        UI.println("      n      PQ      ALEnd      ALStart\n===================================================================");
        int n = 1000;
        Collection<Patient> items = new ArrayList<Patient>();
        while(n <= 1024000){
            for (int i = 0; i < n ; i++){ items.add(new Patient());}
            UI.printf("|%,d|      |%,.0f ms|      |%,.0f ms|      |%,.0f ms|\n", n, useQueuesPQ(items), useQueuesALEnd(items), useQueuesALStart(items));
            System.gc();
            n = n*2;
        }
        

    }

    /**
     * Method for testing the methods on short queues, to make sure that they work.
     * Make sure you change the value of TIMES to something small, like 10,
     * And include debugging println's in the methods
     */
    public void test(){
        Collection<Patient> items = new ArrayList<Patient>();
        for (int i=0; i<10; i++) { items.add(new Patient()); }

        UI.println("======== Testing useQueuesPQ ============");
        useQueuesPQ(items);
        
        UI.println("======== Testing useQueuesALEnd ============");
        useQueuesALEnd(items);
        
        UI.println("======== Testing useQueuesALStart ============");
        useQueuesALStart(items);
    }
        


    public static void main(String[] arguments){
        new MeasuringQueues();
    }


}
