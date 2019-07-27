// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 2
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import java.awt.Color;
import java.util.*;

/**
 * Represents information about an atom in a molecule.
 * <p>
 * The information includes
 * - the kind of atom
 * - the 3D position of the atom
 * x is measured from the left to the right
 * y is measured from the top to the bottom
 * z is measured from the front to the back.
 */

public class Atom implements Comparable<Atom> {
    
    private String kind;    // the kind of atom.
    // coordinates of center of atom, relative to top left front corner
    private double x;       // distance from the left
    private double y;       // distance from the top
    private double z;       // distance from the front
    private int number;     //the number of the atom
    private int bondingTo = -1;  //the number of the atom its bonding to
    
    private double rotateX = x;       // distance from the left after rotation
    private double rotateY = y;       // distance from the top after rotation
    private double rotateZ = z;       // distance from the front after rotation
    
    /**
     * Constructor: requires the position and kind
     */
    public Atom(double x, double y, double z, String kind, int num){
        this.x = x;
        this.y = y;
        this.z = z;
        this.kind = kind;
        this.number = num;
    }
    /**
     * get the kind of atom
     */
    public String getKind(){return kind;}
    
    /**
     * get X position (distance from the left)
     */
    public double getRotateX(){return rotateX;}
    
    /**
     * get Y position (distance from the top)
     */
    public double getY(){return y;}
    
    /**
     * get Z position (distance beyond the front)
     */
    public double getRotateY(){return rotateY;}
    
    /**
     * get the rotated z value
     */
    public double getRotatedZ(){return rotateZ;}
    
    /**
     * get the number of the atom in the list, to draw a bond
     */
    public int getNum(){return number;}
    
    /**
     * get the atom this atom is bonding to
     */
    public int getBond(){return bondingTo;}
    
    /**
     * set the y value to a new value
     */
    public void setY(double newY){y = newY;}
    
    /**
     * set the atom it is bonding to
     */
    public void setBond(int bond){bondingTo = bond;}
    
    /**
     * calculate the new x, y, and z coordinates of the atom by
     * rotating the x and z coordinates @phi degrees around the y axis.
     * Then rotate the y and z coordinates around the x axis by @theta.
     * This is done using two separate rotation matrix's
     */
    public void rotate(double phi, double theta){
        rotateX = ((Math.cos(phi) * x) - ((Math.sin(phi)) * z));
        rotateZ = ((Math.sin(phi) * x) + ((Math.cos(phi)) * z));
        
        rotateY = ((Math.cos(theta) * y) - ((Math.sin(theta)) * rotateZ));
        rotateZ = ((Math.sin(theta) * y) + ((Math.cos(theta)) * rotateZ));
    }
    
    /**
     * Renders the atom on the graphics pane at the x, and y specified by the atom,
     * but displacing the atom by xDisplacement and yDisplacement.
     * using a circle of the size and color specified in the elementInfo map.
     * The circle should also have a black border
     */
    public void render(double xDisplacment, double yDisplacment, Map<String, ElementInfo> elementInfo){
        
        UI.setLineWidth(1);
        double radius = elementInfo.get(kind).getRadius();
        Color color = elementInfo.get(kind).getColor();
        UI.setColor(color);
        UI.fillOval(rotateX + xDisplacment,rotateY + yDisplacment, radius, radius);
        UI.setColor(Color.black);
        UI.drawOval(rotateX + xDisplacment,rotateY + yDisplacment, radius, radius);
    }
    
    /**
     * compareTo returns
     * -1 if this comes before other
     * 0 if this is the same as other
     * 1 if this comes after other
     * The default ordering is by the z position (back to front)
     * so atoms with large z come before atoms with small z
     */
    public int compareTo(Atom other){
        if (rotateZ < other.getRotatedZ()){return -1;}
        else if (rotateZ > other.getRotatedZ()){return 1;}
        else{ return 0; }
    }
    
}
