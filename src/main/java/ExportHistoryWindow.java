import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ExportHistoryWindow {


    // =========================================
    // SERVICES
    // =========================================

    private final MonitoringHistoryService historyService;

    private final List<Transceiver> modules;


    // =========================================
    // UI COMPONENTS
    // =========================================

    private ListView<String> moduleListView;

    private DatePicker startDatePicker;

    private DatePicker endDatePicker;

    private TextField startTimeField;

    private TextField endTimeField;

    private CheckBox normalCheckBox;

    private CheckBox alarmCheckBox;

    private Label resultLabel;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public ExportHistoryWindow(

            MonitoringHistoryService historyService,

            List<Transceiver> modules
    ) {

        this.historyService = historyService;

        this.modules = modules;
    }


    // =========================================
    // SHOW
    // =========================================

    public void show() {


        Stage stage =
                new Stage();


        BorderPane root =
                new BorderPane();


        root.setPadding(
                new Insets(25)
        );


        root.setStyle(
                "-fx-background-color: #f4f6f9;"
        );


        // =====================================
        // HEADER
        // =====================================

        root.setTop(
                createHeader()
        );


        // =====================================
        // CENTER
        // =====================================

        root.setCenter(
                createForm()
        );


        // =====================================
        // BOTTOM
        // =====================================

        root.setBottom(
                createButtonPanel(
                        stage
                )
        );


        Scene scene =
                new Scene(
                        root,
                        700,
                        650
                );


        stage.setTitle(
                "Export Monitoring History"
        );


        stage.setScene(
                scene
        );


        stage.show();
    }


    // =========================================
    // HEADER
    // =========================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "📤 EXPORT MONITORING HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 24px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "Select modules, time range and data type to export CSV"
                );


        subtitle.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        VBox header =
                new VBox(
                        5,
                        title,
                        subtitle
                );


        header.setPadding(
                new Insets(
                        0,
                        0,
                        20,
                        0
                )
        );


        return header;
    }


    // =========================================
    // FORM
    // =========================================

    private VBox createForm() {


        VBox container =
                new VBox(15);


        // =====================================
        // MODULE SECTION
        // =====================================

        Label moduleLabel =
                new Label(
                        "Select Modules"
                );


        moduleLabel.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
        );


        moduleListView =
                new ListView<>();


        moduleListView.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.MULTIPLE
                );


        for (

                Transceiver module

                :

                modules
        ) {

            moduleListView.getItems().add(

                    module.getModuleId()
                            + " - "
                            + module.getModel()
            );
        }


        moduleListView.setPrefHeight(
                120
        );


        // =====================================
        // DATE RANGE
        // =====================================

        Label dateLabel =
                new Label(
                        "Select Time Range"
                );


        dateLabel.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
        );


        startDatePicker =
                new DatePicker(
                        LocalDate.now()
                );


        endDatePicker =
                new DatePicker(
                        LocalDate.now()
                );


        startTimeField =
                new TextField(
                        "00:00"
                );


        endTimeField =
                new TextField(
                        "23:59"
                );


        startTimeField.setPromptText(
                "HH:mm"
        );


        endTimeField.setPromptText(
                "HH:mm"
        );


        GridPane timeGrid =
                new GridPane();


        timeGrid.setHgap(10);

        timeGrid.setVgap(10);


        timeGrid.add(

                new Label("Start Date"),

                0,

                0
        );


        timeGrid.add(

                startDatePicker,

                1,

                0
        );


        timeGrid.add(

                new Label("Start Time"),

                2,

                0
        );


        timeGrid.add(

                startTimeField,

                3,

                0
        );


        timeGrid.add(

                new Label("End Date"),

                0,

                1
        );


        timeGrid.add(

                endDatePicker,

                1,

                1
        );


        timeGrid.add(

                new Label("End Time"),

                2,

                1
        );


        timeGrid.add(

                endTimeField,

                3,

                1
        );


        // =====================================
        // STATUS FILTER
        // =====================================

        Label statusLabel =
                new Label(
                        "Select Data Type"
                );


        statusLabel.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
        );


        normalCheckBox =
                new CheckBox(
                        "NORMAL Data"
                );


        alarmCheckBox =
                new CheckBox(
                        "ALARM / TEST Data"
                );


        normalCheckBox.setSelected(
                true
        );


        alarmCheckBox.setSelected(
                true
        );


        HBox statusBox =
                new HBox(
                        20,
                        normalCheckBox,
                        alarmCheckBox
                );


        // =====================================
        // RESULT LABEL
        // =====================================

        resultLabel =
                new Label(
                        "Ready to export"
                );


        resultLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        container.getChildren().addAll(

                moduleLabel,

                moduleListView,

                dateLabel,

                timeGrid,

                statusLabel,

                statusBox,

                resultLabel
        );


        return container;
    }


    // =========================================
    // BUTTON PANEL
    // =========================================

    private HBox createButtonPanel(
            Stage stage
    ) {


        Button exportButton =
                new Button(
                        "📤 EXPORT CSV"
                );


        Button closeButton =
                new Button(
                        "CLOSE"
                );


        String buttonStyle =
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 18;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        exportButton.setStyle(
                buttonStyle
        );


        closeButton.setStyle(
                buttonStyle
        );


        // EXPORT

        exportButton.setOnAction(
                event -> exportCSV(
                        stage
                )
        );


        // CLOSE

        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox buttonBox =
                new HBox(
                        10,
                        exportButton,
                        closeButton
                );


        buttonBox.setAlignment(
                Pos.CENTER_RIGHT
        );


        buttonBox.setPadding(
                new Insets(
                        20,
                        0,
                        0,
                        0
                )
        );


        return buttonBox;
    }


    // =========================================
    // EXPORT CSV
    // =========================================

    private void exportCSV(
            Stage stage
    ) {


        try {


            // =====================================
            // GET SELECTED MODULES
            // =====================================

            List<String> selectedModuleIds =
                    new ArrayList<>();


            List<String> selectedItems =
                    moduleListView
                            .getSelectionModel()
                            .getSelectedItems();


            for (

                    String item

                    :

                    selectedItems
            ) {


                // Example:
                // TX001 - 400G COSA

                String moduleId =
                        item.split(
                                " - "
                        )[0];


                selectedModuleIds.add(
                        moduleId
                );
            }


            // =====================================
            // DATE TIME
            // =====================================

            LocalDateTime startDateTime =
                    createDateTime(

                            startDatePicker.getValue(),

                            startTimeField.getText()
                    );


            LocalDateTime endDateTime =
                    createDateTime(

                            endDatePicker.getValue(),

                            endTimeField.getText()
                    );


            // =====================================
            // VALIDATE STATUS
            // =====================================

            if (

                    !normalCheckBox.isSelected()

                            &&

                            !alarmCheckBox.isSelected()
            ) {


                showError(
                        "Please select at least one data type."
                );


                return;
            }


            // =====================================
            // FILTER HISTORY
            // =====================================

            List<MonitoringHistory> records =
                    historyService.filterHistory(

                            selectedModuleIds,

                            startDateTime,

                            endDateTime,

                            normalCheckBox.isSelected(),

                            alarmCheckBox.isSelected()
                    );


            // =====================================
            // NO DATA
            // =====================================

            if (records.isEmpty()) {


                showError(
                        "No history data found for selected filters."
                );


                return;
            }


            // =====================================
            // FILE CHOOSER
            // =====================================

            FileChooser fileChooser =
                    new FileChooser();


            fileChooser.setTitle(
                    "Save CSV File"
            );


            fileChooser.setInitialFileName(

                    "monitoring_history_"

                            + LocalDateTime.now()
                            .format(

                                    DateTimeFormatter.ofPattern(
                                            "yyyyMMdd_HHmmss"
                                    )
                            )

                            + ".csv"
            );


            fileChooser.getExtensionFilters().add(

                    new FileChooser.ExtensionFilter(

                            "CSV Files",

                            "*.csv"
                    )
            );


            File file =
                    fileChooser.showSaveDialog(
                            stage
                    );


            if (file == null) {

                return;
            }


            // =====================================
            // WRITE CSV
            // =====================================

            writeCSV(

                    file,

                    records
            );


            resultLabel.setText(

                    "✓ Export completed: "

                            + records.size()

                            + " records"
            );


            resultLabel.setStyle(

                    "-fx-font-size: 13px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #27ae60;"
            );


            showInfo(

                    "Export Completed",

                    records.size()
                            + " monitoring records exported successfully."
            );


        }

        catch (Exception exception) {


            exception.printStackTrace();


            resultLabel.setText(
                    "⚠ Export failed"
            );


            resultLabel.setStyle(

                    "-fx-font-size: 13px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #e74c3c;"
            );


            showError(
                    "Export failed: "
                            + exception.getMessage()
            );
        }
    }


    // =========================================
    // CREATE DATE TIME
    // =========================================

    private LocalDateTime createDateTime(

            LocalDate date,

            String timeText
    ) {


        if (date == null) {

            date =
                    LocalDate.now();
        }


        LocalTime time;


        try {

            time =
                    LocalTime.parse(
                            timeText.trim()
                    );

        }

        catch (Exception exception) {

            throw new IllegalArgumentException(
                    "Invalid time format. Please use HH:mm"
            );
        }


        return LocalDateTime.of(
                date,
                time
        );
    }


    // =========================================
    // WRITE CSV
    // =========================================

    private void writeCSV(

            File file,

            List<MonitoringHistory> records
    ) throws IOException {


        try (

                FileWriter writer =
                        new FileWriter(file)
        ) {


            // =================================
            // HEADER
            // =================================

            writer.write(

                    "Timestamp,"

                            + "Module ID,"

                            + "Temperature (C),"

                            + "Voltage (V),"

                            + "TX Power (dBm),"

                            + "RX Power (dBm),"

                            + "Laser Current (mA),"

                            + "Status\n"
            );


            // =================================
            // DATA
            // =================================

            DateTimeFormatter formatter =

                    DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm:ss"
                    );


            for (

                    MonitoringHistory record

                    :

                    records
            ) {


                writer.write(

                        record.getTimestamp()
                                .format(formatter)

                                + ","

                                + record.getModuleId()

                                + ","

                                + record.getTemperature()

                                + ","

                                + record.getVoltage()

                                + ","

                                + record.getTxPower()

                                + ","

                                + record.getRxPower()

                                + ","

                                + record.getLaserCurrent()

                                + ","

                                + record.getStatus()

                                + "\n"
                );
            }
        }
    }


    // =========================================
    // SHOW ERROR
    // =========================================

    private void showError(
            String message
    ) {


        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );


        alert.setTitle(
                "Error"
        );


        alert.setHeaderText(
                null
        );


        alert.setContentText(
                message
        );


        alert.showAndWait();
    }


    // =========================================
    // SHOW INFO
    // =========================================

    private void showInfo(

            String title,

            String message
    ) {


        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alert.setTitle(
                title
        );


        alert.setHeaderText(
                null
        );


        alert.setContentText(
                message
        );


        alert.showAndWait();
    }
}