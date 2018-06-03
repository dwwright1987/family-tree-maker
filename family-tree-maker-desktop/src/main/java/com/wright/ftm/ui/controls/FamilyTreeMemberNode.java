package com.wright.ftm.ui.controls;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamilyTreeMemberNode extends Label {
    public FamilyTreeMemberNode(FamilyTreeMemberDTO familyTreeMemberDTO) {
        super(familyTreeMemberDTO.getFullName());

        setPadding(new Insets(DEFAULT_PADDING / 2));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, Color.DIMGRAY, Color.DIMGRAY, Color.DIMGRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT, Insets.EMPTY)));
        setOnMouseClicked(event -> {
            
        });
    }
}
