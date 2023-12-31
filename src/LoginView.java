import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    JTextField tfUserName;
    JPasswordField pfPassword;
    JButton btnLogIn;

    public LoginView() {
        super("LogIn");
        setup();

    }

    public void setup() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 500);
        setResizable(false);
        setLocationRelativeTo(null);


        setLayout(new GridLayout(1, 2));


        JPanel outerPanel1 = new JPanel(new BorderLayout());
        outerPanel1.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));


        JPanel panel1 = new JPanel();
        ImageIcon imageIcon = new ImageIcon(LoginView.class.getResource("/pic3.jpg"));
        JLabel imageLabel = new JLabel(imageIcon);
        panel1.setSize(600, 450);
        panel1.add(imageLabel);


        outerPanel1.add(panel1, BorderLayout.CENTER);
        add(outerPanel1);


        JPanel outerPanel2 = new JPanel(new BorderLayout());
        outerPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);

        JLabel title = new JLabel("<html>NIBM Munch<font color='blue'>Hub</font></html>");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 23, 0);

        panel2.add(title, gbc);
        panel2.add(Box.createVerticalStrut(30));

        JLabel lblUserName = new JLabel("Enter Username");
        lblUserName.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;

        panel2.add(lblUserName, gbc);

        tfUserName = new JTextField(25);
        tfUserName.setFont(new Font("Arial", Font.BOLD, 15));
        tfUserName.setForeground(Color.gray);

        tfUserName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        gbc.gridx = 0;
        gbc.gridy = 2;
        tfUserName.setPreferredSize(new Dimension(400, 20)); // Set the preferred size
        panel2.add(tfUserName, gbc);

        JLabel lblPassword = new JLabel("Enter password");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;

        panel2.add(lblPassword, gbc);

        pfPassword = new JPasswordField(25);
        pfPassword.setFont(new Font("Arial", Font.BOLD, 15));
        pfPassword.setForeground(Color.gray);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pfPassword.setPreferredSize(new Dimension(400, 20)); // Set the preferred size
        pfPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        panel2.add(pfPassword, gbc);

        btnLogIn = new JButton("Log In");
        btnLogIn.setForeground(Color.WHITE);
        btnLogIn.setSize(50, 10);
        btnLogIn.setFont(new Font("Arial", Font.BOLD, 15));
        btnLogIn.setFocusable(false);
        btnLogIn.setBackground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel2.add(btnLogIn, gbc);
        panel2.setBackground(Color.WHITE);
        outerPanel2.setBackground(Color.WHITE);
        outerPanel1.setBackground(Color.WHITE);


        outerPanel2.add(panel2, BorderLayout.CENTER);
        add(outerPanel2);

        setVisible(true);
    }
    public String getUserName(){
        return tfUserName.getText();
    }
    public  String getPassword(){
        return pfPassword.getText();
    }

    public void addLogInListener(ActionListener listenerForLogInButton){
        btnLogIn.addActionListener(listenerForLogInButton);
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this,errorMessage);
    }

}
