import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


public class TestModeWindow {


    // =========================================
    // SERVICES
    // =========================================

    private final Transceiver module;

    private final MonitoringService monitoringService;

    private final MonitoringHistoryService monitoringHistoryService;

    private final AlarmHistoryService alarmHistoryService;

    private final Runnable refreshDashboard;


    // =========================================
    // TEST SESSION
    // =========================================

    private final List<MonitoringRecord> testSessionRecords =
            new ArrayList<>();


    // =========================================
    // INPUT FIELDS
    // =========================================

    private TextField temperatureField;

    private TextField voltageField;

    private TextField txPowerField;

    private TextField rxPowerField;

    private TextField wavelengthField;

    private TextField laserCurrentField;


    // =========================================
    // RESULT
    // =========================================

    private Label resultLabel;

    private Label sessionLabel;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public TestModeWindow(

            Transceiver module,

            MonitoringService monitoringService,

            MonitoringHistoryService monitoringHistoryService,

            AlarmHistoryService alarmHistoryService,

            Runnable refreshDashboard
    ) {

        this.module = module;

        this.monitoringService = monitoringService;

        this.monitoringHistoryService = monitoringHistoryService;

        this.alarmHistoryService = alarmHistoryService;

        this.refreshDashboard = refreshDashboard;
    }


    // =========================================
    // SHOW WINDOW
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


        root.setTop(
                createHeader()
        );


        root.setCenter(
                createTestForm()
        );


        root.setBottom(
                createButtonPanel(stage)
        );


        Scene scene =
                new Scene(

                        root,

                        700,

                        750
                );


        stage.setTitle(
                "Test Mode - "
                        + module.getModuleId()
        );


        stage.setScene(scene);

        stage.show();
    }


    // =========================================
    // HEADER
    // =========================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "🧪 TEST MODE"
                );


        title.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label moduleInfo =
                new Label(

                        "Module: "

                                + module.getModuleId()

                                + " | "

                                + module.getModel()
                );


        moduleInfo.setStyle(
                "-fx-font-size: 15px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        Label warning =
                new Label(
                        "Manual values are allowed. All alarms will be marked as TEST."
                );


        warning.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #e67e22;"
        );


        sessionLabel =
                new Label(
                        "Test Session Records: 0"
                );


        sessionLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2980b9;"
        );


        VBox header =
                new VBox(

                        6,

                        title,

                        moduleInfo,

                        warning,

                        sessionLabel,

                        new Separator()
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
    // TEST FORM
    // =========================================

    private VBox createTestForm() {


        Label title =
                new Label(
                        "Inject Telemetry Values"
                );


        title.setStyle(
                "-fx-font-size: 18px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #34495e;"
        );


        GridPane grid =
                new GridPane();


        grid.setHgap(15);

        grid.setVgap(15);

        grid.setPadding(
                new Insets(20)
        );


        temperatureField =
                createInputField(
                        "Example: 90.0"
                );


        voltageField =
                createInputField(
                        "Example: 3.2"
                );


        txPowerField =
                createInputField(
                        "Example: -3.0"
                );


        rxPowerField =
                createInputField(
                        "Example: -12.0"
                );


        wavelengthField =
                createInputField(
                        "Example: 1310"
                );


        laserCurrentField =
                createInputField(
                        "Example: 75.0"
                );


        addRow(
                grid,
                0,
                "Temperature (°C)",
                temperatureField
        );


        addRow(
                grid,
                1,
                "Voltage (V)",
                voltageField
        );


        addRow(
                grid,
                2,
                "TX Power (dBm)",
                txPowerField
        );


        addRow(
                grid,
                3,
                "RX Power (dBm)",
                rxPowerField
        );


        addRow(
                grid,
                4,
                "Wavelength (nm)",
                wavelengthField
        );


        addRow(
                grid,
                5,
                "Laser Current (mA)",
                laserCurrentField
        );


        resultLabel =
                new Label(
                        "Ready for test"
                );


        resultLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        VBox form =
                new VBox(

                        10,

                        title,

                        grid,

                        resultLabel
                );


        return form;
    }


    // =========================================
    // INPUT FIELD
    // =========================================

    private TextField createInputField(
            String prompt
    ) {

        TextField field =
                new TextField();


        field.setPromptText(
                prompt
        );


        field.setPrefWidth(350);


        return field;
    }


    // =========================================
    // ADD ROW
    // =========================================

    private void addRow(

            GridPane grid,

            int row,

            String labelText,

            TextField field
    ) {


        Label label =
                new Label(labelText);


        label.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
        );


        grid.add(
                label,
                0,
                row
        );


        grid.add(
                field,
                1,
                row
        );
    }


    // =========================================
    // BUTTON PANEL
    // =========================================

    private HBox createButtonPanel(
            Stage stage
    ) {


        Button runButton =
                new Button(
                        "▶ RUN TEST"
                );


        Button resetButton =
                new Button(
                        "↺ RESET"
                );


        Button exportButton =
                new Button(
                        "📤 EXPORT CSV"
                );


        Button closeButton =
                new Button(
                        "CLOSE"
                );


        String style =
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 16;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        runButton.setStyle(style);

        resetButton.setStyle(style);

        exportButton.setStyle(style);

        closeButton.setStyle(style);


        runButton.setOnAction(
                event -> runTest()
        );


        resetButton.setOnAction(
                event -> resetFields()
        );


        exportButton.setOnAction(
                event -> exportTestSession(stage)
        );


        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox buttons =
                new HBox(

                        10,

                        runButton,

                        resetButton,

                        exportButton,

                        closeButton
                );


        buttons.setAlignment(
                Pos.CENTER_RIGHT
        );


        buttons.setPadding(
                new Insets(

                        20,

                        0,

                        0,

                        0
                )
        );


        return buttons;
    }


    // =========================================
    // RUN TEST
    // =========================================

    private void runTest() {


        try {


            // =====================================
            // READ VALUES
            // =====================================

            double temperature =
                    Double.parseDouble(
                            temperatureField
                                    .getText()
                                    .trim()
                    );


            double voltage =
                    Double.parseDouble(
                            voltageField
                                    .getText()
                                    .trim()
                    );


            double txPower =
                    Double.parseDouble(
                            txPowerField
                                    .getText()
                                    .trim()
                    );


            double rxPower =
                    Double.parseDouble(
                            rxPowerField
                                    .getText()
                                    .trim()
                    );


            double wavelength =
                    Double.parseDouble(
                            wavelengthField
                                    .getText()
                                    .trim()
                    );


            double laserCurrent =
                    Double.parseDouble(
                            laserCurrentField
                                    .getText()
                                    .trim()
                    );


            // =====================================
            // CREATE TELEMETRY
            // =====================================

            Telemetry telemetry =
                    new Telemetry(

                            temperature,

                            voltage,

                            txPower,

                            rxPower,

                            wavelength,

                            laserCurrent
                    );


            // =====================================
            // UPDATE MODULE
            // =====================================

            module.updateTelemetry(
                    telemetry
            );


            // =====================================
            // MONITOR
            // =====================================

            MonitoringReport report =
                    monitoringService.monitor(
                            module
                    );


            // =====================================
            // SAVE TEST HISTORY
            // =====================================

            monitoringHistoryService.addTestHistory(

                    module,

                    telemetry,

                    report
            );


            // =====================================
            // SAVE TEST SESSION
            // =====================================

            MonitoringRecord testRecord =
                    new MonitoringRecord(

                            module.getModuleId(),

                            module.getModel(),

                            LocalDateTime.now(),

                            telemetry.getTemperature(),

                            telemetry.getVoltage(),

                            telemetry.getRxPower(),

                            telemetry.getTxPower(),

                            telemetry.getWavelength(),

                            telemetry.getLaserCurrent(),

                            report.getStatus().toString(),

                            MonitoringSource.TEST
                    );


            testSessionRecords.add(
                    testRecord
            );


            updateSessionLabel();


            // =====================================
            // CREATE TEST ALARMS
            // =====================================

            List<AlarmRecord> testAlarms =
                    createTestAlarms(
                            telemetry
                    );


            // =====================================
            // SAVE ALARMS
            // =====================================

            if (!testAlarms.isEmpty()) {


                alarmHistoryService.addAlarms(

                        module,

                        testAlarms
                );
            }


            // =====================================
            // RESULT
            // =====================================

            if (testAlarms.isEmpty()) {


                showSuccess(
                        "✓ TEST COMPLETED - No Alarm Generated"
                );
            }

            else {


                showError(

                        "⚠ TEST COMPLETED - "

                                + testAlarms.size()

                                + " Alarm(s) Generated"
                );
            }


            // =====================================
            // REFRESH DASHBOARD
            // =====================================

            if (refreshDashboard != null) {

                refreshDashboard.run();
            }


        }

        catch (NumberFormatException exception) {

            showError(
                    "⚠ Invalid input. Please enter valid numeric values."
            );
        }
    }


    // =========================================
    // EXPORT TEST SESSION
    // =========================================

    private void exportTestSession(
            Stage stage
    ) {


        if (testSessionRecords.isEmpty()) {

            showError(
                    "⚠ No test records available to export."
            );

            return;
        }


        CsvExportService exportService =
                new CsvExportService();


        boolean success =
                exportService.exportTestRecords(

                        stage,

                        testSessionRecords
                );


        if (success) {

            showSuccess(

                    "✓ CSV exported successfully. "

                            + testSessionRecords.size()

                            + " test records exported."
            );
        }
    }


    // =========================================
    // CREATE TEST ALARMS
    // =========================================

    private List<AlarmRecord> createTestAlarms(
            Telemetry telemetry
    ) {


        List<AlarmRecord> alarms =
                new ArrayList<>();


        if (telemetry.getTemperature() > 85.0) {


            alarms.add(

                    new AlarmRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            AlarmType.TEMPERATURE_HIGH,

                            AlarmSeverity.CRITICAL,

                            "Temperature exceeded maximum limit",

                            telemetry.getTemperature(),

                            85.0,

                            AlarmSource.TEST
                    )
            );
        }


        if (telemetry.getVoltage() < 3.0) {


            alarms.add(

                    new AlarmRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            AlarmType.VOLTAGE_LOW,

                            AlarmSeverity.WARNING,

                            "Voltage below minimum limit",

                            telemetry.getVoltage(),

                            3.0,

                            AlarmSource.TEST
                    )
            );
        }


        if (telemetry.getVoltage() > 3.6) {


            alarms.add(

                    new AlarmRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            AlarmType.VOLTAGE_HIGH,

                            AlarmSeverity.WARNING,

                            "Voltage exceeded maximum limit",

                            telemetry.getVoltage(),

                            3.6,

                            AlarmSource.TEST
                    )
            );
        }


        if (telemetry.getRxPower() < -10.0) {


            alarms.add(

                    new AlarmRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            AlarmType.RX_POWER_LOW,

                            AlarmSeverity.WARNING,

                            "RX Optical Power below minimum limit",

                            telemetry.getRxPower(),

                            -10.0,

                            AlarmSource.TEST
                    )
            );
        }


        return alarms;
    }


    // =========================================
    // UPDATE SESSION LABEL
    // =========================================

    private void updateSessionLabel() {

        sessionLabel.setText(

                "Test Session Records: "

                        + testSessionRecords.size()
        );
    }


    // =========================================
    // SUCCESS
    // =========================================

    private void showSuccess(
            String message
    ) {

        resultLabel.setText(
                message
        );


        resultLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #27ae60;"
        );
    }


    // =========================================
    // ERROR
    // =========================================

    private void showError(
            String message
    ) {

        resultLabel.setText(
                message
        );


        resultLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #e74c3c;"
        );
    }


    // =========================================
    // RESET
    // =========================================

    private void resetFields() {


        temperatureField.clear();

        voltageField.clear();

        txPowerField.clear();

        rxPowerField.clear();

        wavelengthField.clear();

        laserCurrentField.clear();


        resultLabel.setText(
                "Ready for test"
        );


        resultLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #7f8c8d;"
        );
    }
}