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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationPageController implements Initializable {

    DatabaseHandler databasehandler;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtMiddleName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private DatePicker txtDateofBirth;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private TextField txtEmailAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = new DatabaseHandler();
        checkData();
    }
    
    /**
     * 
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @FXML
    private void saveButtonAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String fName = txtFirstName.getText();
        String mName = txtMiddleName.getText();
        String lName = txtLastName.getText();
        String pNumber = txtPhoneNumber.getText();
        String eAddress = txtEmailAddress.getText();
        String dateofBirth = txtDateofBirth.getEditor().getText();
        String pass = txtPassword.getText();
        String cPass = txtConfirmPassword.getText();
        String accountName = fName + lName + dateofBirth.substring(dateofBirth.length()-2);
        Boolean active = false;

        if (fName.isEmpty() || lName.isEmpty()
                || pNumber.isEmpty() || eAddress.isEmpty() || dateofBirth.isEmpty() || cPass.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all fields");
            alert.showAndWait();
            return;
        }if(txtPhoneNumber.getText().length() != 10){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be of 10 digits.");
            alert.showAndWait();
            return;
        }
        if(!txtEmailAddress.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Input correct Email Address!");
            alert.showAndWait();
            return;
        }
        if (pass.length() < 8 || pass.length() > 16 ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Password must be between 8 to 16 digit match!");
            alert.showAndWait();
            return;
        }
        if (!pass.equals(cPass)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Password must equal to Confirm Password");
            alert.showAndWait();
            return;
        }
        String qu = "INSERT INTO USERS(fname,mname,lname,"
                + "phonenumber,email,date,accountName,pass,"
                + "cpass,active) VALUES ("
                + "'" + fName + "',"
                + "'" + mName + "',"
                + "'" + lName + "',"
                + "'" + pNumber + "',"
                + "'" + eAddress + "',"
                + "'" + dateofBirth + "',"
                + "'" + accountName + "',"
                + "'" + pass + "',"
                + "'" + cPass + "',"
                + "'" + active + "'"
                + ")";
        System.out.println(qu);
        if (databasehandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User Data has been Saved, Your UserName is :"+accountName+". Please Log In");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Your data has not been saved. Please Try Again.");
            alert.showAndWait();
        }
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
    
    
    private void checkData() {
        String qu = "SELECT * FROM USERS";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("id");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationPageController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(RegistrationPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
