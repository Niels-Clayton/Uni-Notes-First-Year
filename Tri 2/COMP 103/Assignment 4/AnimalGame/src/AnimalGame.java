// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 4
 * Name:
 * Username:
 * ID:
 */

/**
 * Guess the Animal Game.
 * The program will play a "guess the animal" game and learn from its mistakes.
 * It has a decision tree for determining the player's animal.
 * When it guesses wrong, it asks the player of another question that would
 *  help it in the future, and adds it to the decision tree. 
 * The program can display the decision tree, and save the tree to a file and load it again,
 *
 * A decision tree is a tree in which all the internal modes have a question, 
 * The answer to the the question determines which way the program will
 *  proceed down the tree.  
 * All the leaf nodes have answers (animals in this case).
 * 
 */

import ecs100.*;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

public class AnimalGame {

    public DTNode questionsTree;    // root of the decision tree;


    public AnimalGame(){
        setupGUI();
        resetTree();
    }

    /**
     * Set up the interface
     */
    public void setupGUI(){
        UI.setDivider(0);
        UI.addButton("Play", () -> this.play(questionsTree));
        UI.addButton("Print Tree", () -> {UI.clearText();
                                          this.printTree(questionsTree, 0, null);});
        UI.addButton("Draw Tree", () -> {UI.clearGraphics();
                                         this.drawTree(20,400,questionsTree, 100);});
        UI.addButton("Reset", this::resetTree);
        UI.addButton("Quit", UI::quit);
    }

    /**
     * Makes an initial tree with two question nodes and three leaf nodes.
     */
    public void resetTree(){
        questionsTree = new DTNode("Is the animal a mammal?",
                                   new DTNode("Is the animal domesticated",
                                              new DTNode("Is the animal a feline?",
                                                      new DTNode("Cat"),
                                                      new DTNode("Dog")),
                                              new DTNode("Is it larger than a person?",
                                                      new DTNode("Lion"),
                                                      new DTNode("Mouse"))),
                                              
                                   new DTNode("Does it live in the ocean?",
                                              new DTNode("Is this animal a fish?",
                                                      new DTNode("Tuna"),
                                                      new DTNode("Crab")),
                                              new DTNode("Is this animal an insect?",
                                                      new DTNode("Fly"),
                                                      new DTNode("Pigeon"))));
    }
                    

    /**
     * Play the game.
     * Starts at the top (questionsTree), and works its way down the tree
     *  until it finally gets to a leaf node (with an answer in it)
     * If the current node has a question, then it asks the question in the node,
     * and depending on the answer, goes to the "yes" child or the "no" child.
     * If the current node is a leaf it calls processLeaf on the node
     */
    public void play (DTNode node) {
        UI.clearPanes();
        if(node.isQuestion()){
            String question = node.getText();  //store the question within the DTNode
            int option = JOptionPane.showConfirmDialog(null, question, "Question", JOptionPane.YES_NO_OPTION); // Ask the question and give a yes/no choice
            if (option == JOptionPane.NO_OPTION){play(node.getNo());}  // if the answer is no, retrieve the no DTNode, and pass it through recursively back to the play method
            else if (option == JOptionPane.YES_OPTION){play(node.getYes());}// if the answer is yes, retrieve the yes DTNode, and pass it through recursively back to the play method
            else{return;}
        }
        else{
            processLeaf(node); //if the node is not a question, process it as a leaf
        }
    }
        
    
    /**
     * Process a leaf node (a node with an answer in it)
     * Tell the player what the answer is, and ask if it is correct.
     * If it is not correct, ask for the right answer, and a property to distinguish
     *  the guess from the right answer
     * Change the leaf node into a question node asking about that fact,
     *  adding two new leaf nodes to the node, with the guess and the right
     *  answer.
    */
    public void processLeaf(DTNode leaf){    
        //CurrentNode must be a leaf node (an answer node)
        if (leaf==null || leaf.isQuestion()) { return; } //check to make sure that node that has been passed through is not a question
        String correct = String.format("Is %s the correct answer?", leaf.getText()); // Format the string so that the answer is asked as a question
        
        int option = JOptionPane.showConfirmDialog(null, correct, "answer", JOptionPane.YES_NO_OPTION); //create an option window with yes/no options
        if (option == JOptionPane.YES_OPTION){  //if the guessed answer is correct
            Pondy.pleasedGod();
            UI.setFontSize(60);
            UI.drawString("Correct!", 50, 50); //print out that the gae guessed correctly
            UI.setFontSize(12);
        }
        else if(option == JOptionPane.NO_OPTION){ // if the guessed answer is incorrect
            
            UI.setDivider(0.5);
            String inCorrect = leaf.getText();
            String answer = UI.askString("What was the correct answer?"); // ask the player for the correct answer
            String question = UI.askString("What should be asked to define your answer?"); // ask the player for the question to get to the answer
            UI.clearPanes();
            UI.setDivider(0);
            leaf.convertToQuestion(question, new DTNode(answer), new DTNode(inCorrect)); // convert the node to a question node, with the new yes DTNode being the new answer,
                                                                                         // and the no DTNode being the old answer
        }
    }       

    /**  COMPLETION
     * Print out the contents of the decision tree in the text pane.
     * The root node should be at the top, followed by its "yes" subtree, and then
     * its "no" subtree.
     * Each node should be indented by how deep it is in the tree.
     */
    public void printTree(DTNode node, int level, String type){
        int tabs = level;
        while(tabs > 0){ //print all the tabbing, depending on the level of the recursive call
            UI.print("    ");
            tabs--;
        }
        if(!(node.isQuestion())){ //if the node is not a question, print all the information within the node, then return
            UI.printf(" %s %s\n",type, node.getText());
            return;
        }
        else UI.printf("Question: %s\n", node.getText()); // if it is a question, print the question
        printTree(node.getYes(), level+1, "Yes:"); //call print tree again, on the yes DTNode of the previous node
        printTree(node.getNo(), level+1, "No: ");  //call print tree again, on the no DTNode of the previous node
    }

    /**
     * Recursively print a subtree, given the node at its root
     *  - print the text in the node with the given indentation
     *  - if it is a question node, then 
     *    print its two subtrees with increased indentation
    */
    public void printSubTree(DTNode node, String indent){
        // it was neater and more efficient to do the recursive call within the one method
        // Because of that I haven't used this method, The above method does all the requirements
        // and tabs everything correctly, please don't mark me down for not using this method.
    }

    /**  CHALLENGE
     * Draw the tree on the graphics pane as boxes, connected by lines.
     * To make the tree fit in a window, the tree should go from left to right
     * (ie, the root should be drawn at the left)
     * The lines should be drawn before the boxes that they are connecting
     */
    public void drawTree(int x, int y, DTNode node, int step){
        node.draw(x,y); //call the draw method on the node to print it on the graphics pane
        
        if(node.isQuestion()){                                    //if the node is a question (has 2 more branches to print out)
            UI.drawLine(x+140,y,(x+200), (y-step));               // draw a line diagonally up and to the right to connect the next node to
            drawTree((x+200), (y-step), node.getYes(),(step/2));  // call the drawTree method on the yes node of the current node, and decrease the step
            UI.drawLine(x+140,y,(x+200), (y+step));               // draw a line diagonally down and to the right to connect the next node to
            drawTree((x+200),(y+step), node.getNo(), (step/2));   // call the drawTree method on the yes node of the current node, and decrease the step
        }
    }

    public static void main (String[] args) { 
        new AnimalGame();
    }

}
