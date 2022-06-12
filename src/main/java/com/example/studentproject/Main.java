package com.example.studentproject;
import com.example.studentproject.Connection.DataConnect;
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

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        DataConnect data = new DataConnect();
        VBox container = new VBox();
        VBox listStudent = new VBox();
        VBox form = new VBox();
        container.getChildren().add(listStudent);
        StackPane root = new StackPane();
        displayAllStudent(listStudent, data);
        formAdd(form, data);
        root.getChildren().add(container);

        Scene ScreenHomepage = new Scene(root, 1200, 500);
        Scene ScreenFormAdd = new Scene(form, 1200, 500);

        Button addBtn = new Button("ADD");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(ScreenFormAdd);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayAllStudent(listStudent, data);
                stage.setScene(ScreenHomepage);
            }
        });

        container.getChildren().add(addBtn);
        form.getChildren().add(backBtn);

        stage.setTitle("Student");
        stage.setScene(ScreenHomepage);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public void  displayAllStudent (VBox vbox, DataConnect con) {
        vbox.getChildren().clear();
        List<Student> listStud =  con.getStudent();
        int count = 1;
        GridPane listStudent = new GridPane();
        listStudent.setHgap(10);
        listStudent.setVgap(10);

        listStudent.add(new Label("NO"), 0, 0);
        listStudent.add(new Label("Name"), 1, 0);
        listStudent.add(new Label("Class"), 2, 0);
        listStudent.add(new Label("Score"), 3, 0);
        listStudent.add(new Label("Address"), 4, 0);
        listStudent.add(new Label("Phone"), 5, 0);
        listStudent.add(new Label("Delete"), 6, 0);
        listStudent.add(new Label("Update"), 7, 0);

        for(var std : listStud){
            listStudent.add(new Label("" + count) ,0,count);
            listStudent.add(new Label(std.getName()),1,count);
            listStudent.add(new Label(std.getClassName()),2,count);
            listStudent.add(new Label("" + std.getScore()),3,count);
            listStudent.add(new Label(std.getAddress()),4,count);
            listStudent.add(new Label(std.getPhone()),5,count);
            Button deleteBnt = new Button("Delete");
            deleteBnt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    con.deleteStudent(std.getId());
                    displayAllStudent(vbox, con);
                }
            });
            Button updateBtn = new Button("Update");
            updateBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    con.updateStudent(std.getId(), std);

                }
            });
            listStudent.add(deleteBnt, 6,count);
            listStudent.add(updateBtn,7, count);
            count++;

        }
        vbox.getChildren().add(listStudent);

    }

    public void formAdd (VBox vbox, DataConnect con) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        ChoiceBox choiceClass = new ChoiceBox<>();
        ArrayList<Class> listClassName = con.getListClassName();
        for (var classItem : listClassName){
            choiceClass.getItems().add(classItem.getClassName());
        }

        grid.add(new Label("Name:"), 1, 0);
        var tfName = new TextField();
        grid.add(tfName, 2, 0);

        grid.add(new Label("Class:"), 1, 1);
        grid.add(choiceClass, 2,1);
        //
        grid.add(new Label("Score:"), 1, 2);
        var tfScore = new TextField();
        grid.add(tfScore, 2, 2);
        //
        grid.add(new Label("Address:"), 1, 3);
        var tfAddress = new TextField();
        grid.add(tfAddress, 2, 3);

        grid.add(new Label("Phone:"), 1, 4);
        var tfPhone = new TextField();
        grid.add(tfPhone, 2, 4);

        var btnAdd = new Button("Add");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnAdd.setOnAction(e -> {
            String name = tfName.getText();
            Integer idClass = choiceClass.getSelectionModel().getSelectedIndex()+1;
            String phone = tfPhone.getText();
            String address = tfAddress.getText();
            Float score = Float.parseFloat(tfScore.getText());
            con.insertStudent(new Student(name, new Class(idClass),score, address, phone));
            tfName.clear();
            tfPhone.clear();
            tfAddress.clear();
            tfScore.clear();
            choiceClass.getItems().clear();
        });
        grid.add(btnAdd, 1, 5);
        vbox.getChildren().add(grid);
    }
}