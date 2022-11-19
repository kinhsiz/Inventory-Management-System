package model;

/**
 * In-house part model.
 *
 * @author Ruben Rios
 */
public class InHouse extends Part { //InHouse will inherit from Part class using  'extends'
    /**
     * Variable for machine.
     */
    private int machine;

    /**
     * Constructor for In-house object
     * @param id part id
     * @param name part name
     * @param price part price or cost
     * @param stock part stock
     * @param min part minimum value
     * @param max part maximum value
     * @param machine part machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machine) {
        super(id, name, price, stock, min, max);
        this.machine = machine;
    }

    /**
     * Method to obtain machine id.
     * @return machine id
     */
    public int getMachineId() {
        return machine;
    }

    /**
     * Method to set machine id.
     * @param machine machine id.
     */
    public void setMachineId(int machine) {
        this.machine = machine;
    }
}
