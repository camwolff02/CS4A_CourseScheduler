package src.java.utilclasses;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SchedulerGUI implements ActionListener {
    private JFrame frame;
    private JLabel label;
    private JLabel clicks;
    private JButton button;
    private JPanel panel;
    private int count;

    public SchedulerGUI() { 
        this(30, 30, 10, 30);
    }
    
    public SchedulerGUI(int top, int left, int bottom, int right) {
        label = new JLabel("Enter Database name: ");
        clicks = new JLabel("number of clicks: " + count);
        button = new JButton("Load Database");
        button.addActionListener(this);
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        panel.setLayout(new GridLayout(0, 1));  
        panel.add(label);
        panel.add(button);
        panel.add(clicks);

        frame = new JFrame();
        frame.setSize(500_000, 100_000);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Course Scheduelr");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clicks.setText("Number of clicks: " + ++count);
    }
}
