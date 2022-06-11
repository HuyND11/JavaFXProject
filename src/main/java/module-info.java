module com.example.studentproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.studentproject to javafx.fxml;
    opens com.example.studentproject.Modles to javafx.base;
    exports com.example.studentproject;
}