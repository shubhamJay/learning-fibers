package kilim.experiment.impl;

class Fiber {

    private TestTask task;
    Integer pc = 1;
    String name;

    public Fiber(TestTask task, String name) {
        this.task = task;
        this.name = name;
    }

    public void run() {
        task.run(pc);
    }

    public void suspend() {
        ++pc;
        Runner.scheduler.blockCurrentTask();
    }

    public void end() {
        Runner.scheduler.endFiber();
    }
}
