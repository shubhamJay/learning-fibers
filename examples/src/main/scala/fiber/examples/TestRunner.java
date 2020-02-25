package fiber.examples;

public class TestRunner {
    static Dispatcher dispatcher = Dispatcher.getCurrentDispatcher();

    public static void main(String[] args) throws InterruptedException {
        // add tasks

        dispatcher.dispatch(new TestTask(1).fiber);
        dispatcher.dispatch(new TestTask(2).fiber);
        dispatcher.dispatch(new TestTask(3).fiber);
        dispatcher.dispatch(new TestTask(4).fiber);

        Thread.sleep(10000);
    }
}