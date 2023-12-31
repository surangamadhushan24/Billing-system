

import javax.swing.*;

public class LogInModel extends JFrame {


    public boolean validate(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            return  true;
        }
        else {
            return  false;

        }
    }
}