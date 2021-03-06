package fiber.core.test;

import fiber.core.Fiber;
import fiber.core.Task;

import java.io.IOException;

class TestTask implements Task {
    Fiber fiber;

    TestTask(Integer taskNumber) {
        this.fiber = new Fiber(this, "fiber of Task: " + taskNumber);
    }

    @Override
    public Fiber getFiber() {
        return fiber;
    }
    
    // Should fiber be injected or only PC.
    public void run(int pc) {

        System.out.println(Thread.currentThread().getName() + " ==== " + pc);

        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                fiber.sleep(1000);// could be sleep too
                return;
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                fiber.sleep(1000);
                return;
            case 3:
                System.out.println("blocking - 3 | " + fiber.name);
                fiber.sleep(1000);
                return;
            case 4:
                System.out.println("blocking - 4 | " + fiber.name);
                fiber.sleep(1000);
                return;
            case 5:
                System.out.println("blocking - 5 | " + fiber.name);
                fiber.sleep(1000);
                return;
            default:
                System.out.println("end of it" + fiber.name);
                return;
        }
    }
}