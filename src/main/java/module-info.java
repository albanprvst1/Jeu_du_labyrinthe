module labyrinthe {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens labyrinthe to javafx.fxml;
    exports labyrinthe;
}
