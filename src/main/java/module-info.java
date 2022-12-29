module com.example.dna_matching {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dna_matching to javafx.fxml;
    exports com.example.dna_matching;
}