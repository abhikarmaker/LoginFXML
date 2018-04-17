package assignment4;

import assignment4.database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    DatabaseHandler databasehandler;
    PreparedStatement pst;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = new DatabaseHandler();
    }
    
    /**
     * 
     * @param event
     * @throws ClassNotFoundException 
     */
    @FXML
    private void loginButtonAction(ActionEvent event) throws ClassNotFoundException {
        //databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM USERS where accountName = '" + txtUsername.getText() + "' and pass = '" + txtPassword.getText() + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            if (rs.next()) {
                String id = rs.getString("id");
                String accountName = (String) rs.getString("accountName");
                //System.out.println(accountName);
                String pass = rs.getString("pass");
                Boolean active = rs.getBoolean("active");
                //System.out.println(id);
                if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Username or Password Field Cannot be Empty.");
                    alert.showAndWait();
                } else if (txtUsername.getText().equals(accountName) && txtPassword.getText().equals(pass) && active == true) {
                    //loadWindow("/assignment4/WelcomePage.fxml", "Welcome Page");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/assignment4/WelcomePage.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    WelcomePageController display = loader.getController();
                    display.setText(accountName);

                    Parent parent = loader.getRoot();
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setTitle("Welcome Page");
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();
                } else if (active == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("User is Not Active. Contact Admin.");
                    alert.showAndWait();
                }
            } else if (txtUsername.getText().equals("admin") && txtPassword.getText().equals("admin")) {
                loadWindow("/assignment4/AdminPage.fxml", "Admin Page");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("User/Admin Login Failed. Wrong Username or Password.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param event 
     */
    @FXML
    private void signupButtonAction(ActionEvent event) {
        loadWindow("/assignment4/RegistrationPage.fxml", "Registration Page");
    }
    
    /**
     * 
     * @param event 
     */
    @FXML
    private void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    
    /**
     * 
     * @param location
     * @param title 
     */
    void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
