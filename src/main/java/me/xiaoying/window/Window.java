package me.xiaoying.window;

import me.xiaoying.window.awt.MiFrame;
import me.xiaoying.window.component.Component;
import me.xiaoying.window.component.Container;
import me.xiaoying.window.event.EventManager;
import me.xiaoying.window.event.window.WindowCloseEvent;
import me.xiaoying.window.event.window.WindowResizedEvent;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;

public class Window extends Container {
    private final MiFrame miFrame;
    private final EventManager eventManager = new EventManager();

    public Window() {
        this.miFrame = new MiFrame(this);
        this.setComponent(this.miFrame);

        this.width(500);
        this.height(300);

        this.init();
    }

    public Window(String title) {
        this.miFrame = new MiFrame(this);
        this.miFrame.setTitle(title);
        this.setComponent(this.miFrame);

        this.width(500);
        this.height(300);

        this.init();
    }

    public Window(int width, int height) {
        this.miFrame = new MiFrame(this);
        this.setComponent(this.miFrame);

        this.width(width);
        this.height(height);

        this.init();
    }

    public Window(String title, int width, int height) {
        this.miFrame = new MiFrame(this);
        this.miFrame.setTitle(title);
        this.setComponent(this.miFrame);

        this.width(width);
        this.height(height);

        this.init();
    }

    private void init() {
        this.miFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // default fort for component
        FontUIResource fontRes = new FontUIResource(new Font("Microsoft YaHei", Font.PLAIN, 12));
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }

        this.miFrame.setLayout(null);
        this.miFrame.setVisible(true);

        this.miFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                eventManager.callEvent(new WindowResizedEvent(getWindow(), e.getComponent().getWidth(), e.getComponent().getHeight()));
                recalculate();
            }
        });

        this.miFrame.addWindowListener(new WindowListener() {
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

    public Window width(int width) {
        this.getComponent().setSize(width, this.getComponent().getHeight());
        return this;
    }

    public Window height(int height) {
        this.getComponent().setSize(this.getComponent().getWidth(), height);
        return this;
    }

    public Window resizable(boolean bool) {
        this.miFrame.setResizable(bool);
        return this;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public void recalculate() {
        this.getChildren().forEach(Component::recalculate);
    }

    public String title() {
        return this.miFrame.getTitle();
    }

    public Component title(String title) {
        this.miFrame.setTitle(title);
        return this;
    }

    @Override
    public Window getWindow() {
        return this;
    }
}