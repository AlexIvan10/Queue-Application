package Model;

import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private volatile boolean processData = true;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public void run() {
        while (true) {
            Task task = tasks.peek();
            if (task != null) {
                while(processData){
                    Thread.onSpinWait();
                }

                if (task.getServiceTime() > 1) {
                    task.setServiceTime(task.getServiceTime() - 1);
                } else {
                    tasks.poll();
                }
                waitingPeriod.decrementAndGet();
                processData = true;
            }
        }
    }

    public boolean getProcessData(){
        return processData;
    }

    public void setProcessDataFalse(){
        processData = false;
    }

    public int getWaitingPeriod(){
        return waitingPeriod.get();
    }

    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }
}