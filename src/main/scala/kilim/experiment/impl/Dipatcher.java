package kilim.experiment.impl;

import java.util.ArrayList;

class Dispatcher {

    private ArrayList<Fiber> workQueue = new ArrayList<>(10);

    void dispatch(Fiber fiber) {
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
        dispatch(currentFiber);
        run();
    }

    public void endFiber() {
        workQueue.remove(0);
        run();
    }
}