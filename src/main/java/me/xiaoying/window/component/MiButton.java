package me.xiaoying.window.component;

import javax.swing.*;
import java.awt.*;

public class MiButton extends JButton {
    private final Button button;

    public MiButton(Button button) {
        this.button = button;
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!this.getModel().isPressed() && !this.getModel().isRollover()) {
            g.setColor(Color.WHITE);
            this.button.setModel(Model.NORMAL);
        }

        // 采用 else if，避免出现既是 active 又是 hover
        if (this.getModel().isPressed()) {
            this.button.setModel(Model.ACTIVE);
            g.setColor(Color.RED);
        } else if (this.getModel().isRollover()) {
            this.button.setModel(Model.HOVER);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}