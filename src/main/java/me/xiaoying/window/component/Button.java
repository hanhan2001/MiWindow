package me.xiaoying.window.component;

import me.xiaoying.window.awt.MiButton;

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

    public Button(String text, String width, String height) {
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

    private void width(int width) {
        this.miButton.setSize(width, this.miButton.getHeight());
    }

    private void height(int height) {
        this.miButton.setSize(this.miButton.getWidth(), height);
    }

    @Override
    public Component height(String height) {
        this.setHeight = true;
        return super.height(height);
    }

    @Override
    public Component width(String width) {
        this.setWidth = true;
        return super.width(width);
    }

    @Override
    public void recalculate() {
        if (!this.setWidth) {
            this.width(this.miButton.getFontMetrics(this.miButton.getFont()).stringWidth(this.miButton.getText()));
        }
        else
            this.width(super.width(true));

        if (!this.setHeight)
            this.height(this.miButton.getFontMetrics(this.miButton.getFont()).getHeight());
        else
            this.height(super.height(true));
    }
}