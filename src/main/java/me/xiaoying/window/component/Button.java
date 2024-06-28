package me.xiaoying.window.component;

import java.awt.*;

public class Button extends Component {
    private final MiButton miButton = new MiButton(this);

    public Button() {
        this.setComponent(this.miButton);

        this.init();
    }

    public Button(String text) {
        this.setComponent(this.miButton);

        this.miButton.setText(text);
        this.height(this.miButton.getFontMetrics(this.miButton.getFont()).getHeight());
        this.width(this.miButton.getFontMetrics(this.miButton.getFont()).stringWidth(this.miButton.getText()));

        this.init();
    }

    public Button(String text, int width, int height) {
        this.setComponent(this.miButton);

        this.miButton.setText(text);
        this.width(width);
        this.height(height);
        this.init();
    }

    private void init() {
        this.miButton.setBorder(null);
        this.miButton.setFocusPainted(false);
        this.miButton.setBorderPainted(false);
        this.miButton.setMargin(new Insets(0, 0, 0, 0));

        this.miButton.repaint();
        this.miButton.setVisible(true);
    }
}