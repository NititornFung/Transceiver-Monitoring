import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;


public class DashboardApplication extends Application {


    // =========================================
    // SYSTEM COMPONENTS
    // =========================================

    private Transceiver selectedModule;

    private MonitoringService monitoringService;

    private TelemetrySimulator telemetrySimulator;

    private OpticalSpectrumSimulator spectrumSimulator;

    private MonitoringHistoryService monitoringHistoryService;

    private AlarmHistoryService alarmHistoryService;


    // =========================================
    // DASHBOARD LABELS
    // =========================================

    private Label systemStatusLabel;

    private Label temperatureValueLabel;
    private Label voltageValueLabel;
    private Label rxPowerValueLabel;
    private Label txPowerValueLabel;
    private Label laserCurrentValueLabel;
    private Label wavelengthValueLabel;

    private Label temperatureStatusLabel;
    private Label voltageStatusLabel;
    private Label rxPowerStatusLabel;
    private Label txPowerStatusLabel;
    private Label laserCurrentStatusLabel;
    private Label wavelengthStatusLabel;


    // =========================================
    // DASHBOARD CARDS
    // =========================================

    private VBox temperatureCard;
    private VBox voltageCard;
    private VBox rxPowerCard;
    private VBox txPowerCard;
    private VBox laserCurrentCard;
    private VBox wavelengthCard;


    // =========================================
    // REAL-TIME MONITORING
    // =========================================

    private Timeline monitoringTimeline;


    // =========================================
    // MONITORING STATE
    // =========================================

    private boolean monitoringStarted = false;

    private boolean monitoringPaused = false;


    // =========================================
    // VOLTAGE GRAPH
    // =========================================

    private LineChart<Number, Number> voltageChart;

    private XYChart.Series<Number, Number> voltageSeries;

    private int timeIndex = 0;


    // =========================================
    // OPTICAL SPECTRUM GRAPH
    // =========================================

    private LineChart<Number, Number> spectrumChart;

    private XYChart.Series<Number, Number> spectrumSeries;

    private NumberAxis spectrumXAxis;


    // =========================================
    // APPLICATION START
    // =========================================

    @Override
    public void start(Stage stage) {


        // =========================================
        // CREATE SERVICES
        // =========================================

        monitoringService =
                new MonitoringService();


        telemetrySimulator =
                new TelemetrySimulator();


        spectrumSimulator =
                new OpticalSpectrumSimulator();


        monitoringHistoryService =
                new MonitoringHistoryService();


        alarmHistoryService =
                new AlarmHistoryService();


        // =========================================
        // CREATE TRANSCEIVER
        // =========================================

        selectedModule =
                new Transceiver(
                        "TX001",
                        "400G COSA"
                );


        // =========================================
        // INITIAL TELEMETRY
        // =========================================

        updateTelemetryData();


        // =========================================
        // ROOT
        // =========================================

        BorderPane root =
                new BorderPane();


        // =========================================
        // HEADER
        // =========================================

        Label title =
                new Label(
                        "OPTICAL TRANSCEIVER MONITORING SYSTEM"
                );


        title.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );


        systemStatusLabel =
                new Label(
                        "● STOPPED"
                );


        systemStatusLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: gray;"
        );


        HBox header =
                new HBox(
                        20,
                        title,
                        systemStatusLabel
                );


        header.setAlignment(
                Pos.CENTER_LEFT
        );


        header.setPadding(
                new Insets(
                        20,
                        30,
                        20,
                        30
                )
        );


        header.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: transparent transparent #dddddd transparent;"
        );


        // =========================================
        // MODULE INFORMATION
        // =========================================

        Label moduleLabel =
                new Label(
                        "Module : "
                                + selectedModule.getModuleId()
                );


        moduleLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;"
        );


        Label modelLabel =
                new Label(
                        "Model : "
                                + selectedModule.getModel()
                );


        modelLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;"
        );


        HBox moduleInfo =
                new HBox(
                        60,
                        moduleLabel,
                        modelLabel
                );


        moduleInfo.setAlignment(
                Pos.CENTER_LEFT
        );


        // =========================================
        // CONTROL PANEL
        // =========================================

        HBox controlPanel =
                createControlPanel();


        // =========================================
        // TELEMETRY GRID
        // =========================================

        GridPane telemetryGrid =
                new GridPane();


        telemetryGrid.setHgap(15);

        telemetryGrid.setVgap(15);

        telemetryGrid.setAlignment(
                Pos.CENTER
        );


        // =========================================
        // CREATE TELEMETRY CARDS
        // =========================================

        temperatureCard =
                createTelemetryCard(
                        "TEMPERATURE"
                );


        voltageCard =
                createTelemetryCard(
                        "VOLTAGE"
                );


        rxPowerCard =
                createTelemetryCard(
                        "RX POWER"
                );


        txPowerCard =
                createTelemetryCard(
                        "TX POWER"
                );


        laserCurrentCard =
                createTelemetryCard(
                        "LASER CURRENT"
                );


        wavelengthCard =
                createTelemetryCard(
                        "WAVELENGTH"
                );


        // =========================================
        // ADD CARDS
        // =========================================

        telemetryGrid.add(
                temperatureCard,
                0,
                0
        );


        telemetryGrid.add(
                voltageCard,
                1,
                0
        );


        telemetryGrid.add(
                rxPowerCard,
                2,
                0
        );


        telemetryGrid.add(
                txPowerCard,
                0,
                1
        );


        telemetryGrid.add(
                laserCurrentCard,
                1,
                1
        );


        telemetryGrid.add(
                wavelengthCard,
                2,
                1
        );


        // =========================================
        // CREATE GRAPHS
        // =========================================

        createVoltageChart();

        createSpectrumChart();


        // =========================================
        // CONTENT
        // =========================================

        VBox content =
                new VBox(
                        25,
                        moduleInfo,
                        controlPanel,
                        telemetryGrid,
                        voltageChart,
                        spectrumChart
                );


        content.setPadding(
                new Insets(
                        25,
                        40,
                        40,
                        40
                )
        );


        content.setAlignment(
                Pos.TOP_CENTER
        );


        // =========================================
        // SCROLL PANE
        // =========================================

        ScrollPane scrollPane =
                new ScrollPane(
                        content
                );


        scrollPane.setFitToWidth(
                true
        );


        scrollPane.setPannable(
                true
        );


        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );


        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );


        // =========================================
        // SET ROOT
        // =========================================

        root.setTop(
                header
        );


        root.setCenter(
                scrollPane
        );


        // =========================================
        // SCENE
        // =========================================

        Scene scene =
                new Scene(
                        root,
                        1200,
                        800
                );


        // =========================================
        // STAGE
        // =========================================

        stage.setTitle(
                "Optical Transceiver Monitoring System"
        );


        stage.setScene(
                scene
        );


        stage.show();


        // =========================================
        // INITIAL UPDATE
        // =========================================

        updateDashboard();
    }


    // =========================================
    // CREATE CONTROL PANEL
    // =========================================

    private HBox createControlPanel() {


        Button startButton =
                new Button(
                        "▶ START"
                );


        Button pauseButton =
                new Button(
                        "⏸ PAUSE"
                );


        Button resumeButton =
                new Button(
                        "▶ RESUME"
                );


        Button testModeButton =
                new Button(
                        "🧪 TEST MODE"
                );


        Button historyButton =
                new Button(
                        "📊 MONITORING HISTORY"
                );


        Button alarmHistoryButton =
                new Button(
                        "🚨 ALARM HISTORY"
                );


        startButton.setPrefWidth(110);

        pauseButton.setPrefWidth(110);

        resumeButton.setPrefWidth(110);

        testModeButton.setPrefWidth(130);

        historyButton.setPrefWidth(180);

        alarmHistoryButton.setPrefWidth(160);


        // =========================================
        // BUTTON ACTIONS
        // =========================================

        startButton.setOnAction(
                event -> startMonitoring()
        );


        pauseButton.setOnAction(
                event -> pauseMonitoring()
        );


        resumeButton.setOnAction(
                event -> resumeMonitoring()
        );


        testModeButton.setOnAction(
                event -> openTestMode()
        );


        historyButton.setOnAction(
                event -> showMonitoringHistory()
        );


        alarmHistoryButton.setOnAction(
                event -> showAlarmHistory()
        );


        HBox controlPanel =
                new HBox(
                        10,
                        startButton,
                        pauseButton,
                        resumeButton,
                        testModeButton,
                        historyButton,
                        alarmHistoryButton
                );


        controlPanel.setAlignment(
                Pos.CENTER
        );


        controlPanel.setPadding(
                new Insets(15)
        );


        return controlPanel;
    }


    // =========================================
    // START MONITORING
    // =========================================

    private void startMonitoring() {


        if (monitoringStarted) {

            if (monitoringPaused) {

                resumeMonitoring();
            }

            return;
        }


        monitoringStarted = true;

        monitoringPaused = false;


        startRealTimeMonitoring();


        updateSystemStatus(
                "● RUNNING",
                "#00aa00"
        );
    }


    // =========================================
    // PAUSE MONITORING
    // =========================================

    private void pauseMonitoring() {


        if (
                monitoringTimeline != null
                        &&
                        monitoringStarted
                        &&
                        !monitoringPaused
        ) {

            monitoringTimeline.pause();

            monitoringPaused = true;


            updateSystemStatus(
                    "● PAUSED",
                    "orange"
            );
        }
    }


    // =========================================
    // RESUME MONITORING
    // =========================================

    private void resumeMonitoring() {


        if (
                monitoringTimeline != null
                        &&
                        monitoringPaused
        ) {

            monitoringTimeline.play();

            monitoringPaused = false;


            updateSystemStatus(
                    "● RUNNING",
                    "#00aa00"
            );
        }
    }


    // =========================================
    // UPDATE SYSTEM STATUS
    // =========================================

    private void updateSystemStatus(
            String status,
            String color
    ) {


        systemStatusLabel.setText(
                status
        );


        systemStatusLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: "
                        + color
                        + ";"
        );
    }


    // =========================================
    // OPEN TEST MODE
    // =========================================

    private void openTestMode() {


        // =========================================
        // PAUSE REAL-TIME MONITORING
        // =========================================

        boolean wasRunning =
                monitoringStarted
                        &&
                        !monitoringPaused;


        if (wasRunning) {

            pauseMonitoring();
        }


        // =========================================
        // OPEN TEST WINDOW
        // =========================================

        TestModeWindow testModeWindow =
                new TestModeWindow(

                        selectedModule,

                        monitoringService,

                        monitoringHistoryService,

                        alarmHistoryService,

                        this::updateDashboard
                );


        testModeWindow.show();


        // =========================================
        // KEEP PAUSED AFTER TEST MODE
        // =========================================

        if (wasRunning) {

            updateSystemStatus(
                    "● PAUSED",
                    "orange"
            );
        }
    }


    // =========================================
    // SHOW MONITORING HISTORY
    // =========================================

    private void showMonitoringHistory() {


        MonitoringHistoryWindow historyWindow =
                new MonitoringHistoryWindow(
                        monitoringHistoryService
                );


        historyWindow.show();
    }


    // =========================================
    // SHOW ALARM HISTORY
    // =========================================

    private void showAlarmHistory() {


        AlarmHistoryWindow alarmWindow =
                new AlarmHistoryWindow(
                        alarmHistoryService
                );


        alarmWindow.show();
    }


    // =========================================
    // CREATE TELEMETRY CARD
    // =========================================

    private VBox createTelemetryCard(
            String title
    ) {


        Label titleLabel =
                new Label(title);


        titleLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );


        Label valueLabel =
                new Label("--");


        valueLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );


        Label statusLabel =
                new Label("WAITING");


        statusLabel.setStyle(
                "-fx-font-weight: bold;"
        );


        VBox card =
                new VBox(
                        10,
                        titleLabel,
                        valueLabel,
                        statusLabel
                );


        card.setAlignment(
                Pos.CENTER
        );


        card.setPrefSize(
                280,
                150
        );


        card.setPadding(
                new Insets(20)
        );


        card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;"
        );


        switch (title) {


            case "TEMPERATURE":

                temperatureValueLabel = valueLabel;

                temperatureStatusLabel = statusLabel;

                break;


            case "VOLTAGE":

                voltageValueLabel = valueLabel;

                voltageStatusLabel = statusLabel;

                break;


            case "RX POWER":

                rxPowerValueLabel = valueLabel;

                rxPowerStatusLabel = statusLabel;

                break;


            case "TX POWER":

                txPowerValueLabel = valueLabel;

                txPowerStatusLabel = statusLabel;

                break;


            case "LASER CURRENT":

                laserCurrentValueLabel = valueLabel;

                laserCurrentStatusLabel = statusLabel;

                break;


            case "WAVELENGTH":

                wavelengthValueLabel = valueLabel;

                wavelengthStatusLabel = statusLabel;

                break;
        }


        return card;
    }


    // =========================================
    // CREATE VOLTAGE GRAPH
    // =========================================

    private void createVoltageChart() {


        NumberAxis xAxis =
                new NumberAxis();


        xAxis.setLabel(
                "Time (seconds)"
        );


        NumberAxis yAxis =
                new NumberAxis(
                        3.0,
                        3.6,
                        0.1
                );


        yAxis.setLabel(
                "Voltage (V)"
        );


        yAxis.setAutoRanging(
                false
        );


        voltageChart =
                new LineChart<>(
                        xAxis,
                        yAxis
                );


        voltageChart.setTitle(
                "Real-Time Voltage Monitoring"
        );


        voltageChart.setAnimated(
                false
        );


        voltageChart.setCreateSymbols(
                false
        );


        voltageChart.setPrefHeight(
                300
        );


        voltageSeries =
                new XYChart.Series<>();


        voltageSeries.setName(
                "Voltage"
        );


        voltageChart.getData().add(
                voltageSeries
        );
    }


    // =========================================
    // CREATE OPTICAL SPECTRUM GRAPH
    // =========================================

    private void createSpectrumChart() {


        spectrumXAxis =
                new NumberAxis(
                        1307,
                        1313,
                        1
                );


        spectrumXAxis.setLabel(
                "Wavelength (nm)"
        );


        spectrumXAxis.setAutoRanging(
                false
        );


        NumberAxis yAxis =
                new NumberAxis(
                        -50,
                        5,
                        5
                );


        yAxis.setLabel(
                "Optical Power (dBm)"
        );


        yAxis.setAutoRanging(
                false
        );


        spectrumChart =
                new LineChart<>(
                        spectrumXAxis,
                        yAxis
                );


        spectrumChart.setTitle(
                "Optical Spectrum Analyzer"
        );


        spectrumChart.setAnimated(
                false
        );


        spectrumChart.setCreateSymbols(
                false
        );


        spectrumChart.setPrefHeight(
                350
        );


        spectrumSeries =
                new XYChart.Series<>();


        spectrumSeries.setName(
                "Optical Spectrum"
        );


        spectrumChart.getData().add(
                spectrumSeries
        );
    }


    // =========================================
    // START REAL-TIME MONITORING
    // =========================================

    private void startRealTimeMonitoring() {


        if (monitoringTimeline != null) {

            monitoringTimeline.play();

            return;
        }


        monitoringTimeline =
                new Timeline(
                        new KeyFrame(
                                Duration.seconds(1),

                                event -> {

                                    updateTelemetryData();

                                    updateDashboard();

                                    saveMonitoringData();
                                }
                        )
                );


        monitoringTimeline.setCycleCount(
                Timeline.INDEFINITE
        );


        monitoringTimeline.play();
    }


    // =========================================
    // SAVE MONITORING DATA
    // =========================================

    private void saveMonitoringData() {


        MonitoringReport report =
                monitoringService.monitor(
                        selectedModule
                );


        monitoringHistoryService.addHistory(
                selectedModule,
                report.getStatus()
        );


        alarmHistoryService.processAlarms(
                selectedModule,
                report.getAlarms()
        );
    }


    // =========================================
    // UPDATE TELEMETRY
    // =========================================

    private void updateTelemetryData() {


        Telemetry telemetry =
                telemetrySimulator.generateTelemetry(
                        selectedModule.getModel()
                );


        selectedModule.updateTelemetry(
                telemetry
        );
    }


    // =========================================
    // UPDATE DASHBOARD
    // =========================================

    private void updateDashboard() {


        Telemetry telemetry =
                selectedModule.getTelemetry();


        if (telemetry == null) {

            return;
        }


        updateVoltageGraph(
                telemetry.getVoltage()
        );


        updateSpectrumGraph(
                telemetry.getWavelength()
        );


        MonitoringReport report =
                monitoringService.monitor(
                        selectedModule
                );


        temperatureValueLabel.setText(
                String.format(
                        "%.2f °C",
                        telemetry.getTemperature()
                )
        );


        voltageValueLabel.setText(
                String.format(
                        "%.3f V",
                        telemetry.getVoltage()
                )
        );


        rxPowerValueLabel.setText(
                String.format(
                        "%.2f dBm",
                        telemetry.getRxPower()
                )
        );


        txPowerValueLabel.setText(
                String.format(
                        "%.2f dBm",
                        telemetry.getTxPower()
                )
        );


        laserCurrentValueLabel.setText(
                String.format(
                        "%.2f mA",
                        telemetry.getLaserCurrent()
                )
        );


        wavelengthValueLabel.setText(
                String.format(
                        "%.3f nm",
                        telemetry.getWavelength()
                )
        );


        updateCardStatus(
                temperatureCard,
                temperatureStatusLabel,
                getTemperatureStatus(report)
        );


        updateCardStatus(
                voltageCard,
                voltageStatusLabel,
                getVoltageStatus(report)
        );


        updateCardStatus(
                rxPowerCard,
                rxPowerStatusLabel,
                getRxPowerStatus(report)
        );


        updateCardStatus(
                txPowerCard,
                txPowerStatusLabel,
                "NORMAL"
        );


        updateCardStatus(
                laserCurrentCard,
                laserCurrentStatusLabel,
                getLaserCurrentStatus(report)
        );


        updateCardStatus(
                wavelengthCard,
                wavelengthStatusLabel,
                "NORMAL"
        );
    }


    // =========================================
    // UPDATE VOLTAGE GRAPH
    // =========================================

    private void updateVoltageGraph(
            double voltage
    ) {


        timeIndex++;


        voltageSeries.getData().add(
                new XYChart.Data<>(
                        timeIndex,
                        voltage
                )
        );


        if (
                voltageSeries.getData().size()
                        > 30
        ) {

            voltageSeries.getData().remove(
                    0
            );
        }
    }


    // =========================================
    // UPDATE SPECTRUM GRAPH
    // =========================================

    private void updateSpectrumGraph(
            double centerWavelength
    ) {


        spectrumXAxis.setLowerBound(
                centerWavelength - 3.0
        );


        spectrumXAxis.setUpperBound(
                centerWavelength + 3.0
        );


        spectrumXAxis.setTickUnit(
                1.0
        );


        List<SpectrumPoint> spectrumData =
                spectrumSimulator.generateSpectrum(
                        centerWavelength
                );


        spectrumSeries.getData().clear();


        for (
                SpectrumPoint point :
                spectrumData
        ) {

            spectrumSeries.getData().add(
                    new XYChart.Data<>(
                            point.getWavelength(),
                            point.getPower()
                    )
            );
        }
    }


    // =========================================
    // UPDATE CARD STATUS
    // =========================================

    private void updateCardStatus(

            VBox card,

            Label statusLabel,

            String status
    ) {


        statusLabel.setText(
                status
        );


        if (
                status.equals(
                        "NORMAL"
                )
        ) {

            statusLabel.setStyle(
                    "-fx-text-fill: #00aa00;" +
                            "-fx-font-weight: bold;"
            );


            card.setStyle(
                    "-fx-background-color: #ffffff;" +
                            "-fx-border-color: #cccccc;" +
                            "-fx-border-radius: 8;" +
                            "-fx-background-radius: 8;"
            );

        } else {

            statusLabel.setStyle(
                    "-fx-text-fill: red;" +
                            "-fx-font-weight: bold;"
            );


            card.setStyle(
                    "-fx-background-color: #fff0f0;" +
                            "-fx-border-color: red;" +
                            "-fx-border-radius: 8;" +
                            "-fx-background-radius: 8;"
            );
        }
    }


    // =========================================
    // GET TEMPERATURE STATUS
    // =========================================

    private String getTemperatureStatus(
            MonitoringReport report
    ) {


        for (
                Alarm alarm :
                report.getAlarms()
        ) {

            if (
                    alarm.getType()
                            == AlarmType.TEMPERATURE_HIGH
            ) {

                return "ALARM";
            }
        }


        return "NORMAL";
    }


    // =========================================
    // GET VOLTAGE STATUS
    // =========================================

    private String getVoltageStatus(
            MonitoringReport report
    ) {


        for (
                Alarm alarm :
                report.getAlarms()
        ) {

            if (
                    alarm.getType()
                            == AlarmType.VOLTAGE_LOW
                            ||
                            alarm.getType()
                                    == AlarmType.VOLTAGE_HIGH
            ) {

                return "ALARM";
            }
        }


        return "NORMAL";
    }


    // =========================================
    // GET RX POWER STATUS
    // =========================================

    private String getRxPowerStatus(
            MonitoringReport report
    ) {


        for (
                Alarm alarm :
                report.getAlarms()
        ) {

            if (
                    alarm.getType()
                            == AlarmType.RX_POWER_LOW
            ) {

                return "ALARM";
            }
        }


        return "NORMAL";
    }


    // =========================================
    // GET LASER CURRENT STATUS
    // =========================================

    private String getLaserCurrentStatus(
            MonitoringReport report
    ) {


        for (
                Alarm alarm :
                report.getAlarms()
        ) {

            if (
                    alarm.getType()
                            == AlarmType.LASER_CURRENT_LOW
                            ||
                            alarm.getType()
                                    == AlarmType.LASER_CURRENT_HIGH
            ) {

                return "ALARM";
            }
        }


        return "NORMAL";
    }


    // =========================================
    // MAIN
    // =========================================

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}