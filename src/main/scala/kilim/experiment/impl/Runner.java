package kilim.experiment.impl;


import java.util.ArrayList;

public class Runner {
    static Scheduler scheduler = new Scheduler();

    public static void main(String[] args) {
        // add tasks
        scheduler.add(new TestTask(1).fiber);
        scheduler.add(new TestTask(2).fiber);
        scheduler.add(new TestTask(3).fiber);
        scheduler.add(new TestTask(4).fiber);
        scheduler.run();
    }
}


class Scheduler {

    private ArrayList<Fiber> workQueue = new ArrayList<>(10);

    void add(Fiber fiber) {
        workQueue.add(fiber);
    }

    void run() {
        if (workQueue.isEmpty()) {
            System.out.println("end of execution");
        } else {
            Fiber currentFiber = workQueue.get(0);
            currentFiber.run();

        }
    }

    void blockCurrentTask() {
        Fiber currentFiber = workQueue.get(0);
        workQueue.remove(0);
        add(currentFiber);
        run();
    }

    public void endFiber() {
        workQueue.remove(0);
        run();
    }
}