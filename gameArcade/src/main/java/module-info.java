module com.cookiecoders.gamearcade {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cookiecoders.gamearcade to javafx.fxml;
    exports com.cookiecoders.gamearcade;
}