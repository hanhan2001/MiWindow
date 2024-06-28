package me.xiaoying.window.component;

import me.xiaoying.window.Window;
import me.xiaoying.window.event.component.ClickedComponentEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 元素
 */
public abstract class Component implements Cloneable {
    private String name = null;
    private Component parent = null;
    private Window window;
    private final String normalSymbols = "[^a-zA-Z0-9%.]";
    private java.awt.Component component;
    private int width = 0;
    private String extraWidth = null;
    private int height = 0;
    private String extraHeight = null;
    private Model model = Model.NORMAL;
    private HashMap<Model, Set<Runnable>> knownPseudo = new HashMap<>();
    private Background background = new Background(this);
    private Date lastChangeMode = new Date();
    private Model lastModel = Model.NORMAL;

    public String name() {
        return this.name;
    }

    public Component name(String name) {
        this.name = name;
        return this;
    }

    protected void setModel(Model model) {
        // 判断上次设置 model 时间，避免出现循环设置导致系统卡死
        if (model == this.lastModel && new Date().getTime() - this.lastChangeMode.getTime() < 50)
            return;

        this.lastChangeMode = new Date();
        this.lastModel = model;

        this.model = model;
        switch (this.model) {
            case NORMAL:
//                this.module.close
                break;
            case ACTIVE:
                this.active();
                break;
            case HOVER:
                this.hover();
                break;
        }

        Graphics graphics = this.getComponent().getGraphics();
        graphics.setColor(this.background.color());
        this.getComponent().paint(graphics);
        this.recalculate();
    }

    public int width() {
        return this.getComponent().getWidth();
    }

    public String width(boolean extra) {
        if (!extra || this.extraWidth == null)
            return this.width() + "px";

        return this.extraWidth;
    }

    public Component width(String width) {
        width = width.replace(" ", "");

        // 判断是否存在特殊符号
        Pattern pattern = Pattern.compile(this.normalSymbols);
        Matcher matcher = pattern.matcher(width);
        if (matcher.find())
            throw new IllegalArgumentException("Unsupported height.");

        StringBuilder numberOfString = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        // 处理传递参数
        boolean matchNumber = true;
        for (String s : width.split("")) {
            // 判断匹配模式
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

        // 判断是否不存在任何长度数字
        if (numberOfString.length() == 0)
            return this;

        double number = 0;
        // 根据传递单位处理高度
        switch (unit.toString().toUpperCase(Locale.ENGLISH)) {
            case "":
            case "PX":
                number = Double.parseDouble(numberOfString.toString());
                break;
            case "%":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                // 处理其他对象
                // 若当前对象定位方式为 absolute 时根据上级非 absolute 定位方式元素计算
                number = this.getParent().width() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            // 根据窗口宽的百分比设置
            case "VW":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                number = this.window.width() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            // 根据窗口高的百分比设置
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

        this.extraWidth = width;
        return this.width(number);
    }

    private Component width(double width) {
        return this.width((int) width);
    }

    private Component width(int width) {
        this.width = width;
        this.getComponent().setSize(width, this.getComponent().getHeight());
        return this;
    }

    public int height() {
        return this.getComponent().getHeight();
    }

    public String height(boolean extra) {
        if (!extra || this.extraHeight == null)
            return this.width() + "px";

        return this.extraHeight;
    }

    public Component height(String height) {
        height = height.replace(" ", "");

        // 判断是否存在特殊符号
        Pattern pattern = Pattern.compile(this.normalSymbols);
        Matcher matcher = pattern.matcher(height);
        if (matcher.find())
            throw new IllegalArgumentException("Unsupported height.");

        StringBuilder numberOfString = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        // 处理传递参数
        boolean matchNumber = true;
        for (String s : height.split("")) {
            // 判断匹配模式
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

        // 判断是否不存在任何长度数字
        if (numberOfString.length() == 0)
            return this;

        double number = 0;
        // 根据传递单位处理高度
        switch (unit.toString().toUpperCase(Locale.ENGLISH)) {
            case "":
            case "PX":
                number = Double.parseDouble(numberOfString.toString());
                break;
            case "%":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getHeight() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                // 处理其他对象
                // 若当前对象定位方式为 absolute 时根据上级非 absolute 定位方式元素计算
                number = this.getParent().height() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            // 根据窗口宽的百分比设置
            case "VW":
                // 处理特殊对象
                if (this instanceof Window) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    number = dimension.getWidth() * Double.parseDouble(numberOfString.toString()) / 100;
                    break;
                }
                number = this.window.width() * Double.parseDouble(numberOfString.toString()) / 100;
                break;
            // 根据窗口高的百分比设置
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

        this.extraHeight = height;
        return this.height(number);
    }

    private Component height(double height) {
        return this.height((int) height);
    }

    private Component height(int height) {
        height = height - 18;
        this.getComponent().setSize(this.getComponent().getWidth(), height);
        this.height = height;
        return this;
    }

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
     * 根据 position 方式获取父级对象<br>
     * 当 position 方式为 absolute 时将会定位到上级非 absolute 对象作为 position 定位的父级对象<br>
     * 可以用 {@code component.position(PositionType.RELATIVE)} 更改父级元素的定位方式
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
    public Component getExtraParent() {
        return this.parent;
    }

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
        if (this.knownPseudo.get(Model.HOVER) == null)
            return;

        this.knownPseudo.get(Model.HOVER).forEach(Runnable::run);
    }

    public Component hover(Runnable runnable) {
        Set<Runnable> runnables;
        if ((runnables = this.knownPseudo.get(Model.HOVER)) == null)
            runnables = new HashSet<>();

        runnables.add(runnable);
        this.knownPseudo.put(Model.HOVER, runnables);
        return this;
    }

    public void active() {
        if (this.knownPseudo.get(Model.ACTIVE) == null)
            return;

        this.knownPseudo.get(Model.ACTIVE).forEach(Runnable::run);
    }

    public Component active(Runnable runnable) {
        Set<Runnable> runnables;
        if ((runnables = this.knownPseudo.get(Model.ACTIVE)) == null)
            runnables = new HashSet<>();

        runnables.add(runnable);
        this.knownPseudo.put(Model.ACTIVE, runnables);
        return this;
    }

    public Background background() {
        return this.background;
    }

    public void recalculate() {
        this.height(this.height(true));
        this.width(this.width(true));
    }

    public void visible(boolean bool) {
        this.getComponent().setVisible(bool);
    }

    protected java.awt.Component getComponent() {
        return this.component;
    }

    @Override
    protected Component clone() {
        Background background = new Background(this);

        try {
            Component component = (Component) super.clone();
            component.name = this.name;
            component.width = this.width;
            component.height = this.height;
            component.background = background;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}