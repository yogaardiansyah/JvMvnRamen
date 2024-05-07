module com.jvramenshoppu {
    requires javafx.controls;
    requires transitive java.sql;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.jvramenshoppu to javafx.fxml;

    exports com.jvramenshoppu;
}
