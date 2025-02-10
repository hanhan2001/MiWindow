package me.xiaoying.window;

public abstract class Component {
    protected int width;
    protected int height;

    public abstract void setVisible(boolean visible);

    public int getWidth() {
        return this.width;
    }

    public abstract Component setWidth(int width);

    public int getHeight() {
        return this.height;
    }

    public abstract Component setHeight(int height);
}