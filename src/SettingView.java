import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SettingView extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/munch_hub";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Suranga1999@";

    JButton btnViewTable;
    JButton btnBack;
    JButton btnAdd;
    JButton btnDelete;
    JButton btnUpdate;
    JTextField tfItemName;
    JTextField tfId;
    JTextField tfPrice;
    JTextField tfCategory;
    JTable menuTable;
    DefaultTableModel tableModel;
    String[] columnNames = {"Item_id", "Item_name", "Item_category", "price"};

    public SettingView() {
        super("Setting");
        setup();
    }

    public void setup() {
        btnViewTable = new JButton("Display Table");
        btnBack = new JButton("Back");

        btnViewTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTable();
            }
        });
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(6,2,5,5));
        JLabel lblId = new JLabel("Enter Id");
        JLabel lblName = new JLabel("Enter Name");
        JLabel lblPrice = new JLabel("Enter Price");
        JLabel lblCategory = new JLabel("Enter Category");
        tfId = new JTextField(8);
        tfItemName = new JTextField(8);
        tfPrice = new JTextField(8);
        tfCategory = new JTextField(8);
        btnAdd = new JButton("Add");
        btnDelete= new JButton("Delete");
        btnUpdate = new JButton("Update Price");

        southPanel.add(lblId);
        southPanel.add(tfId);
        southPanel.add(lblName);
        southPanel.add(tfItemName);
        southPanel.add(lblPrice);
        southPanel.add(tfPrice);
        southPanel.add(lblCategory);
        southPanel.add(tfCategory);
        southPanel.add(btnAdd);
        southPanel.add(btnDelete);
        southPanel.add(btnUpdate);
        southPanel.add(btnBack);



        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(btnViewTable, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addEventListeners();
    }

    private void addEventListeners() {
        btnViewTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTable();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = tfItemName.getText();
                double price = Double.parseDouble(tfPrice.getText());
                String category = tfCategory.getText();
                addItemToDatabase(itemName,price,category);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemId = Integer.parseInt(tfId.getText());
                deleteItemFromDatabase(itemId);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemId = Integer.parseInt(tfId.getText());
                double newPrice = Double.parseDouble(tfPrice.getText());
                updateItemPriceInDatabase(itemId, newPrice);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardView view = new DashboardView();
                view.setVisible(true);
                dispose();
            }
        });
    }

    private void addItemToDatabase(String itemName, double price,String category) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO menu_items (Item_name,Item_category, price) VALUES (?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                displayTable();
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteItemFromDatabase(int itemId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "DELETE FROM menu_items WHERE Item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {

                displayTable();
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateItemPriceInDatabase(int itemId, double newPrice) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "UPDATE menu_items SET price = ? WHERE Item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, itemId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {

                displayTable();
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void displayTable() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT Item_id, Item_name, item_category, price FROM menu_items";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            tableModel.setRowCount(0);

            // Populate the table with the result set
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getInt("Item_id");
                row[1] = resultSet.getString("Item_name");
                row[2] = resultSet.getString("item_category");
                row[3] = resultSet.getDouble("price");
                tableModel.addRow(row);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
