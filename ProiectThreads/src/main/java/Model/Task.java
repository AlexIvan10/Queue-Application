package Model;

public class Task implements Comparable<Task>{
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int fullServiceTime;
    private int waitingTime;

    public Task(int arrivalTime, int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        fullServiceTime = serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getFullServiceTime(){
        return fullServiceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Task t) {
        if(arrivalTime < t.getArrivalTime())
            return -1;
        else if(arrivalTime > t.getArrivalTime())
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "(" + id + " " + arrivalTime + " " + serviceTime + ") ";
    }
}