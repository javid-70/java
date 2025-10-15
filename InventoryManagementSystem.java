import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class InventoryManagementSystem extends JFrame implements ActionListener {
    private JTextField nameField, quantityField, priceField;
    private JButton addButton, updateButton, deleteButton, saveButton, loadButton;
    private JTable table;
    private DefaultTableModel model;

    private static final String FILE_NAME = "inventory.csv";

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        nameField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"Product Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addProduct();
        } else if (e.getSource() == updateButton) {
            updateProduct();
        } else if (e.getSource() == deleteButton) {
            deleteProduct();
        } else if (e.getSource() == saveButton) {
            saveToFile();
        } else if (e.getSource() == loadButton) {
            loadFromFile();
        }
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String qtyText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyText);
            double price = Double.parseDouble(priceText);
            model.addRow(new Object[]{name, quantity, price});
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be numeric!");
        }
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update!");
            return;
        }

        String name = nameField.getText().trim();
        String qtyText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyText);
            double price = Double.parseDouble(priceText);
            model.setValueAt(name, row, 0);
            model.setValueAt(quantity, row, 1);
            model.setValueAt(price, row, 2);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be numeric!");
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete!");
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(model.getValueAt(i, 0) + "," +
                        model.getValueAt(i, 1) + "," +
                        model.getValueAt(i, 2));
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data saved to " + FILE_NAME);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    private void loadFromFile() {
        model.setRowCount(0);
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
            JOptionPane.showMessageDialog(this, "Data loaded from " + FILE_NAME);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No file found or error loading!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementSystem::new);
    }
}
