package me.xiaoying.window.component;

import java.awt.*;

public class Button extends Component {
    private final MiButton miButton = new MiButton(this);
    private boolean setWidth = false;
    private boolean setHeight = false;

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

    @Override
    public Component height(int height) {
        this.setWidth = true;
        return super.height(height);
    }

    @Override
    public Component width(int width) {
        this.setHeight = true;
        return super.width(width);
    }

    @Override
    public void recalculate() {
        int width = 0;
        int height = 0;

        if (!this.setWidth)
            this.width(width);
        if (!this.setHeight)
            this.height(height);
    }

    public String text() {
        return this.miButton.getText();
    }

    public Button text(String text) {
        this.miButton.setText(text);
        this.recalculate();
        return this;
    }
}