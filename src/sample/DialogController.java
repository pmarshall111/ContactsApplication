package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    //in here we need to connect the fxml to Java. So connect up all fields and have a method in here to edit/add new contact.

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextArea notes;

    private Contact toEdit;

    public Contact addNewContact() {
        ContactData ourContacts = ContactData.getSingleton();
        Contact created = new Contact(firstName.getText().trim(),
                                        lastName.getText().trim(),
                                        phoneNumber.getText().trim(),
                                        notes.getText().trim());

        ourContacts.addContact(created);
        return created;
    }

    public void setContact(Contact contact) {
        toEdit = contact;

        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        phoneNumber.setText(contact.getPhoneNumber());
        notes.setText(contact.getNotes());
    }

    public void editContact() {
        //idea is to call a function to load in original contact. we then know which contact to edit because we've saved it within the controller.
        ContactData ourContacts = ContactData.getSingleton();
        ourContacts.editContact(toEdit, new Contact(firstName.getText().trim(),
                                    lastName.getText().trim(),
                                    phoneNumber.getText().trim(),
                                    notes.getText().trim()));

        toEdit = null;
    }
}
