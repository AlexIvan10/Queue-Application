package Logic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class SimulationManager implements Runnable {
    public int timeLimit = 15;
    public int minArrivalTime = 2;
    public int maxArrivalTime = 8;
    public int maxProcessingTime = 4;
    public int minProcessingTime = 2;
    public int numberOfServers = 2;
    public int numberOfClients = 6;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    private volatile boolean stop = true;
    private int averageWaitingTime = 0;
    private int averageServiceTime = 0;
    private int peekHour = 0;
    private int maxNoOfClients = 0;
    private int tasksCompleted = 0;

    public SimulationManager() {
        generatedTasks = new ArrayList<>();
        frame = new SimulationFrame();

        frame.addStartListener(new StartListener());
    }

    private void generateNRandomTasks() {
        Random random = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            Task task = new Task(arrivalTime, serviceTime);
            generatedTasks.add(task);
        }
        Collections.sort(generatedTasks);
        int id = 1;
        for (Task task : generatedTasks) {
            task.setId(id++);
        }
    }

    public boolean existsTasks() {
        boolean generatedTasksStatus = false;
        boolean serversTasksStatus = false;
        if (generatedTasks != null) {
            if (!generatedTasks.isEmpty())
                generatedTasksStatus = true;
            List<Server> servers = scheduler.getServers();
            for (Server server : servers) {
                if (server.getWaitingPeriod() != 0)
                    serversTasksStatus = true;
            }
        }
        return generatedTasksStatus || serversTasksStatus;
    }

    public void updateData() {
        timeLimit = frame.getSimulationInterval();
        minArrivalTime = frame.getMinArrivalTime();
        maxArrivalTime = frame.getMaxArrivalTime();
        maxProcessingTime = frame.getMaxServiceTime();
        minProcessingTime = frame.getMinServiceTime();
        numberOfServers = frame.getNoOfQueues();
        numberOfClients = frame.getNoOfClients();
        if (frame.getStrategy() == 1)
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
        else
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;

        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks();
    }

    public void verifyPeekHour(int currentTime) {
        int noOfClients = 0;
        for (Server server : scheduler.getServers()) {
            noOfClients += server.getTasks().length;
        }
        if (maxNoOfClients < noOfClients) {
            peekHour = currentTime - 1;
            maxNoOfClients = noOfClients;
        }
    }

    public void processDataInServers(int currentTime) {
        for (Server server : scheduler.getServers()) {
            Task[] tasks = server.getTasks();
            if (tasks != null && tasks.length > 0) {
                Task task = tasks[0];
                if (task != null && task.getArrivalTime() != currentTime) {
                    if (task.getServiceTime() == 1 && currentTime < timeLimit) {
                        tasksCompleted ++;
                        averageServiceTime += task.getFullServiceTime();
                    }
                    server.setProcessDataFalse();
                }
            }
        }
    }

    public void verifyServersStatus() {
        boolean serversProcessDataStatus = true;
        while (serversProcessDataStatus) {
            serversProcessDataStatus = false;
            for (Server server : scheduler.getServers()) {
                if (!server.getProcessData())
                    serversProcessDataStatus = true;
            }
        }
    }

    @Override
    public void run() {
        int currentTime = 0;

        while (stop) {
            Thread.onSpinWait();
        }

        updateData();

        String filePath = "log.txt";
        try {
            FileWriter writer = new FileWriter(filePath);

            while (currentTime < timeLimit) {
                if (!existsTasks())
                    break;

                verifyPeekHour(currentTime);

                Iterator<Task> iterator = generatedTasks.iterator();
                while (iterator.hasNext()) {
                    Task task = iterator.next();
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(task);
                        averageWaitingTime += task.getWaitingTime();
                        iterator.remove();
                    }
                }

                printServers(writer, currentTime);
                currentTime++;

                processDataInServers(currentTime);

                verifyServersStatus();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

            if (!existsTasks() && currentTime < timeLimit) {
                printServers(writer, currentTime);
            }

            double awt = averageWaitingTime / (double) numberOfClients;
            String formattedAwt = String.format("%.2f", awt);
            writer.write("Average waiting time : " + formattedAwt + "\n");
            System.out.println("Average waiting time : " + formattedAwt);

            double ast = averageServiceTime / (double) tasksCompleted;
            String formattedAst = String.format("%.2f", ast);
            writer.write("Average service time : " + formattedAst + "\n");
            System.out.println("Average service time : " + formattedAst);

            writer.write("Peek hour: " + peekHour + '\n');
            System.out.println("Peek hour: " + peekHour);

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing in txt file");
        }
        frame.endOfSimulationMessage();
    }

    public void printServers(FileWriter writer, int currentTime) {
        try {
            writer.write("Time " + currentTime + "\n");
            writer.write("Waiting list: ");
            String time = "Time " + currentTime;
            String waitingList = "Waiting list: ";
            String[] queuesList = new String[numberOfServers];

            System.out.println("Time " + currentTime);
            System.out.print("Waiting list: ");

            if (generatedTasks.isEmpty()) {
                writer.write("empty\n");
                waitingList += "empty";
                System.out.println("empty");
            } else {
                for (Task task : generatedTasks) {
                    writer.write(String.valueOf(task));
                    waitingList += task;
                    System.out.print(task);
                }
                writer.write("\n");
                System.out.println();
            }

            List<Server> servers = scheduler.getServers();
            int serverNo = 1;
            for (Server server : servers) {
                writer.write("Queue " + serverNo + ": ");
                queuesList[serverNo - 1] = "Queue " + serverNo + " ";
                System.out.print("Queue " + serverNo + ": ");
                List<Task> tasks = List.of(server.getTasks());
                if (tasks.isEmpty()) {
                    writer.write("closed\n");
                    queuesList[serverNo - 1] += "closed";
                    System.out.println("closed" + " " + server.getWaitingPeriod());
                } else {
                    for (Task task : tasks) {
                        writer.write(String.valueOf(task));
                        queuesList[serverNo - 1] += task;
                        System.out.print(task);
                    }
                    writer.write("\n");
                    System.out.println();
                }
                serverNo++;
            }
            writer.write("\n");
            frame.updateSimulation(time, waitingList, queuesList);
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error writing in txt file");
        }
    }

    public void startThread() {
        stop = false;
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }

    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int minArrivalTime = frame.getMinArrivalTime();
            int maxArrivalTime = frame.getMaxArrivalTime();
            int minServiceTime = frame.getMinServiceTime();
            int maxServiceTime = frame.getMaxServiceTime();
            if (minArrivalTime <= maxArrivalTime && minServiceTime <= maxServiceTime) {
                frame.changeToSimulationWindow();
                startThread();
            }
        }
    }
}