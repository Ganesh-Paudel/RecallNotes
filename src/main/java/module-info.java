module com.ganesh.recallnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ganesh.recallnotes to javafx.fxml;
    exports com.ganesh.recallnotes;
    exports com.ganesh.recallnotes.Controllers;
    opens com.ganesh.recallnotes.Controllers to javafx.fxml;
}