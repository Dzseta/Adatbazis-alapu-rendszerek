package hu.alkfejl;

import hu.alkfejl.controller.PersonController;
import hu.alkfejl.model.Contract;
import hu.alkfejl.model.Person;
import hu.alkfejl.view.ContractAddDialog;
import hu.alkfejl.view.PersonAddDialog;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private PersonController controller = new PersonController();

    private TableView<Person> table = new TableView<>();
    private TableView<Contract> contractTable = new TableView<>();
    private Scene personScene;
    private Scene contractScene;

    @Override
    public void start(Stage stage) {
        constructTable();
        constructContractTable();

        Button refresh = new Button("Refresh");
        refresh.setOnAction(event -> {
            table.setItems(FXCollections.observableArrayList(controller.getAll()));
        });
        Button refresh2 = new Button("Refresh");
        refresh2.setOnAction(event -> {
            contractTable.setItems(FXCollections.observableArrayList(controller.listContracts()));
        });

        VBox root = new VBox(createMenuBar(stage), table, refresh);
        root.setSpacing(10);
        VBox root2 = new VBox(createMenuBar(stage), contractTable, refresh2);
        root2.setSpacing(10);
        personScene = new Scene(root, 400, 400);
        contractScene = new Scene(root2, 400, 400);

        stage.setScene(personScene);
        stage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu personMenu = new Menu("Szemely"); // menu letrehozasa
        Menu contractMenu = new Menu("Szerzodes");
        menuBar.getMenus().addAll(personMenu, contractMenu); // menu hozzaadasa a menubarhoz
        MenuItem addSzemelyMenuItem = new MenuItem("Szemely felvetele"); // menupont letrehozasa
        MenuItem listSzemelyMenuItem = new MenuItem("Szemely listazasa"); // menupont letrehozasa
        MenuItem addSzerzodes = new MenuItem("Szerzodes felvetele");
        MenuItem listSzerzodes = new MenuItem("Szerzodes listazasa");

        addSzemelyMenuItem.setOnAction(e -> new PersonAddDialog(controller)); // menupont esemenykezelese
        addSzerzodes.setOnAction(e -> new ContractAddDialog(controller));
        listSzemelyMenuItem.setOnAction(e -> {
            stage.setScene(personScene);
        });
        listSzerzodes.setOnAction(e -> {
            stage.setScene(contractScene);
        });

        personMenu.getItems().addAll(addSzemelyMenuItem, listSzemelyMenuItem); // menupot hozzaadasa a menuhoz
        contractMenu.getItems().addAll(addSzerzodes, listSzerzodes);

        return menuBar;
    }

    private void constructContractTable() {
        contractTable.setEditable(false);

        TableColumn<Contract, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Contract, String>("name"));

        TableColumn<Contract, String> posCol = new TableColumn<>("Position");
        posCol.setCellValueFactory(new PropertyValueFactory<Contract, String>("pos"));

        TableColumn<Contract, Boolean> ptCol = new TableColumn<>("Part time");
        ptCol.setCellValueFactory(new PropertyValueFactory<Contract, Boolean>("partTime"));

        contractTable.getColumns().addAll(nameCol, posCol, ptCol);
        contractTable.setItems(FXCollections.observableArrayList(controller.listContracts()));
    }

    private void constructTable() {
        table.setEditable(false);

        TableColumn<Person, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

        TableColumn<Person, Integer> birthCol = new TableColumn<>("Birth Year");
        birthCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("birthYear"));

        table.getColumns().addAll(nameCol, emailCol, birthCol);
        table.setItems(FXCollections.observableArrayList(controller.getAll()));
    }

    public static void main(String[] args) {
        launch();
    }

}