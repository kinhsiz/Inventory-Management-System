package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class model.
 *
 * @author Ruben Rios.
 */
public class Inventory {
    /**
     * List for all parts used in application.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List for all products used in application.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method that adds a part to Part list.
     * @param part part to add.
     */
    public static void addPart(Part part) {
        allParts.add(part); //will add parameter 'part' at the end of the array 'allParts'
    }

    /**
     * Method that adds a product to Product list.
     * @param product product to add.
     */
    public static void addProduct(Product product) {
        allProducts.add(product); //will add parameter 'product' at the end of the array 'allProducts'
    }

    /**
     * Variable that hold values for part id.
     */
    private static int nextPartId = 0; //private, it does not need to be exposed outside Inventory class

    /**
     * Method that generates a part id for each part element.
     * @return part id.
     */
    public static int generateNewPartId() {
        nextPartId++;
        return nextPartId;
    }

    /**
     * Variable that holds product id values.
     */
    private static int nextProductId = 0; //private, it does not need to be exposed outside Inventory class
    /**
     * Method that generates a product id for each product element.
     * @return product id.
     */
    public static int generateNewProductId() {
        nextProductId++;
        return nextProductId;
    }
    /**
     * Method that searches for part id using an integer.
     * @param partId part id to look for.
     * @return matched values or null
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allCurrentParts = Inventory.getAllParts();
        for(int i = 0; i < allCurrentParts.size();i++){
           Part foundPart = allCurrentParts.get(i);
           if(foundPart.getId() == partId){
               return foundPart;
           }
        }
        return null;
    }
    /**
     * Method that searches for product id using an integer.
     * @param productId product id to look for.
     * @return  matched values or null
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allCurrentProducts = Inventory.getAllProducts();
        for(int i = 0; i < allCurrentProducts.size();i++){
            Product foundProduct = allCurrentProducts.get(i);
            if(foundProduct.getId() == productId){
                return foundProduct;
            }
        }
        return null;
    }
    /**
     * Method that searches for part name using a string.
     * @param partName part name to look for.
     * @return matched values or null
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allCurrentParts = Inventory.getAllParts();
        for(Part parts : allCurrentParts) {
            if(parts.getName().contains(partName)) {
                foundParts.add(parts);
            }
        }
        return foundParts;
    }
    /**
     * Method that searches for product name using a string.
     * @param productName product name to look for.
     * @return matched values or null
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allCurrentProducts = Inventory.getAllProducts();
        for(Product products : allCurrentProducts) {
            if(products.getName().contains(productName)) {
                foundProducts.add(products);
            }
        }
        return foundProducts;
    }
    /**
     * Method that updates selected part.
     * @param index part index.
     * @param selectedPart selected part to update.
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Method that updates selected product.
     * @param index product index.
     * @param newProduct selected product to update.
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     * Method that removes selected part from list.
     * @param selectedPart part to remove.
     * @return boolean value.
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }
    /**
     * Method that removes selected product from list.
     * @param selectedProduct product to remove.
     * @return boolean value.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }
    /**
     * Method that returns list of all parts.
     * @return all parts list.
     */
    public static ObservableList<Part> getAllParts() { //gets all elements from array 'allParts'
        return allParts;
    }

    /**
     * Method that returns list of all products.
     * @return all products list
     */
    public static ObservableList<Product> getAllProducts() { //gets all elements from array 'allProducts'
        return allProducts;
    }
}

/*Notes:
    A static inner class may be instantiated(using 'new' keyword) without instantiating its outer class, i.e., a static class does not need to
    create an instance of its outer containing class in order to create its own instance.
        -Company(outer class).Employee(inner class) employee1 = new Employee();

    A static class can only access members of its outer containing class if the members are static in nature. This means
    that a static nested class does not have access to the instance variables and methods of the outer class that are non-static.

 */
