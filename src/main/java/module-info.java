module com.lp3.cemiterio.cemiterio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.lp3.cemiterio.cemiterio to javafx.fxml;
    opens lp3.cemiterio.models to javafx.fxml;
    
    exports com.lp3.cemiterio.cemiterio;
    exports lp3.cemiterio.models;
    requires itextpdf;
}
