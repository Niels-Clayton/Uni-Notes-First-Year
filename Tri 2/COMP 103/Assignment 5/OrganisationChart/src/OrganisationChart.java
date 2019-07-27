// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 5
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import javax.swing.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/** <description of class OrganisationChart>
 */

public class OrganisationChart {

    // Fields
    private Employee organisation;          // the root of the current organisational chart
    private Employee selectedEmployee = null; // the employee selected by pressing the mouse
    private Employee newPerson = null;      // the employee constructed from the data
                                            //  the user entered

    private String newInitials = null;      // the data the user entered
    private String newRole = null;
    

    // constants for the layout
    public static final double NEW_LEFT = 10; // left of the new person Icon
    public static final double NEW_TOP = 10;  // top of the new person Icon

    public static final double ICON_X = 40;   // position and size of the retirement icon
    public static final double ICON_Y = 90;   
    public static final double ICON_RAD = 20;   

    /**
     * Construct a new OrganisationChart object
     * Set up the GUI
     */
    public OrganisationChart() {
        this.setupGUI();
        organisation = new Employee(null, "CEO");   // Set the root node of the organisation
        redraw();
    }

    /**
     * Set up the GUI (buttons and mouse)
     */
    public void setupGUI(){
        UI.setMouseMotionListener( this::doMouse );
        UI.addTextField("Initials", (String v)-> {newInitials=v; redraw();});
        UI.addTextField("Role", (String v)-> {newRole=v; redraw();});
        UI.addButton("Load test tree",  this::makeTestTree);
        UI.addButton("Save to file", this:: makeFile);
        UI.addButton("Load from file", () -> makeTree(readFile()));
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1100,500);
        UI.setDivider(0);
    }

    /**
     * Most of the work is initiated by the mouse.
     * The action depends on where the mouse is pressed:
     *   on an employee in the tree, or
     *   the new person
     * and where it is released:
     *   on another employee in the tree,
     *   on the retirement Icon, or
     *   empty space
     * An existing person will be moved around in the tree, retired, or repositioned.
     * The new person will be added into the tree;
     */
    public void doMouse(String action, double x, double y){
        if (action.equals("pressed")){
            if (onNewIcon(x, y)) {
                // get the new person
                newPerson = new Employee(newInitials, newRole);
                selectedEmployee = null;
            }
            else {
                // find the selected employee
                selectedEmployee = findEmployee(x, y, organisation);
                newPerson = null;
            }
        }
        else if (action.equals("released") || action.equals("dragged")){
            Employee targetEmployee = findEmployee(x, y, organisation); 

            // acting on an employee in the tree
            if (selectedEmployee != null) {
                if (onRetirementIcon(x, y) ){
                    // moving employee to retirement 
                    retirePerson(selectedEmployee);
                }
                else if (targetEmployee == null || targetEmployee==selectedEmployee) { 
                    // repositioning employee
                    selectedEmployee.moveOffset(x);
                }
                else if (targetEmployee != null) {
                    // Moving existing employee around in the hierarchy.
                    moveEmployee(selectedEmployee, targetEmployee);  
                }
            }

            // acting on the new person
            else if (newPerson != null) {  
                if (targetEmployee != null ) {
                    // Moving new person to hierarchy.
                    addNewPerson(newPerson, targetEmployee);
                    newInitials=null;
                    newRole=null;
                }
            }
            this.redraw();
        }
    }

    
    
    /**
     * Find and return an employee that is currently placed over the position (x,y). 
     * Must do a recursive search of the subtree whose root is the given employee.
     * Returns an Employee if it finds one,
     * Returns null if it doesn't.
     * [Completion:] If (x,y) is on two employees, it should return the top one.
     */

    private Employee findEmployee(double x, double y, Employee empl){
        if (empl.on(x, y)) {   // base case: (x,y) is on root of subtree
            return empl;
        }
        else {
            if(empl.isManager()){
                Stack<Employee> clickedEmployees = new Stack<>();
                
                for(Employee staff : empl.getTeam()){
                    Employee clicked = findEmployee(x,y,staff);
                    if(clicked != null) clickedEmployees.push(clicked);
                }
                if( !clickedEmployees.isEmpty()) return clickedEmployees.pop();
            }
        }
        return null;  // it wasn't found;
    }

    /**
     * Add the new employee to the target
     * [STEP 2:] If target is not vacant, add new employee to the target's team
     * [STEP 3:] If target is vacant, fill the position with the initials of new employee
     * [COMPLETION:] If the newPerson has a role but no initials, change the role of the target.
     */
    public void addNewPerson(Employee newEmpl, Employee target){
        if ((newEmpl == null) || (target == null)){return;}   //invalid arguments.
    
        if(newEmpl.isVacant() && newEmpl.emplRole() != null){
            target.setRole(newEmpl.emplRole());
        }
        
        else if(!target.isVacant()){
            target.addToTeam(newEmpl);
            newEmpl.setManager(target);
        }
        
        else if(target.isVacant()){
            target.fillVacancy(newEmpl);
        }
    }


    /** Move a current employee (empl) to a new position (target)
     *  [STEP 2:] If the target is not vacant, then
     *    add the employee to the team of the target,
     *    (bringing the whole subtree of the employee with them)
     *  [STEP 3:] If the target is vacant, then
     *     make the employee fill the vacancy, and
     *     if the employee was a manager, then make their old position vacant, but
     *     if they were not a manager, just remove them from the tree.
     *        [COMPLETION:]
     *   Moving the CEO is a problem, and shouldn't be allowed. 
     *   In general, moving any employee to a target that is in the
     *   employee's subtree is a problem and should not be allowed. (Why?)
     */
    private void moveEmployee(Employee empl, Employee target) {
        if ((empl == null) || (target == null)){return;}   //invalid arguments.
        
        if(inSubtree(target, empl)){  //if the target location iz within the subtree of the employee return.
            //UI.println("working");
            return;
        }
        
        if(!target.isVacant()){
            empl.getManager().removeFromTeam(empl); // remove the employee form their previous managers team
            empl.setManager(target);                // set the manager of the employee to the target
            target.addToTeam(empl);                 // add the employee to their new managers team
        }
        if(target.isVacant()){
            if(empl.isManager()){                   // If the employee is a manager
                target.fillVacancy(empl);           // fill the vacancy at the target with the employee
                empl.makeVacant();                  // make the previous role of the empoyee vacant
            }
            else{
                target.fillVacancy(empl);           // if employee isn't a manager, fill vacancy
                retirePerson(empl);                 // remove employee from previous location.
            }
        }

    }
    /** STEP 3
     * Retire an employee.
     * If they are a manager or the CEO, then make the position vacant
     *  (leaving the employee object in the tree, but no initials)
     * If they are not a manager, then remove them from the tree completely.
     */
    public void retirePerson(Employee empl){
        if(empl.isManager()){
            empl.makeVacant();
        }
        else{
            empl.getManager().removeFromTeam(empl);
        }
    }


    /** (COMPLETION)
     *        Return true if person is in the subtree, and false otherwise
     *        Uses == to determine node equality
     *  Check if person is the same as the root of subTree
     *  if not, check if in any of the subtrees of the team members of the root
     *   (recursive call, which must return true if it finds the person)
    */
    private boolean inSubtree(Employee person, Employee subTree) {
        if (subTree==organisation) { return true; }  // first simple case!!
        if (person==subTree)       { return true; }  // second simple case!!
        
        if(subTree.isManager()){
            for(Employee e : subTree.getTeam()){
                boolean inTree = inSubtree(person, e);
                if(inTree) return true;
            }
        }
        return false;
    }
    


    // Drawing the tree  =========================================
    /**
     * Redraw the chart.
     */
    private void redraw() {
        UI.clearGraphics();
        drawTree(organisation);
        drawNewIcon();
        drawRetireIcon();
    }

    /** [STEP 1:]
     *  Recursive method to draw all nodes in a subtree, given the root node.
     *        (The provided code just draws the root node;
     *         you need to make it draw all the nodes.)
     */
    private void drawTree(Employee empl) {
        empl.draw();
        if(empl.isManager()){
            for(Employee e : empl.getTeam()){
                drawTree(e);
            }
        }
    }

    // OTHER DRAWING METHODS =======================================
    /**
     * Redraw the new Person box
     */
    private void drawNewIcon(){
        UI.setColor((newInitials==null)?Employee.V_BACKGROUND:Employee.BACKGROUND);
        UI.fillRect(NEW_LEFT,NEW_TOP,Employee.WIDTH, Employee.HEIGHT);
        UI.setColor(Color.black);
        UI.drawRect(NEW_LEFT,NEW_TOP,Employee.WIDTH, Employee.HEIGHT);
        UI.drawString((newInitials==null)?"--":newInitials, NEW_LEFT+5, NEW_TOP+12);
        UI.drawString((newRole==null)?"--":newRole, NEW_LEFT+5, NEW_TOP+26); 
    }

    /**
     * Redraw the retirement Icon
     */
    private void drawRetireIcon(){
        UI.setColor(Color.red);
        UI.setLineWidth(5);
        UI.drawOval(ICON_X-ICON_RAD, ICON_Y-ICON_RAD, ICON_RAD*2, ICON_RAD*2);
        double off = ICON_RAD*0.68;
        UI.drawLine((ICON_X - off), (ICON_Y - off), (ICON_X + off), (ICON_Y + off));
        UI.setLineWidth(1);
        UI.setColor(Color.black);
    }

    /** is the mouse position on the New Person box */
    private boolean onNewIcon(double x, double y){
        return ((x >= NEW_LEFT) && (x <= NEW_LEFT + Employee.WIDTH) &&
                (y >= NEW_TOP) && (y <= NEW_TOP + Employee.HEIGHT));
    }

    /** is the mouse position on the retirement icon */
    private boolean onRetirementIcon(double x, double y){
        return (Math.abs(x - ICON_X) < ICON_RAD) && (Math.abs(y - ICON_Y) < ICON_RAD);
    }
    

    // Testing ==============================================
    /**
     * Makes an initial tree so you can test your program
     */
    private void makeTestTree(){
        organisation = new Employee("AA", "CEO");
        Employee aa = new Employee("AS", "VP1");
        Employee bb = new Employee("BV", "VP2");
        Employee cc = new Employee("CW", "VP3");
        Employee dd = new Employee("DM", "VP4");
        Employee a1 = new Employee("AF", "AL");
        Employee a2 = new Employee("AH", "AL");
        Employee b1 = new Employee("BK", "AS");
        Employee b2 = new Employee("BL", "DPA");
        Employee d1 = new Employee("CX", "DBP");
        Employee d2 = new Employee("CY", "SEP");
        Employee d3 = new Employee("CZ", "MSP");

        organisation.addToTeam(aa); aa.setOffset(-160);
        organisation.addToTeam(bb); bb.setOffset(-50);
        organisation.addToTeam(cc); cc.setOffset(15);
        organisation.addToTeam(dd); dd.setOffset(120);

        aa.addToTeam(a1); a1.setOffset(-25);
        aa.addToTeam(a2); a2.setOffset(25);
        bb.addToTeam(b1); b1.setOffset(-25);
        bb.addToTeam(b2); b2.setOffset(25);
        dd.addToTeam(d1); d2.setOffset(-50);
        dd.addToTeam(d2); 
        dd.addToTeam(d3); d3.setOffset(50);
        
        this.redraw();
    }

    /** Test for printing out the tree structure, indented text */
    private void printTree(Employee empl, String indent){
        UI.println(indent+empl+ " " +
                   (empl.getManager()==null?"noM":"hasM") + " " +
                   empl.getTeam().size()+" reports");
        String subIndent = indent+"  ";
        for (Employee tm : empl.getTeam()){
            printTree(tm, subIndent);
        }
    }
    
    /** Move the current layout of the tree to a file that can be read and displayed
     *  information to store is:
     *   - Initials
     *   - Role
     *   - Offset
     *   - if manager, number of employees
     *
     *   the file must be in order, otherwise employees may be added to managers that aren't implemented yet.
     *   Create the file by traversing the tree in a pre-order traversal. */
    private void makeFile(){
        Queue<Employee> employees = preOrder(organisation, new LinkedList<>());
        System.out.print(employees);
        try{
            PrintStream out = new PrintStream(new File("Company Employees"));
            while (!employees.isEmpty()){
                Employee e = employees.poll();
                out.println(e.toStringFull());
            }
        }
        catch (IOException e){
            System.out.print("Failed to write to file");
        }
    }
    
    /** Create the tree again, pop employees off the queue and pass them through to addToTree  */
    private void makeTree(Queue<Employee> tree){
    
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to load from file?\nThis will erase the current setup", "Load?", 2);
        if (option == 0){  //if the guessed answer is correct
            Employee CEO = tree.poll();
            organisation = CEO;
            addToTree(tree, CEO.getEmplNumber(), CEO);
            redraw();
        }
        else if(option == 1){ // if the guessed answer is incorrect
            return;
        }
    }
    
    /** add each person to the correct place within the tree, and set their manager and emplyees */
    private void addToTree(Queue<Employee> tree, int iteration, Employee manager){
        
        for (int i = 0; i < iteration; i++){
            Employee e = tree.poll();
            e.setManager(manager);
            manager.addToTeam(e);
            if(e.getEmplNumber() > 0){
                addToTree(tree, e.getEmplNumber(), e);
            }
        }
        
    }
    
    
    /** Read from a file a tree, put the tree together and then display it*/
    private Queue readFile(){
        Queue<Employee> makeTree = new LinkedList<>();
        try{
            Scanner scan = new Scanner(new File(UIFileChooser.open()));
            while(scan.hasNext()){
                String initials = scan.next();
                String position = scan.next();
                int offset = scan.nextInt();
                int employees = scan.nextInt();
                makeTree.offer(makeEmpl(initials, position, offset, employees));
            }
        }
        catch(IOException e){
            System.out.print("failed to read file");
        }
        return makeTree;
    }
    
    /** Use pre-order traversal to create a queue of all the employees in the tree
     * this queue will be in the correct order to read from a file and recreate. */
    private Queue<Employee> preOrder(Employee empl, Queue<Employee> queue){
        queue.offer(empl);
        if(empl.isManager()){
            for(Employee e : empl.getTeam()){
                preOrder(e, queue);
            }
        }
        return queue;
    }
    
    /** Return a new employee with given information */
    private Employee makeEmpl(String init, String pos, double offset, int employees){
        
        if(init.equals("NULL")){
            Employee e = new Employee(init, pos, offset, employees);
            e.makeVacant();
            return e;
        }
        else{
            Employee e = new Employee(init, pos, offset, employees);
            return e;
        }
    }
    
    
    
    
    // Main
    public static void main(String[] arguments) {
        new OrganisationChart();
    }        

}
