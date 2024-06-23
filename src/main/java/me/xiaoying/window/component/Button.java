package me.xiaoying.window.component;

import javax.swing.*;

public class Button extends Component {
    private final JButton jButton = new JButton();

    public Button() {
        this.setComponent(this.jButton);
    }
}