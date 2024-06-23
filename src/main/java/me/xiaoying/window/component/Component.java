package me.xiaoying.window.component;

import me.xiaoying.window.Window;

import java.awt.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 元素
 */
public abstract class Component {
    private String name = null;
    private Component parent = null;
    private Window window;
    private final String normalSymbols = "[^a-zA-Z0-9.]";

    public String getName() {
        return this.name;
    }

    public Component setName(String name) {
        this.name = name;
        return this;
    }

    public int width() {
        return this.getComponent().getWidth();
    }

    public Component width(int width) {
        this.getComponent().setSize(width, this.getComponent().getHeight());
        return this;
    }

    public int height() {
        return this.getComponent().getHeight();
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

        return this.height(number);
    }

    public Component height(double height) {
        return this.height((int) height);
    }

    public Component height(int height) {
        this.getComponent().setSize(this.getComponent().getWidth(), height);
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

    public abstract java.awt.Component getComponent();
}