import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TestModeWindow {


    // =========================================
    // SYSTEM COMPONENTS
    // =========================================

    private final Transceiver selectedModule;

    private final MonitoringService monitoringService;

    private final MonitoringHistoryService monitoringHistoryService;

    private final AlarmHistoryService alarmHistoryService;


    // =========================================
    // CALLBACK
    // =========================================

    private final Runnable dashboardUpdater;


    // =========================================
    // INPUT FIELDS
    // =========================================

    private TextField temperatureField;

    private TextField voltageField;

    private TextField rxPowerField;

    private TextField txPowerField;

    private TextField laserCurrentField;

    private TextField wavelengthField;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public TestModeWindow(

            Transceiver selectedModule,

            MonitoringService monitoringService,

            MonitoringHistoryService monitoringHistoryService,

            AlarmHistoryService alarmHistoryService,

            Runnable dashboardUpdater
    ) {


        this.selectedModule =
                selectedModule;


        this.monitoringService =
                monitoringService;


        this.monitoringHistoryService =
                monitoringHistoryService;


        this.alarmHistoryService =
                alarmHistoryService;


        this.dashboardUpdater =
                dashboardUpdater;
    }


    // =========================================
    // OPEN WINDOW
    // =========================================

    public void show() {


        Stage stage =
                new Stage();


        stage.setTitle(
                "Test Mode - "
                        + selectedModule.getModuleId()
        );


        // =========================================
        // MODULE INFORMATION
        // =========================================

        Label moduleLabel =
                new Label(
                        "Module : "
                                + selectedModule.getModuleId()
                );


        Label modelLabel =
                new Label(
                        "Model : "
                                + selectedModule.getModel()
                );


        moduleLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );


        modelLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );


        HBox moduleInfo =
                new HBox(
                        40,
                        moduleLabel,
                        modelLabel
                );


        moduleInfo.setAlignment(
                Pos.CENTER
        );


        // =========================================
        // TITLE
        // =========================================

        Label title =
                new Label(
                        "TEST MODE"
                );


        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );


        Label description =
                new Label(
                        "Modify telemetry parameters manually and verify monitoring behavior."
                );


        description.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: gray;"
        );


        // =========================================
        // CREATE INPUT FIELDS
        // =========================================

        createInputFields();


        // =========================================
        // FORM
        // =========================================

        GridPane form =
                new GridPane();


        form.setHgap(15);

        form.setVgap(15);

        form.setAlignment(
                Pos.CENTER
        );


        // =========================================
        // ADD FORM ROWS
        // =========================================

        addFormRow(
                form,
                0,
                "Temperature",
                temperatureField,
                "°C"
        );


        addFormRow(
                form,
                1,
                "Voltage",
                voltageField,
                "V"
        );


        addFormRow(
                form,
                2,
                "RX Power",
                rxPowerField,
                "dBm"
        );


        addFormRow(
                form,
                3,
                "TX Power",
                txPowerField,
                "dBm"
        );


        addFormRow(
                form,
                4,
                "Laser Current",
                laserCurrentField,
                "mA"
        );


        addFormRow(
                form,
                5,
                "Wavelength",
                wavelengthField,
                "nm"
        );


        // =========================================
        // BUTTONS
        // =========================================

        Button runTestButton =
                new Button(
                        "▶ RUN TEST"
                );


        Button resetButton =
                new Button(
                        "↻ RESET"
                );


        Button closeButton =
                new Button(
                        "✕ CLOSE"
                );


        runTestButton.setPrefWidth(
                130
        );


        resetButton.setPrefWidth(
                100
        );


        closeButton.setPrefWidth(
                100
        );


        // =========================================
        // BUTTON ACTIONS
        // =========================================

        runTestButton.setOnAction(
                event -> runTest()
        );


        resetButton.setOnAction(
                event -> resetParameters()
        );


        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox buttonBox =
                new HBox(
                        15,
                        runTestButton,
                        resetButton,
                        closeButton
                );


        buttonBox.setAlignment(
                Pos.CENTER
        );


        // =========================================
        // ROOT
        // =========================================

        VBox root =
                new VBox(
                        20,
                        title,
                        description,
                        moduleInfo,
                        form,
                        buttonBox
                );


        root.setAlignment(
                Pos.CENTER
        );


        root.setPadding(
                new Insets(
                        30
                )
        );


        // =========================================
        // SCENE
        // =========================================

        Scene scene =
                new Scene(
                        root,
                        600,
                        550
                );


        stage.setScene(
                scene
        );


        // =========================================
        // MODAL WINDOW
        // =========================================

        stage.initModality(
                Modality.APPLICATION_MODAL
        );


        stage.showAndWait();
    }


    // =========================================
    // CREATE INPUT FIELDS
    // =========================================

    private void createInputFields() {


        Telemetry telemetry =
                selectedModule.getTelemetry();


        // =========================================
        // DEFAULT VALUES
        // =========================================

        double temperature = 40.0;

        double voltage = 3.30;

        double rxPower = -4.0;

        double txPower = -2.0;

        double laserCurrent = 50.0;

        double wavelength = 1310.0;


        // =========================================
        // LOAD CURRENT TELEMETRY
        // =========================================

        if (telemetry != null) {


            temperature =
                    telemetry.getTemperature();


            voltage =
                    telemetry.getVoltage();


            rxPower =
                    telemetry.getRxPower();


            txPower =
                    telemetry.getTxPower();


            laserCurrent =
                    telemetry.getLaserCurrent();


            wavelength =
                    telemetry.getWavelength();
        }


        // =========================================
        // CREATE TEXT FIELDS
        // =========================================

        temperatureField =
                new TextField(
                        String.valueOf(
                                temperature
                        )
                );


        voltageField =
                new TextField(
                        String.valueOf(
                                voltage
                        )
                );


        rxPowerField =
                new TextField(
                        String.valueOf(
                                rxPower
                        )
                );


        txPowerField =
                new TextField(
                        String.valueOf(
                                txPower
                        )
                );


        laserCurrentField =
                new TextField(
                        String.valueOf(
                                laserCurrent
                        )
                );


        wavelengthField =
                new TextField(
                        String.valueOf(
                                wavelength
                        )
                );


        // =========================================
        // FIELD SIZE
        // =========================================

        temperatureField.setPrefWidth(
                150
        );


        voltageField.setPrefWidth(
                150
        );


        rxPowerField.setPrefWidth(
                150
        );


        txPowerField.setPrefWidth(
                150
        );


        laserCurrentField.setPrefWidth(
                150
        );


        wavelengthField.setPrefWidth(
                150
        );
    }


    // =========================================
    // ADD FORM ROW
    // =========================================

    private void addFormRow(

            GridPane form,

            int row,

            String parameter,

            TextField field,

            String unit
    ) {


        Label parameterLabel =
                new Label(
                        parameter
                );


        parameterLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );


        Label unitLabel =
                new Label(
                        unit
                );


        unitLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: gray;"
        );


        form.add(
                parameterLabel,
                0,
                row
        );


        form.add(
                field,
                1,
                row
        );


        form.add(
                unitLabel,
                2,
                row
        );
    }


    // =========================================
    // RUN TEST
    // =========================================

    private void runTest() {


        try {


            // =========================================
            // READ VALUES
            // =========================================

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


            double rxPower =
                    Double.parseDouble(
                            rxPowerField
                                    .getText()
                                    .trim()
                    );


            double txPower =
                    Double.parseDouble(
                            txPowerField
                                    .getText()
                                    .trim()
                    );


            double laserCurrent =
                    Double.parseDouble(
                            laserCurrentField
                                    .getText()
                                    .trim()
                    );


            double wavelength =
                    Double.parseDouble(
                            wavelengthField
                                    .getText()
                                    .trim()
                    );


            // =========================================
            // CREATE TEST TELEMETRY
            // =========================================

            Telemetry testTelemetry =
                    new Telemetry(

                            wavelength,

                            temperature,

                            voltage,

                            txPower,

                            rxPower,

                            laserCurrent
                    );


            // =========================================
            // UPDATE MODULE
            // =========================================

            selectedModule.updateTelemetry(
                    testTelemetry
            );


            // =========================================
            // MONITORING
            // =========================================

            MonitoringReport report =
                    monitoringService.monitor(
                            selectedModule
                    );


            // =========================================
            // SAVE MONITORING HISTORY
            // =========================================

            monitoringHistoryService.addHistory(

                    selectedModule,

                    report.getStatus()
            );


            // =========================================
            // PROCESS ALARMS
            // =========================================

            alarmHistoryService.processAlarms(

                    selectedModule,

                    report.getAlarms()
            );


            // =========================================
            // UPDATE DASHBOARD
            // =========================================

            dashboardUpdater.run();


            // =========================================
            // SHOW RESULT
            // =========================================

            showTestResult(
                    report
            );


        } catch (
                NumberFormatException e
        ) {


            showError(
                    "Invalid Input",
                    "Please enter valid numeric values."
            );
        }
    }


    // =========================================
    // RESET PARAMETERS
    // =========================================

    private void resetParameters() {


        Telemetry telemetry =
                selectedModule.getTelemetry();


        if (telemetry == null) {

            temperatureField.setText(
                    "40.0"
            );


            voltageField.setText(
                    "3.30"
            );


            rxPowerField.setText(
                    "-4.0"
            );


            txPowerField.setText(
                    "-2.0"
            );


            laserCurrentField.setText(
                    "50.0"
            );


            wavelengthField.setText(
                    "1310.0"
            );


            return;
        }


        temperatureField.setText(
                String.valueOf(
                        telemetry.getTemperature()
                )
        );


        voltageField.setText(
                String.valueOf(
                        telemetry.getVoltage()
                )
        );


        rxPowerField.setText(
                String.valueOf(
                        telemetry.getRxPower()
                )
        );


        txPowerField.setText(
                String.valueOf(
                        telemetry.getTxPower()
                )
        );


        laserCurrentField.setText(
                String.valueOf(
                        telemetry.getLaserCurrent()
                )
        );


        wavelengthField.setText(
                String.valueOf(
                        telemetry.getWavelength()
                )
        );
    }


    // =========================================
    // SHOW TEST RESULT
    // =========================================

    private void showTestResult(
            MonitoringReport report
    ) {


        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alert.setTitle(
                "Test Result"
        );


        alert.setHeaderText(
                "Monitoring Result : "
                        + report.getStatus()
        );


        StringBuilder result =
                new StringBuilder();


        result.append(
                "Module : "
        );


        result.append(
                selectedModule.getModuleId()
        );


        result.append(
                "\n"
        );


        result.append(
                "Model : "
        );


        result.append(
                selectedModule.getModel()
        );


        result.append(
                "\n\n"
        );


        if (
                report.getAlarms().isEmpty()
        ) {


            result.append(
                    "ALARM : None"
            );


        } else {


            result.append(
                    "ALARM COUNT : "
            );


            result.append(
                    report.getAlarms().size()
            );


            result.append(
                    "\n\n"
            );


            for (
                    Alarm alarm :
                    report.getAlarms()
            ) {


                result.append(
                        "⚠ "
                );


                result.append(
                        alarm.getType()
                );


                result.append(
                        "\n"
                );


                result.append(
                        "Severity : "
                );


                result.append(
                        alarm.getSeverity()
                );


                result.append(
                        "\n"
                );


                result.append(
                        "Actual : "
                );


                result.append(
                        alarm.getActualValue()
                );


                result.append(
                        "\n"
                );


                result.append(
                        "Threshold : "
                );


                result.append(
                        alarm.getThreshold()
                );


                result.append(
                        "\n\n"
                );
            }
        }


        alert.setContentText(
                result.toString()
        );


        alert.showAndWait();
    }


    // =========================================
    // SHOW ERROR
    // =========================================

    private void showError(

            String title,

            String message
    ) {


        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
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