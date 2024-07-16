package me.xiaoying.window.awt;

import me.xiaoying.window.Window;
import me.xiaoying.window.component.Attribute;
import me.xiaoying.window.component.Component;

import javax.swing.*;
import java.awt.*;

public class MiFrame extends JFrame {
    private final Window window;

    public MiFrame(Window window) {
        this.window = window;
    }

    @Override
    public void paint(Graphics g) {
        if (this.window.getAttributes().get(Attribute.DISPLAY).toString().equalsIgnoreCase("null"))
            this.window.visible(false);

        if (this.window.getAttributes().get(Attribute.DISPLAY).toString().equalsIgnoreCase("block")) {
            for (int i = 0; i < this.window.getChildren().size(); i++) {
                if (i == 0) {
                    this.window.getChildren().get(i).getComponent().setLocation(0, 0);
                    continue;
                }

                Component last = this.window.getChildren().get(i - 1);
                this.window.getChildren().get(i).getComponent().setLocation(0, (int) (last.getComponent().getLocation().getY() + last.getComponent().getHeight()));
            }
        }
        super.paint(g);
    }
}