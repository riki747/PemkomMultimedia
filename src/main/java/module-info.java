//module com.mycompany.multimedia {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    opens com.mycompany.multimedia to javafx.fxml;
//    exports com.mycompany.multimedia;
//    requires javafx.media;
//}

module com.mycompany.multimedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.mycompany.multimedia to javafx.fxml;
    exports com.mycompany.multimedia;
}

