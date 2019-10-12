package kilim.experiment.impl;

class TestTask {
    Fiber fiber;

    public TestTask(Integer taskNumber) {
        this.fiber = new Fiber(this, "fiber of Task: " + taskNumber);
    }

    // Should fiber be injected or only PC.
    public void run(Integer pc) {

        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                // what about state?
                // try using continuation and  state machines to resume.
                //rename to dispatcher

                fiber.suspend(); // could be sleep too
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                fiber.suspend();
            case 3:
                System.out.println("blocking - 3 | " + fiber.name);
                fiber.suspend();
            case 4:
                System.out.println("blocking - 4 | " + fiber.name);
                fiber.suspend();
            case 5:
                System.out.println("blocking - 5 | " + fiber.name);
                fiber.suspend();
            default:
                System.out.println("end of it" + fiber.name);
                fiber.end();
        }
    }
}
