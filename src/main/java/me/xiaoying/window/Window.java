package me.xiaoying.window;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK14;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

public class Window extends Component {
    private volatile long id;
    private volatile boolean closed = true;

    public Window(String name) {
        this(name, 800, 600);
    }

    public Window(String title, int width, int height) {
        new Thread(() -> {
            if (!GLFW.glfwInit())
                throw new IllegalStateException("Unable to initialize GLFW");

            GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
//            GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

            this.id = GLFW.glfwCreateWindow(width, height, title, 0, 0);

            if (this.id == 0)
                throw new RuntimeException("Failed to create the GLFW window");

            try (MemoryStack stack = MemoryStack.stackPush()) {
                VkApplicationInfo appInfo = VkApplicationInfo.calloc()
                        .sType(VK14.VK_STRUCTURE_TYPE_APPLICATION_INFO)
                        .pApplicationName(stack.UTF8("Vulkan Application"))
                        .applicationVersion(VK14.VK_MAKE_VERSION(1, 0, 0))
                        .pEngineName(stack.UTF8("No Engine"))
                        .engineVersion(VK14.VK_MAKE_VERSION(1, 0, 0))
                        .apiVersion(VK14.VK_API_VERSION_1_4);

                VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc()
                        .sType(VK14.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                        .pApplicationInfo(appInfo);

                PointerBuffer instance = stack.mallocPointer(1);
                if (VK14.vkCreateInstance(createInfo, null, instance) != VK14.VK_SUCCESS) {
                    throw new RuntimeException("Failed to create Vulkan instance");
                }


                this.closed = false;
                while (!GLFW.glfwWindowShouldClose(this.id))
                    GLFW.glfwPollEvents();

                GLFW.glfwDestroyWindow(this.id);
                GLFW.glfwTerminate();
                this.closed = true;
            }
        }).start();

        // sleep thread to wait the window
        while (this.id == 0);
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return GLFW.glfwGetWindowTitle(this.id);
    }

    public Window setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.id, title);
        return this;
    }

    @Override
    public Window setVisible(boolean visible) {
        if (visible)
            GLFW.glfwShowWindow(this.id);
        else
            GLFW.glfwHideWindow(this.id);

        return this;
    }

    @Override
    public int getWidth() {
        int[] width = new int[1], height = new int[1];
        GLFW.glfwGetWindowSize(this.id, width, height);
        return width[0];
    }

    @Override
    public Window setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public int getHeight() {
        int[] width = new int[1], height = new int[1];
        GLFW.glfwGetWindowSize(this.id, width, height);

        if (GLFW.glfwGetWindowAttrib(this.id, GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE)
            return height[0] + 32;

        return height[0];
    }

    /**
     * Get exact of the window<br>
     * Title's height not in window's height, so we need a method to get the exact window height.
     *
     * @return Exact height of the window
     */
    public int getHeightExact() {
        int[] width = new int[1], height = new int[1];
        GLFW.glfwGetWindowSize(this.id, width, height);
        return height[0];
    }

    @Override
    public Window setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getX() {
        int[] x = new int[1], y = new int[1];
        GLFW.glfwGetWindowPos(this.id, x, y);
        return x[0];
    }

    public Window setX(int x) {
        return this.setPosition(x, this.getY());
    }

    public Window setY(int y) {
        return this.setPosition(this.getX(), y);
    }

    public int getY() {
        int[] x = new int[1], y = new int[1];
        GLFW.glfwGetWindowPos(this.id, x, y);
        return y[0];
    }

    public Window setPosition(int x, int y) {
        if (GLFW.glfwGetWindowAttrib(this.id, GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE)
            y += 32;

        GLFW.glfwSetWindowPos(this.id, x, y);
        return this;
    }

    public boolean isClosed() {
        return this.closed;
    }
}