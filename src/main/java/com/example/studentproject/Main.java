package com.example.studentproject;
import com.example.studentproject.Connection.DataConnect;
import com.example.studentproject.Modles.Admin;
import com.example.studentproject.Modles.Class;
import com.example.studentproject.Modles.Student;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    private Stage window;
    private Scene homepage, formUpdate, formAdd, loginScreen, registerScreen ;
    private VBox root;
    private static final DataConnect con = new DataConnect();

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        root = new VBox();
        VBox ViewFormAdd = new VBox();
        VBox ViewFormUpdate = new VBox();
        VBox ViewLogin = new VBox();
        VBox ViewRegister = new VBox();
        displayAllStudent(root);
        ScreenFormAdd(ViewFormAdd);
        showLoginScreen(ViewLogin);
        showRegisterForm(ViewRegister);

        homepage = new Scene(root, 1200, 500);
        loginScreen = new Scene(ViewLogin, 1200, 500);
        registerScreen = new Scene(ViewRegister, 1200, 500);
        formAdd = new Scene(ViewFormAdd, 1200, 500);
        formUpdate = new Scene(ViewFormUpdate, 1200, 500);

        stage.setTitle("Student");
        stage.setScene(homepage);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public void  displayAllStudent (VBox vbox) {
        vbox.getChildren().clear();
        List<Student> listStud =  con.getStudent();
        int count = 2;
        GridPane listStudent = new GridPane();
        listStudent.setHgap(10);
        listStudent.setVgap(10);
        listStudent.setAlignment(Pos.CENTER);

        listStudent.add(new Label("NO"), 0, 1);
        listStudent.add(new Label("Name"), 1, 1);
        listStudent.add(new Label("Class"), 2, 1);
        listStudent.add(new Label("Score"), 3, 1);
        listStudent.add(new Label("Address"), 4, 1);
        listStudent.add(new Label("Phone"), 5, 1);
        listStudent.add(new Label("Delete"), 6, 1);
        listStudent.add(new Label("Update"), 7, 1);

        for(var std : listStud){
            listStudent.add(new Label("" + count) ,0,count);
            listStudent.add(new Label(std.getName()),1,count);
            listStudent.add(new Label(std.getClassName()),2,count);
            listStudent.add(new Label("" + std.getScore()),3,count);
            listStudent.add(new Label(std.getAddress()),4,count);
            listStudent.add(new Label(std.getPhone()),5,count);
            Button btnDelete = new Button("Delete");
            Button btnEdit = new Button("Edit");
            btnDelete.setPadding(new Insets(5, 15, 5, 15));
            btnEdit.setPadding(new Insets(5, 15, 5, 15));
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    con.deleteStudent(std.getId());
                    displayAllStudent(root);
                }
            });
            btnEdit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ArrayList<Student> stdUpdate = con.getStudentUpdate(std.getId());
                    for (var std : stdUpdate) {
                        System.out.println(std.getName());

                    ChoiceBox choiceClass = new ChoiceBox<>();
                    ArrayList<Class> listClassName = con.getListClassName();
                    for (var classItem : listClassName){
                        choiceClass.getItems().add(classItem.getClassName());
                    }
                    listStudent.add(new Label("Name:"), 8, 0);
                    var tfName = new TextField();
                    listStudent.add(tfName, 9, 0);

                    listStudent.add(new Label("Class:"), 8, 1);
                    listStudent.add(choiceClass, 9,1);
                    //
                    listStudent.add(new Label("Score:"), 8, 2);
                    var tfScore = new TextField();
                    listStudent.add(tfScore, 9, 2);
                    //
                    listStudent.add(new Label("Address:"), 8, 3);
                    var tfAddress = new TextField();
                    listStudent.add(tfAddress, 9, 3);

                    listStudent.add(new Label("Phone:"), 8, 4);
                    var tfPhone = new TextField();
                    listStudent.add(tfPhone, 9, 4);
                    tfName.setText(std.getName());
                    choiceClass.setValue(std.getClassName());
                    tfScore.setText("" + std.getScore());
                    tfAddress.setText(std.getAddress());
                    tfPhone.setText(std.getPhone());
                    var btnUpdate = new Button("Update");
                    btnUpdate.setPadding(new Insets(5, 15, 5, 15));
                    var btnCancel = new Button("Cancel");
                    btnCancel.setPadding(new Insets(5, 15, 5, 15));
                    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            displayAllStudent(root);
                        }
                    });
                    btnUpdate.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            if(checkEmpty(tfName) && checkEmpty(tfAddress) && checkEmpty(tfPhone) && checkEmpty(tfScore)){
                                if(checkFormatScore(tfScore.getText())){
                                    String name = tfName.getText();
                                    int idClass = choiceClass.getSelectionModel().getSelectedIndex()+1;
                                    String phone = tfPhone.getText();
                                    String address = tfAddress.getText();
                                    Float score = Float.parseFloat(tfScore.getText());
                                    if (checkScore(score)){
                                        if (checkFormatPhone(phone)){
                                            displayAllStudent(root);
                                            con.updateStudent(std.getId(), new Student(name, new Class(idClass), score, address, phone));
                                        }else {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Error");
                                            alert.setHeaderText("Phone unsuitable");
                                            alert.setContentText("Please input Phone again.");
                                            alert.show();
                                        }
                                    }else {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Score unsuitable");
                                        alert.setContentText("Please input score form 0 to 10.");
                                        alert.show();
                                    }
                                }else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Score doesn't correct");
                                    alert.setContentText("Please input score with correct format");
                                    alert.show();
                                }
                            }else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Error");
                                alert.setHeaderText("Empty Input");
                                alert.setContentText("Please enter all field.");
                                alert.show();
                            }
                        }
                    });
                        listStudent.add(btnCancel,8, 5);
                        listStudent.add(btnUpdate,9,5);
                    }
                }
            });
            listStudent.add(btnDelete, 6,count);
            listStudent.add(btnEdit,7, count);
            count++;
        }
        Button btnAdd = new Button("ADD");
        Button btnLogout = new Button("Logout");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnLogout.setPadding(new Insets(5, 15, 5, 15));
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {;
                window.setScene(loginScreen);
            }
        });
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(formAdd);
            }
        });
        listStudent.add(btnLogout, 0, 0);
        listStudent.add(btnAdd,1, 0);
        vbox.getChildren().add(listStudent);
    }

    public void ScreenFormAdd (VBox vbox) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        ChoiceBox choiceClass = new ChoiceBox<>();
        ArrayList<Class> listClassName = con.getListClassName();
        for (var classItem : listClassName){
            choiceClass.getItems().add(classItem.getClassName());
        }

        grid.add(new Label("Name:"), 0, 0);
        var tfName = new TextField();
        grid.add(tfName, 1, 0);

        grid.add(new Label("Class:"), 0, 1);
        grid.add(choiceClass, 1,1);
        //
        grid.add(new Label("Score:"), 0, 2);
        var tfScore = new TextField();
        grid.add(tfScore, 1, 2);
        //
        grid.add(new Label("Address:"), 0, 3);
        var tfAddress = new TextField();
        grid.add(tfAddress, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        var tfPhone = new TextField();
        grid.add(tfPhone, 1, 4);

        Button btnAdd = new Button("Add");
        Button btnBack = new Button("Back");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnBack.setPadding(new Insets(5, 15, 5, 15));
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tfName.clear();
                tfPhone.clear();
                tfAddress.clear();
                tfScore.clear();
                choiceClass.setValue("");
                displayAllStudent(root);
                window.setScene(homepage);
            }
        });
        btnAdd.setOnAction(e -> {
            if(checkEmpty(tfName) && checkEmpty(tfAddress) && checkEmpty(tfPhone) && checkEmpty(tfScore)){
                if(checkFormatScore(tfScore.getText())){
                    String name = tfName.getText();
                    int idClass = choiceClass.getSelectionModel().getSelectedIndex()+1;
                    String phone = tfPhone.getText();
                    String address = tfAddress.getText();
                    Float score = Float.parseFloat(tfScore.getText());
                    if (checkScore(score)){
                        if (checkFormatPhone(phone)){
                            con.insertStudent(new Student(name, new Class(idClass),score, address, phone));
                            tfName.clear();
                            tfPhone.clear();
                            tfAddress.clear();
                            tfScore.clear();
                            choiceClass.setValue("");
                        }else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText("Phone unsuitable");
                            alert.setContentText("Please input Phone again.");
                            alert.show();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Score unsuitable");
                        alert.setContentText("Please input score form 0 to 10.");
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Score doesn't correct");
                    alert.setContentText("Please input score with correct format");
                    alert.show();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Input");
                alert.setContentText("Please enter all field.");
                alert.show();
            }
        });
        grid.add(btnBack, 0, 5);
        grid.add(btnAdd, 1, 5);
        vbox.getChildren().add(grid);
    }
    public void showLoginScreen(VBox loginPage ){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        Label labelLogin =new Label("LOGIN");
        Label LabelName = new Label("Name: ");
        Label LabelPassword = new Label("Password: ");
        TextField name = new TextField();
        PasswordField password= new PasswordField();

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name.setText("");
                password.setText("");
                window.setScene(registerScreen);
            }
        });

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(actionEvent -> {
            if (checkEmpty(name) && checkEmpty(password)){
                if(checkLogin(name, password)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setHeaderText("Hi "+name.getText());
                    alert.setContentText("Login successfully!");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            name.setText("");
                            password.setText("");
                            window.setScene(homepage);
                        }
                    });
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setContentText("Login fail!");
                    alert.show();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Input");
                alert.setContentText("Please enter all field.");
                alert.show();
            }
        });
        grid.add(labelLogin, 0,0);
        grid.add(LabelName, 0, 1);
        grid.add(name, 1,1);
        grid.add(LabelPassword, 0, 2);
        grid.add(password, 1, 2);
        grid.add(btnRegister, 0,3);
        grid.add(btnLogin, 1, 3);
        loginPage.getChildren().add(grid);
        loginPage.setSpacing(15);
        loginPage.setAlignment(Pos.BASELINE_CENTER);
    }

    public void showRegisterForm (VBox registerView) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        Label labelRegister =new Label("REGISTER");
        Label LabelName = new Label("Name: ");
        Label LabelPassword = new Label("Password: ");
        Label LabelRePassword = new Label("RePassword: ");
        TextField name = new TextField();
        PasswordField password= new PasswordField();
        PasswordField rePassword= new PasswordField();
        Button btnRegister = new Button("Register");
        Button btnLogin = new Button("Back Login");
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkEmpty(name) && checkEmpty(password) && checkEmpty(rePassword)){
                    if (checkCorrectRePass(password.getText(), rePassword.getText())){
                        con.registerAdmin(new Admin(name.getText(), password.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Register");
                        alert.setHeaderText("Congratulations! Register successfully!");
                        alert.setContentText("Please Login");
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                window.setScene(loginScreen);
                            }
                        });
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Password is not correct with RePassword");
                        alert.setContentText("Please enter password again.");
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Input");
                    alert.setContentText("Please enter all field.");
                    alert.show();
                }

            }
        });

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name.setText("");
                password.setText("");
                rePassword.setText("");
                window.setScene(loginScreen);
            }
        });
        grid.add(labelRegister, 0,0);
        grid.add(LabelName, 0, 1);
        grid.add(name, 1,1);
        grid.add(LabelPassword, 0, 2);
        grid.add(password, 1, 2);
        grid.add(LabelRePassword, 0, 3);
        grid.add(rePassword, 1, 3);
        grid.add(btnLogin, 0, 4);
        grid.add(btnRegister, 1,4);
        registerView.getChildren().add(grid);
        registerView.setSpacing(15);
        registerView.setAlignment(Pos.BASELINE_CENTER);
    }

    public boolean checkLogin(TextField name, TextField password){
        ArrayList<Admin> adminList = con.getAdmin();
        for(var admin : adminList){
            System.out.printf(admin.name);
            System.out.printf(admin.password);
            if(name.getText().equals(admin.name) && password.getText().equals(admin.password)){
                return true;
            }
        }
        return false;
    }
    public boolean checkEmpty(TextField tf){
        if (tf.getText().isEmpty()){
            return false;
        }
        return true;
    }
    public boolean checkCorrectRePass(String pass, String rePass){
        if(pass.equals(rePass)){
            return true;
        }
        return false;
    }
    public boolean checkFormatScore (String  score) {
        String regex = "[-+]?(\\d*\\.\\d+|\\d+)";
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(score);

        // If match found and equal to input1
        if(m.find() && m.group().equals(score)){
            return true;
        }
        return false;
    }

    public boolean checkScore (Float score) {
        if(score >= 0 && score <= 10){
            return true;
        }
        return false;
    }

    public boolean checkFormatPhone (String  phone) {
        String regex = "^0\\d{9}+$";
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(phone);

        // If match found and equal to input1
        if(m.find() && m.group().equals(phone) && phone.length() == 10){
            System.out.println("Ok");
            return true;
        }
        System.out.println("Error");
        return false;
    }
}