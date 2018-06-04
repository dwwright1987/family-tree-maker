package com.wright.ftm.ui.controls;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
import com.wright.ftm.ui.alerts.WarningAlert;
import com.wright.ftm.ui.dialogs.FamilyMemberDialog;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamilyTreeMemberNode extends Label {
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
    private FamilyTreeDTO familyTreeDTO;
    private FamilyTreeMemberDTO familyTreeMemberDTO;

    public FamilyTreeMemberNode(FamilyTreeMemberDTO familyTreeMemberDTO, FamilyTreeDTO familyTreeDTO) {
        super(familyTreeMemberDTO.getFullName());

        this.familyTreeDTO = familyTreeDTO;
        this.familyTreeMemberDTO = familyTreeMemberDTO;

        setBorder(new Border(new BorderStroke(Color.DIMGRAY, Color.DIMGRAY, Color.DIMGRAY, Color.DIMGRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT, Insets.EMPTY)));
        setContextMenu(createContextMenu());
        setOnMouseClicked(this::handleMouseEvent);
        setPadding(new Insets(DEFAULT_PADDING / 2));
    }

    private void handleMouseEvent(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            showFamilyMemberDialog();
        }
    }

    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(createModifyContextMenuItem());

        return  contextMenu;
    }

    private MenuItem createModifyContextMenuItem() {
        MenuItem menuItem = new MenuItem("Modify");
        menuItem.setOnAction(event -> showFamilyMemberDialog());

        return menuItem;
    }

    private void showFamilyMemberDialog() {
        FamilyMemberDialog familyMemberDialog = new FamilyMemberDialog(familyTreeMemberDTO, familyTreeDTO);
        familyMemberDialog.showAndWait().ifPresent(familyTreeMemberDTO -> {
            FamilyTreeMemberDTO createdFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(familyTreeMemberDTO, familyTreeDTO);
            if (createdFamilyTreeMemberDTO == null) {
                WarningAlert.show("Could not update family member");
            }
        });
    }
}
