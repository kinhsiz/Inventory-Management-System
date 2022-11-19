package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for product model.
 *
 * @author Ruben Rios
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Construct for product.
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product stock
     * @param min product minimum
     * @param max product maximum
     */
    public Product(int id, String name, double price, int stock, int min, int max) { //constructor
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets product id.
     * @return product id.
     */
    public int getId() {
        return id;
    }
    /**
     * sets product id.
     * @param id id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * gets product name.
     * @return product name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets product name.
     * @param name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets product price.
     * @return product price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets product price.
     * @param price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Gets product stock.
     * @return product stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * Sets product stock.
     * @param stock stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Gets product minimum.
     * @return product minimum
     */
    public int getMin() {
        return min;
    }
    /**
     * Sets product minimum.
     * @param min minimum value to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets product maximum.
     * @return product maximum
     */
    public int getMax() {
        return max;
    }
    /**
     * Sets product maximum.
     * @param max maximum value to set.
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Adds associated part to associated list.
     * @param part part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * Removes selected part from selected part list.
     * @param selectedAssociatedPart
     * @return boolean value
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return  false;
        }
    }
    /**
     * Gets all associated parts.
     * @return associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
