package miller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.io.File;

public class Main extends Application{

    private final int BTNH = 21;
    private final int BTNW = 21;
    private Stage appStage;
    private Label statusBarLabel;

    private static ObservableList<Contact> contacts = FXCollections.observableArrayList(
            new Contact("Keith", "Miller", "cheapstr@yahoo.com", "703-626-6675"),
            new Contact("Rachel", "Otts", "rachelotts@gmail.com", "205-276-4422")
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        this.appStage = stage;
        VBox vBox = new VBox();
        vBox.setId("mainForm");
        MenuBar menubar = createMenuBar();
        ToolBar toolBar = createToolBar();
        TableView table = createTable();
        HBox statusBar = createStatusBar();

        vBox.getChildren().addAll(menubar, toolBar, table, statusBar);
        vBox.setVgrow(table, Priority.ALWAYS);

        Scene mainScene = new Scene(vBox);
       // mainScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        stage.getIcons().add(new Image(Main.class.getResource("resources/main128.png").toExternalForm()));
        stage.setScene(mainScene);
        stage.setTitle("Address Book");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();

    }

    private MenuBar createMenuBar(){

        MenuBar mainMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        mainMenu.setPadding(new Insets(0,0,0,0));

        // Add items to the file menu...
        MenuItem addItemMenu = new MenuItem("New Contact");
        MenuItem delItemMenu = new MenuItem("Delete Contact");
        MenuItem openItemMenu = new MenuItem("Open File");
        MenuItem saveItemMenu = new MenuItem("Save File");

        // Add items to the help menu
        MenuItem helpItemMenu = new MenuItem("Help");
        MenuItem aboutItemMenu = new MenuItem("About Adddress Book");

        // Create an icon for the menu entry...
        ImageView addView = new ImageView(new Image(Main.class.getResourceAsStream("resources/add24.png")));
        addView.setFitHeight(16);
        addView.setFitWidth(16);
        addItemMenu.setGraphic(addView);
        addItemMenu.setOnAction(e -> newContact());

        ImageView delView = new ImageView(new Image(Main.class.getResourceAsStream("resources/remove24.png")));
        delView.setFitWidth(16);
        delView.setFitHeight(16);
        delItemMenu.setGraphic(delView);

        ImageView openView = new ImageView(new Image(Main.class.getResourceAsStream("resources/open32.png")));
        openView.setFitWidth(16);
        openView.setFitHeight(16);
        openItemMenu.setGraphic(openView);

        ImageView saveView = new ImageView(new Image(Main.class.getResourceAsStream("resources/save32.png")));
        saveView.setFitWidth(16);
        saveView.setFitHeight(16);
        saveItemMenu.setGraphic(saveView);

        ImageView helpView = new ImageView(new Image(Main.class.getResourceAsStream("resources/help32.png")));
        helpView.setFitWidth(16);
        helpView.setFitHeight(16);
        helpItemMenu.setGraphic(helpView);

        ImageView aboutView = new ImageView(new Image(Main.class.getResourceAsStream("resources/main32.png")));
        aboutView.setFitWidth(16);
        aboutView.setFitHeight(16);
        aboutItemMenu.setGraphic(aboutView);

        // Attach individual MenuItems to the menu
        fileMenu.getItems().addAll(addItemMenu, delItemMenu, openItemMenu, saveItemMenu);
        helpMenu.getItems().addAll(helpItemMenu, aboutItemMenu);

        mainMenu.getMenus().addAll(fileMenu, editMenu, helpMenu);
        return mainMenu;
    }

    private ToolBar createToolBar(){

        ToolBar mainToolBar = new ToolBar();
        mainToolBar.setPadding(new Insets(1, 1, 1, 1));
        mainToolBar.setPrefHeight(35);

        // Create the individual buttons...starting with "New Item" button
        Button btnNewItem = new Button();
        ImageView imgNewContact = new ImageView(new Image(Main.class.getResourceAsStream("resources/add32.png")));
        imgNewContact.setFitHeight(BTNH -2);
        imgNewContact.setFitWidth(BTNW - 2);
        btnNewItem.setGraphic(imgNewContact);
        btnNewItem.setPrefWidth(BTNW);
        btnNewItem.setPrefHeight(BTNH);
        btnNewItem.setOnAction(e -> newContact());

        // Delete Item Button
        Button btnRemoveItem = new Button();
        ImageView imgRemoveContact = new ImageView(new Image(Main.class.getResourceAsStream("resources/remove32.png")));
        imgRemoveContact.setFitHeight(BTNH -2);
        imgRemoveContact.setFitWidth(BTNW - 2);
        btnRemoveItem.setGraphic(imgRemoveContact);
        btnRemoveItem.setPrefWidth(BTNW);
        btnRemoveItem.setPrefHeight(BTNH);

        // Open File Button
        Button btnOpen = new Button();
        ImageView imgOpenItem = new ImageView(new Image(Main.class.getResourceAsStream("resources/open32.png")));
        imgOpenItem.setFitHeight(BTNH -2);
        imgOpenItem.setFitWidth(BTNW - 2);
        btnOpen.setGraphic(imgOpenItem);
        btnOpen.setPrefWidth(BTNW);
        btnOpen.setPrefHeight(BTNH);
        btnOpen.setOnAction(e -> openFile());

        // Save File Button
        Button btnSave = new Button();
        ImageView imgSaveItem = new ImageView(new Image(Main.class.getResourceAsStream("resources/save32.png")));
        imgSaveItem.setFitHeight(BTNH -2);
        imgSaveItem.setFitWidth(BTNW - 2);
        btnSave.setGraphic(imgSaveItem);
        btnSave.setPrefWidth(BTNW);
        btnSave.setPrefHeight(BTNH);


        mainToolBar.getItems().addAll(btnNewItem, btnRemoveItem, btnOpen, btnSave);
        return mainToolBar;
    }

    private TableView createTable(){

        TableView mainTable = new TableView();

        // Set the columns...
        TableColumn lNameColumn = new TableColumn("Last Name");
        TableColumn fNameColumn = new TableColumn("First Name");
        TableColumn emailColumn = new TableColumn("eMail");
        TableColumn phoneColumn = new TableColumn("Phone");

        lNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("eMail"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("Phone"));


        mainTable.getColumns().addAll(lNameColumn, fNameColumn, emailColumn, phoneColumn);
        mainTable.setItems(contacts);

        return mainTable;
    }

    private HBox createStatusBar(){
        HBox statusBar = new HBox();
        statusBar.setAlignment(Pos.BOTTOM_LEFT);
        statusBar.setId("statusbar");
        statusBar.setPrefHeight(14);
        statusBarLabel = new Label("This is the status bar...");
        statusBar.getChildren().add(statusBarLabel);
        return statusBar;
    }

    private void setStatusBar(String str){
        statusBarLabel.setText(str);
    }

    private void openFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open contacts file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File chosenFile = fileChooser.showOpenDialog(appStage);

        if(chosenFile != null)
            getFile(chosenFile);
    }

    private File getFile(File f) {
        setStatusBar("Using file: " + f.toString());
        return f;
    }

    private void newContact(){ new ContactForm().start(appStage); }

    public static void AddToObservableList(Contact c)
    {
        contacts.add(c);
    }
}
