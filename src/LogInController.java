import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInController {
    LogInModel model;
    LoginView view;

    public LogInController(LogInModel model, LoginView view) {
        this.model = model;
        this.view = view;
        this.view.addLogInListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    String username = view.getUserName();
                    String password = view.getPassword();

                    boolean loginSuccessful=model.validate(username,password);
                    if(loginSuccessful){
                        DashboardView dashboardView = new DashboardView();
                        dashboardView.setVisible(true);
                        view.setVisible(false);

                    }
                    else {
                        view.displayErrorMessage("Invalid username or password");
                    }


                }
                catch (Exception ex){
                    view.displayErrorMessage(ex.getMessage());

                }

            }
        });
    }
}

