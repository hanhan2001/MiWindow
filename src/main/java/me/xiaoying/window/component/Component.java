package me.xiaoying.window.component;

import me.xiaoying.window.Window;
import me.xiaoying.window.awt.MiButton;
import me.xiaoying.window.event.component.ClickedComponentEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Component
 */
public abstract class Component implements Cloneable {
    private Component parent = null;
    private Window window;
    protected final String specialSymbols = "[^a-zA-Z0-9%.]";
    private java.awt.Component component;
    private Background background = new Background(this);
    private StateManager stateManager = new StateManager(this);
    private EnumMap<StateManager.Model, Set<Runnable>> knownPseudo = new EnumMap<>(StateManager.Model.class);
    private final AttributeManager attributeManager = new AttributeManager(this);

    /**
     * Get name for this component
     *
     * @return String
     */
    public String name() {
        Object object = this.attributeManager.get(Attribute.NAME);
        if (object == null)
            return null;
        else
            return object.toString();
    }

    /**
     * Set name for this component
     *
     * @param name String
     * @return Component
     */
    public Component name(String name) {
        this.attributeManager.set(Attribute.NAME, name);
        return this;
    }

    /**
     * Get width for this component
     *
     * @return Final width for this component
     */
    public int width() {
        return this.getComponent().getWidth();
    }

    /**
     * Get width for this component
     *
     * @param exact get truth width for this component
     * @return String
     */
    public String width(boolean exact) {
        if (!exact || this.attributeManager.get(Attribute.WIDTH).toString() == null)
            return this.getComponent().getWidth() + "px";

        return this.attributeManager.get(Attribute.WIDTH).toString();
    }

    /**
     * Set width for this component<br>
     * Support unit
     * <ul>
     *     <li>px</li>
     *     <li>%</li>
     *     <li>vw</li>
     *     <li>vh</li>
     * </ul>
     *
     * @param width Width
     * @return Component
     */
    public Component width(String width) {
        double number = this.calculator(width, "HORIZONTAL", false);
        Component c = this.width((int) number, false);
        this.attributeManager.set(Attribute.WIDTH, width);
        return c;
    }

    /**
     * Set width for this component<br>
     * Unit is px
     *
     * @param width Width
     * @return Component
     */
    private Component width(double width) {
        return this.width((int) width);
    }

    /**
     * Set width for this component<br>
     * Unit is px
     *
     * @param width Width
     * @return Component
     */
    private Component width(int width) {
        this.width(width, true);
        return this;
    }

    private Component width(int width, boolean record) {
        this.getComponent().setSize(width, this.getComponent().getHeight());
        if (record)
            this.attributeManager.set(Attribute.WIDTH, width);
        return this;
    }

    /**
     * Get height for this component
     *
     * @return Integer
     */
    public int height() {
        return this.getComponent().getHeight();
    }

    /**
     * Get height for this component
     *
     * @param exact get truth width for this component
     * @return String
     */
    public String height(boolean exact) {
        if (!exact || this.attributeManager.get(Attribute.HEIGHT).toString() == null)
            return this.width() + "px";

        return this.attributeManager.get(Attribute.HEIGHT).toString();
    }

    /**
     * Set height for this component<br>
     * Support unit
     * <ul>
     *     <li>px</li>
     *     <li>%</li>
     *     <li>vw</li>
     *     <li>vh</li>
     * </ul>
     *
     * @param height Height
     * @return Component
     */
    public Component height(String height) {
        double number = this.calculator(height, "ENDWAYS", false);;
        Component c = this.height((int) number - 18, false);
        this.attributeManager.set(Attribute.HEIGHT, height);
        return c;
    }

    /**
     * Set width for this component<br>
     * Unit is px
     *
     * @param height Height
     * @return Component
     */
    private Component height(double height) {
        return this.height((int) height);
    }

    /**
     * Set width for this component<br>
     * Unit is px
     *
     * @param height Height
     * @return Component
     */
    private Component height(int height) {
        height = height - 18;
        this.height(height, true);
        return this;
    }

    private Component height(int height, boolean record) {
        this.getComponent().setSize(this.getComponent().getWidth(), height);
        if (record)
            this.attributeManager.set(Attribute.HEIGHT, height);
        return this;
    }

    /**
     * Get text for this component
     *
     * @return String
     */
    public String text() {
        return this.attributeManager.get(Attribute.TEXT).toString();
    }

    /**
     * Set text to component
     *
     * @param text String
     * @return Component
     */
    public Component text(String text) {
        if (!(this.component instanceof MiButton))
            return this;

        MiButton miButton = (MiButton) this.component;
        miButton.setText(text);
        this.attributeManager.set(Attribute.TEXT, text);
        return this;
    }

    /**
     * Set parent for this component
     *
     * @param component Component
     */
    public void setParent(Component component) {
        if (this.parent != null)
            return;

        // 若父级对象为 Window
        if (component instanceof Window) {
            this.window = (Window) component;
            this.parent = component;
            return;
        }

        this.parent = component;
        // 尝试通过获取父级对象获取到 Window
        while (component.getParent() != null)
            component = component.getParent();

        if (!(component instanceof Window))
            return;
        this.window = (Window) component;
    }

    /**
     * Obtain component base on position <br>
     * get component which position isn't absolute if parent's position is absolute<br>
     * {@code component.position(PositionType.RELATIVE)} can change location mode for parent component
     *
     * @return Component
     */
    public Component getParent() {
        return this.parent;
    }

    /**
     * 获取真正意义上的父级对象<br>
     * 此方法不受 {@code position} 影响
     *
     * @return Component
     */
    public Component getExactParent() {
        return this.parent;
    }

    /**
     * Get component's window
     *
     * @return Window
     */
    public Window getWindow() {
        return this.window;
    }

    protected Component setComponent(java.awt.Component component) {
        this.component = component;

        this.getComponent().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWindow().getEventManager().callEvent(new ClickedComponentEvent(Component.this));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return this;
    }

    public void hover() {
        if (this.knownPseudo.get(StateManager.Model.HOVER) == null)
            return;

        this.knownPseudo.get(StateManager.Model.HOVER).forEach(Runnable::run);
    }

    public Component hover(Runnable runnable) {
        Set<Runnable> runnables;
        if ((runnables = this.knownPseudo.get(StateManager.Model.HOVER)) == null)
            runnables = new HashSet<>();

        runnables.add(runnable);
        this.knownPseudo.put(StateManager.Model.HOVER, runnables);
        return this;
    }

    public void active() {
        if (this.knownPseudo.get(StateManager.Model.ACTIVE) == null)
            return;

        this.knownPseudo.get(StateManager.Model.ACTIVE).forEach(Runnable::run);
    }

    public Component active(Runnable runnable) {
        Set<Runnable> runnables;
        if ((runnables = this.knownPseudo.get(StateManager.Model.ACTIVE)) == null)
            runnables = new HashSet<>();

        runnables.add(runnable);
        this.knownPseudo.put(StateManager.Model.ACTIVE, runnables);
        return this;
    }

    public StateManager getStateManager() {
        return this.stateManager;
    }

    /**
     * Get background for this component
     *
     * @return Background
     */
    public Background background() {
        return this.background;
    }

    /**
     * Set color of characters for this Component
     *
     * @param color Color of java.awt
     * @return Component
     */
    public Component color(Color color) {
        this.component.setForeground(color);
        this.attributeManager.set(Attribute.COLOR, color);
        return this;
    }

    /**
     * Set color of characters for this Component
     *
     * @param color Color of me.xiaoying.window
     * @return Component
     */
    public Component color(me.xiaoying.window.Color color) {
        return this.color(color.toAWTColor());
    }

    /**
     * Set component's display type<br>
     * <ul>
     *     <li>block</li>
     *     <li>flex</li>
     *     <li>null</li>
     * </ul>
     *
     * @param display String
     * @return Component
     */
    public Component display(String display) {
        this.attributeManager.set(Attribute.DISPLAY, display);
        return this;
    }

    /**
     * Set component's position type<br>
     * <ul>
     *     <li>static</li>
     *     <li>relative</li>
     *     <li>absolute</li>
     *     <li>fixed</li>
     * </ul>
     *
     * @param position String
     * @return Component
     */
    public Component position(String position) {
        this.attributeManager.set(Attribute.POSITION, position);
        return this;
    }

    public Component top(String value) {
        double endways = this.calculator(value, "ENDWAYS", false);
        this.attributeManager.set(Attribute.TOP, value);
        return this;
    }

    /**
     * Recalculate something for this component
     */
    public void recalculate() {
        this.height(this.height(true));
        this.width(this.width(true));
    }

    /**
     * Set visible for this component<br>
     * {@code component.display(null)} same aas this method
     *
     * @param bool visible
     */
    public void visible(boolean bool) {
        this.getComponent().setVisible(bool);
    }

    /**
     * Get position type of the component
     *
     * @return String
     */
    public String position() {
        return this.getAttributes().get(Attribute.POSITION).toString();
    }

    /**
     * Get attributes for this component
     *
     * @return Map
     */
    public AttributeManager getAttributes() {
        return this.attributeManager;
    }

    public java.awt.Component getComponent() {
        return this.component;
    }

    public double calculator(String value, String direction, boolean record) {
        direction = direction.toUpperCase(Locale.ENGLISH);

        // 判断是否存在特殊符号
        Pattern pattern = Pattern.compile(this.specialSymbols);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find())
            throw new IllegalArgumentException("Unsupported unit.");

        StringBuilder numberOfString = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        boolean matchNumber = true;
        for (String s : value.split("")) {
            if (!matchNumber) {
                unit.append(s);
                continue;
            }

            try {
                Integer.parseInt(s);
            } catch (Exception e) {
                matchNumber = false;
                if (s.equalsIgnoreCase("."))
                    continue;

                unit.append(s);
                continue;
            }

            numberOfString.append(s);
        }

        if (numberOfString.length() == 0)
            return -1;

        double number = -1;
        switch (unit.toString().toUpperCase(Locale.ENGLISH)) {
            case "":
            case "PX":
                number = Double.parseDouble(numberOfString.toString());
                break;
            case "%":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    if ("HORIZONTAL".equalsIgnoreCase(direction))
                        number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    if ("ENDWAYS".equalsIgnoreCase(direction))
                        number = dimension.getHeight() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                // 处理其他对象
                Component parent = null;
                while (true) {
                    if (parent == null)
                        parent = this.getParent();

                    if ("RELATIVE".equalsIgnoreCase(parent.position()) || "STATIC".equalsIgnoreCase(parent.position()))
                        break;

                    parent = parent.getParent();
                    if (parent instanceof Window)
                        break;
                }
                // 处理当 parent 为 window 且position 不是 relative和static的情况
                if (!parent.position().equalsIgnoreCase("RELATIVE") && !parent.position().equalsIgnoreCase("STATIC")) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    if ("HORIZONTAL".equalsIgnoreCase(direction))
                        number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    if ("ENDWAYS".equalsIgnoreCase(direction))
                        number = dimension.getHeight() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }

                if ("HORIZONTAL".equalsIgnoreCase(direction))
                    number = parent.width() * Double.parseDouble(numberOfString.toString()) / 100;
                if ("ENDWAYS".equalsIgnoreCase(direction))
                    number = parent.height() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            case "VW":
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                number = this.window.width() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            case "VH":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getHeight() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                number = this.window.height() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
        }
        if (record)
            this.attributeManager.set(Attribute.HEIGHT, number);
        return number;
    }
}