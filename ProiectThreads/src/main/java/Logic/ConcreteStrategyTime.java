package Logic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server server = servers.getFirst();
        int minTime = server.getWaitingPeriod();
        for (Server s : servers) {
            if (s.getWaitingPeriod() < minTime) {
                server = s;
                minTime = s.getWaitingPeriod();
            }
        }
        t.setWaitingTime(minTime);
        server.addTask(t);
    }
}
