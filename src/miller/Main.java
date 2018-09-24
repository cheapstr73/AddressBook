
package miller;

// import java classes
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

// Import javafx classes
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class Main extends Application{

    // Standard toolbar button height and width...
    private final int BTNH = 21;
    private final int BTNW = 21;

    // Set up a few instance variables that will be needed later.
    private Stage appStage;
    private Label statusBarLabel;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;

    // Observable list to feed the main window's TableView control. Make static as we only need one copy of this list for the application.
    static ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        // appStage is an instance variable that will act as parent to the child stages initOwner method.
        this.appStage = stage;
        VBox vBox = new VBox();
        vBox.setId("mainForm"); // ...for the .css

        // Each of these will be created in their own methods
        MenuBar menubar = createMenuBar();
        ToolBar toolBar = createToolBar();
        TableView table = createTable();
        HBox statusBar = createStatusBar();

        // Add everything to the VBox control and set the VGrow so the table will automatically fill the scene when window is resized.
        vBox.getChildren().addAll(menubar, toolBar, table, statusBar);
        vBox.setVgrow(table, Priority.ALWAYS);

	    // Create the scene and associate the .css
        Scene mainScene = new Scene(vBox);
        mainScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        // Set up the stage to be displayed to screen
        stage.getIcons().add(new Image(Main.class.getResource("resources/main128.png").toExternalForm()));
        stage.setScene(mainScene);
        stage.setTitle("Address Book");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    /**
     * This method creates the main window's menu bar.
     * @return MenuBar - This the final MenuBar control.
     */
    private MenuBar createMenuBar(){

        MenuBar mainMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
	    // Make sure the menu is flush to the left of the window...
        mainMenu.setPadding(new Insets(0,0,0,0));

        // Add items to the 'file' menu...
        MenuItem addItemMenu = new MenuItem("New Contact");
        MenuItem delItemMenu = new MenuItem("Delete Contact");
        MenuItem openItemMenu = new MenuItem("Open File");
        MenuItem saveItemMenu = new MenuItem("Save File");

        // Add items to the 'help' menu
        MenuItem helpItemMenu = new MenuItem("Help");
        MenuItem aboutItemMenu = new MenuItem("About Adddress Book");

        // Create a 16x16 icon for each menu item
        ImageView addView = new ImageView(new Image(Main.class.getResourceAsStream("resources/addcontact.png")));
        addView.setFitHeight(16);
        addView.setFitWidth(16);
        addItemMenu.setGraphic(addView);
        addItemMenu.setOnAction(e -> newContact());

        ImageView delView = new ImageView(new Image(Main.class.getResourceAsStream("resources/removecontact.png")));
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

	// Add a separator to seperate the file menu's items into two distinct groups
        SeparatorMenuItem hr = new SeparatorMenuItem();

        // Attach individual MenuItems to the 'file' and 'help' menus.
        fileMenu.getItems().addAll(openItemMenu, saveItemMenu, hr, addItemMenu, delItemMenu);
        helpMenu.getItems().addAll(helpItemMenu, aboutItemMenu);

	// Now add the main menu entries to the menu bar.
        mainMenu.getMenus().addAll(fileMenu, editMenu, helpMenu);
        return mainMenu;
    }

    /**
     * This method creates the main window's toolbar. Each toolbar button is decorated with a 16x16 icon and a tooltip.
     * @return ToolBar - This is the main window's ToolBar control.
     */
    private ToolBar createToolBar(){

        ToolBar mainToolBar = new ToolBar();
        mainToolBar.setPadding(new Insets(1, 1, 1, 1));
        mainToolBar.setPrefHeight(35);

        // Create the individual buttons...starting with "New Item" button
        Button btnNewItem = new Button();
	    // Create an icon for the button...
        ImageView imgNewContact = new ImageView(new Image(Main.class.getResourceAsStream("resources/addcontact.png")));
        imgNewContact.setFitHeight(BTNH -2); // Make the icon slightly smaller than the size of the button..
        imgNewContact.setFitWidth(BTNW - 2); // Make the icon slightly smaller than the size of the button..
        btnNewItem.setGraphic(imgNewContact);
        btnNewItem.setPrefWidth(BTNW);
        btnNewItem.setPrefHeight(BTNH);
        btnNewItem.setOnMouseEntered(e -> setStatusBar("Add new contact."));
        btnNewItem.setOnMouseExited(e -> setStatusBar(""));
        btnNewItem.setOnAction(e -> newContact());

        // Remove Item Button
        Button btnDeleteItem = new Button();
        ImageView imgRemoveContact = new ImageView(new Image(Main.class.getResourceAsStream("resources/removecontact.png")));
        imgRemoveContact.setFitHeight(BTNH -2); // Make the icon slightly smaller than the size of the button..
        imgRemoveContact.setFitWidth(BTNW - 2); // Make the icon slightly smaller than the size of the button..
        btnDeleteItem.setGraphic(imgRemoveContact);
        btnDeleteItem.setPrefWidth(BTNW);
        btnDeleteItem.setPrefHeight(BTNH);
        btnDeleteItem.setOnMouseEntered(e -> setStatusBar("Delete selected contact."));
        btnDeleteItem.setOnMouseExited(e -> setStatusBar(""));

        // Open File Button
        Button btnOpen = new Button();
        ImageView imgOpenItem = new ImageView(new Image(Main.class.getResourceAsStream("resources/open32.png")));
        imgOpenItem.setFitHeight(BTNH -2); // Make the icon slightly smaller than the size of the button..
        imgOpenItem.setFitWidth(BTNW - 2); // Make the icon slightly smaller than the size of the button..
        btnOpen.setGraphic(imgOpenItem);
        btnOpen.setPrefWidth(BTNW);
        btnOpen.setPrefHeight(BTNH);
        btnOpen.setOnAction(e -> openFile());

        // Save File Button
        Button btnSave = new Button();
        ImageView imgSaveItem = new ImageView(new Image(Main.class.getResourceAsStream("resources/save32.png")));
        imgSaveItem.setFitHeight(BTNH -2); // Make the icon slightly smaller than the size of the button..
        imgSaveItem.setFitWidth(BTNW - 2); // Make the icon slightly smaller than the size of the button..
        btnSave.setGraphic(imgSaveItem);
        btnSave.setPrefWidth(BTNW);
        btnSave.setPrefHeight(BTNH);
        btnSave.setOnAction(e -> saveDefaultContactList());

        // Create the tooltips for the toolbar buttons
        Tooltip openTT = new Tooltip("File Open");
        Tooltip saveTT = new Tooltip("Save File");
        Tooltip newTT = new Tooltip("New Contact");
        Tooltip removeTT = new Tooltip("Delete Contact");
        btnOpen.setTooltip(openTT);
        btnSave.setTooltip(saveTT);
        btnNewItem.setTooltip(newTT);
        btnDeleteItem.setTooltip(removeTT);

        // Add a seperator to visually seperate button groups
        Separator hr = new Separator();

        mainToolBar.getItems().addAll(btnOpen, btnSave, hr, btnNewItem, btnDeleteItem);
        return mainToolBar;
    }

    /**
     * This method creates the TableView control for the window. The data for the table is set by the ObservableList
     * <i>contacts</i>. Whenever the ObservableList is updated, the table will update accordingly. Also sets a MouseEvent
     * for the table rows in order to check for a double-click. Double-clicking a non-empty row will open the child
     * form and populate it's form elements with the selected <b>Contact</b> object's data.
     * @return TableView - This is the main window's TableView control
     */
    private TableView createTable(){

        TableView mainTable = new TableView();

        // Here we set up the columns headers for the table
        TableColumn lNameColumn = new TableColumn("Last Name");
        lNameColumn.setPrefWidth(100);
        TableColumn fNameColumn = new TableColumn("First Name");
        fNameColumn.setPrefWidth(100);
        TableColumn emailColumn = new TableColumn("eMail");
        emailColumn.setPrefWidth(150);
        TableColumn phone1Column = new TableColumn("Phone");
        TableColumn phone2column = new TableColumn("Phone");

        // Set the value of each cell using <i>PropertyValueFactory</i>.
        lNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("eMail1"));
        phone1Column.setCellValueFactory(new PropertyValueFactory<Contact, String>("Phone1"));
        phone2column.setCellValueFactory(new PropertyValueFactory<Contact, String>("Phone2"));

        // Create the context menu to use on the table rows.
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit Contact");
        MenuItem deleteItem = new MenuItem("Delete Contact");
        contextMenu.getItems().addAll(editItem, deleteItem);

        // Add each column to the table.
        mainTable.getColumns().addAll(lNameColumn, fNameColumn, emailColumn, phone1Column, phone2column);
        mainTable.setItems(contacts);

        // Set up the table row to act on a double-click.
        mainTable.setRowFactory(e ->{
            TableRow<Contact> row = new TableRow<>();
            /*row.setContextMenu(contextMenu);
            editItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Contact item = (Contact) mainTable.getSelectionModel().getSelectedItem();
                    newContact(item);
                }
            });*/
            row.setOnMouseClicked(clickEvent -> {
                if(clickEvent.getClickCount() == 2 && (!row.isEmpty())){
                    Contact rowItem = row.getItem();
                    //Contact c = newContact(rowItem);
                    //TESTING Contact dd = new Contact("aaa", "aaa", "aaa", "aaa", "333","333");
                     //mainTable.getItems().set(mainTable.getSelectionModel().getSelectedIndex(), c);
                    // System.out.println(c.getFirstName());
                    newContact(rowItem);
                }
            });
            return row;
        });
        openDefaultContactList();
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Contact List Files", "*.lst"));
        File chosenFile = fileChooser.showOpenDialog(appStage);

        if(chosenFile != null)
            getFile(chosenFile);
    }

    private File getFile(File f) {
        setStatusBar("Using file: " + f.toString());
        return f;
    }

    /**
     * This will open the newContact form. With no arguments the form will be blank.
     */
    private void newContact(){ new ContactForm().start(appStage); }

    /**
     * This method will open the newContact form, but with passing the <b>Contact</b> object, the form will populate
     * it's controls with the data in the object. This method is called on a non-empty row item being double-clicked
     * or by using the <i>Edit</i> option on the currently selected item.
     * @param c - This is the <b>Contact</b> object to pass to the new form.
     */
    private void newContact(Contact c){
        new ContactForm(c, appStage);
    }

    /**
     * This method add an item to the ObservableList. Once the ObservableList is updated, the main window's table
     * will update appropriately.
     * @param c - The <b>Contact</b> object to add to the ObservableList <i>contacts</i>.
     */
    public static void AddToObservableList(Contact c)
    {
        contacts.add(c);
    }

    private static void openDefaultContactList(){

        try
        {
            input = new ObjectInputStream(Files.newInputStream(Paths.get("default.lst")));
            while(true)
                contacts.add((Contact) input.readObject());

        }
        catch(EOFException e)
        {


        }
        catch(ClassNotFoundException e)
        {

        }
        catch(IOException e)
        {
            System.err.print("Could not open default.lst. Terminating.");
        }

        finally
        {
            closeDefaultContactList();
        }
    }

    public static void saveDefaultContactList(){
        try
        {
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get("default.lst")));
            for(Contact c : contacts)
                output.writeObject(c);
            closeDefaultContactList();
        }
        catch(IOException e)
        {
            System.err.print("Error opening file. Terminating.");

            System.exit(1);
        }
    }

    public static void closeDefaultContactList(){
        try
        {
            if(output != null)
                output.close();
        }
        catch (IOException e)
        {
            System.err.print("Error closing file. Terminating.");
        }
    }
}
