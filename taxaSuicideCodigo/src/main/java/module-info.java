module com.taxasuicidetdc.taxasuicidecodigo {
    requires javafx.controls;
    requires javafx.fxml;
    requires weka;


    opens com.taxasuicidetdc.taxasuicidecodigo to javafx.fxml;
    exports com.taxasuicidetdc.taxasuicidecodigo;
}