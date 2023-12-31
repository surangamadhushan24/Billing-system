import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;




class DashboardView extends JFrame {

    // sidebar buttons
    JButton home;
    JButton setting;
    JButton logout;

    //  menu category buttons
    JButton addOn;
    JButton lunch;
    JButton breakfast;
    JButton dessert;

    // panels
    JPanel leftSidePanel;
    JPanel rowPanel;
    JPanel menuCategory;
    JPanel menuItems;
    JPanel rightSidePanel;

    DefaultTableModel model;
    JTable table;

    // display total panel
    JTextField tfSubTotal;
    JTextField tfDiscount;
    JTextField tfTotal;
    JTextField tfCash;
    JTextField tfBalance;
    JButton btnPay;
    JButton btnPrint;
    JButton btnClear;
    JButton btnVoid;


    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/munch_hub";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Suranga1999@";

    public DashboardView() {
        super("Dashboard");
        setup();
    }

    public void setup() {
        // Create main panels
        JPanel header = new JPanel();
        JPanel sidebar = new JPanel();
        JPanel centerPanel = new JPanel();

        //sub panels of center panel
        leftSidePanel = new JPanel();
        rightSidePanel = new JPanel();
        rightSidePanel.setBackground(Color.cyan);


        //menu category & menu items panels
        menuCategory = new JPanel();
        menuItems = new JPanel();

        // Header title
        JLabel title = new JLabel("<html>NIBM Munch<font color='blue'>Hub</font></html>");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        header.add(title);

        //sidebar
        sidebar.setPreferredSize(new Dimension(80,500));
        sidebar.setBackground(Color.white);
        GridLayout sidebarGrid = new GridLayout(0,1);
        sidebarGrid.setVgap(200);
        sidebar.setLayout(sidebarGrid);
       ;


        setting = new JButton("Setting");
        setting.setPreferredSize(new Dimension(80,20));
        logout = new JButton("Exit");
        logout.setPreferredSize(new Dimension(80,20));


        sidebar.add(setting);
        sidebar.add(logout);

        // divide center panel
        centerPanel.setLayout(new GridLayout(1,2));
        //add sub panels to center panel
        centerPanel.add(leftSidePanel);
        centerPanel.add(rightSidePanel);

        //create BorderLayout(lefSidePanel)
        leftSidePanel.setLayout(new BorderLayout());
        leftSidePanel.add(menuCategory,BorderLayout.NORTH);
        leftSidePanel.add(menuItems,BorderLayout.CENTER);
        menuCategory.setBackground(Color.lightGray);

        //create border layout (right side)
        rightSidePanel.setLayout(new BorderLayout());

        // menu category buttons
        addOn = new JButton("Add On");
        breakfast = new JButton("Breakfast");
        lunch = new JButton("Lunch");
        dessert = new JButton("Dessert");

        menuCategory.add(addOn);
        menuCategory.add(breakfast);
        menuCategory.add(lunch);
        menuCategory.add(dessert);

        addOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loadMenuItemsFromDatabase("Add On");
            }
        });

        breakfast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loadMenuItemsFromDatabase("Breakfast");
            }
        });

        lunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenuItemsFromDatabase("Lunch");
            }
        });

        dessert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenuItemsFromDatabase("Dessert");
            }
        });

        //right side table
        model = new DefaultTableModel();
        model.addColumn("Item");
        model.addColumn("Qty");
        model.addColumn("price");

        table = new JTable(model);
        rightSidePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        table.setShowGrid(false);
        table.setRowHeight(30);
        table.setFont(new Font("Arial",Font.BOLD,18));

        // create right side panel bottom panel

        JPanel displayTotal = new JPanel();
        displayTotal.setLayout(new GridLayout(7,2,10,10));
        JLabel lblSubTotal = new JLabel("Subtotal");
        lblSubTotal.setFont(new Font("Arial",Font.BOLD,18));
        tfSubTotal = new JTextField(10);
        tfSubTotal.setFont(new Font("Arial",Font.BOLD,18));
        JLabel lblDiscount = new JLabel("Discount");
        lblDiscount.setFont(new Font("Arial",Font.BOLD,18));
        tfDiscount = new JTextField(10);
        tfDiscount.setFont(new Font("Arial",Font.BOLD,18));
        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial",Font.BOLD,18));
        tfTotal = new JTextField(10);
        tfTotal.setFont(new Font("Arial",Font.BOLD,18));
        JLabel lblCash = new JLabel("Cash");
        lblCash.setFont(new Font("Arial",Font.BOLD,18));
        tfCash = new JTextField(10);
        tfCash.setFont(new Font("Arial",Font.BOLD,18));
        JLabel lblBalance = new JLabel("Balance");
        lblBalance.setFont(new Font("Arial",Font.BOLD,18));
        tfBalance = new JTextField(10);
        tfBalance.setFont(new Font("Arial",Font.BOLD,18));
        btnPay = new JButton("Pay");
        btnPay.setFont(new Font("Arial",Font.BOLD,18));
        btnPay.setPreferredSize(new Dimension(100,40));
        btnPrint =  new JButton("Print");
        btnPrint.setFont(new Font("Arial",Font.BOLD,18));
        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial",Font.BOLD,18));
        btnVoid = new JButton("Void");
        btnVoid.setFont(new Font("Arial",Font.BOLD,18));

        displayTotal.add(lblSubTotal);
        displayTotal.add(tfSubTotal);
        displayTotal.add(lblDiscount);
        displayTotal.add(tfDiscount);
        displayTotal.add(lblTotal);
        displayTotal.add(tfTotal);
        displayTotal.add(lblCash);
        displayTotal.add(tfCash);
        displayTotal.add(lblBalance);
        displayTotal.add(tfBalance);
        displayTotal.add(btnPay);
        displayTotal.add(btnPrint);
        displayTotal.add(btnClear);
        displayTotal.add(btnVoid);

        tfTotal.setText("0");
        tfSubTotal.setText("0");
        tfDiscount.setText("0");
        tfCash.setText("0");
        tfBalance.setText("0");
        tfTotal.setEditable(false);
        tfSubTotal.setEditable(false);
        tfBalance.setEditable(false);

        rightSidePanel.add(displayTotal,BorderLayout.SOUTH);
        // display default panels
        loadMenuItemsFromDatabase("Breakfast");

        // Create BorderLayout (main window)
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(centerPanel,BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1540, 720);
        setLocationRelativeTo(null);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cash = Double.valueOf(tfCash.getText());

                double subtotal = Double.valueOf(tfSubTotal.getText());
                double discount = Double.valueOf(tfDiscount.getText());
                double total = subtotal - discount;
                String displayTotal = String.valueOf(total);
                tfTotal.setText(displayTotal);



                double balance = cash - total;
                String displayBalance = String.valueOf(balance);
                if(balance<0){
                    tfBalance.setText("0");
                }
                else {
                    tfBalance.setText(displayBalance);
                }
            }
        });

       btnPrint.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               printBill();
           }
       });
       btnClear.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               clear();
           }
       });
       btnVoid.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               voidItem();
           }
       });

       setting.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               SettingView settingView = new SettingView();
               settingView.setVisible(true);
               dispose();
           }
       });



        logout.addActionListener(e -> System.exit(0));

    }
    //load menu items from database
    public void loadMenuItemsFromDatabase(String category) {
        // Clear the rowPanel from the previous menu category
        if (rowPanel != null) {
            leftSidePanel.remove(rowPanel);
        }

        menuItems.removeAll();
        menuItems.revalidate();
        menuItems.repaint();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT item_name FROM menu_items WHERE item_category = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> menuItems = new ArrayList<>();
            while (resultSet.next()) {
                menuItems.add(resultSet.getString("item_name"));
            }
            // Create a new rowPanel
            rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            for (String itemName : menuItems) {
                JButton itemButton = new JButton(itemName);
                itemButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int quantity = getQuantityFromInput();
                        double price = getPriceFromDatabase(itemName);
                        double total = quantity * price;
                        addToTable(itemName, quantity, total);
                    }
                });

                itemButton.setPreferredSize(new Dimension(174, 34));
                rowPanel.add(itemButton);
                rowPanel.setBackground(Color.darkGray);
            }

            leftSidePanel.add(rowPanel, BorderLayout.CENTER);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        menuCategory.revalidate();
        menuCategory.repaint();
    }

    public void clear(){
        while (model.getRowCount()>0){
            model.removeRow(0);

        }
        tfTotal.setText("0");
        tfSubTotal.setText("0");
        tfDiscount.setText("0");
        tfCash.setText("0");
        tfBalance.setText("0");
    }

    public void voidItem(){
        if(table.getSelectedRow()!=-1) {
            model.removeRow(table.getSelectedRow());
            calSubTotal();
            if(table.getRowCount()==0){
                tfSubTotal.setText("0");
                tfTotal.setText("0");
                tfDiscount.setText("0");
                tfCash.setText("0");
                tfBalance.setText("0");
            }
        }


    }
    public int getQuantityFromInput(){
        String input = JOptionPane.showInputDialog("Enter quantity:",JOptionPane.INFORMATION_MESSAGE);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void addToTable(String itemName,int quantity,double total){
       Vector vector = new Vector();

        vector.add(itemName);
        vector.add(quantity);
        vector.add(total);

        model.addRow(vector);
        calSubTotal();
    }

    public void calSubTotal(){
        int numberOfRows = table.getRowCount();
        double tot = 0;
        for(int i = 0;i<numberOfRows;i++){
            tot+= Double.valueOf(table.getValueAt(i,2).toString());
            String subTotal = String.valueOf(tot);
            tfSubTotal.setText(subTotal);

        }
    }

    public double getPriceFromDatabase(String itemName){
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT price FROM menu_items WHERE item_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("price");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void printBill() {
        try {
            // Create a JTextArea and add the bill content
            JTextArea bill = new JTextArea();
            bill.setFont(new Font("Monospaced", Font.PLAIN, 12));
            bill.setEditable(false);
            bill.setLineWrap(true);

            bill.setText("                        NIBM MUNCHHUB \n");
            bill.setText(bill.getText() + "                 \t22B,Matara Rd, \n");
            bill.setText(bill.getText() + "                  \tGalle Srilanka, \n");
            bill.setText(bill.getText() + "                    \t+9412235673, \n");
            bill.setText(bill.getText() + "----------------------------------------------------------------\n");
            bill.setText(bill.getText() + " Item \tQty \tPrice \n");
            bill.setText(bill.getText() + "----------------------------------------------------------------\n");


            for (int i = 0; i < table.getRowCount(); i++) {
                String name = String.valueOf(table.getValueAt(i,0));
                String qty = String.valueOf(table.getValueAt(i,1));
                String price = String.valueOf(table.getValueAt(i,2));
                bill.setText(bill.getText() + name + "\t" + qty + "\t" + price + " \n");
            }

            bill.setText(bill.getText() + "----------------------------------------------------------------\n");
            bill.setText(bill.getText() + "SubTotal :\t"+tfSubTotal.getText()+"\n");
            bill.setText(bill.getText() + "Discount :\t"+tfDiscount.getText()+"\n");
            bill.setText(bill.getText() + "Total :\t"+tfTotal.getText()+"\n");
            bill.setText(bill.getText() + "Cash :\t"+tfCash.getText()+"\n");
            bill.setText(bill.getText() + "Balance :\t"+tfBalance.getText()+"\n");
            bill.setText(bill.getText() + "====================================\n");
            bill.setText(bill.getText() + "                     Thanks For Your Business...!" + "\n");
            bill.setText(bill.getText() + "----------------------------------------------------------------\n");

            // Print the bill directly
            boolean printed = bill.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Bill printed successfully.", "Print Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Printing failed.", "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }
}
