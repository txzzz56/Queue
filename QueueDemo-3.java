package org.example.demo;

import java.util.Optional;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Denne delen av koden håndterer brukergrensesnittet for køen,
 * inkludert hvordan brukeren kan komme seg fram til ulike funksjoner 
 * for å legge til, fjerne, vise og sortere elementer i køen gjennom en grafisk grensesnitt
 */

/** Queue Demo arver fra superklassen Application */
public class QueueDemo extends Application {


    private Queue queue;

    // Ulike UI-komponenter for å vise status og ta inn brukerinput
    private Label countLabel; /* Label for å vise antall elementer i køen */
    private Label frontLabel; /* Label for å vise indeksen til det første elementet */
    private Label rearLabel; /* Label for å vise indeksen til det siste elementet */

    private TextField inputField; /* Inndatafelt for å ta inn tall som skal legges til i køen */

    private TextArea outputArea; /* Utdatafelt for å vise resultatet av inndatafeltet */

    private Button addButton; /* Knapp for å legge til elementer i køen */
    private Button removeButton; /* Knapp for å fjerne elementer fra køen */
    private Button showButton; /* Knapp for å vise elementer i køen */
    private Button sortButton; /* Knapp for å sortere elementer i køen */

    @Override /* Start() metode for å starte applikasjonen */
    public void start(Stage stage) {

        stage.setTitle("Queue Demonstration");

        // Inndatafelt for å spørre brukeren om størrelsen på køen ved oppstart
        TextInputDialog sizeDialog = new TextInputDialog();

        sizeDialog.setTitle("Queue Size");

        sizeDialog.setHeaderText("Enter the maximum size of the queue:");

        Optional<String> result = sizeDialog.showAndWait();

        int size;

        try {
/* Henter og konverterer den innputtede størrelsen til et heltall */
            size = Integer.parseInt(result.get().trim());

        } catch (Exception ex) {
/* Hvis den innputtede størrelsen ikke er et gyldig heltall, viser feilmelding */
            showError("Invalid size.");

            return;
        }

        queue = new Queue(size);

        // Tittel for applikasjonen øverst i vinduet
        Label title = new Label("Queue Demonstration");
/*Styling for tittelen */
        title.setStyle("""
                -fx-font-size: 24px;
                -fx-font-weight: bold;
                -fx-text-fill: #FFD700;
                """);

        // Subtittel som beskriver hva applikasjonen gjør, plassert under tittelen
        Label subtitle = new Label("Add, remove, show and sort a queue");
/*Styling for subtittelen */
        subtitle.setStyle("""
                -fx-font-size: 13px;
                -fx-text-fill: #FFF3B0;
                """);

        VBox header = new VBox(3); /* Vertikal boks for å holde tittelen og subtittelene sammen */

        header.getChildren().addAll(
                title,
                subtitle
        );

        header.setAlignment(Pos.CENTER);

        // Labels for å vise statusen til køen
        countLabel = new Label("COUNT: 0"); /* Label for å vise antall elementer i køen */
        frontLabel = new Label("F: 0"); /* Label for å vise indeksen til det første elementet */
        rearLabel = new Label("R: -1"); /* Label for å vise indeksen til det siste elementet */
/* Styling for statuslabels/ subtittelen */
        String labelStyle = """
                -fx-text-fill: #FFD700;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                """;

        countLabel.setStyle(labelStyle);
        frontLabel.setStyle(labelStyle);
        rearLabel.setStyle(labelStyle);

        HBox statusBox = new HBox(25); /* Horisontal boks for å holde statuslabelsene sammen */

        statusBox.getChildren().addAll(
                countLabel,
                frontLabel,
                rearLabel
        );

        statusBox.setAlignment(Pos.CENTER);

        // Inndatafelt og knapp for å legge til elementer i køen
        Label inputLabel = new Label("Add Number:");

        inputLabel.setStyle("-fx-text-fill: #FFF3B0;"); /* Styling for inndatafeltet */

        inputField = new TextField();

        inputField.setPrefWidth(100);

        addButton = new Button("Add");

        HBox inputBox = new HBox(10); /* Horisontal boks for å holde inndatafeltet og knappen sammen */

        inputBox.getChildren().addAll(
                inputLabel,
                inputField,
                addButton
        );

        inputBox.setAlignment(Pos.CENTER);

        // Handlingsknapper for å fjerne, vise og sortere elementer i køen
        removeButton = new Button("Remove");

        showButton = new Button("Show");

        sortButton = new Button("Sort");

        HBox actionBox = new HBox(10); /* Horisontal boks for å holde handlingsknappene sammen */

        actionBox.getChildren().addAll(
                removeButton,
                showButton,
                sortButton
        );

        actionBox.setAlignment(Pos.CENTER);

        // Utdatafelt for å vise resultatet av inndatafeltet og handlingene som utføres på køen
        outputArea = new TextArea();

        outputArea.setEditable(false);

        outputArea.setPrefHeight(220);
/* Styling for utdatafeltet */
        outputArea.setStyle("""
                -fx-control-inner-background: #FFF8DC;
                -fx-font-family: Consolas;
                -fx-font-size: 13px;
                """);

        // Knapp for å lukke applikasjonen og gå tilbake til hovedmenyen
        Button returnBtn = new Button("Return");

        returnBtn.setOnAction(e ->
                stage.close());

        // Styling for alle knapper
        for (Button b : new Button[]{
                addButton,
                removeButton,
                showButton,
                sortButton,
                returnBtn
        }) {

            styleButton(b);
        }

        // Event handlers for knappene og inndatafeltet
        addButton.setOnAction(e -> addElement());

        removeButton.setOnAction(e -> removeElement());

        showButton.setOnAction(e -> showQueue());

        sortButton.setOnAction(e -> sortQueue());

        inputField.setOnAction(e -> addElement());

        // Layout for hele applikasjonen, som setter sammen alle de ulike delene i en vertikal boks
        VBox root = new VBox(15);

        root.getChildren().addAll(
                header,
                statusBox,
                inputBox,
                actionBox,
                outputArea,
                returnBtn
        );
/* Styling for hovedvinduet */
        root.setPadding(new Insets(20));

        root.setAlignment(Pos.CENTER);

        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #8B0000, #DAA520);
                """);

        // Setter scenen og viser applikasjonen
        Scene scene = new Scene(root, 550, 550);

        stage.setScene(scene);

        stage.show();
    }

    // Styling for knapper, som inkluderer normal og hover-effekt
    private void styleButton(Button b) {

        String normal = """
                -fx-background-color: #C0392B;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 15;
                -fx-padding: 8 20 8 20;
                """;

        String hover = """
                -fx-background-color: #F1C40F;
                -fx-text-fill: #4A0E0E;
                -fx-font-weight: bold;
                -fx-background-radius: 15;
                -fx-padding: 8 20 8 20;
                """;

        b.setStyle(normal);

        b.setOnMouseEntered(e -> {

            if (!b.isDisabled()) {

                b.setStyle(hover);
            }
        });

        b.setOnMouseExited(e -> {

            if (!b.isDisabled()) {

                b.setStyle(normal);
            }
        });
    }

    // Legg til element i køen, som inkluderer validering av input og oppdatering av statuslabelsene etterpå
    private void addElement() {

        try {

            int value = Integer.parseInt(
                    inputField.getText().trim()
            );

            String message = queue.enqueue(value);

            outputArea.appendText(message + "\n");

            inputField.clear();

            updateLabels();

        } catch (NumberFormatException ex) {

            showError("Invalid integer.");
        }
    }

    // Fjerner element fra køen, som inkluderer oppdatering av statuslabelsene etterpå
    private void removeElement() {

        String message = queue.dequeue();

        outputArea.appendText(message + "\n");

        updateLabels();
    }

    // Viser elementene i køen, som inkluderer en sjekk for om køen er tom før den prøver å vise innholdet
    private void showQueue() {

        outputArea.appendText(
                "Queue: " + queue.displayQueue() + "\n"
        );
    }

    // Sorterer elementene i køen, som inkluderer en sjekk for om køen er tom før den prøver å sortere innholdet
    private void sortQueue() {

        int[] sorted = queue.getSortedQueue();

        outputArea.appendText(
                "Sorted: " + Arrays.toString(sorted) + "\n"
        );
    }

    // Oppdaterer statuslabelsene for å vise gjeldende antall elementer i køen, og indeksene til det første og siste elementet
    private void updateLabels() {

        countLabel.setText(
                "COUNT: " + queue.getCount()
        );

        frontLabel.setText(
                "F: " + queue.getFront()
        );

        rearLabel.setText(
                "R: " + queue.getRear()
        );
    }

    // Feilmelding som vises i en alert-boks hvis det oppstår en feil, for eksempel ved ugyldig input
    private void showError(String msg) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
