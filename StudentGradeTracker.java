import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeTracker extends JFrame implements ActionListener {
    private JTextField nameField, subjectsField;
    private JTextArea displayArea;
    private JButton setSubjectsButton, calculateButton;
    private JPanel subjectsPanel, inputPanel, displayPanel;
    private JTextField[] marksFields;
    private int numSubjects;

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel subjectsLabel = new JLabel("Number of Subjects:");
        subjectsField = new JTextField(5);

        setSubjectsButton = new JButton("Set Subjects");
        calculateButton = new JButton("Calculate Average and Grade");
        calculateButton.setEnabled(false);

        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new GridLayout(0, 2, 1, 1));
        
        displayArea = new JTextArea(10, 20);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(subjectsLabel);
        inputPanel.add(subjectsField);
        inputPanel.add(setSubjectsButton);
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);
        add(subjectsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setSubjectsButton.addActionListener(this);
        calculateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setSubjectsButton) {
            try {
                numSubjects = Integer.parseInt(subjectsField.getText());
                subjectsPanel.removeAll();
                marksFields = new JTextField[numSubjects];
                
                for (int i = 0; i < numSubjects; i++) {
                    subjectsPanel.add(new JLabel("Subject " + (i + 1) + ":"));
                    subjectsPanel.setFont(new Font("Serif",Font.BOLD,10));

                    marksFields[i] = new JTextField(5);
                    
                    subjectsPanel.add(marksFields[i]);
                }
                subjectsPanel.revalidate();
                subjectsPanel.repaint();
                calculateButton.setEnabled(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of subjects.");
            }
        } else if (e.getSource() == calculateButton) {
            String name = nameField.getText();
            try {
                double total = 0;
                for (JTextField field : marksFields) {
                    total += Double.parseDouble(field.getText());
                }
                double average = total / numSubjects;
                String grade = calculateGrade(average);
                displayArea.append(name + " - Total Marks Obtained: " + total + "\n");
                displayArea.append(name + " - Average: " + average + " - Grade: " + grade + "\n");
                displayArea.setFont(new Font("Serif",Font.BOLD,20));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for all marks.");
            }
        }
    }

    private String calculateGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTracker().setVisible(true));
    }
}
