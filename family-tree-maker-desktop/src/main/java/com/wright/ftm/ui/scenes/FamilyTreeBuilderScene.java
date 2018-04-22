package com.wright.ftm.ui.scenes;

import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamilyTreeBuilderScene {
    private static final String NO_FAMILIES_EXIST = "No Families Exist.";
    private static final ObservableList<String> familyTreeNames = FXCollections.observableArrayList();

    public static Scene build(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private static BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenuBar(primaryStage));
        borderPane.setLeft(createFamiliesPane());

        return borderPane;
    }

    private static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(primaryStage));

        return menuBar;
    }

    private static Menu createFileMenu(Stage primaryStage) {
        Menu menu = new Menu("File");
        menu.getItems().addAll(createExportMenuItem(), new SeparatorMenuItem(), createExitMenuItem(primaryStage));

        return menu;
    }

    private static MenuItem createExportMenuItem() {
        return new MenuItem("Export");
    }

    private static MenuItem createExitMenuItem(Stage primaryStage) {
        MenuItem menuItem = new MenuItem("Exit");
        menuItem.setOnAction(event -> primaryStage.close());

        return menuItem;
    }

    private static VBox createFamiliesPane() {
        ListView<String> familyTreeNamesView = createFamilyTreeNamesView();

        VBox familiesBox = new VBox();
        familiesBox.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE , BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        familiesBox.setMinWidth(250);
        familiesBox.setAlignment(Pos.CENTER);
        familiesBox.setPadding(new Insets(DEFAULT_PADDING));
        familiesBox.setSpacing(DEFAULT_PADDING);
        familiesBox.getChildren().addAll(familyTreeNamesView, createCreateFamilyTreeButton());

        familyTreeNamesView.prefHeightProperty().bind(familiesBox.heightProperty());

        return familiesBox;
    }

    private static ListView<String> createFamilyTreeNamesView() {
        familyTreeNames.add(NO_FAMILIES_EXIST);

        ListView<String> familyTreeNamesView = new ListView<>();
        familyTreeNamesView.setEditable(false);
        familyTreeNamesView.setItems(familyTreeNames.sorted());

        return familyTreeNamesView;
    }

    private static Button createCreateFamilyTreeButton() {
        Button createFamilyTreeButton = new Button("Create Family Tree");
        createFamilyTreeButton.setOnMouseClicked(event -> createFamilyNameDialog());

        return createFamilyTreeButton;
    }

    private static void createFamilyNameDialog() {
        TextInputDialog familyNameDialog = new TextInputDialog();
        familyNameDialog.setHeight(100);
        familyNameDialog.setWidth(300);
        familyNameDialog.setTitle("Create Family Tree");
        familyNameDialog.setHeaderText("Family Name");
        familyNameDialog.setContentText("Enter family name:");

        Stage familyNameStage = (Stage) familyNameDialog.getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(familyNameStage);
        UiLocationUtils.center(familyNameStage);

        familyNameDialog.showAndWait().ifPresent(familyName -> {
            if (StringUtils.isNotEmpty(familyName)) {
                if (familyTreeNames.contains(NO_FAMILIES_EXIST)) {
                    familyTreeNames.clear();
                }

                familyTreeNames.add(familyName);
            }
        });
    }
}
