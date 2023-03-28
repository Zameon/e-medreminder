module com.example.alarm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.alarm to javafx.fxml;
    exports com.example.alarm;
}