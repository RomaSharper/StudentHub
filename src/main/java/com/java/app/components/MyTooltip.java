package com.java.app.components;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class MyTooltip extends Tooltip {
    public MyTooltip(String text) {
        super(text);
        setAutoHide(false);

        setOnShowing(event -> {
            Node node = getGraphic();
            if (node != null) {
                node.setOnMouseEntered(mouseEvent ->
                    show(node, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY() + 10)
                );

                node.setOnMouseMoved(mouseEvent ->
                    show(node, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY() + 10)
                );

                node.setOnMouseExited(mouseEvent -> hide());
            }
        });
    }

    public MyTooltip(String text, Node contentNode) {
        super(text);
        setAutoHide(false);
        setGraphic(contentNode);

        setOnShowing(event -> {
            Node node = getGraphic();
            if (node != null) {
                node.setOnMouseEntered(mouseEvent ->
                    show(node, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY() + 10)
                );

                node.setOnMouseMoved(mouseEvent ->
                    show(node, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY() + 10)
                );

                node.setOnMouseExited(mouseEvent -> hide());
            }
        });
    }
}
