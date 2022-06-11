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
import java.util.List;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        DataConnect data = new DataConnect();
        VBox container = new VBox();
        VBox homePage = new VBox();
        VBox form = new VBox();
        container.getChildren().add(homePage);
        StackPane root = new StackPane();
        displayAllStudent(homePage, data);
        formAdd(form, data);
        root.getChildren().add(container);

        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Student");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public void  displayAllStudent (VBox vbox, DataConnect con) {
        vbox.getChildren().clear();
        List<Student> listStud =  con.getStudent();
        Integer count = 0;
        for(var std : listStud){
            count++;
            HBox stdRow = new HBox();
            stdRow.setSpacing(20);
            Label No =new Label( "" +count);
            Label name =new Label( "" +std.getName());
            Label className =new Label( "" +std.getClassName());
            Label score =new Label( "" +std.getScore());
            Label address =new Label( "" +std.getAddress());
            Label phone =new Label( "" +std.getPhone());
            Button deleteBnt = new Button("Delete");
            deleteBnt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    con.deleteStudent(std.getId());
                    displayAllStudent(vbox, con);
                }
            });
            stdRow.getChildren().addAll(No, name, className, score, address, phone, deleteBnt);
            vbox.getChildren().add(stdRow);
        }
    }

    public void formAdd (VBox vbox, DataConnect con) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Name:"), 1, 0);
        var tfName = new TextField();
        grid.add(tfName, 2, 0);

        grid.add(new Label("Class:"), 1, 1);
        var tfIdClass = new TextField();
        grid.add(tfIdClass, 2,1);
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
            Integer idClass = Integer.parseInt(tfIdClass.getText());
            String phone = tfPhone.getText();
            String address = tfAddress.getText();
            Float score = Float.parseFloat(tfScore.getText());
            con.insertStudent(new Student(name, new Class(idClass),score, address, phone));
        });
        grid.add(btnAdd, 1, 5);
        vbox.getChildren().add(grid);
    }

}