package me.xiaoying.window.awt;

import me.xiaoying.window.Window;
import me.xiaoying.window.component.Attribute;
import me.xiaoying.window.component.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MiFrame extends JFrame {
    private final Window window;

    public MiFrame(Window window) {
        this.window = window;
    }

    @Override
    public void paint(Graphics g) {
//        Attribute.DISPLAY.run(this.window, g, this.window.getAttributes().get(Attribute.DISPLAY));

        String type = this.window.getAttributes().get(Attribute.DISPLAY).toString();
        switch (type.toUpperCase(Locale.ENGLISH)) {
            case "NULL":
                this.setVisible(false);
                break;
            case "BLOCK": {
                for (int i = 0; i < this.window.getChildren().size(); i++) {
                    if (i == 0) {
                        this.window.getChildren().get(i).getComponent().setLocation(0, 0);
                        continue;
                    }

                    me.xiaoying.window.component.Component last = this.window.getChildren().get(i - 1);
                    this.window.getChildren().get(i).getComponent().setLocation((int) last.getComponent().getLocation().getX(), (int) (last.getComponent().getLocation().getY() + last.getComponent().getHeight()));
                }
                break;
            }
            case "FLEX": {
                for (int i = 0; i < this.window.getChildren().size(); i++) {
                    if (i == 0) {
                        this.window.getChildren().get(i).getComponent().setLocation(0, 0);
                        continue;
                    }

                    Component last = this.window.getChildren().get(i - 1);
                    this.window.getChildren().get(i).getComponent().setLocation((int) last.getComponent().getLocation().getX() + last.getComponent().getWidth(), (int) last.getComponent().getLocation().getY());
                }
                break;
            }
        }
        super.paint(g);
    }
}