package me.xiaoying.window.component;

import java.util.Date;

public class StateManager {
    private final Component component;
    private Model model = Model.NORMAL;
    private Date lastSetMode = new Date();

    public StateManager(Component component) {
        this.component = component;
    }

    /**
     * Get the model of the current component
     *
     * @return Model
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * Set model of component to call active, hover or normal event
     *
     * @param model Model
     */
    public void setModel(Model model) {
        if (this.model == model && new Date().getTime() - this.lastSetMode.getTime() < 50)
            return;

        this.model = model;
        switch (this.model) {
            case NORMAL:
                break;
            case HOVER:
                this.component.hover();
                break;
            case ACTIVE:
                this.component.active();
                break;
        }
    }

    /**
     * Get component
     *
     * @return Component
     */
    public Component getComponent() {
        return this.component;
    }

    public enum Model {
        NORMAL,
        HOVER,
        ACTIVE
    }
}