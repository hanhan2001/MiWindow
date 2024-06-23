package me.xiaoying.window.component;

import javax.swing.*;

public class Button extends Component {
    private final JButton jButton = new JButton();

    @Override
    public java.awt.Component getComponent() {
        return this.jButton;
    }
}