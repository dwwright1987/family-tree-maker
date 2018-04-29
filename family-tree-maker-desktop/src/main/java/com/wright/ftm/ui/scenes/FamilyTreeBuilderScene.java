package com.wright.ftm.ui.scenes;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.services.FamilyTreesService;
import com.wright.ftm.ui.alerts.WarningAlert;
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

import java.util.List;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamilyTreeBuilderScene {
    private static final String NO_FAMILIES_EXIST = "No Families Exist.";
    private static final FamilyTreesService familyTreesService = new FamilyTreesService();
    private static final ObservableList<FamilyTreeDTO> familyTrees = FXCollections.observableArrayList();

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
        ListView<FamilyTreeDTO> familyTreesView = createFamilyTreesView();

        VBox familiesBox = new VBox();
        familiesBox.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE , BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        familiesBox.setMinWidth(250);
        familiesBox.setAlignment(Pos.CENTER);
        familiesBox.setPadding(new Insets(DEFAULT_PADDING));
        familiesBox.setSpacing(DEFAULT_PADDING);
        familiesBox.getChildren().addAll(familyTreesView, createCreateFamilyTreeButton());

        familyTreesView.prefHeightProperty().bind(familiesBox.heightProperty());

        return familiesBox;
    }

    private static ListView<FamilyTreeDTO> createFamilyTreesView() {
        List<FamilyTreeDTO> allFamilyTrees = familyTreesService.getAllFamilyTrees();
        if (allFamilyTrees.isEmpty()) {
            familyTrees.add(createFamilyTree(NO_FAMILIES_EXIST));
        } else {
            familyTrees.addAll(allFamilyTrees);
        }

        ListView<FamilyTreeDTO> familyTreeNamesView = new ListView<>();
        familyTreeNamesView.setEditable(false);
        familyTreeNamesView.setItems(familyTrees); //TODO: Sort so lower case are in the same order as upper case
        familyTreeNamesView.setCellFactory(param -> new ListCell<FamilyTreeDTO>() {
            protected void updateItem(FamilyTreeDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(item.getName());
                }
            }
        });

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
                if (familyTrees.size() == 1 && NO_FAMILIES_EXIST.equals(familyTrees.get(0).getName())) {
                    familyTrees.clear();
                }

                if (familyTreesService.createFamilyTree(familyName)) {
                    familyTrees.add(createFamilyTree(familyName));
                } else {
                    WarningAlert.show("Could not create family tree: " + familyName);
                }
            }
        });
    }

    private static FamilyTreeDTO createFamilyTree(String name) {
        FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
        familyTreeDTO.setName(name);

        return familyTreeDTO;
    }
}
