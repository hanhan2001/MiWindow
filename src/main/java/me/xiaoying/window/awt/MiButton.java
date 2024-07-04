package me.xiaoying.window.awt;

import me.xiaoying.window.component.Button;
import me.xiaoying.window.component.StateManager;

import javax.swing.*;
import java.awt.*;

public class MiButton extends JButton implements MiComponent {
    private final me.xiaoying.window.component.Button button;

    public MiButton(Button button) {
        this.button = button;
        super.setContentAreaFilled(false);
    }

    @Override
    public void paint(Graphics graphics) {
        if (!this.getModel().isPressed() && !this.getModel().isRollover()) {
            this.button.getStateManager().setModel(StateManager.Model.NORMAL);
            this.button.getAttributes().repaint(graphics);
        }

        // 采用 else if，避免出现既是 active 又是 hover
        if (this.getModel().isPressed()) {
            this.button.getStateManager().setModel(StateManager.Model.ACTIVE);
            this.button.getAttributes().repaint(graphics);
        } else if (this.getModel().isRollover()) {
            this.button.getStateManager().setModel(StateManager.Model.HOVER);
            this.button.getAttributes().repaint(graphics);
        }
        graphics.fillRect(0, 0, getWidth(), getHeight());
        super.paint(graphics);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}