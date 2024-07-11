package me.xiaoying.window.awt;

import me.xiaoying.window.component.Component;
import me.xiaoying.window.component.StateManager;

import javax.swing.*;
import java.awt.*;

public class MiButton extends JButton implements MiComponent {
    private final Component component;

    public MiButton(Component button) {
        this.component = button;
        super.setContentAreaFilled(false);
    }

    @Override
    public void paint(Graphics graphics) {
        if (!this.getModel().isPressed() && !this.getModel().isRollover()) {
            this.component.getStateManager().setModel(StateManager.Model.NORMAL);
            this.component.getAttributes().repaint(graphics);
        }

        // 采用 else if，避免出现既是 active 又是 hover
        if (this.getModel().isPressed()) {
            this.component.getStateManager().setModel(StateManager.Model.ACTIVE);
            this.component.getAttributes().repaint(graphics);
        } else if (this.getModel().isRollover()) {
            this.component.getStateManager().setModel(StateManager.Model.HOVER);
            this.component.getAttributes().repaint(graphics);
        }
        graphics.fillRect(0, 0, getWidth(), getHeight());
        super.paint(graphics);
    }
}