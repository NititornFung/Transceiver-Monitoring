import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class ExportAlarmHistoryWindow {


    private final AlarmHistoryService alarmHistoryService;

    private final List<Transceiver> modules;

    private final List<CheckBox> moduleCheckBoxes =
            new ArrayList<>();


    private ComboBox<String> typeComboBox;

    private ComboBox<String> severityComboBox;

    private ComboBox<String> sourceComboBox;

    private DatePicker fromDatePicker;

    private DatePicker toDatePicker;

    private TextField fromTimeField;

    private TextField toTimeField;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ExportAlarmHistoryWindow(

            AlarmHistoryService alarmHistoryService,

            List<Transceiver> modules
    ) {


        this.alarmHistoryService =
                alarmHistoryService;

        this.modules =
                modules;
    }


    // =====================================================
    // SHOW
    // =====================================================

    public void show() {


        Stage stage =
                new Stage();


        stage.setTitle(
                "Export Alarm History"
        );


        BorderPane root =
                new BorderPane();

        root.setPadding(
                new Insets(25)
        );


        root.setTop(
                createHeader()
        );


        root.setCenter(
                createContent()
        );


        root.setBottom(
                createButtonPanel(
                        stage
                )
        );


        Scene scene =
                new Scene(
                        root,
                        700,
                        700
                );


        stage.setScene(
                scene
        );

        stage.show();
    }


    // =====================================================
    // HEADER
    // =====================================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "🚨 EXPORT ALARM HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 24px;"
                        + "-fx-font-weight: bold;"
        );


        Label subtitle =
                new Label(
                        "Filter alarm records before exporting CSV"
                );


        subtitle.setStyle(
                "-fx-text-fill: #7f8c8d;"
        );


        return new VBox(

                5,

                title,

                subtitle
        );
    }


    // =====================================================
    // CONTENT
    // =====================================================

    private VBox createContent() {


        VBox content =
                new VBox(15);


        content.setPadding(
                new Insets(
                        25,
                        0,
                        0,
                        0
                )
        );


        // =============================================
        // MODULES
        // =============================================

        Label moduleLabel =
                new Label(
                        "Select Modules"
                );


        moduleLabel.setStyle(
                "-fx-font-weight: bold;"
        );


        VBox moduleBox =
                new VBox(6);


        for (

                Transceiver module :
                modules

        ) {


            CheckBox checkBox =
                    new CheckBox(

                            module.getModuleId()

                                    + " - "

                                    + module.getModel()
                    );


            checkBox.setSelected(
                    true
            );


            checkBox.setUserData(
                    module.getModuleId()
            );


            moduleCheckBoxes.add(
                    checkBox
            );


            moduleBox.getChildren().add(
                    checkBox
            );
        }


        // =============================================
        // ALARM TYPE
        // =============================================

        typeComboBox =
                new ComboBox<>();


        typeComboBox.getItems().add(
                "ALL"
        );


        for (

                AlarmType type :
                AlarmType.values()

        ) {

            typeComboBox.getItems().add(
                    type.toString()
            );
        }


        typeComboBox.setValue(
                "ALL"
        );


        // =============================================
        // SEVERITY
        // =============================================

        severityComboBox =
                new ComboBox<>();


        severityComboBox.getItems().add(
                "ALL"
        );


        for (

                AlarmSeverity severity :
                AlarmSeverity.values()

        ) {

            severityComboBox.getItems().add(
                    severity.toString()
            );
        }


        severityComboBox.setValue(
                "ALL"
        );


        // =============================================
        // SOURCE
        // =============================================

        sourceComboBox =
                new ComboBox<>();


        sourceComboBox.getItems().addAll(

                "ALL",

                "MONITORING",

                "TEST"
        );


        sourceComboBox.setValue(
                "ALL"
        );


        // =============================================
        // TIME
        // =============================================

        fromDatePicker =
                new DatePicker();

        toDatePicker =
                new DatePicker();


        fromTimeField =
                new TextField(
                        "00:00:00"
                );

        toTimeField =
                new TextField(
                        "23:59:59"
                );


        content.getChildren().addAll(

                moduleLabel,

                moduleBox,

                new Label("Alarm Type"),

                typeComboBox,

                new Label("Severity"),

                severityComboBox,

                new Label("Source"),

                sourceComboBox,

                new Label("From Date"),

                fromDatePicker,

                fromTimeField,

                new Label("To Date"),

                toDatePicker,

                toTimeField
        );


        return content;
    }


    // =====================================================
    // BUTTON PANEL
    // =====================================================

    private HBox createButtonPanel(
            Stage stage
    ) {


        Button exportButton =
                new Button(
                        "📥 EXPORT CSV"
                );


        Button closeButton =
                new Button(
                        "CLOSE"
                );


        exportButton.setOnAction(

                event -> exportCsv(
                        stage
                )
        );


        closeButton.setOnAction(

                event -> stage.close()
        );


        HBox box =
                new HBox(

                        10,

                        exportButton,

                        closeButton
                );


        box.setAlignment(
                Pos.CENTER_RIGHT
        );


        return box;
    }


    // =====================================================
    // EXPORT
    // =====================================================

    private void exportCsv(
            Stage stage
    ) {


        try {


            List<String> selectedModules =
                    new ArrayList<>();


            for (

                    CheckBox checkBox :
                    moduleCheckBoxes

            ) {


                if (checkBox.isSelected()) {

                    selectedModules.add(

                            checkBox.getUserData()
                                    .toString()
                    );
                }
            }


            AlarmType type =
                    getAlarmType();


            AlarmSeverity severity =
                    getSeverity();


            AlarmSource source =
                    getSource();


            LocalDateTime from =
                    createDateTime(

                            fromDatePicker.getValue(),

                            fromTimeField.getText(),

                            false
                    );


            LocalDateTime to =
                    createDateTime(

                            toDatePicker.getValue(),

                            toTimeField.getText(),

                            true
                    );


            List<AlarmRecord> alarms =
                    alarmHistoryService.filterAlarms(

                            selectedModules,

                            type,

                            severity,

                            source,

                            from,

                            to
                    );


            if (alarms.isEmpty()) {


                showAlert(

                        Alert.AlertType.WARNING,

                        "No Data",

                        "No alarm records found."
                );


                return;
            }


            FileChooser fileChooser =
                    new FileChooser();


            fileChooser.setTitle(
                    "Save Alarm History CSV"
            );


            fileChooser.setInitialFileName(
                    "alarm_history.csv"
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


            CsvExporter.exportAlarmHistory(

                    file,

                    alarms
            );


            showAlert(

                    Alert.AlertType.INFORMATION,

                    "Success",

                    "Exported "
                            + alarms.size()
                            + " alarm records."
            );


            stage.close();


        }

        catch (Exception exception) {


            exception.printStackTrace();


            showAlert(

                    Alert.AlertType.ERROR,

                    "Error",

                    exception.getMessage()
            );
        }
    }


    // =====================================================
    // GET TYPE
    // =====================================================

    private AlarmType getAlarmType() {


        String value =
                typeComboBox.getValue();


        if (value == null
                || value.equals("ALL")) {

            return null;
        }


        return AlarmType.valueOf(
                value
        );
    }


    // =====================================================
    // GET SEVERITY
    // =====================================================

    private AlarmSeverity getSeverity() {


        String value =
                severityComboBox.getValue();


        if (value == null
                || value.equals("ALL")) {

            return null;
        }


        return AlarmSeverity.valueOf(
                value
        );
    }


    // =====================================================
    // GET SOURCE
    // =====================================================

    private AlarmSource getSource() {


        String value =
                sourceComboBox.getValue();


        if (value == null
                || value.equals("ALL")) {

            return null;
        }


        return AlarmSource.valueOf(
                value
        );
    }


    // =====================================================
    // CREATE DATETIME
    // =====================================================

    private LocalDateTime createDateTime(

            LocalDate date,

            String timeText,

            boolean endOfDay
    ) {


        if (date == null) {

            return null;
        }


        try {


            return LocalDateTime.of(

                    date,

                    LocalTime.parse(
                            timeText
                    )
            );


        }

        catch (Exception exception) {


            return LocalDateTime.of(

                    date,

                    endOfDay

                            ?

                            LocalTime.MAX

                            :

                            LocalTime.MIN
            );
        }
    }


    // =====================================================
    // ALERT
    // =====================================================

    private void showAlert(

            Alert.AlertType type,

            String title,

            String message
    ) {


        Alert alert =
                new Alert(type);


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