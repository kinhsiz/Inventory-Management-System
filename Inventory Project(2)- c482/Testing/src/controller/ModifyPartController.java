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
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller that handles user requests to modify a part.
 *
 * @author Ruben Rios
 */
public class ModifyPartController implements Initializable {
    /**
     * Text field for part id.
     */
    @FXML
    private TextField idText;
    /**
     * Radio button for inHouse button.
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
     * Variable for part selected by user.
     */
    private Part selectedPart;

    /**
     * Action event that changes label to Machine ID.
     * @param event changes text.
     */
    @FXML
    void InHouseBtn(ActionEvent event) {
        machineCompanyLabel.setText("Machine ID");
    }

    /**
     * Action event that changes label to Company name;
     * @param event changes text.
     */
    @FXML
    void OutsourcedBtn(ActionEvent event) {
        machineCompanyLabel.setText("Company Name");
    }

    /**
     * Action event that launches main screen view.
     * @param event loads main screen view.
     * @throws Exception
     */
    @FXML
    void onCancelBtn(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Action event that saves part modification or changes by users.
     * @param event saves modified part.
     */
    @FXML
    void onSaveBtn(ActionEvent event) {
        try {
            int id = selectedPart.getId();
            String name = nameText.getText();
            int inventory = Integer.parseInt(invText.getText()); //The input text is a string, the variable 'id' is an int. Convert string --> int using Integer.parseInt;
            double price = Double.parseDouble(priceText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            int machineId;
            String companyName;
            int index;

            if(name.isEmpty()) {
                displayAlert(3);
            }else {
                if (minMaxRequirement(min, max) && inventoryMinMaxRequirement(max,min, inventory) ) {
                    if(inHouseBtn.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineCompanyText.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, inventory, min, max, machineId);
                            index = Inventory.getAllParts().indexOf(selectedPart); //get index of selected part
                            Inventory.updatePart(index,newInHousePart);   //**********************************************//
                            mainScreenReturn(event);

                        } catch (Exception e) {
                            displayAlert(4);
                        }
                    }if(outsourcedBtn.isSelected()) {
                        try {
                            companyName = machineCompanyText.getText();
                            Outsourced newOutsourcedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                            index = Inventory.getAllParts().indexOf(selectedPart);
                            Inventory.updatePart(index, newOutsourcedPart);
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
     * Method that checks if minimum and maximum values meet criteria.
     * @param min part maximum value.
     * @param max part minimum value.
     * @return boolean value;
     */
    private boolean minMaxRequirement(int min, int max) { //not needed outside modifyPartController.java
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
     * @param max part maximum value.
     * @param min part minimum value.
     * @param inventory part value.
     * @return boolean value.
     */
    private boolean inventoryMinMaxRequirement(int max, int min, int inventory) { //not needed outside modifyPartController.java
        if(inventory <= max && inventory >= min) {
            return true;
        }else {
            displayAlert(2);
            return false;
        }
    }
    /**
     * Action event that launches main screen.
     * @param event loads main screen view.
     * @throws Exception
     */
    private void mainScreenReturn(ActionEvent event) throws Exception { //not needed outside modifyPartController.java
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
            alert.setContentText("Inventory has to be equal or less than Max and greater than min");
            alert.showAndWait();
        }
        if(alertType == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Please, type a name for the part you want to modify");
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
     * Launches when file is opened and populates initial table views for parts and products.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainScreenController.modifyPart(); //gets selected part from partTable in main screen

        inHouseBtn.setToggleGroup(TG);
        outsourcedBtn.setToggleGroup(TG);

        if(selectedPart instanceof InHouse) {
            TG.selectToggle(inHouseBtn);
            machineCompanyLabel.setText("Machine ID");
            machineCompanyText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            TG.selectToggle(outsourcedBtn);
            machineCompanyLabel.setText("Company Name");
            machineCompanyText.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
        idText.setText(String.valueOf(selectedPart.getId()));
        nameText.setText(selectedPart.getName());
        invText.setText(String.valueOf(selectedPart.getStock()));
        priceText.setText(String.valueOf(selectedPart.getPrice()));
        maxText.setText(String.valueOf(selectedPart.getMax()));
        minText.setText(String.valueOf(selectedPart.getMin()));
    }
}
