import me.xiaoying.window.Color;
import me.xiaoying.window.Window;
import me.xiaoying.window.component.Button;
import me.xiaoying.window.component.Div;
import me.xiaoying.window.event.Listener;

public class Main implements Listener {
    public static void main(String[] args) {
        Window window = (Window) new Window("Hello World").name("window").height("50vh");

        Div div = new Div("Hello");
        window.add(div);
        div.width("50vw")
                .height("50vh")
                .background()
                .color(Color.CHOCOLATES);
        div.active(() -> {
            div.background().color(Color.PINK);
            div.color(Color.YELLOW);
        }).hover(() -> {
            div.background().color(Color.BLUE);
            div.color(Color.WHITE);
        });

        Button button = new Button("Hello World");
        window.add(button);
        button.background().color(Color.RED);
        button.width("40vw");
        button.height("40vh");
        button.active(() -> {
            button.background().color(Color.BLACK);
        }).hover(() -> {
            button.background().color(Color.ORANGE);
        });

        Button b = new Button("Bad World");
//        div.add(b);
        window.add(b);
        b.width("30%");
        b.height("30%");
        b.hover(() -> {
            b.background().color(Color.VIOLET);
        });
        b.active(() -> {
            b.background().color(Color.YELLOW);
        });
//        System.out.println(div.width());
//        System.out.println(div.height());

//        Window window = (Window) new Window("Hello World").name("window").height("50vh");
//        window.getEventManager().registerEvents(new WindowListener());
//
//        Button button = new Button("asdjao你sdjpo");
//        window.add(button);
//        button.height("50%");
//        button.width("50%");
//        button.active(() -> {
//            System.out.println("Active");
//            button.text("你别按我啦＞︿＜");
//            button.background().color(Color.RED);
//            button.color(Color.BLUE);
//            window.title("阿巴阿巴阿巴");
//        }).hover(() -> {
//            System.out.println("Hovered");
//            button.background().color(Color.BLUE);
//            button.color(Color.WHITE);
//        });
    }
}