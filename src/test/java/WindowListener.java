import me.xiaoying.window.event.EventHandler;
import me.xiaoying.window.event.Listener;
import me.xiaoying.window.event.window.WindowCloseEvent;
import me.xiaoying.window.event.window.WindowResizedEvent;

public class WindowListener implements Listener {
    @EventHandler
    public void onCloseWindow(WindowCloseEvent event) {
        System.out.println(122518201);
        System.out.println(123455);
    }

    @EventHandler
    public void resizedWindow(WindowResizedEvent event) {
        System.out.println("你好");
    }
}