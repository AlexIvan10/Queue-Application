package Logic;

import Model.Server;
import Model.Task;

import java.util.*;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTaskPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTaskPerServer){
        this.maxNoServers = maxNoServers;
        this.maxTaskPerServer = maxTaskPerServer;
        servers = new ArrayList<>();

        for (int i = 0; i < this.maxNoServers; i++) {
            Server server = new Server();
            Thread thread = new Thread(server);
            servers.add(server);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers, t);
    }

    public List<Server> getServers(){
        return servers;
    }
}