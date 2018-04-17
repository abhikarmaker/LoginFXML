package assignment4;

import assignment4.database.DatabaseHandler;
import assignment4.model.Users;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

public class AdminPageController implements Initializable {

    ObservableList<Users> list = FXCollections.observableArrayList();
    DatabaseHandler databasehandler;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<Users, String> colFname;
    @FXML
    private TableColumn<Users, String> colMname;
    @FXML
    private TableColumn<Users, String> colLname;
    @FXML
    private TableColumn<Users, String> colPhone;
    @FXML
    private TableColumn<Users, String> colEmail;
    @FXML
    private TableColumn<Users, String> colDOB;
    @FXML
    private TableColumn<Users, Boolean> colActive;
    @FXML
    private TableView<Users> tableView;
    @FXML
    private TableColumn<Users, String> colPass;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnDisplay;
    @FXML
    private TableColumn<Users, String> colID;
    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        onLoad();
    }

    void initCol() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        colMname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("mname"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        colActive.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    /**
     *
     * @param event
     * @throws ClassNotFoundException
     */
    @FXML
    private void updateButtonAction(ActionEvent event) throws ClassNotFoundException {
        databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM USERS where id=" + txtId.getText();
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            if (rs.next()) {
                Boolean active = rs.getBoolean("active");
                if (active == false) {
                    updateUser(txtId.getText(), active);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("User is Activated.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("User is Already Activated.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     *
     * @param userId
     * @param active
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateUser(String userId, Boolean active) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        active = true;
        String updateStmt = "UPDATE users\n"
                + "SET active = '" + active + "'\n"
                + "WHERE id = " + userId;
        //Execute UPDATE operation
        databasehandler.execAction(updateStmt);
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void selectButtonAction(ActionEvent event) throws SQLException {
        databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM USERS where id=" + txtId.getText();
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            if (rs.next()) {
                String colId = rs.getString("id");
                String colFname = rs.getString("fname");
                String colMname = rs.getString("lname");
                String colLname = rs.getString("mname");
                String colPhone = rs.getString("phonenumber");
                String colEmail = rs.getString("email");
                String colDOB = rs.getString("date");
                String colPass = rs.getString("pass");
                Boolean colActive = rs.getBoolean("active");
                list.add(new Users(colId, colFname, colMname, colLname, colPhone, colEmail, colDOB, colPass, colActive));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("User Id is not in the database.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            rs.close();
        }
        tableView.getItems().setAll(list);
    }

    /**
     *
     * @param event
     */
    @FXML
    private void displayButtonAction(ActionEvent event) {
        databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM Users";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while (rs.next()) {
                String colId = rs.getString("id");
                String colFname = rs.getString("fname");
                String colMname = rs.getString("lname");
                String colLname = rs.getString("mname");
                String colPhone = rs.getString("phonenumber");
                String colEmail = rs.getString("email");
                String colDOB = rs.getString("date");
                String colPass = rs.getString("pass");
                Boolean colActive = rs.getBoolean("active");
                list.add(new Users(colId, colFname, colMname, colLname, colPhone, colEmail, colDOB, colPass, colActive));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }
    void onLoad(){
        databasehandler = new DatabaseHandler();
        String qu = "SELECT * FROM Users";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while (rs.next()) {
                String colId = rs.getString("id");
                String colFname = rs.getString("fname");
                String colMname = rs.getString("lname");
                String colLname = rs.getString("mname");
                String colPhone = rs.getString("phonenumber");
                String colEmail = rs.getString("email");
                String colDOB = rs.getString("date");
                String colPass = rs.getString("pass");
                Boolean colActive = rs.getBoolean("active");
                list.add(new Users(colId, colFname, colMname, colLname, colPhone, colEmail, colDOB, colPass, colActive));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }

    public void searchUser() {
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

    @FXML
    private void selectButtonAction(InputMethodEvent event) {
    }
}
