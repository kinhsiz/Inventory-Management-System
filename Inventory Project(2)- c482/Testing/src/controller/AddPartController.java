package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller that handles the part addition by the user.
 *
 * @author Ruben Rios
 */
public class AddPartController implements Initializable {
    /**
     * Text field for part id.
     */
    @FXML
    private TextField idText;
    /**
     * A class that let only one single toggle to be selected.
     */
    @FXML
    private RadioButton inHouseBtn;
    /**
     * A class that let only one single toggle to be selected.
     */
    @FXML
    private ToggleGroup TG = new ToggleGroup();
    /**
     * Text field for part stock.
     */
    @FXML
    private TextField invText;
    /**
     * label for machine id or company name
     */
    @FXML
    private Label machineCompanyLabel;
    /**
     * Text field for machine id or company name.
     */
    @FXML
    private TextField machineCompanyText;
    /**
     * Text field for part maximum.
     */
    @FXML
    private TextField maxText;
    /**
     * Text field for part minimum.
     */
    @FXML
    private TextField minText;
    /**
     * Text field for part name.
     */
    @FXML
    private TextField nameText;
    /**
     * Text field for outsourced button
     */
    @FXML
    private RadioButton outsourcedBtn;
    /**
     * Text field for part price or cost.
     */
    @FXML
    private TextField priceText;
    /**
     * Action event that changes label to Machine ID.
     * @param event changes text.
     */
    @FXML
    void inHouseRadioBtn(ActionEvent event) {
        machineCompanyLabel.setText("Machine ID");
    }
    /**
     * Action event that changes label to Company name;
     * @param event changes text.
     */
    @FXML
    void outsourcedRadioBtn(ActionEvent event) {
        machineCompanyLabel.setText("Company Name");
    }
    /**
     * Action event that launches main screen view.
     * @param event loads main screen.
     * @throws Exception
     */
    @FXML
    void onCancelPartBtn(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Action event that saves new part done by user.
     * @param event saves part.
     */
    @FXML
    void onSavePartBtn(ActionEvent event) throws Exception {
        try {
            int id =0;
            String name = nameText.getText();
            int inventory = Integer.parseInt(invText.getText()); //The input text is a string, the variable 'id' is an int. Convert string --> int using Integer.parseInt;
            double price = Double.parseDouble(priceText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            int machineId;
            String companyName;

            if(name.isEmpty()) {
                displayAlert(3);
            } else {
                if (minMaxRequiremet(min, max) && inventoryMinMaxRequirement(max,min,inventory) ) {
                    if(inHouseBtn.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineCompanyText.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, inventory, min, max, machineId);
                            newInHousePart.setId(Inventory.generateNewPartId());
                            Inventory.addPart(newInHousePart);
                            mainScreenReturn(event);
                        } catch (Exception e) {
                            displayAlert(4);
                        }
                    }if(outsourcedBtn.isSelected()) {
                        try {
                            companyName = machineCompanyText.getText();
                            Outsourced newOutsourcedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                            newOutsourcedPart.setId(Inventory.generateNewPartId());
                            Inventory.addPart(newOutsourcedPart);
                            mainScreenReturn(event);
                        } catch (Exception e) {
                            displayAlert(5);
                        }

                    }
                }
            }
        } catch (NumberFormatException e) {
            displayAlert(6);
        }
    }
    /**
     * Method that checks if minimum and maximun values meet criteria.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return boolean value;
     */
    public boolean minMaxRequiremet(int min, int max) {
        if(min > 0 && min < max) {
            System.out.println("true");
            return true;
        } else {
            displayAlert(1);
            return false;
        }
    }
    /**
     * Method that checks if min, max, and inventory meet the required values.
     * @param max the maximum value.
     * @param min the minimum value.
     * @param inventory inventory value.
     * @return boolean value.
     */
    public boolean inventoryMinMaxRequirement(int max,int min, int inventory) {
        if(inventory <= max && inventory >= min) {
            return true;

        }else {
            displayAlert(2);
            return false;
        }
    }
    /**
     * Action event that launches main screen.
     * @param event loads main screen.
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
            alert.setContentText("Min has to be greater than 0 and less than Max");
            alert.showAndWait();
        }
        if(alertType == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Inventory has to be equal or less than Max");
            alert.showAndWait();
        }
        if(alertType == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Please, type a name for the part you want to add");
            alert.showAndWait();
        }
        if(alertType == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("MachineId have to be a numerical value");
            alert.showAndWait();
        }
        if(alertType == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Company Name should contain only alphanumeric values");
            alert.showAndWait();
        }
        if(alertType == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Invalid or empty fields, please check again");
            alert.showAndWait();
        }
    }

    /**
     * Initiates controller and sets toggle group for radio buttons
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseBtn.setToggleGroup(TG);
        outsourcedBtn.setToggleGroup(TG);
    }
}
/* Notes:
    The 'try' keyword is used to specify a block where we should place exception code. It should be followed by either 'catch' or 'finally'.
    The 'catch' block is used to handle the exception
 */