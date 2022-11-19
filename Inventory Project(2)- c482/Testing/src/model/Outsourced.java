package model;

/**
 * Outsource class model for outsourced part.
 *
 * @author Ruben Rios
 */
public class Outsourced extends Part{
    /**
     * Variable for company name.
     */
    private String companyName;

    /**
     * Construct for outsourced part.
     * @param id part id
     * @param name part name
     * @param price part price or cost
     * @param stock part stock
     * @param min part min
     * @param max part max
     * @param companyName part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Method that returns company name.
     * @return company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Method that sets part company name.
     * @param companyName company name to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
