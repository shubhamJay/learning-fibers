package fiber.minimized.impl;

public class Runner {
    static Dispatcher dispatcher = new Dispatcher();

    public static void main(String[] args) throws InterruptedException {
        // add tasks

        dispatcher.dispatch(new TestTask(1).fiber);
        dispatcher.dispatch(new TestTask(2).fiber);
        dispatcher.dispatch(new TestTask(3).fiber);
        dispatcher.dispatch(new TestTask(4).fiber);

        Thread.sleep(10000);
    }
}