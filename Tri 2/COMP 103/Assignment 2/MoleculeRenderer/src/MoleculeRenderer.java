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
import java.io.*;

/**
 * Renders a molecule on the graphics pane from different positions.
 * <p>
 * A molecule consists of a collection of molecule elements, i.e., atoms.
 * Each atom has a type or element (eg, Carbon, or Hydrogen, or Oxygen, ..),
 * and a three dimensional position in the molecule (x, y, z).
 * <p>
 * Each molecule is described in a file by a list of atoms and their positions.
 * The molecule is rendered by drawing a colored circle for each atom.
 * The size and color of each atom is determined by the type of the atom.
 * <p>
 * The description of the size and color for rendering the different types of
 * atoms is stored in the file "element-info.txt" which should be read and
 * stored in a Map.  When an atom is rendered, the element type should be looked up in
 * the map to find the size and color.
 * <p>
 * A molecule can be rendered from different perspectives, and the program
 * provides buttons to control the perspective of the rendering.
 */

public class MoleculeRenderer {
    
    public static final double MIDX = 300;   // The middle on the (x axis)
    public static final double MIDY = 200;     // The middle on the (y axis)
    public static final double MIDZ = 300;   // The middle depth (z axis)
    private double angleXY = 0;
    private double angleYZ = 0;
    
    // Map containing info about the size and color of each element type.
    private Map<String, ElementInfo> elements = new HashMap<>();
    
    // The collection of the atoms in the current molecule
    private List<Atom> molecule = new ArrayList<Atom>();
    
    // Constructor:
    
    /**
     * Sets up the Graphical User Interface and reads the file of element data of
     * each possible type of atom into a Map: where the type is the key
     * and an ElementInfo object is the value (containing size and color).
     */
    public MoleculeRenderer(){
        setupGUI();
        readElementInfo();    //  Read the atom info
     
    }
    
    public void setupGUI(){
        UI.addButton("Molecule", this::loadMolecule);
        UI.addButton("FromFront", this::showFromFront);
        UI.addButton("FromBack", this::showFromBack);
        UI.addButton("FromRight", this::showFromRight);
        UI.addButton("FromLeft", this::showFromLeft);
        UI.addButton("FromTop", this::showFromTop);
        UI.addButton("FromBot", this::showFromBot);
        UI.addButton("Quit", UI::quit);
        UI.setKeyListener(this::doKey);
        UI.setImmediateRepaint(false);
        UI.setDivider(0.0);
    }
    
    /**
     * When key press is on left key, rotate the molecule left 2 degrees at a time
     * When key press is on right key, rotate the molecule right 2 degrees at a time
     * When key press is on up key, rotate the molecule up 2 degrees at a time
     * When key press is on down key, rotate the molecule down 2 degrees at a time
     */
    private void doKey(String key){
        if(key.equalsIgnoreCase("right")){
            UI.clearGraphics();
            angleXY -= (2 * (Math.PI/180));
            display(angleXY, angleYZ);
        }
        
        else if(key.equalsIgnoreCase("left")){
            UI.clearGraphics();
            angleXY += (2 * (Math.PI/180));
            display(angleXY, angleYZ);
        }
        else if(key.equalsIgnoreCase("up")){
            UI.clearGraphics();
            angleYZ += (2 * (Math.PI/180));
            display(angleXY, angleYZ);
        }
        else if(key.equalsIgnoreCase("down")){
            UI.clearGraphics();
            angleYZ -= (2 * (Math.PI/180));
            display(angleXY, angleYZ);
        }
    }
    /**
     * Reads the file "element-info.txt" which contains radius and color
     * information about each type of element:
     * element name, a radius, and red, green, blue, components of the color (integers)
     * Stores the info in a Map in the elements field.
     * The element name is the key.
     */
    private void readElementInfo(){
        UI.printMessage("Reading the element information...");
        try{
            Scanner scan = new Scanner(new File("element-info.txt"));
            while (scan.hasNext()){
                String element = scan.next();
                int radius = scan.nextInt();
                int R = scan.nextInt();
                int G = scan.nextInt();
                int B = scan.nextInt();
                Color color = new Color(R, G, B);
                
                ElementInfo newElement = new ElementInfo(element, radius, color);
                elements.put(element, newElement);
            }
            
            UI.printMessage("Reading element information: " + elements.size() + " elements found.");
        } catch (IOException ex){
            UI.printMessage("Reading element information FAILED." + ex);
        }
    }
    
    /**
     * Ask the user to choose a file that contains info about a molecule,
     * load the information, and render it on the graphics pane.
     */
    public void loadMolecule(){
        String filename = UIFileChooser.open();
        readMoleculeFile(filename);
        showFromFront();
    }
    
    /**
     * Reads the molecule data from a file containing one line for
     * each atom in the molecule.
     * Each line contains an atom type and the 3D coordinates of the atom.
     * For each atom, the method constructs a Atom object,
     * and adds it to the List of molecule elements in the molecule.
     * To obtain the color and the size of each atom, it has to look up the name
     * of the atom in the Map of ElementInfo objects.
     */
    public void readMoleculeFile(String fname){
        molecule.clear();
        int minY = 1000;
        int maxY = -1000;
        try{
            Scanner scan = new Scanner(new File(fname));
            int count = 0;
            while (scan.hasNext()){
                if(!scan.hasNextInt()){
                    String element = scan.next();
                    int x = scan.nextInt();
                    int y = scan.nextInt();
                    int z = scan.nextInt();
                    Atom newAtom = new Atom(x, y, z, element, count);
                    molecule.add(newAtom);
                    count++;
    
                    if (y < minY){minY = y;}  //find the min and max y value
                    if (y > maxY){maxY = y;}
                }
                else{
                    int atom1 = scan.nextInt();
                    int atom2 = scan.nextInt();
                    molecule.get(atom1).setBond(atom2);
                }
            }
            scan.close();
        } catch (Exception e){
            UI.println("Reading molecule file " + fname + " failed." + e);
        }
        int displaceY = maxY - minY;   // alter the y valused so that they are centered around MIDY instead
        for(Atom atom : molecule){
            atom.setY((atom.getY() - (displaceY/2)));
        }
        
    }
    
    /**
     * Display the molecule with the viewing angleXY being phi degrees from infront,
     * and angle YZ being theta drgrees from original position
     */
    
    public void display(double phi, double theta){
        for (Atom atom : molecule){
            atom.rotate(phi, theta);
        }
        Collections.sort(molecule);
        
        for(Atom atom : molecule){
            atom.render(MIDX, MIDY, elements);
            
            int bond = atom.getBond();
            
            for(Atom bonds : molecule){
                if(bonds.getNum() == bond && (!(bond== -1))){
                    UI.setLineWidth(3);
                    
                    double atom1X = atom.getRotateX() + MIDX + ((elements.get(atom.getKind()).getRadius())/2);
                    double atom1Y = atom.getRotateY() + MIDY + ((elements.get(atom.getKind()).getRadius())/2);
                    double atom2X = bonds.getRotateX()+ MIDX + ((elements.get(bonds.getKind()).getRadius())/2);
                    double atom2Y = bonds.getRotateY()+ MIDY + ((elements.get(bonds.getKind()).getRadius())/2);
    
                    UI.drawLine(atom1X, atom1Y, atom2X,atom2Y);
                }
            }
        }
        UI.repaintGraphics();
    }
    
    /**
     * Renders the molecule from the front.
     * Set the rotation to 0 degrees along x and z, this
     * means that the molecule wont rotate and will therefore
     * display front to back.
     */
    public void showFromFront(){
        UI.clearGraphics();
        angleXY = 0;
        angleYZ = 0;
        display(angleXY, angleYZ);
    }

    /**
     * Renders the molecule from the back.
     * Set the rotation to 180 degrees along x and z
     */
    public void showFromBack(){
        UI.clearGraphics();
        angleXY = Math.PI;
        angleYZ = 0;
        display(angleXY, angleYZ);
    }

    /**
     * Renders the molecule from the left.
     * Set the angle of rotation to -90 degrees along x and z
     */
    public void showFromLeft(){
        UI.clearGraphics();
        angleXY = -(Math.PI/2);
        angleYZ = 0;
        display(angleXY, angleYZ);
    }

    /**
     * Renders the molecule from the right.
     * Set the angle of rotation to 90 degrees along x and z
     */
    public void showFromRight(){
        UI.clearGraphics();
        angleXY = Math.PI/2;
        angleYZ = 0;
        display(angleXY, angleYZ);
    }

    /**
     * Renders the molecule from the top.
     * Set the angle ot rotation to -90 degrees along y and z
     */
    public void showFromTop(){
        UI.clearGraphics();
        angleXY = 0;
        angleYZ = -(Math.PI/2);
        display(angleXY, angleYZ);
    }

    /**
     * Renders the molecule from the bottom.
     * Set the angle ot rotation to 90 degrees along y and z
     */
    public void showFromBot(){
        UI.clearGraphics();
        angleXY = 0;
        angleYZ = (Math.PI/2);
        display(angleXY, angleYZ);
    }
    
    public static void main(String args[]){
        new MoleculeRenderer();
    }
}
