package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView tableView;
    @FXML
    private BorderPane mainBorderPane;


    public void initialize() {
        //create ContactData instance and load data.
        //add data to table within fxml

        ContactData ourContacts = ContactData.getSingleton();
        ourContacts.loadContacts();

//        right now we have empty contact list to start with
//        System.out.println(ourContacts.getContacts());

        tableView.setItems(ourContacts.getContacts());

        //create columns for our table
        TableColumn<Contact,String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        TableColumn<Contact,String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        TableColumn<Contact,String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        TableColumn<Contact,String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));

        tableView.getColumns().setAll(firstNameCol, lastNameCol, phoneNumberCol, notesCol);

        tableView.getSelectionModel().selectFirst();
    }

    public void showNewContactDialog() {
        Dialog<ButtonType> newContact = new Dialog<>();
        newContact.initOwner(mainBorderPane.getScene().getWindow());
        
        newContact.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));
        
        try {
            newContact.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't open dialog");
        }

        newContact.getDialogPane().getButtonTypes().add(ButtonType.OK);
        newContact.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = newContact.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            DialogController controller = fxmlLoader.getController();
            Contact created = controller.addNewContact();

            tableView.getSelectionModel().select(created);
        } else {
            System.out.println("New contact cancelled");
        }
    }

    public void showEditContactDialog() {
        Dialog<ButtonType> editContact = new Dialog<>();
        editContact.initOwner(mainBorderPane.getScene().getWindow());

        editContact.setTitle("Edit existing contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));

        try {
            editContact.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't open dialog");
        }

        editContact.getDialogPane().getButtonTypes().add(ButtonType.OK);
        editContact.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController controller = fxmlLoader.getController();
        controller.setContact((Contact) tableView.getSelectionModel().getSelectedItem());

        Optional<ButtonType> result = editContact.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            controller.editContact();
        } else {
            System.out.println("Edit contact cancelled");
        }
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            Contact toDelete = (Contact) tableView.getSelectionModel().getSelectedItem();

            if (toDelete != null && showDeleteCheck()) ContactData.getSingleton().deleteContact(toDelete);
        }
    }

    public void handleDeleteClick() {
        Contact toDelete = (Contact) tableView.getSelectionModel().getSelectedItem();

        if (toDelete != null && showDeleteCheck()) ContactData.getSingleton().deleteContact(toDelete);
    }

    public boolean showDeleteCheck() {
        Alert check = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this contact?");
        Optional<ButtonType> result = check.showAndWait();

        if (result.isPresent() && result.get().equals(ButtonType.OK)) return true;
        return false;
    }

    public void handleExitClick() {
        Platform.exit();
    }

}
