package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Contacts app");
        primaryStage.setScene(new Scene(root, 500, 375));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ContactData ourContacts = ContactData.getSingleton();
        ourContacts.saveContacts();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
