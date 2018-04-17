package assignment4;

import assignment4.database.DatabaseHandler;
import assignment4.model.Users;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserProfileController implements Initializable {

    ObservableList<Users> list = FXCollections.observableArrayList();
    DatabaseHandler databasehandler;

    @FXML
    private TextField txtDateofBirth;
    @FXML
    private TextField txtEmailAddress;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtMiddleName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtAccountName;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void initCol() {
        DatePicker datepicker = new DatePicker();
        txtFirstName.setText("fname");
        txtMiddleName.setText("mname");
        txtLastName.setText("lname");
        txtPhoneNumber.setText("phonenumber");
        txtEmailAddress.setText("email");
        txtAccountName.setText("accountName");
        txtDateofBirth.setText("date");
    }

    /**
     *
     * @param event
     */
    @FXML
    private void updateButtonAction(ActionEvent event) {
        databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM USERS where accountName= '" + txtAccountName.getText() + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            if (rs.next()) {
                String accountName = rs.getString("accountName");
                String fname = rs.getString("fname");
                String mname = rs.getString("mname");
                String lname = rs.getString("lname");
                String phone = rs.getString("phonenumber");
                String email = rs.getString("email");
                String date = rs.getString("date");

                String updateStmt = "UPDATE users\n"
                        + "SET fname = '" + txtFirstName.getText() + "',\n"
                        + "mname = '" + txtMiddleName.getText() + "',\n"
                        + "lname = '" + txtLastName.getText() + "',\n"
                        + "phonenumber = '" + txtPhoneNumber.getText() + "',\n"
                        + "email = '" + txtEmailAddress.getText() + "',\n"
                        + "date = '" + txtDateofBirth.getText() + "'\n"
                        + "WHERE accountName = '" + txtAccountName.getText() + "'";

                databasehandler.execAction(updateStmt);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("User is Updated.");
                alert.showAndWait();

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     *
     * @param event
     */
    @FXML
    private void backButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param accountName
     * @param fname
     * @param mname
     * @param lname
     * @param phone
     * @param email
     * @param date
     */
    public void setText(String accountName, String fname, String mname, String lname, String phone, String email, String date) {
        this.txtAccountName.setText(accountName);
        this.txtFirstName.setText(fname);
        this.txtMiddleName.setText(mname);
        this.txtLastName.setText(lname);
        this.txtPhoneNumber.setText(phone);
        this.txtEmailAddress.setText(email);
        this.txtDateofBirth.setText(date);

    }
}
