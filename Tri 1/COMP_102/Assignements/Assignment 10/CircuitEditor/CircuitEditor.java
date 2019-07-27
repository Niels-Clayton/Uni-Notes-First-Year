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
import java.util.*;
import java.io.*;

/**
* A Circuit Diagram editor.
* The diagrams consist of components (resistors, capacitors, sources, or connectors)
* and wires.
* Components can be horizontal or vertical. (which way the two connectors come out)
* Components can have text labels
* Wires run horizontally from the start position, then vertically to the end position.
* The user can add new components or wires, or select existing components or wires
*  to rotate, label, or delete. When selecting, the user can also move items
* One component or one wire can be selected. It should be highlighted in the diagram.
* After each button, or after the mouse is released, the diagram will be redrawn.
*
*   Instructions:
*     Using the mouse.
*      If one of the component tools has been chosen:
*         Add a new component by clicking the mouse at the point
*         where the component should be.
*         The new component will become the selected component.
*      If the Wire tool has been chosen:
*         Add a wire by dragging from one end to the other
*         The new wire will become the selected wire
*      If the Select button has been chosen:
*         - pressing on a component then releasing will move
*           the component to the release point, and make it be selected.
*         - pressing on a wire then releasing will move
*           the wire by the distance between the pressed and released point
*           and make it be selected
*         - pressing anywhere else makes nothing be selected
*    TextField and other buttons
*      If a value is entered in the label box, and there is a selected component, then
*         the component will be given that label.
*      If the Delete button is pressed,
*         then the selected component or the selected wire is removed from the diagram
*      If the Rotate button is pressed,
*         then the selected component is rotated or
*         the selected wire is reversed (exchanging the start and end points).
*      If the Clear button is pressed, the whole drawing is removed.
*/

public class CircuitEditor{

    private ArrayList<Component> components = new ArrayList<Component>();
    private ArrayList<Wire> wires = new ArrayList<Wire>();

    private Component selectedComponent;
    private Wire selectedWire;
    private Component pressedComponent;
    private Wire pressedWire;
    private double startX, startY;
    private String tool = "resistor";
    private int comp_type = 0;


    //Constructor
    public CircuitEditor(){
        this.setupGUI();
    }

    /** Sets up the user interface - mouselistener, buttons, and textField */
    public void setupGUI(){
        UI.setMouseListener( this::doMouse );
        UI.addButton("Clear", this::clearCircuit);
        UI.addButton("Resistor", this::set_resistor);
        UI.addButton("Capacitor", this::set_capacitor);
        UI.addButton("Source", this::set_source);
        UI.addButton("Connector", this::set_connector);
        UI.addButton("wire", this::set_wire);
        UI.addButton("select", this::set_select);
        UI.addButton("Rotate", this::doRotate);
        UI.addButton("Delete", this::doDelete);
        UI.addTextField("Label", this::setLabel);
        UI.addButton("save", this::saveDrawing);
        UI.addButton("Load", this::loadDrawing);
        UI.addButton("Quit", UI::quit);
        UI.setLineWidth(2);
        UI.setDivider(0.0);  // Hide the text area.
    }

    // Methods to change the tool that controls what will be drawn next
    // These methods just save information to the fields.

    public void set_wire(){
        this.tool = "wire";
    }
    public void set_resistor(){
        this.tool = "resistor";
    }
    public void set_capacitor(){
        this.tool = "capacitor";
    }
    public void set_source(){ this.tool = "source";}
    public void set_select(){this.tool = "select"; }
    public void set_connector(){this.tool = "connector";}

    //Clear the diagram and reset everything.

    public void clearCircuit(){
        this.components.clear();
        this.wires.clear();
        this.redraw();
    }

    /**
    * Add the label to the selected component (if there is one)
    */
    public void setLabel(String v){
        if(selectedComponent != null && this.comp_type == 0){
            selectedComponent.setLabel(v);
        }
        else{
            UI.println("There is no selected component");
        }
        this.redraw();
    }

    /**
    * Delete the selected component (if there is one)
    */
    public void doDelete(){
        if(this.comp_type == 0){
            if(this.selectedComponent != null){
                this.components.remove(this.selectedComponent);
            }
        }
        if(this.comp_type == 1){
            if(this.selectedWire != null){
                this.wires.remove(this.selectedWire);
            }
        }
        this.redraw();
    }

    /**
    * Rotate the selected component between horizontal and vertical
    */
    public void doRotate(){
        if(this.comp_type == 0){
            if(this.selectedComponent != null){
                this.selectedComponent.rotate();
            }
        }
        if(this.comp_type == 1){
            if(this.selectedWire != null){
                this.selectedWire.rotate_wire();
            }
        }
        this.redraw();
    }


    /**
    * Respond to mouse events
    * When pressed,
    *   Remember the position and any component or wire it is on.
    * When released:
    *   make the mouse implement the instructions above.
    *   redraw the diagram
    */
    public void doMouse(String action, double x, double y) {
        selectedWire = findWireAt(x, y);
        selectedComponent = findComponentAt(x, y);

        if(tool.equals("resistor")){
            if(action.equals("clicked")){
                this.components.add(new Component("resistor", x, y));
                this.redraw();
                Trace.println(this.components.size());
            }
        }
        if(tool.equals("capacitor")){
            if(action.equals("clicked")){
                this.components.add(new Component("capacitor", x, y));
                this.redraw();
                Trace.println(this.components.size());
            }
        }
        if(tool.equals("source")){
            if(action.equals("clicked")){
                this.components.add(new Component("source", x, y));
                this.redraw();
                Trace.println(this.components.size());
            }
        }
        if(tool.equals("connector")){
            if(action.equals("clicked")){
                this.components.add(new Component("connector", x, y));
                this.redraw();
                Trace.println(this.components.size());
            }
        }

        if(tool.equals("select")){
            if(action.equals("pressed")){
                if(selectedWire != null){
                    this.comp_type = 1;
                    this.redraw();
                    selectedWire.highlight_wire();
                    this.pressedWire = selectedWire;
                }
                if(selectedComponent != null){
                    this.comp_type = 0;
                    this.redraw();
                    selectedComponent.highlight();
                    this.pressedComponent = selectedComponent;
                }
            }
            if(action.equals("released")){
                if(pressedComponent != null){
                    if((Math.abs(x - this.pressedComponent.x_position()) >20 ||
                    (Math.abs(y - this.pressedComponent.y_position()) >20))){
                        this.pressedComponent.moveTo(x, y);
                        this.pressedComponent = null;
                        this.redraw();
                    }
                }
            }
        }

        if(tool.equals("wire")){
            if(action.equals("pressed")){
                this.startX = x;
                this.startY = y;
            }
            if( action.equals("released")){
                this.wires.add(new Wire(startX, startY ,x ,y));
                this.redraw();
                Trace.println(this.wires.size());
            }
        }
    }

    /**
    * Redraws the diagram.
    * First redraws all the wires,
    * Then highlights the selected wire, if there is one
    * Then redraws all the components
    * Then highlights the selected component, if there is one
    */
    public void redraw(){
        UI.clearGraphics();
        for(Wire wire : wires){
            wire.draw_wire();
        }
        for(Component component : components){
            component.redraw();
        }
    }

    // Two methods that may be useful

    /**
    * Find and return a component from the diagram that the point (x, y) is on
    * Return null if no such component
    */
    public Component findComponentAt(double x, double y){
        for(Component comp : components){
            if(comp.on(x, y)){
                return comp;
            }
        }
        return null;
    }


    /**
    * Find and return a wire in the diagram that the position (x,y) is on.
    * Return null if no such wire.
    */
    public Wire findWireAt(double x, double y){
        for(Wire wire : wires){
            if(wire.on_wire(x, y)){
                return wire;
            }
        }
        return null;
    }

    /**
    * Load a new drawing from a file.
    * Each line of the file has the type of component or wire, and the field values
    */
    public void loadDrawing(){
        try{
            Scanner scan = new Scanner(new File(UIFileChooser.open()));
            while(scan.hasNext()){
                String line = scan.nextLine();
                Scanner sc = new Scanner(line);
                String type = sc.next();
                if(type.equals("wire")){
                    double x1 = (sc.nextDouble());
                    double y1 = (sc.nextDouble());
                    double x2 = (sc.nextDouble());
                    double y2 = (sc.nextDouble());
                    this.wires.add(new Wire(x1, y1 ,x2 ,y2));

                }
                else{
                    double x = sc.nextDouble();
                    double y = sc.nextDouble();
                    boolean orientation = Boolean.parseBoolean(sc.next());
                    if(sc.hasNext()){
                        String label = sc.nextLine();
                        this.components.add(new Component(type, x, y, orientation, label));
                    }
                    else{
                        Component comp = new Component(type, x, y);
                        if(orientation != true){
                            comp.rotate();
                        }
                        this.components.add(comp);
                    }
                }
                sc.close();
            }
            scan.close();
        }
        catch(IOException e){}
            this.redraw();
        }

        /**
        * Save a drawing to a file.
        * Each line of the file has the type of component or wire, and the field values
        */
        public void saveDrawing(){
            try{
                PrintStream print = new PrintStream(new File(UI.askString("name the file")));
                for(Wire wire : wires){
                    print.println(wire.toString());
                }
                for(Component comp : components){
                    print.println(comp.toString());
                }
            }
            catch(IOException e){}

            }



            // Main:  constructs a new CircuitEditor object
            public static void main(String[] arguments){
                new CircuitEditor();
            }

        }
