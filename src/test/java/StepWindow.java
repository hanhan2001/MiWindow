import me.xiaoying.window.Window;

import java.util.concurrent.atomic.AtomicInteger;

public class StepWindow {
    public static void main(String[] args) {
        int screenWidth = 1920, screenHeight = 1080;

        Window window = new Window("TTT", 500, 200);

        window.setTitle("5555")
                .setX((screenWidth - window.getWidth()) / 2)
                .setY((screenHeight - window.getHeight()) / 2);

        window.setVisible(true);

        AtomicInteger step = new AtomicInteger();
        int[][] steps = {
                // 四角
                {0, 0},
                {0, screenHeight - window.getHeight()},
                {screenWidth - window.getWidth(), screenHeight - window.getHeight()},
                {screenWidth - window.getWidth(), 0},

                // 中心点
                {(screenWidth - window.getWidth()) / 2, (screenHeight - window.getHeight()) / 2},

                // 左上到中心中点
                {(screenWidth - window.getWidth()) / 4, (screenHeight - window.getHeight()) / 4},
                // 中心到右下
                {((screenWidth - window.getWidth()) / 2 + screenWidth - window.getWidth()) / 2, ((screenHeight - window.getHeight()) / 2 + screenHeight - window.getHeight()) / 2},
                // 中心到右上
                {((screenWidth - window.getWidth()) / 2 + screenWidth - window.getWidth()) / 2, (screenHeight  - window.getHeight()) / 4},
                // 中心点到左下
                {(screenWidth - window.getWidth()) / 4, (screenHeight - window.getHeight() + (screenHeight - window.getHeight()) / 2) / 2}
        };

        window.onUpdate(() -> {
            int x = steps[step.get()][0], y = steps[step.get()][1];

            window.setPosition(x, y);

            step.getAndIncrement();
            if (step.get() >= steps.length)
                step.set(0);
        });
    }
}