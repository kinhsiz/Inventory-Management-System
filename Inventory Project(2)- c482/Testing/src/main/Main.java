package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
/** This application is an Inventory Management System that manages an
 * inventory consisting of parts and products.
 *
 * A future enhancement would be to restrict the parts a specific product
 * has access to.
 *
 * JavaDoc files are located inside project zip file.
 * @author Ruben Rios
 */
public class Main extends Application {
    /**
     * This method opens up the main screen view from a fxml file
     * @param stage loads stage.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml")); //name of fxml file inside
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** This is the main method.
     *
     * The main method populates part and product table views.
     *
     * It populates part and product screen with the corresponding items
     * */
    public static void main(String[] args) {
        //Add parts to inventory. Create new parts using InHouse Class from model.InHouse
        int partId = Inventory.generateNewPartId();
        InHouse keyboard = new InHouse(partId,"Keyboard", 85.00, 10, 1, 15, 1002);

        partId = Inventory.generateNewPartId();
        InHouse mouse = new InHouse(partId,"Mouse", 45.00, 15, 1, 25, 1002);

        partId = Inventory.generateNewPartId();
        InHouse powerCordLaptop = new InHouse(partId,"Power cord laptop", 90.00, 7, 1, 15, 1002);

        partId = Inventory.generateNewPartId();
        InHouse desktopScreen = new InHouse(partId,"Desktop screen", 125.00, 8, 1, 15, 1002);

        partId = Inventory.generateNewPartId();
        InHouse laptopScreen = new InHouse(partId,"Laptop screen", 140.00, 10, 1, 15, 1002);

        partId = Inventory.generateNewPartId();
        Outsourced powerCordDesktop = new Outsourced(partId, "Power cord desktop", 95, 5, 1, 15, "jjj");

        Inventory.addPart(keyboard);
        Inventory.addPart(mouse);
        Inventory.addPart(powerCordLaptop);
        Inventory.addPart(desktopScreen);
        Inventory.addPart(laptopScreen);
        Inventory.addPart(powerCordDesktop);

        //Add products to inventory
        int productId = Inventory.generateNewProductId();
        Product desktop  = new Product(productId, "Desktop",250.00, 5, 1, 15);

        productId = Inventory.generateNewProductId();
        Product laptop= new Product(productId, "Laptop", 200.00, 7, 1, 10);

        Inventory.addProduct(desktop);
        Inventory.addProduct(laptop);

        launch(args); //will launch the JavaFX application
    }
}

/* Notes:
Test data should be in the Main.java file. When the program starts it will only load the data values once. On the other hand
if test data is placed in the controller it will load the data value every time the controller initializes (e.g. pressing add button
back and forth);

productId will initiate at 0 (from Inventory.java file) and update by 1 everytime the Inventory.generateNewPartId or Inventory.generateNewProductId is called.

 */



