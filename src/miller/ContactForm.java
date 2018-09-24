package miller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

public class ContactForm extends Application {

    private Stage addContactStage = new Stage();
    private Contact selectedContact;
    // Standard width for majority of the TextFields
    private final int FIELDW = 150;
    private boolean update = false;
    private String fname = "";

    // Controls to be filled or read from...
    private TextField fNameField;
    private TextField lNameField;
    private TextField mNameField;
    private TextField email1Field;
    private TextField email2Field;
    private TextField phone1Field;
    private TextField phone2Field;
    private TextField addressField;
    private TextField cityField;
    private ComboBox<String> suffixComboBox;
    private ComboBox<String> stateComboBox;
    private ComboBox<String> email1ComboBox;
    private ComboBox<String> email2ComboBox;
    private ComboBox<String> phone1ComboBox;
    private ComboBox<String> phone2ComboBox;

    ContactForm(){}

    ContactForm(Contact c, Stage stage){
        selectedContact = c;

        // Set the update flag to true. This will determine whether to update the current object to create a new one.
        update = true;

        // Call the start method and create the stage.
        start(stage);

        // Once the stage is created we can fill the form using the provided Contact object.
        fillContactForm(c);
    }

    public void start(Stage stage){

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10, 10, 10, 15));
        gridPane.setHgap(8);
        gridPane.setVgap(10);

        // FIRST NAME
        Label fNameLabel = new Label("First Name:");
        fNameField = new TextField();
        fNameField.setText(fname);
        fNameField.setPrefWidth(FIELDW);

        // LAST NAME
        Label lNameLabel = new Label("Last Name:");
        lNameField = new TextField();
        lNameField.setPrefWidth(FIELDW);

        // MIDDLE NAME
        Label mNameLabel = new Label("Middle Name:");
        mNameField = new TextField();
        mNameField.setPrefWidth(FIELDW);
        mNameField.setMaxWidth(FIELDW);

        // SUFFIX (ie Sr., Jr., etc)
        Label suffixLabel = new Label("Suffix:");
        suffixComboBox = new ComboBox<>();
        String[] suffixList = new String[] {"", "Sr.", "Jr.", "I", "II", "III"};
        suffixComboBox.getItems().addAll(suffixList);

        // AVATAR/IMAGE
        ImageView avatarIV = new ImageView(new Image(ContactForm.class.getResource("resources/avatar_m.png").toExternalForm()));
        avatarIV.setFitWidth(FIELDW);
        avatarIV.setFitHeight(FIELDW);
        GridPane.setColumnSpan(avatarIV, 1);
        GridPane.setRowSpan(avatarIV, 6);

        // ADDRESS
        Label addresslabel = new Label("Address:");
        addressField = new TextField();
        GridPane.setColumnSpan(addressField, 2);

        // CITY
        Label cityLabel = new Label("City:");
        cityField = new TextField();
        cityField.setPrefWidth(FIELDW);

        // STATE - Array of state abbreviations fill combo box
        Label stateLabel = new Label("State:");
        stateComboBox = new ComboBox<>();
        String[] stateList = new String[]{
                "AL",
                "AK",
                "AZ",
                "AR",
                "CA",
                "CO",
                "CT",
                "DE",
                "FL",
                "GA",
                "HI",
                "ID",
                "IL",
                "IN",
                "IA",
                "KS",
                "KY",
                "LA",
                "ME",
                "MD",
                "MA",
                "MI",
                "MN",
                "MS",
                "MO",
                "MT",
                "NE",
                "NV",
                "NH",
                "NJ",
                "NM",
                "NY",
                "NC",
                "ND",
                "OH",
                "OK",
                "OR",
                "PA",
                "RI",
                "SC",
                "SD",
                "TN",
                "TX",
                "UT",
                "VT",
                "VA",
                "WA",
                "WV",
                "WI",
                "WY"
                };
        stateComboBox.getItems().addAll(stateList);
        stateComboBox.setVisibleRowCount(10);

        // E-MAIL 1 FIELD
        Label email1Label = new Label("Primary E-Mail:");
        email1Field = new TextField();
        email1Field.setPrefWidth(FIELDW);

        // E-MAIL TYPE COMBO BOX
        email1ComboBox = new ComboBox<>();
        email1ComboBox.getItems().addAll("Personal", "Work", "Other");
        email1ComboBox.setPrefWidth(110);

        // E-MAIL 2 FIELD
        Label email2Label = new Label("Secondary E-Mail:");
        email2Field = new TextField();
        email1Field.setPrefWidth(FIELDW);

        // E-MAIL TYPE COMBO BOX
        email2ComboBox = new ComboBox<>();
        email2ComboBox.getItems().addAll("Personal", "Work", "Other");
        email2ComboBox.setPrefWidth(110);

        // PRIMARY PHONE FIELD
        Label phone1Label = new Label("Primary Phone:");
        phone1Field = new TextField();
        phone1Field.setPrefWidth(FIELDW);

        // PHONE TYPE COMBO BOX
        phone1ComboBox = new ComboBox<>();
        phone1ComboBox.getItems().addAll("Cell", "Home", "Work", "Other");
        phone1ComboBox.setPrefWidth(110);

        // SECONDARY PHONE FIELD
        Label phone2Label = new Label("Primary Phone:");
        phone2Field = new TextField();
        phone2Field.setPrefWidth(FIELDW);

        // PHONE TYPE COMBO BOX
        phone2ComboBox = new ComboBox<>();
        phone2ComboBox.getItems().addAll("Cell", "Home", "Work", "Other");
        phone2ComboBox.setPrefWidth(110);

        // TEST BUTTON
        Button button = new Button("Add Contact");
        button.setOnAction(e -> {
            if(!update)
                Main.contacts.add(getContact());
            addContactStage.close();
        });

        // ADD EVERYTHING TO THE GRID PANE HERE...
        gridPane.add(fNameLabel, 0, 0);
        gridPane.add(fNameField, 0, 1);

        gridPane.add(mNameLabel, 1, 0);
        gridPane.add(mNameField, 1, 1);

        gridPane.add(lNameLabel, 2, 0);
        gridPane.add(lNameField, 2, 1);

        gridPane.add(suffixLabel, 3, 0);
        gridPane.add(suffixComboBox, 3, 1);

        gridPane.add(avatarIV, 4, 0);

        gridPane.add(addresslabel, 0, 2);
        gridPane.add(addressField, 0, 3);

        gridPane.add(cityLabel, 2, 2);
        gridPane.add(cityField, 2, 3);

        gridPane.add(stateLabel, 3, 2);
        gridPane.add(stateComboBox, 3, 3);

        gridPane.add(email1Label, 0, 4);
        gridPane.add(email1Field, 0, 5);
        gridPane.add(email1ComboBox, 1, 5);

        gridPane.add(email2Label, 2, 4);
        gridPane.add(email2Field, 2, 5);
        gridPane.add(email2ComboBox, 3, 5);

        gridPane.add(phone1Label, 0, 6);
        gridPane.add(phone1Field, 0, 7);
        gridPane.add(phone1ComboBox, 1, 7);

        gridPane.add(phone2Label, 2, 6);
        gridPane.add(phone2Field, 2, 7);
        gridPane.add(phone2ComboBox, 3, 7);

        gridPane.add(button, 3, 8);

        Scene childScene = new Scene(gridPane);
        childScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        addContactStage.setScene(childScene);
        addContactStage.setTitle("Add Contact");
        addContactStage.getIcons().add(new Image(Main.class.getResource("resources/add32.png").toExternalForm()));
        addContactStage.setWidth(775);
        addContactStage.setHeight(400);
        addContactStage.initOwner(stage);
        addContactStage.initModality(Modality.APPLICATION_MODAL);
        addContactStage.setResizable(true);
        addContactStage.show();
    }

    private boolean isValid()

    {
        return !fNameField.getText().isEmpty() && !lNameField.getText().isEmpty();
    }

    private Contact getContact(){

        return new Contact(
            fNameField.getText(),
            lNameField.getText(),
            //mNameField.getText(),
            //suffixComboBox.getSelectionModel().getSelectedItem(),
           // addressField.getText(),
            //cityField.getText(),
            email1Field.getText(),
            //email1ComboBox.getSelectionModel().getSelectedItem(),
            email2Field.getText(),
            //email2ComboBox.getSelectionModel().getSelectedItem(),
            phone1Field.getText(),
            //phone1ComboBox.getSelectionModel().getSelectedItem(),
            phone2Field.getText()
            //phone2ComboBox.getSelectionModel().getSelectedItem();
        );
    }

    /**
     * This method will take in a <b>Contact</b> object and use that object to fill out the form.
     * @param c - A <b>Contact</b> object.
     */
    private void fillContactForm(Contact c){
        fNameField.setText(c.getFirstName());
        lNameField.setText(c.getLastName());
        email1Field.setText(c.getEMail1());
        email2Field.setText(c.getEmail2());
        phone1Field.setText(c.getPhone1());
        phone2Field.setText(c.getPhone2());
    }
}

