public class LogInMain {
    public static void main(String[] args) {
        LoginView view = new LoginView();
        LogInModel model= new LogInModel();
        LogInController controller = new LogInController(model,view);
        view.setVisible(true);

    }


}
