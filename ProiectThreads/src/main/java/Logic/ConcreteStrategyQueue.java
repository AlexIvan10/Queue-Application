package Logic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t){
        Server server = servers.getFirst();
        int minSize = server.getTasks().length;
        for(Server s : servers){
            if(s.getTasks().length < minSize){
                server = s;
                minSize = s.getTasks().length;
            }
        }
        t.setWaitingTime(server.getWaitingPeriod());
        server.addTask(t);
    }
}
