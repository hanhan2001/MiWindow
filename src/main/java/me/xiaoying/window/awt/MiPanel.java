package me.xiaoying.window.awt;

import me.xiaoying.window.component.Div;
import me.xiaoying.window.component.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MiPanel extends JPanel implements MiComponent {
    private final Div div;

    public MiPanel(Div div) {
        this.div = div;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                div.getStateManager().setModel(StateManager.Model.ACTIVE);
                div.getAttributes().repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                div.getStateManager().setModel(StateManager.Model.HOVER);
                div.getAttributes().repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                div.getStateManager().setModel(StateManager.Model.HOVER);
                div.getAttributes().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                div.getStateManager().setModel(StateManager.Model.NORMAL);
                div.getAttributes().repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (this.div.getStateManager().getModel() == StateManager.Model.NORMAL)
            this.div.getAttributes().repaint();
        super.paint(g);
    }
}