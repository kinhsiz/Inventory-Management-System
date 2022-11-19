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


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A controller than handles part additions to a product by user.
 * @author Ruben Rios
 */
public class AddProductController implements Initializable {
    /**
     * A list where selected parts for a specific product are added to.
     */
    private ObservableList<Part> addedAssocParts = FXCollections.observableArrayList(); //where selected parts will be added
    /**
     * Column for associated part id.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    /**
     * Column for associated part stock.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    /**
     * Column for associated part name.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    /**
     * Column for associated part price.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;
    /**
     * Table view for all associated parts.
     */
    @FXML
    private TableView<Part> associatedPartTable;
    /**
     * Text field for part product id.
     */
    @FXML
    private TextField idText;
    /**
     * Text field for product maximum.
     */
    @FXML
    private TextField maxText;
    /**
     * Text field for product minimum.
     */
    @FXML
    private TextField minText;
    /**
     * Text field for product name.
     */
    @FXML
    private TextField nameText;
    /**
     * Text field for product stock.
     */
    @FXML
    private TextField invText;
    /**
     * Column for part id.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * Column for part stock.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * Column for part name.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * Column for part price.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    /**
     * Table for all parts.
     */
    @FXML
    private TableView<Part> partTable;
    /**
     * Text field for product price or cost.
     */
    @FXML
    private TextField priceText;
    /**
     * Text field for search bar.
     */
    @FXML
    private TextField searchPartText;

    /**
     * Action event that adds a part to selected table view.
     * @param event add associated parts.
     */
    @FXML
    void onAddBtnAction(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem(); //var holding selected part
        if(selectedPart == null) {
            displayAlert(1);
        } else {
            addedAssocParts.add(selectedPart);
            associatedPartTable.setItems(addedAssocParts);
        }
    }

    /**
     * Action event that cancels action and launches main screen view.
     * @param event loads main screen view.
     * @throws Exception
     */
   @FXML
    void onCancelBtnAction(ActionEvent event) throws Exception {
       Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
       Scene scene = new Scene(parent);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Inventory Management System");
       stage.setScene(scene);
       stage.show();
    }
    /**
     * It returns all parts that match the criteria by re-populating
     * product table, null if none matched.
     * @param event searches for parts.
     */
    @FXML
    void onGetResultPartHandler(ActionEvent event) {

        String searchedString = searchPartText.getText();
        ObservableList<Part> foundParts = searchByPart(searchedString);

        partTable.setItems(foundParts);

        if (foundParts.size() == 0) {
            displayAlert(2);
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

    /**
     * Action event that removes selected associated part from table view.
     * @param event removes selected associated parts.
     */
    @FXML
    void onRemoveAssPartBtnAction(ActionEvent event) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) { //isPresent return a boolean value. If value is present, then get() returns the value
                addedAssocParts.remove(selectedPart);
                associatedPartTable.setItems(addedAssocParts);
            }
        }
    }

    /**
     * An action event that saves a product made by user.
     * @param event saves new product.
     * @throws Exception
     */
    @FXML
    void onSaveBtnAction(ActionEvent event) throws Exception {
        try {
            int id =0;
            String name = nameText.getText();
            int inventory = Integer.parseInt(invText.getText()); //The input text is a string, the variable 'id' is an int. Convert string --> int using Integer.parseInt;
            double price = Double.parseDouble(priceText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());

            if(name.isEmpty()) {
                displayAlert(4);
            }else {
                if (minMaxRequirement(min, max) && inventoryMinMaxRequirement(max,min,inventory) ){
                    Product newProduct = new Product(id,name, price, inventory,min,max);
                    for(Part part: addedAssocParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setId(Inventory.generateNewPartId());
                    Inventory.addProduct(newProduct);
                    mainScreenReturn(event);
                }
            }
        } catch (Exception e) {
            displayAlert(7);
        }
    }
    /**
     * Method that checks if minimum and maximum values meet criteria.
     * @param min product minimum value.
     * @param max product maximum value.
     * @return boolean value;
     */
    private boolean minMaxRequirement(int min, int max) {
        if(min > 0 && min < max) {
            System.out.println("true");
            return true;
        } else {
            displayAlert(5);
            return false;
        }
    }
    /**
     * Method that checks if min, max, and inventory meet the required values.
     * @param max product minimum value.
     * @param min product maximum value.
     * @param inventory for product.
     * @return boolean value.
     */
    private boolean inventoryMinMaxRequirement(int max,int min,int inventory) {
        if(inventory <= max && inventory >= min) {
            return true;

        }else {
            displayAlert(6);
            return false;
        }
    }
    /**
     * Action event that launches main screen.
     * @param event loads main screen view.
     * @throws Exception
     */
    private void mainScreenReturn(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     *  Shows different types of alerts.
     *  @param alertType type of alert.
     */
    private void displayAlert(int alertType) {
        if(alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, select the part you want to add");
            alert.showAndWait();
        }
        if(alertType == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
        if(alertType == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Please, select the part you want to remove from selected parts");
            alert.showAndWait();
        }
        if(alertType == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a name for the part you want to add");
            alert.showAndWait();
        }
        if(alertType == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Min has to be greater than 0 and less than Max");
            alert.showAndWait();
        }
        if(alertType == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Inventory quantity must be between minimum and maximum values");
            alert.showAndWait();
        }
        if(alertType == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Invalid or empty fields, please check again");
            alert.showAndWait();
        }


    }
    /**
     * Launches when file is opened and populates initial table views for parts only.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
