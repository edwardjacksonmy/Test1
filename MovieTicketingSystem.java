import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import java.util.HashSet;
import java.util.Set;


public class MovieTicketingSystem extends Application {
    private TextField movieNameField;
    private ComboBox<String> experienceComboBox;
    private ToggleGroup showtimeToggleGroup;
    private TextField seatField;
    private ToggleGroup popcornToggleGroup;

    private RadioButton royalPopcornComboMemberSpecialRadioBtn;
    private RadioButton royalPopcornRadioBtn;
    private RadioButton royalPopcornComboRadioBtn;

    private static final double BEANIE_PRICE = 19.90;
    private static final double CLASSIC_PRICE = 15.90;
    private static final double DELUXE_PRICE = 23.90;
    private static final double FAMILY_FRIENDLY_PRICE = 23.90;
    private static final double FLEXOUND_PRICE = 25.90;
    private static final double IMAX_PRICE = 25.90;
    private static final double INDULGE_PRICE = 120.00;
    private static final double INFINITY_PRICE = 120.00;
    private static final double JUNIOR_PRICE = 15.90;
    private static final double ONYX_PRICE = 89.90;

    private static final double ROYAL_POPCORN_COMBO_MEMBER_SPECIAL_PRICE = 19.90;
    private static final double ROYAL_POPCORN_PRICE = 17.90;
    private static final double ROYAL_POPCORN_COMBO_PRICE = 21.90;

    private Set<String> selectedSeats = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Ticketing System");

        // Create input fields and controls
        movieNameField = new TextField();
        experienceComboBox = new ComboBox<>();
        experienceComboBox.getItems().addAll("Beanie", "Classic", "Deluxe", "Family-Friendly", "Flexound", "IMAX",
                "Indulge", "Infinity", "Junior", "Onyx");

        showtimeToggleGroup = new ToggleGroup();
        VBox showtimeBox = createShowtimeSelectionBox();

        seatField = new TextField();

        popcornToggleGroup = new ToggleGroup();
        VBox popcornBox = createPopcornSelectionBox();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmitButton());



        // Create the layout grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add labels and controls to the grid
        grid.add(new Label("Movie Name:"), 0, 0);
        grid.add(movieNameField, 1, 0);

        grid.add(new Label("Experience:"), 0, 1);
        grid.add(experienceComboBox, 1, 1);

        grid.add(new Label("Session:"), 0, 2);
        grid.add(showtimeBox, 1, 2);

        grid.add(new Label("Seat(s):"), 0, 3);
        grid.add(seatField, 1, 3);

        grid.add(new Label("Food and Beverages:"), 0, 4);
        grid.add(popcornBox, 1, 4);

        grid.add(submitButton, 1, 5);

        Scene scene = new Scene(grid, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createShowtimeSelectionBox() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        RadioButton am11RadioButton = new RadioButton("11:00 AM");
        am11RadioButton.setToggleGroup(showtimeToggleGroup);

        RadioButton pm1_30RadioButton = new RadioButton("01:30 PM");
        pm1_30RadioButton.setToggleGroup(showtimeToggleGroup);

        RadioButton pm4RadioButton = new RadioButton("04:00 PM");
        pm4RadioButton.setToggleGroup(showtimeToggleGroup);

        RadioButton pm6_30RadioButton = new RadioButton("06:30 PM");
        pm6_30RadioButton.setToggleGroup(showtimeToggleGroup);

        RadioButton pm9RadioButton = new RadioButton("09:00 PM");
        pm9RadioButton.setToggleGroup(showtimeToggleGroup);

        vbox.getChildren().addAll(am11RadioButton, pm1_30RadioButton, pm4RadioButton, pm6_30RadioButton, pm9RadioButton);

        return vbox;
    }

    private VBox createPopcornSelectionBox() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        royalPopcornComboMemberSpecialRadioBtn = new RadioButton("Royal Popcorn Combo – Member Special");
        royalPopcornComboMemberSpecialRadioBtn.setToggleGroup(popcornToggleGroup);

        royalPopcornRadioBtn = new RadioButton("Royal Popcorn");
        royalPopcornRadioBtn.setToggleGroup(popcornToggleGroup);

        royalPopcornComboRadioBtn = new RadioButton("Royal Popcorn Combo");
        royalPopcornComboRadioBtn.setToggleGroup(popcornToggleGroup);

        vbox.getChildren().addAll(royalPopcornComboMemberSpecialRadioBtn, royalPopcornRadioBtn, royalPopcornComboRadioBtn);

        return vbox;
    }

    private void handleSubmitButton() {
        String movieName = movieNameField.getText();
        String experience = experienceComboBox.getValue();
        RadioButton selectedShowtimeRadioButton = (RadioButton) showtimeToggleGroup.getSelectedToggle();
        String showtime = selectedShowtimeRadioButton != null ? selectedShowtimeRadioButton.getText() : "";
        String seats = seatField.getText();
        RadioButton selectedPopcornRadioButton = (RadioButton) popcornToggleGroup.getSelectedToggle();
        String popcorn = selectedPopcornRadioButton != null ? selectedPopcornRadioButton.getText() : "";

        try {
            if (!validateInputs(movieName, experience, showtime, seats, popcorn)) {
                return; // If inputs are invalid, return without processing
            }

            // Check if the seat format is valid (alphabet + number)
            String[] seatList = seats.split(",");
            for (String seat : seatList) {
                if (!isSeatFormatValid(seat)) {
                    showAlert("Invalid Seat Format", "Seats must be in the format alphabet + number (e.g., A1, B12, Z100).");
                    return; // If any seat format is invalid, return without processing
                }

                if (selectedSeats.contains(seat)) {
                    showAlert("Duplicated Seat", "The seat " + seat + " is already selected. Please choose a different seat.");
                    return; // If seat is duplicated, return without processing
                }
                selectedSeats.add(seat);
            }

            // Continue processing if all inputs are valid

            // Calculate ticket and popcorn prices
            double ticketPrice = calculateTicketPrice(experience);
            double popcornPrice = calculatePopcornPrice(popcorn);

            int numberOfTickets = seatList.length;
            double totalAmount = ticketPrice * numberOfTickets + popcornPrice;

            // Create the "Thank You" dialog
            Alert thankYouDialog = new Alert(Alert.AlertType.CONFIRMATION);
            thankYouDialog.setTitle("Thank You!");
            thankYouDialog.setHeaderText("Confirmation");
            thankYouDialog.setContentText("You selected " + movieName + " with " + experience + " experience at " + showtime + " for " + numberOfTickets + " seat(s) and a " + popcorn + ". The total is " + totalAmount);

            // Create and set the cancel button within the dialog
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            thankYouDialog.getButtonTypes().setAll(ButtonType.OK, cancelButtonType);

            // Attach an event handler to the cancel button
            thankYouDialog.setOnCloseRequest(event -> {
                if (cancelButtonType.equals(thankYouDialog.getResult())) {
                    clearSelections();
                }
            });

            thankYouDialog.showAndWait();
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    private void clearSelections() {
        movieNameField.clear();
        experienceComboBox.getSelectionModel().clearSelection();
        showtimeToggleGroup.selectToggle(null);
        seatField.clear();
        popcornToggleGroup.selectToggle(null);
    }
    private boolean validateInputs(String movieName, String experience, String showtime, String seats, String popcorn) {
        if (movieName.isEmpty() || experience == null || showtime.isEmpty() || seats.isEmpty() || popcorn == null) {
            showAlert("Error", "Please fill in all the required fields.");
            return false;
        }

        return true;
    }
    private boolean isSeatFormatValid(String seat) {
        // Regular expression pattern to match alphabet + number format (e.g., A1, B12, Z100)
        String pattern = "^[A-Za-z]+\\d+$";
        return seat.matches(pattern);
    }



    private double calculateTicketPrice(String experience) {
        switch (experience) {
            case "Beanie":
                return BEANIE_PRICE;
            case "Classic":
                return CLASSIC_PRICE;
            case "Deluxe":
                return DELUXE_PRICE;
            case "Family-Friendly":
                return FAMILY_FRIENDLY_PRICE;
            case "Flexound":
                return FLEXOUND_PRICE;
            case "IMAX":
                return IMAX_PRICE;
            case "Indulge":
                return INDULGE_PRICE;
            case "Infinity":
                return INFINITY_PRICE;
            case "Junior":
                return JUNIOR_PRICE;
            case "Onyx":
                return ONYX_PRICE;
            default:
                return 0.0;
        }
    }

    private double calculatePopcornPrice(String popcorn) {
        switch (popcorn) {
            case "Royal Popcorn Combo – Member Special":
                return ROYAL_POPCORN_COMBO_MEMBER_SPECIAL_PRICE;
            case "Royal Popcorn":
                return ROYAL_POPCORN_PRICE;
            case "Royal Popcorn Combo":
                return ROYAL_POPCORN_COMBO_PRICE;
            default:
                return 0.0;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}