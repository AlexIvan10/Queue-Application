package Logic;

import Model.Server;
import Model.Task;

import java.util.*;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}

enum SelectionPolicy {
    SHORTEST_QUEUE, SHORTEST_TIME
}
