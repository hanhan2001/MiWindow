package me.xiaoying.window.awt;

import me.xiaoying.window.Window;
import me.xiaoying.window.component.Attribute;

import javax.swing.*;
import java.awt.*;

public class MiFrame extends JFrame {
    private final Window window;

    public MiFrame(Window window) {
        this.window = window;
    }

    @Override
    public void paint(Graphics g) {
        Attribute.DISPLAY.run(this.window, g, this.window.getAttributes().get(Attribute.DISPLAY));
        super.paint(g);
    }
}