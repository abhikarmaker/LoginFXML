package assignment4;

import assignment4.database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WelcomePageController implements Initializable {

    DatabaseHandler databasehandler;

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnEditProfile;
    @FXML
    private TextField txtAccountName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       databasehandler = new DatabaseHandler();
    }
    
    /**
     * 
     * @param event 
     */
    @FXML
    private void logoutButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }
    
    /**
     * 
     * @param event
     * @throws ClassNotFoundException 
     */
    @FXML
    private void editProfileButtonAction(ActionEvent event) throws ClassNotFoundException{
        //loadWindow("/assignment4/UserProfile.fxml", "User Profile Page");
        String qu = "SELECT * FROM USERS where accountName = '"+txtAccountName.getText()+"'";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            if (rs.next()) {
                String fname = rs.getString("fname");
                String mname = rs.getString("mname");
                String lname = rs.getString("lname");
                String phone = rs.getString("phonenumber");
                String email = rs.getString("email");
                String accountName = (String) rs.getString("accountName");
                String date = rs.getString("date");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/assignment4/UserProfile.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                UserProfileController display = loader.getController();
                display.setText(accountName, fname, mname, lname, phone, email, date);

                Parent parent = loader.getRoot();
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("User Profile Page");
                stage.setScene(new Scene(parent));
                stage.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param accountName 
     */
    public void setText(String accountName) {
        this.txtAccountName.setText(accountName);
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
