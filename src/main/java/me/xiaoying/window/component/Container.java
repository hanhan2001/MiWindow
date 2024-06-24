package me.xiaoying.window.component;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器
 */
public abstract class Container extends Component {
    private final List<Component> knownComponent = new ArrayList<>();

    public void add(Component component) {
        if (this.knownComponent.contains(component))
            return;

        component.setParent(this);
        this.knownComponent.add(component);
        ((java.awt.Container) this.getComponent()).add(component.getComponent());
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
        super.recalculate();

        this.knownComponent.forEach(Component::recalculate);
    }
}