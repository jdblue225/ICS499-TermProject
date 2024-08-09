module com.cookiecoders.gamearcade {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.compiler;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires javafx.media;


    opens com.cookiecoders.gamearcade to javafx.fxml;
    exports com.cookiecoders.gamearcade;
    exports com.cookiecoders.gamearcade.util;
    opens com.cookiecoders.gamearcade.util to javafx.fxml;
    exports com.cookiecoders.gamearcade.ui.controllers;
    opens com.cookiecoders.gamearcade.ui.controllers to javafx.fxml;
    exports com.cookiecoders.gamearcade.games;
}