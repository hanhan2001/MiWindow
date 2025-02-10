import me.xiaoying.window.Window;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static void main(String[] args) {
        int screenWidth = 1920, screenHeight = 1080;

        Window window = new Window("TTT", 500, 200);

        window.setTitle("5555")
                .setX((screenWidth - window.getWidth()) / 2)
                .setY((screenHeight - window.getHeight()) / 2);

        window.setVisible(true);
        while (!window.isClosed())
            System.out.println(GLFW.glfwGetWindowAttrib(window.getId(), GLFW.GLFW_REFRESH_RATE));
    }
}