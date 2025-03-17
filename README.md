# Queue Simulation Application
This is a queue management simulation application designed to allocate clients to queues in a way that minimizes waiting times. The simulation allows users to input various parameters such as the number of clients, number of queues, arrival times, and service times. The program runs the simulation and displays the real-time progress of tasks in queues, along with the average waiting time.

## Table of Contents
- **Features**
- **Technologies Used**
- **Usage**
- **Architecture**
- **Use Cases**
- **New Features**

---

## Features
- Graphical User Interface (GUI): An easy-to-use interface for interacting with the simulation.
- Users can input data
- Real-time Updates: The status of queues and the list of waiting tasks are updated in real time.
- Simulation Result Logging: Progress and the average waiting time are saved in a text file.
- Multiple Strategies: The simulation supports two strategies for task allocation:
  - Shortest Queue: Allocates tasks to the server with the shortest queue.
  - Shortest Service Time: Allocates tasks to the server with the shortest processing time.

---

## Technologies Used
- Java
- Swing for the GUI

---

## Usage
- After running the application, you will be prompted to input the following parameters:
  - Number of clients (N)
  - Number of queues (Q)
  - Maximum simulation time
  - Minimum and maximum arrival times
  - Minimum and maximum service times
  - Strategy for task allocation: Choose between Shortest Queue or Shortest Service Time.
- Once all inputs are provided, click "Start" to begin the simulation.
- You can monitor the real-time status of the queues and tasks.
- A log of the simulation’s progress and the average waiting time will be saved in a .txt file.

---

## Architecture
- Main Classes
  - SimulationFrame: Handles the graphical interface and updates the simulation in real time.
  - ConcreteStrategyQueue: Implements the "Shortest Queue" strategy for allocating tasks to servers.
  - ConcreteStrategyTime: Implements the "Shortest Service Time" strategy for allocating tasks to servers.
  - Scheduler: Manages server creation and task dispatching based on the selected strategy.
  - SimulationManager: Handles the simulation process, task generation, and real-time updates of the simulation.
  - Server: Represents a server that processes tasks. Each server can add tasks and run simulations at regular intervals.
  - Task: Defines the attributes of a task, including arrival time and service time.
- Interfaces
  - Strategy: The interface that defines methods for task allocation strategies.

---

## Future Improvements
- Replace the "Exit" button with a "Back" button to allow creating new simulations without restarting the application.

---
