package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame {
    private JFrame dataFrame = new JFrame("Queue Simulator");
    private JFrame simulationFrame = new JFrame("Queue Simulator");
    private JPanel panel = new JPanel();
    private JPanel fullPanel = new JPanel();
    private JPanel noOfClientsPanel = new JPanel();
    private JPanel noOfQueuesPanel = new JPanel();
    private JPanel simulationIntervalPanel = new JPanel();
    private JPanel minMaxArrivalTimePanel = new JPanel();
    private JPanel minMaxServiceTimePanel = new JPanel();
    private JPanel strategyPanel = new JPanel();
    private JPanel simulationPanel = new JPanel();
    private JPanel queuesPanel = new JPanel();
    private JButton startButton = new JButton("Start");
    private JButton exitButton = new JButton("Exit");
    private JLabel noOfClientsLabel = new JLabel("Number of clients");
    private JLabel noOfQueuesLabel = new JLabel("Number of queues");
    private JLabel simulationIntervalLabel = new JLabel("Simulation interval");
    private JLabel strategyLabel = new JLabel("Strategy");
    private JLabel minMaxArrivalTimeLabel = new JLabel("[Min - Max] Arrival Time");
    private JLabel minMaxServiceTimeLabel = new JLabel("[Min - Max] Service Time");
    private JLabel endSimulationMessage = new JLabel("");
    private JLabel[] queuesLabel;
    private JTextField noOfClientsTextField = new JTextField();
    private JTextField noOfQueuesTextField = new JTextField();
    private JTextField simulationIntervalTextField = new JTextField();
    private JTextField minArrivalTimeTextField = new JTextField();
    private JTextField maxArrivalTimeTextField = new JTextField();
    private JTextField minServiceTimeTextField = new JTextField();
    private JTextField maxServiceTimeTextField = new JTextField();
    String[] strategies = {"Shortest queue", "Shortest time"};
    private JComboBox strategyChoiseBox = new JComboBox(strategies);
    private Dimension smallerDimension = new Dimension(100, 30);
    private Dimension dimension = new Dimension(100, 30);
    private Font font = new JTextField().getFont();
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private int noOfQueues = 0;

    public SimulationFrame(){
        fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));

        noOfClientsPanel.setLayout(new BoxLayout(noOfClientsPanel, BoxLayout.Y_AXIS));
        addLabel(noOfClientsLabel, noOfClientsPanel);
        addTextField(noOfClientsTextField, noOfClientsPanel);

        noOfQueuesPanel.setLayout(new BoxLayout(noOfQueuesPanel, BoxLayout.Y_AXIS));
        addLabel(noOfQueuesLabel, noOfQueuesPanel);
        addTextField(noOfQueuesTextField, noOfQueuesPanel);

        simulationIntervalPanel.setLayout(new BoxLayout(simulationIntervalPanel, BoxLayout.Y_AXIS));
        addLabel(simulationIntervalLabel, simulationIntervalPanel);
        addTextField(simulationIntervalTextField, simulationIntervalPanel);

        minMaxArrivalTimePanel.setLayout(new BoxLayout(minMaxArrivalTimePanel, BoxLayout.Y_AXIS));
        addLabel(minMaxArrivalTimeLabel, minMaxArrivalTimePanel);
        JPanel arrivalTimeTextPanel = new JPanel();
        addSmallerTextField(minArrivalTimeTextField, arrivalTimeTextPanel);
        addSmallerTextField(maxArrivalTimeTextField, arrivalTimeTextPanel);
        minMaxArrivalTimePanel.add(arrivalTimeTextPanel);

        minMaxServiceTimePanel.setLayout(new BoxLayout(minMaxServiceTimePanel, BoxLayout.Y_AXIS));
        addLabel(minMaxServiceTimeLabel, minMaxServiceTimePanel);
        JPanel serviceTimeTextPanel = new JPanel();
        addSmallerTextField(minServiceTimeTextField, serviceTimeTextPanel);
        addSmallerTextField(maxServiceTimeTextField, serviceTimeTextPanel);
        minMaxServiceTimePanel.add(serviceTimeTextPanel);

        strategyPanel.setLayout(new BoxLayout(strategyPanel, BoxLayout.Y_AXIS));
        JPanel strategyAllignPanel = new JPanel();
        strategyAllignPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addLabel(strategyLabel, strategyAllignPanel);
        strategyPanel.add(strategyAllignPanel);
        addChoiseBox(strategyChoiseBox, strategyPanel);

        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(noOfClientsPanel);
        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(noOfQueuesPanel);
        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(simulationIntervalPanel);
        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(minMaxArrivalTimePanel);
        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(minMaxServiceTimePanel);
        fullPanel.add(Box.createVerticalStrut(10));
        fullPanel.add(strategyPanel);
        fullPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addButton(startButton, buttonPanel);
        fullPanel.add(buttonPanel);

        panel.add(fullPanel);

        Dimension displayDimension = Toolkit.getDefaultToolkit().getScreenSize();
        dataFrame.setSize(600, 570);
        int x = (displayDimension.width - dataFrame.getWidth()) / 5;
        int y = (displayDimension.height - dataFrame.getHeight()) / 5;
        dataFrame.setLocation(x, y);
        dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataFrame.setResizable(false);
        dataFrame.add(panel);
        dataFrame.setVisible(true);
    }

    public void changeToSimulationWindow(){
        simulationPanel.setLayout(new BorderLayout());
        queuesPanel.setLayout(new BoxLayout(queuesPanel, BoxLayout.Y_AXIS));

        noOfQueues = Integer.parseInt(noOfQueuesTextField.getText()) + 2;
        queuesLabel = new JLabel[noOfQueues];
        if(noOfQueues > 22)
            noOfQueues = 22;
        queuesLabel[0] = new JLabel("Time");
        queuesLabel[0].setFont(font.deriveFont(18.0f));
        queuesPanel.add(queuesLabel[0]);
        queuesLabel[1] = new JLabel("Waiting list");
        queuesLabel[1].setFont(font.deriveFont(18.0f));
        queuesPanel.add(queuesLabel[1]);
        for (int i = 2; i < noOfQueues; i++) {
            queuesLabel[i] = new JLabel();
            queuesLabel[i].setFont(font.deriveFont(18.0f));
            queuesLabel[i].setText("Queue " + (i - 1));
            queuesPanel.add(queuesLabel[i]);
        }

        simulationPanel.add(queuesPanel, BorderLayout.WEST);

        JPanel exitButtonPanel = new JPanel();
        exitButtonPanel.setLayout(new BoxLayout(exitButtonPanel, BoxLayout.Y_AXIS));
        exitButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        exitButton.addActionListener(e -> System.exit(0));
        addLabel(endSimulationMessage, exitButtonPanel);
        addButton(exitButton, exitButtonPanel);

        simulationPanel.add(exitButtonPanel, BorderLayout.SOUTH);

        Dimension displayDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (displayDimension.width - simulationFrame.getWidth()) / 5;
        int y = (displayDimension.height - simulationFrame.getHeight()) / 5;
        simulationFrame.setLocation(x, y);
        simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulationFrame.setResizable(false);
        simulationFrame.setSize(900, 700);
        simulationFrame.add(simulationPanel);
        simulationFrame.setVisible(true);
        dataFrame.setVisible(false);
    }

    public void updateSimulation(String time, String waitingList, String[] queues){
        queuesLabel[0].setText(time);
        queuesLabel[1].setText(waitingList);
        for (int i = 2; i < noOfQueues; i++) {
            queuesLabel[i].setText(queues[i - 2]);
        }
    }

    public void addButton(JButton button, JPanel panel) {
        button.setFont(new Font(button.getName(), Font.PLAIN, 20));
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        panel.add(button);
    }

    public void addLabel(JLabel label, JPanel panel) {
        label.setPreferredSize(dimension);
        label.setFont(font.deriveFont(18.0f));
        panel.add(label);
    }

    public void addChoiseBox(JComboBox comboBox, JPanel panel){
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font(font.getName(), font.getStyle(), 17));
        comboBox.setFocusable(false);
        comboBox.setBorder(border);
        panel.add(comboBox);
    }

    private void customizeTextField(JTextField textField, JPanel panel){
        textField.setFont(new Font(font.getName(), font.getStyle(), 20));
        textField.setBorder(border);
        panel.add(textField);
    }

    public void addTextField(JTextField textField, JPanel panel) {
        textField.setPreferredSize(dimension);
        customizeTextField(textField, panel);
    }

    public void addSmallerTextField(JTextField textField, JPanel panel) {
        textField.setPreferredSize(smallerDimension);
        customizeTextField(textField, panel);
    }

    public void addStartListener(ActionListener actionListener) {
        startButton.addActionListener(actionListener);
    }

    public int getNoOfClients() {
        return Integer.parseInt(noOfClientsTextField.getText());
    }

    public int getNoOfQueues() {
        return Integer.parseInt(noOfQueuesTextField.getText());
    }

    public int getSimulationInterval() {
        return Integer.parseInt(simulationIntervalTextField.getText());
    }

    public int getMinArrivalTime() {
        return Integer.parseInt(minArrivalTimeTextField.getText());
    }

    public int getMaxArrivalTime() {
        return Integer.parseInt(maxArrivalTimeTextField.getText());
    }

    public int getMinServiceTime() {
        return Integer.parseInt(minServiceTimeTextField.getText());
    }

    public int getMaxServiceTime() {
        return Integer.parseInt(maxServiceTimeTextField.getText());
    }
    public void endOfSimulationMessage(){
        endSimulationMessage.setText("Finish");
    }

    public int getStrategy() {
        String strategy = (String) strategyChoiseBox.getSelectedItem();
        if(strategy.equals("Shortest queue"))
            return 1;
        return 2;
    }
}
