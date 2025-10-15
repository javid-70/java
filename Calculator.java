import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    // Components
    private JTextField textField;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator() {
        // Frame setup
        setTitle("Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Text field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        add(textField, BorderLayout.NORTH);

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        // Button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "%", "←", "Exit"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            textField.setText(textField.getText() + command);
        } 
        else if (command.equals("C")) {
            textField.setText("");
            num1 = num2 = result = 0;
        } 
        else if (command.equals("←")) {
            String text = textField.getText();
            if (text.length() > 0)
                textField.setText(text.substring(0, text.length() - 1));
        } 
        else if (command.equals("Exit")) {
            System.exit(0);
        } 
        else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(textField.getText());
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/': result = num2 != 0 ? num1 / num2 : 0; break;
                    case '%': result = num1 % num2; break;
                }
                textField.setText(String.valueOf(result));
                num1 = result;
            } catch (Exception ex) {
                textField.setText("Error");
            }
        } 
        else {
            try {
                num1 = Double.parseDouble(textField.getText());
                operator = command.charAt(0);
                textField.setText("");
            } catch (Exception ex) {
                textField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
