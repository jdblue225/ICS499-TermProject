module com.cookiecoders.gamearcade {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.cookiecoders.gamearcade to javafx.fxml;
    exports com.cookiecoders.gamearcade;
}