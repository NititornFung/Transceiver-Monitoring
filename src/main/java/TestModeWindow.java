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
    // INPUT FIELDS
    // =========================================

    private TextField temperatureField;

    private TextField voltageField;

    private TextField txPowerField;

    private TextField rxPowerField;

    private TextField wavelengthField;

    private TextField laserCurrentField;


    // =========================================
    // RESULT LABEL
    // =========================================

    private Label resultLabel;


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


        // HEADER

        root.setTop(
                createHeader()
        );


        // CENTER

        root.setCenter(
                createTestForm()
        );


        // BUTTONS

        root.setBottom(
                createButtonPanel(stage)
        );


        Scene scene =
                new Scene(
                        root,
                        650,
                        700
                );


        stage.setTitle(
                "Test Mode - "
                        + module.getModuleId()
        );


        stage.setScene(scene);

        stage.show();
    }


    // =========================================
    // CREATE HEADER
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
                        "⚠ Alarms generated in this mode will be marked as TEST"
                );


        warning.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #e67e22;"
        );


        VBox header =
                new VBox(

                        6,

                        title,

                        moduleInfo,

                        warning,

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
    // CREATE TEST FORM
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


        // =====================================
        // TEMPERATURE
        // =====================================

        temperatureField =
                new TextField();

        temperatureField.setPromptText(
                "Example: 90.0"
        );


        // =====================================
        // VOLTAGE
        // =====================================

        voltageField =
                new TextField();

        voltageField.setPromptText(
                "Example: 3.2"
        );


        // =====================================
        // TX POWER
        // =====================================

        txPowerField =
                new TextField();

        txPowerField.setPromptText(
                "Example: -3.0"
        );


        // =====================================
        // RX POWER
        // =====================================

        rxPowerField =
                new TextField();

        rxPowerField.setPromptText(
                "Example: -12.0"
        );


        // =====================================
        // WAVELENGTH
        // =====================================

        wavelengthField =
                new TextField();

        wavelengthField.setPromptText(
                "Example: 1310"
        );


        // =====================================
        // LASER CURRENT
        // =====================================

        laserCurrentField =
                new TextField();

        laserCurrentField.setPromptText(
                "Example: 75.0"
        );


        // =====================================
        // ADD FORM ROWS
        // =====================================

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


        // =====================================
        // RESULT
        // =====================================

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
    // ADD ROW
    // =========================================

    private void addRow(

            GridPane grid,

            int row,

            String labelText,

            TextField field
    ) {


        Label label =
                new Label(
                        labelText
                );


        label.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
        );


        field.setPrefWidth(
                350
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


        runButton.setStyle(
                buttonStyle
        );

        resetButton.setStyle(
                buttonStyle
        );

        closeButton.setStyle(
                buttonStyle
        );


        // RUN TEST

        runButton.setOnAction(
                event -> runTest()
        );


        // RESET

        resetButton.setOnAction(
                event -> resetFields()
        );


        // CLOSE

        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox buttons =
                new HBox(

                        10,

                        runButton,

                        resetButton,

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
            // READ INPUT VALUES
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
            // RUN MONITORING
            // =====================================

            MonitoringReport report =
                    monitoringService.monitor(
                            module
                    );


            // =====================================
            // SAVE MONITORING HISTORY
            // =====================================

            saveMonitoringHistory(

                    telemetry,

                    report
            );


            // =====================================
            // CREATE TEST ALARMS
            // =====================================

            List<AlarmRecord> testAlarms =
                    createTestAlarms(
                            telemetry
                    );


            // =====================================
            // SAVE ALARM HISTORY
            // =====================================

            if (!testAlarms.isEmpty()) {


                alarmHistoryService.addAlarms(

                        module,

                        testAlarms
                );
            }


            // =====================================
            // SHOW RESULT
            // =====================================

            if (testAlarms.isEmpty()) {


                resultLabel.setText(

                        "✓ TEST PASSED - No Alarm Generated"
                );


                resultLabel.setStyle(

                        "-fx-font-size: 14px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-text-fill: #27ae60;"
                );
            }

            else {


                resultLabel.setText(

                        "⚠ TEST COMPLETED - "

                                + testAlarms.size()

                                + " Alarm(s) Generated"
                );


                resultLabel.setStyle(

                        "-fx-font-size: 14px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-text-fill: #e74c3c;"
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


            resultLabel.setText(

                    "⚠ Invalid input. Please enter valid numeric values."
            );


            resultLabel.setStyle(

                    "-fx-font-size: 14px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #e74c3c;"
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


        // =====================================
        // TEMPERATURE HIGH
        // =====================================

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


        // =====================================
        // VOLTAGE LOW
        // =====================================

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


        // =====================================
        // VOLTAGE HIGH
        // =====================================

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


        // =====================================
        // RX POWER LOW
        // =====================================

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
    // SAVE MONITORING HISTORY
    // =========================================

    private void saveMonitoringHistory(

            Telemetry telemetry,

            MonitoringReport report
    ) {


        MonitoringRecord record =
                new MonitoringRecord(

                        module.getModuleId(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getRxPower(),

                        telemetry.getTxPower(),

                        telemetry.getLaserCurrent(),

                        "TEST | "
                                + report.getStatus()
                );


        monitoringHistoryService.addHistory(

                module,

                record
        );
    }


    // =========================================
    // RESET FIELDS
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