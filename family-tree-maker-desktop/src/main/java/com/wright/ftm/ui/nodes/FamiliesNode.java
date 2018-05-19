package com.wright.ftm.ui.nodes;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.services.FamilyTreesService;
import com.wright.ftm.ui.alerts.ConfirmationAlert;
import com.wright.ftm.ui.alerts.WarningAlert;
import com.wright.ftm.ui.utils.FamilyTreeComparator;
import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamiliesNode {
    private static final String NO_FAMILIES_EXIST = "No Families Exist.";
    private static final ObservableList<FamilyTreeDTO> familyTrees = FXCollections.observableArrayList();
    private static final FamilyTreesService familyTreesService = new FamilyTreesService();

    public static VBox build() {
        VBox familiesBox = new VBox();
        familiesBox.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE , BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        familiesBox.setMinWidth(250);
        familiesBox.setAlignment(Pos.CENTER);
        familiesBox.setPadding(new Insets(DEFAULT_PADDING));
        familiesBox.setSpacing(DEFAULT_PADDING);
        familiesBox.getChildren().addAll(createFamilyTreesView(familiesBox), createCreateFamilyTreeButton());

        return familiesBox;
    }

    private static ListView<FamilyTreeDTO> createFamilyTreesView(Pane parent) {
        List<FamilyTreeDTO> allFamilyTrees = familyTreesService.getAllFamilyTrees();
        if (allFamilyTrees.isEmpty()) {
            familyTrees.add(createFamilyTree(NO_FAMILIES_EXIST));
        } else {
            familyTrees.addAll(allFamilyTrees);
        }

        ListView<FamilyTreeDTO> familyTreeNamesView = new ListView<>();
        familyTreeNamesView.setEditable(false);
        familyTreeNamesView.setItems(familyTrees.sorted(new FamilyTreeComparator()));
        familyTreeNamesView.setCellFactory(param -> createFamilyTreeNamesListCell());
        familyTreeNamesView.prefHeightProperty().bind(parent.heightProperty());

        return familyTreeNamesView;
    }

    private static FamilyTreeDTO createFamilyTree(String name) {
        FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
        familyTreeDTO.setName(name);

        return familyTreeDTO;
    }

    private static ListCell<FamilyTreeDTO> createFamilyTreeNamesListCell() {
        ListCell<FamilyTreeDTO> cell = new ListCell<FamilyTreeDTO>() {
            protected void updateItem(FamilyTreeDTO item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        };

        cell.setContextMenu(createFamilyTreeNamesListCellContextMenu(cell));

        return cell;
    }

    private static ContextMenu createFamilyTreeNamesListCellContextMenu(ListCell<FamilyTreeDTO> cell) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(createFamilyTreeNamesListCellDeleteContextMenuItem(cell));

        return  contextMenu;
    }

    private static MenuItem createFamilyTreeNamesListCellDeleteContextMenuItem(ListCell<FamilyTreeDTO> cell) {
        MenuItem menuItem = new MenuItem("Delete");
        menuItem.setOnAction(event -> {
            FamilyTreeDTO familyTreeDTO = cell.getItem();

            ConfirmationAlert.show("Are you sure you want to remove the " + familyTreeDTO.getName() + " family and all associated members?", buttonType -> {
                if (buttonType == ButtonType.OK) {
                    if (familyTreesService.removeFamilyTree(familyTreeDTO)) {
                        familyTrees.remove(familyTreeDTO);
                    } else {
                        WarningAlert.show("Could not remove family tree: " + familyTreeDTO.getName());
                    }
                }
            });
        });

        return menuItem;
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

                FamilyTreeDTO familyTreeDTO = familyTreesService.createFamilyTree(familyName);
                if (familyTreeDTO == null) {
                    WarningAlert.show("Could not create family tree: " + familyName);
                } else {
                    familyTrees.add(familyTreeDTO);
                }
            }
        });
    }
}
