package me.xiaoying.window.component;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器
 */
public abstract class Container extends Component {
    private final List<Component> knownComponent = new ArrayList<>();
    private boolean setWidth = false;
    private boolean setHeight = false;

    @Override
    public Component height(String height) {
        this.setWidth = true;
        return super.height(height);
    }

    @Override
    public Component width(String width) {
        this.setHeight = true;
        return super.width(width);
    }

    public void add(Component component) {
        if (this.knownComponent.contains(component))
            return;

        component.setParent(this);
        ((java.awt.Container) this.getComponent()).add(component.getComponent(), 0);
        this.knownComponent.add(component);
        this.getComponent().repaint();
    }

    public void remove(Component component) {
        if (!this.knownComponent.contains(component))
            return;

        component.setParent(null);
        this.knownComponent.remove(component);
        ((java.awt.Container) this.getComponent()).remove(component.getComponent());
    }

    public List<Component> getChildren() {
        return this.knownComponent;
    }

    @Override
    public void recalculate() {
        int width = 0;
        int height = 0;
        for (Component component : this.knownComponent) {
            component.recalculate();
            width += component.width();
            height += component.height();
        }

        if (!this.setWidth)
            this.width(width + "px");
        if (!this.setHeight)
            this.height(height + "px");
    }
}