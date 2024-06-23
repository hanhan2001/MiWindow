import me.xiaoying.window.Window;
import me.xiaoying.window.component.Button;
import me.xiaoying.window.event.Listener;

public class Main implements Listener {
    public static void main(String[] args) {
        Window window = (Window) new Window("Hello World").height("50vh");
        window.getEventManager().registerEvents(new WindowListener());
        System.out.println();

        Button button = new Button();
        window.add(button);
        button.width(200);
        button.height("50px");

//        new Thread(() -> {
//            int time = 0;
//            while (time <= 300) {
//                try {
//                    Robot robot = new Robot();
//                    robot.delay(500);
//                } catch (AWTException e) {
//                    throw new RuntimeException(e);
//                }
//                time++;
//
//                window.height(window.height() - 10);
//            }
//        }).start();
    }
}