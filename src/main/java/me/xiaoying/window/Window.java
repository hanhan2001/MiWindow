package me.xiaoying.window;

import me.xiaoying.window.component.Container;
import me.xiaoying.window.event.EventManager;
import me.xiaoying.window.event.window.WindowCloseEvent;
import me.xiaoying.window.event.window.WindowResizedEvent;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends Container {
    private final JFrame jFrame;
    private final EventManager eventManager = new EventManager();

    public Window() {
        this.jFrame = new JFrame();
        this.width(500);
        this.height(300);

        this.init();
    }

    public Window(String title) {
        this.jFrame = new JFrame(title);
        this.width(500);
        this.height(300);

        this.init();
    }

    public Window(int width, int height) {
        this.jFrame = new JFrame();
        this.width(width);
        this.height(height);

        this.init();
    }

    public Window(String title, int width, int height) {
        this.jFrame = new JFrame(title);
        this.width(width);
        this.height(height);

        this.init();
    }

    private void init() {
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.jFrame.setLayout(null);
        this.jFrame.setVisible(true);

        this.jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                eventManager.callEvent(new WindowResizedEvent(getWindow(), e.getComponent().getWidth(), e.getComponent().getHeight()));
            }
        });
        this.jFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
//                eventManager.callEvent(new W);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                eventManager.callEvent(new WindowCloseEvent(getWindow()));
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public Window resizable(boolean bool) {
        this.jFrame.setResizable(bool);
        return this;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public Window getWindow() {
        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return this.jFrame;
    }
}