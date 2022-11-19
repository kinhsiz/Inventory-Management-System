package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * A controller that controls the main screen of the inventory application.
 *
 * LOGICAL ERROR: when modifying a part, the modified object would change its index location in the array.
 * Instead of retaining the same location. I corrected by getting the initial part's index in the list and use the
 * method set() to use the same index and replace it with the modified version.
 *
 * RUNTIME ERROR: when modifying a part and not selecting an item to modify. Used a method to let user know that
 * a part was not selected.
 *
 * @author Ruben Rios
 * */

public class MainScreenController implements Initializable {
    //---------------Part Table-------------------
    /**
     * The column for part id.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;//first ? is the object type the column will be working with //second ? is the data type
    /**
     * The column for part stock.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * The column for part name.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * The column for part price.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    /**
     * The table view for Parts.
     */
    @FXML
    private TableView<Part> partTable;
    /**
     * The text field for search bar.
     */
    @FXML
    private TextField partText;
    //----------------Product Table----------------------
    /**
     * The column for product id.
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    /**
     * The column for product stock.
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    /**
     * The column for product name.
     */
    @FXML
    private TableColumn<Product, String> productNameCol;
    /**
     * The column for product price and cost.
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    /**
     * The table view for Product.
     */
    @FXML
    private TableView<Product> productTable;
    /**
     * The text field for product search bar.
     */
    @FXML
    private TextField productText;
    /**
     * Variable used for the part to modify.
     */
    private static Part modifyPart;
    /**
     * Variable used for the product to modify.
     */
    private static Product modifyProduct;

    /**
     * It returns a selected part by the user
     * @return the selected part
     */
    public static Part modifyPart(){
        return modifyPart; //returns static variable from onModifyProductActionBtn(ActionEvent event);
    }

    /**
     * It returns a selected product by the user
     * @return selected product
     */
    public static Product modifyProduct(){
        return modifyProduct;
    }
    //---------------------------------------------PART BUTTON SECTION--------------------------------------------------------------
    /**
     * An action event that triggers when user presses add button and open add part file.
     * @param event loads view for adding parts.
     * @throws Exception
     */
    @FXML
    void onAddPartActionBtn(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * An action event that triggers when user presses modify button and open modify part file.
     * @param event load view for modifying parts.
     * @throws Exception
     */
    @FXML
    void onModifyPartActionBtn(ActionEvent event) throws Exception {
        modifyPart = partTable.getSelectionModel().getSelectedItem(); //selects the part to modify;
        if(modifyPart != null){
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        } else {
            displayAlert(4);
        }
    }

    /**
     * Action event that deletes a selected part by the user.
     * @param event removes selected part.
     */
    @FXML
    void onDeletePartActionBtn(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(4);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){ //isPresent return a boolean value. If value is present, then get() returns the value
                Inventory.deletePart(selectedPart);
            }
        }
    }
    //---------------------------------------------PART BUTTON SECTION ENDS---------------------------------------------
    //--------------------------------------------BUTTON PRODUCT SECTION------------------------------------------------
    /**
     *It retrieves the add product fxml file.
     * @param event loads view for product addition.
     */
    @FXML
    void onAddProductActionBtn(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * It saves the part selected by user and retrieves modify fxml file.
     * @param event loads view for product modification.
     */
    @FXML
    void onModifyProductActionBtn(ActionEvent event) throws IOException {
        modifyProduct = productTable.getSelectionModel().getSelectedItem(); //will hold selected product to be used in ModifyProductController

        if(modifyProduct != null){
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }else {
            displayAlert(5);
        }
    }
    /**
     * It saves selected product by user and removes it if appropriate.
     * @param event removes selected product.
     */
    @FXML
    void onDeleteProductActionBtn(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null){
            displayAlert(5);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                System.out.println(associatedParts);
                if(associatedParts.size() == 0) { // associatedParts == null did not work
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    displayAlert(6);
                }
            }

        }
    }
    //--------------------------------------------BUTTON PRODUCT SECTION ENDS-------------------------------------------

    /**
     * An action event that terminates the program when button is pressed.
     * @param event exits application.
     */
    @FXML
    void onExitBtn(ActionEvent event) {
        System.exit(0);
    }

    //---------------------------------------SEARCH PART SECTION---------------------------------------------------------

    /**
     * It returns all parts that match the criteria by re-populating
     * part table, null if none matched.
     * @param actionEvent searches for parts.
     */
    public void getResultPartHandler(ActionEvent actionEvent) {
        ObservableList<Part> AllParts = Inventory.getAllParts();
        String searchedString = partText.getText();
        ObservableList<Part> foundParts = searchByPart(searchedString);

        partTable.setItems(foundParts);

        if(foundParts.size() == 0) {
            displayAlert(1);
        }
    }
    /**
     * returns all values that match user input.
     * @param partialName string by user input.
     * @return all matched values or null.
     */
    private ObservableList<Part> searchByPart(String partialName) {
        ObservableList<Part> AllParts = Inventory.getAllParts(); //newAllParts contains the values from allParts array
        ObservableList<Part> allFoundParts = FXCollections.observableArrayList();// where matched string will be placed after for loop

        for(Part partElements : AllParts) { //partElements is a temp variable
           String element = partElements.getName().toLowerCase(); //set part name to lower case
           partialName = partialName.toLowerCase(); //set string parameter to lower case

            if(element.contains((partialName)) || String.valueOf(partElements.getId()).contains(partialName)) {
                allFoundParts.add(partElements);
            }
        }

        return allFoundParts;
    }
    //---------------------------------------------SEARCH PART SECTION ENDS---------------------------------------------

    //--------------------------------------------SEARCH PRODUCT SECTION------------------------------------------------

    /**
     * It returns all parts that match the criteria by re-populating
     * product table, null if none matched.
     * @param actionEvent searches for products.
     */
    public void getResultProductHandler(ActionEvent actionEvent) {
        ObservableList<Product> AllProducts = Inventory.getAllProducts();
        String searchedString = productText.getText();
        ObservableList<Product> foundProducts = searchByProduct(searchedString);

        productTable.setItems(foundProducts);

        if(foundProducts.size() == 0) {
            displayAlert(2);
        }
    }
    /**
     * returns all values that match user input.
     * @param partialName string by user input.
     * @return all matched values or null.
     */
    private ObservableList<Product> searchByProduct(String partialName) {
        ObservableList<Product> AllProducts = Inventory.getAllProducts(); //AllProducts contains the values from allProducts array
        ObservableList<Product> allFoundProducts = FXCollections.observableArrayList();// where matched string will be placed after for loop

        for(Product productElements : AllProducts) { //productElements is a temp variable
            String element = productElements.getName().toLowerCase(); //set part name to lower case
            partialName = partialName.toLowerCase(); //set string parameter to lower case

            if(element.contains((partialName)) || String.valueOf(productElements.getId()).contains(partialName)) {
                allFoundProducts.add(productElements);
            }
        }

        return allFoundProducts;
    }
    //-----------------------------------------SEARCH PRODUCT SECTION ENDS----------------------------------------------

    /**
     * Shows different types of alerts
     * @param alertType type of alert.
     */
    private void displayAlert(int alertType) {
        if(alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
        if(alertType == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Product no found");
            alert.showAndWait();
        }
        if(alertType == 3){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure you want to make this changes?");
            alert.showAndWait();
        }
        if(alertType == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Part not selected");
            alert.showAndWait();
        }
        if(alertType == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Product not selected");
            alert.showAndWait();
        }
        if(alertType == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, remove all associated parts before deleting");
            alert.showAndWait();
        }
    }


    /**
     * Launches when file is opened and populates initial table views for parts and products.
     * @param url
     * @param resourceBundle
     */
    @Override
    //main method of a controller. First method called when controller is instantiated (when screen loads up);
    //setCellValueFactory assigns its cell a value from the ObservableList
    //PropertyValueFactory<>() take a String arg. matching a field name in ObservableList. Uses getter method and returns a value to column cell
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //inside which observable list to be working with
        //Part table view data
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Product table view data
        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

//Notes:
/*
contains() method can be used as myString = 'hello' -- myString.contains('hello)
or 'hello'.contains('hello');

String.valueOf(); will return a converted integer to a string

 */