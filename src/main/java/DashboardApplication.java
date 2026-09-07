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
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class DashboardApplication extends Application {


    // =====================================================
    // SERVICES
    // =====================================================

    private final TelemetrySimulator simulator =
            new TelemetrySimulator();

    private final MonitoringService monitoringService =
            new MonitoringService();

    private final MonitoringHistoryService monitoringHistoryService =
            new MonitoringHistoryService();

    private final AlarmHistoryService alarmHistoryService =
            new AlarmHistoryService();


    // =====================================================
    // MODULES
    // =====================================================

    private final List<Transceiver> modules =
            new ArrayList<>();


    // =====================================================
    // MODULE CARD COMPONENTS
    // =====================================================

    private final Label[] temperatureLabels =
            new Label[3];

    private final Label[] voltageLabels =
            new Label[3];

    private final Label[] rxPowerLabels =
            new Label[3];

    private final Label[] txPowerLabels =
            new Label[3];

    private final Label[] wavelengthLabels =
            new Label[3];

    private final Label[] statusLabels =
            new Label[3];

    private final VBox[] moduleCards =
            new VBox[3];


    // =====================================================
    // DASHBOARD SUMMARY
    // =====================================================

    private Label totalModulesValue;

    private Label normalModulesValue;

    private Label activeAlarmsValue;

    private Label totalAlarmsValue;


    // =====================================================
    // ALARM PANEL
    // =====================================================

    private VBox alarmPanel;

    private Label alarmMessageLabel;


    // =====================================================
    // CHART SERIES
    // =====================================================

    private XYChart.Series<Number, Number> voltageSeries1;
    private XYChart.Series<Number, Number> voltageSeries2;
    private XYChart.Series<Number, Number> voltageSeries3;

    private XYChart.Series<Number, Number> wavelengthSeries1;
    private XYChart.Series<Number, Number> wavelengthSeries2;
    private XYChart.Series<Number, Number> wavelengthSeries3;

    private XYChart.Series<Number, Number> spectrumSeries;


    // =====================================================
    // CHART AXIS
    // =====================================================

    private NumberAxis voltageXAxis;

    private NumberAxis wavelengthXAxis;


    // =====================================================
    // MONITORING CONTROL
    // =====================================================

    private Timeline monitoringTimeline;

    private Label monitoringStatusLabel;

    private Button startButton;

    private Button stopButton;


    // =====================================================
    // TIME
    // =====================================================

    private int time =
            0;


    // =====================================================
    // CHART SETTINGS
    // =====================================================

    private static final int MAX_DATA_POINTS =
            30;


    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        launch(args);
    }


    // =====================================================
    // START APPLICATION
    // =====================================================

    @Override
    public void start(Stage stage) {


        // =================================================
        // CREATE MODULES
        // =================================================

        modules.add(

                new Transceiver(

                        "TX001",

                        "400G COSA"
                )
        );


        modules.add(

                new Transceiver(

                        "TX002",

                        "800G COSAz"
                )
        );


        modules.add(

                new Transceiver(

                        "TX003",

                        "800G SR8"
                )
        );


        // =================================================
        // ROOT
        // =================================================

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color: #f4f6f9;"
        );


        // =================================================
        // MAIN CONTENT
        // =================================================

        VBox content =
                new VBox(20);

        content.setPadding(

                new Insets(

                        25,

                        40,

                        40,

                        40
                )
        );


        // =================================================
        // HEADER
        // =================================================

        content.getChildren().add(

                createHeader()
        );


        // =================================================
        // CONTROL PANEL
        // =================================================

        content.getChildren().add(

                createControlPanel()
        );


        // =================================================
        // DASHBOARD SUMMARY
        // =================================================

        content.getChildren().add(

                createDashboardSummary()
        );


        // =================================================
        // MODULE TITLE
        // =================================================

        Label moduleTitle =
                new Label(
                        "MODULE STATUS"
                );


        moduleTitle.setStyle(

                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        content.getChildren().add(
                moduleTitle
        );


        // =================================================
        // MODULE CARDS
        // =================================================

        HBox cardsContainer =
                new HBox(20);


        cardsContainer.setAlignment(
                Pos.CENTER
        );


        for (

                int i = 0;

                i < modules.size();

                i++
        ) {


            VBox card =
                    createModuleCard(

                            modules.get(i),

                            i
                    );


            moduleCards[i] =
                    card;


            cardsContainer.getChildren().add(
                    card
            );
        }


        content.getChildren().add(
                cardsContainer
        );


        // =================================================
        // ALARM PANEL
        // =================================================

        content.getChildren().add(

                createAlarmPanel()
        );


        // =================================================
        // VOLTAGE GRAPH
        // =================================================

        content.getChildren().add(

                createVoltageChart()
        );


        // =================================================
        // WAVELENGTH GRAPH
        // =================================================

        content.getChildren().add(

                createWavelengthChart()
        );


        // =================================================
        // OPTICAL SPECTRUM
        // =================================================

        content.getChildren().add(

                createSpectrumChart()
        );


        // =================================================
        // FOOTER
        // =================================================

        Label footer =
                new Label(

                        "Optical Transceiver Monitoring System | "
                                + "Real-Time Telemetry Dashboard"
                );


        footer.setStyle(

                "-fx-font-size: 12px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        content.getChildren().add(
                footer
        );


        // =================================================
        // SCROLL PANE
        // =================================================

        ScrollPane scrollPane =
                new ScrollPane();


        scrollPane.setContent(
                content
        );


        scrollPane.setFitToWidth(
                true
        );


        scrollPane.setPannable(
                true
        );


        scrollPane.setVbarPolicy(

                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );


        scrollPane.setHbarPolicy(

                ScrollPane.ScrollBarPolicy.NEVER
        );


        // =================================================
        // SET ROOT
        // =================================================

        root.setCenter(
                scrollPane
        );


        // =================================================
        // SCENE
        // =================================================

        Scene scene =
                new Scene(

                        root,

                        1400,

                        900
                );


        stage.setTitle(

                "Optical Transceiver Monitoring System"
        );


        stage.setMinWidth(
                1100
        );


        stage.setMinHeight(
                750
        );


        stage.setScene(
                scene
        );


        stage.show();


        // =================================================
        // CREATE MONITORING TIMELINE
        // =================================================

        startRealTimeMonitoring();


        // =================================================
        // INITIAL SUMMARY
        // =================================================

        updateDashboardSummary();
    }


    // =====================================================
    // HEADER
    // =====================================================

    private VBox createHeader() {


        Label title =
                new Label(

                        "OPTICAL TRANSCEIVER MONITORING SYSTEM"
                );


        title.setStyle(

                "-fx-font-size: 30px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1f2d3d;"
        );


        Label subtitle =
                new Label(

                        "Real-Time Monitoring, Diagnostics and Alarm Management Platform"
                );


        subtitle.setStyle(

                "-fx-font-size: 14px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        return new VBox(

                5,

                title,

                subtitle
        );
    }


    // =====================================================
    // CONTROL PANEL
    // =====================================================

    private VBox createControlPanel() {


        // =================================================
        // BUTTONS
        // =================================================

        startButton =
                new Button(
                        "▶ START MONITORING"
                );


        stopButton =
                new Button(
                        "⏸ STOP MONITORING"
                );


        Button refreshButton =
                new Button(
                        "🔄 REFRESH NOW"
                );


        Button testModeButton =
                new Button(
                        "🧪 TEST MODE"
                );


        Button monitoringHistoryButton =
                new Button(
                        "📊 MONITORING HISTORY"
                );


        Button alarmHistoryButton =
                new Button(
                        "🚨 ALARM HISTORY"
                );


        // =================================================
        // BUTTON STYLE
        // =================================================

        String normalButtonStyle =

                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 16;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        String startButtonStyle =

                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 16;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: #27ae60;"
                        + "-fx-text-fill: white;";


        String stopButtonStyle =

                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 16;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: #e74c3c;"
                        + "-fx-text-fill: white;";


        startButton.setStyle(
                startButtonStyle
        );

        stopButton.setStyle(
                stopButtonStyle
        );

        refreshButton.setStyle(
                normalButtonStyle
        );

        testModeButton.setStyle(
                normalButtonStyle
        );

        monitoringHistoryButton.setStyle(
                normalButtonStyle
        );

        alarmHistoryButton.setStyle(
                normalButtonStyle
        );


        // =================================================
        // INITIAL STATE
        // =================================================

        startButton.setDisable(
                false
        );

        stopButton.setDisable(
                true
        );


        // =================================================
        // STATUS
        // =================================================

        monitoringStatusLabel =
                new Label(
                        "● MONITORING STOPPED"
                );


        monitoringStatusLabel.setStyle(

                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #e74c3c;"
        );


        // =================================================
        // BUTTON ACTIONS
        // =================================================

        startButton.setOnAction(
                event -> startMonitoring()
        );

        stopButton.setOnAction(
                event -> stopMonitoring()
        );

        refreshButton.setOnAction(
                event -> refreshDashboard()
        );

        testModeButton.setOnAction(
                event -> openTestMode()
        );

        monitoringHistoryButton.setOnAction(
                event -> openMonitoringHistory()
        );

        alarmHistoryButton.setOnAction(
                event -> openAlarmHistory()
        );


        // =================================================
        // BUTTON PANEL
        // =================================================

        HBox buttonPanel =
                new HBox(

                        12,

                        startButton,

                        stopButton,

                        refreshButton,

                        testModeButton,

                        monitoringHistoryButton,

                        alarmHistoryButton
                );


        buttonPanel.setAlignment(
                Pos.CENTER_LEFT
        );


        return new VBox(

                10,

                buttonPanel,

                monitoringStatusLabel
        );
    }


    // =====================================================
    // DASHBOARD SUMMARY
    // =====================================================

    private VBox createDashboardSummary() {


        Label title =
                new Label(
                        "DASHBOARD SUMMARY"
                );


        title.setStyle(

                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        // =================================================
        // TOTAL MODULES
        // =================================================

        totalModulesValue =
                new Label(
                        String.valueOf(
                                modules.size()
                        )
                );


        VBox totalModulesCard =
                createSummaryCard(

                        "TOTAL MODULES",

                        totalModulesValue,

                        "#3498db"
                );


        // =================================================
        // NORMAL MODULES
        // =================================================

        normalModulesValue =
                new Label(
                        "0"
                );


        VBox normalModulesCard =
                createSummaryCard(

                        "NORMAL MODULES",

                        normalModulesValue,

                        "#27ae60"
                );


        // =================================================
        // ACTIVE ALARMS
        // =================================================

        activeAlarmsValue =
                new Label(
                        "0"
                );


        VBox activeAlarmCard =
                createSummaryCard(

                        "ACTIVE ALARMS",

                        activeAlarmsValue,

                        "#e74c3c"
                );


        // =================================================
        // TOTAL ALARMS
        // =================================================

        totalAlarmsValue =
                new Label(
                        "0"
                );


        VBox totalAlarmCard =
                createSummaryCard(

                        "TOTAL ALARMS",

                        totalAlarmsValue,

                        "#f39c12"
                );


        // =================================================
        // CONTAINER
        // =================================================

        HBox summaryContainer =
                new HBox(

                        20,

                        totalModulesCard,

                        normalModulesCard,

                        activeAlarmCard,

                        totalAlarmCard
                );


        summaryContainer.setAlignment(
                Pos.CENTER_LEFT
        );


        return new VBox(

                12,

                title,

                summaryContainer
        );
    }


    // =====================================================
    // CREATE SUMMARY CARD
    // =====================================================

    private VBox createSummaryCard(

            String titleText,

            Label valueLabel,

            String color
    ) {


        Label title =
                new Label(
                        titleText
                );


        title.setStyle(

                "-fx-font-size: 12px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        valueLabel.setStyle(

                "-fx-font-size: 32px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: "
                        + color
                        + ";"
        );


        VBox card =
                new VBox(

                        8,

                        title,

                        valueLabel
                );


        card.setPadding(
                new Insets(20)
        );


        card.setPrefWidth(
                220
        );


        card.setPrefHeight(
                110
        );


        card.setAlignment(
                Pos.CENTER_LEFT
        );


        card.setStyle(

                "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 12;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-width: 1;"
        );


        return card;
    }


    // =====================================================
    // UPDATE DASHBOARD SUMMARY
    // =====================================================

    private void updateDashboardSummary() {


        // =================================================
        // TOTAL MODULES
        // =================================================

        totalModulesValue.setText(

                String.valueOf(
                        modules.size()
                )
        );


        // =================================================
        // NORMAL MODULES
        // =================================================

        int normalCount =
                0;


        for (Transceiver module : modules) {

            if (module.getTelemetry() == null) {

                continue;
            }


            MonitoringReport report =
                    monitoringService.monitor(
                            module
                    );


            if (
                    report.getStatus()
                            .toString()
                            .equals("NORMAL")
            ) {

                normalCount++;
            }
        }


        normalModulesValue.setText(

                String.valueOf(
                        normalCount
                )
        );


        // =================================================
        // ACTIVE ALARMS
        // =================================================

        activeAlarmsValue.setText(

                String.valueOf(

                        alarmHistoryService
                                .getActiveAlarmCount()
                )
        );


        // =================================================
        // TOTAL ALARMS
        // =================================================

        totalAlarmsValue.setText(

                String.valueOf(

                        alarmHistoryService
                                .getTotalAlarms()
                )
        );
    }


    // =====================================================
    // OPEN TEST MODE
    // =====================================================

    private void openTestMode() {


        TestModeSelectorWindow selectorWindow =
                new TestModeSelectorWindow(

                        modules,

                        monitoringService,

                        monitoringHistoryService,

                        alarmHistoryService,

                        this::refreshDashboard
                );


        selectorWindow.show();
    }


    // =====================================================
    // OPEN MONITORING HISTORY
    // =====================================================

    private void openMonitoringHistory() {


        MonitoringHistoryWindow window =
                new MonitoringHistoryWindow(

                        monitoringHistoryService,

                        modules
                );


        window.show();
    }


    // =====================================================
    // OPEN ALARM HISTORY
    // =====================================================

    private void openAlarmHistory() {


        AlarmHistoryWindow window =
                new AlarmHistoryWindow(

                        alarmHistoryService,

                        modules
                );


        window.show();
    }


    // =====================================================
    // CREATE MODULE CARD
    // =====================================================

    private VBox createModuleCard(

            Transceiver module,

            int index
    ) {


        VBox card =
                new VBox(10);


        card.setPadding(
                new Insets(18)
        );


        card.setPrefWidth(
                380
        );


        card.setMinWidth(
                300
        );


        card.setStyle(

                "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 12;"
                        + "-fx-background-radius: 12;"
        );


        // =================================================
        // MODULE ID
        // =================================================

        Label moduleId =
                new Label(
                        module.getModuleId()
                );


        moduleId.setStyle(

                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        // =================================================
        // MODEL
        // =================================================

        Label model =
                new Label(
                        module.getModel()
                );


        model.setStyle(

                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        // =================================================
        // TELEMETRY LABELS
        // =================================================

        temperatureLabels[index] =
                new Label(
                        "🌡 Temperature: -- °C"
                );


        voltageLabels[index] =
                new Label(
                        "⚡ Voltage: -- V"
                );


        rxPowerLabels[index] =
                new Label(
                        "📥 RX Power: -- dBm"
                );


        txPowerLabels[index] =
                new Label(
                        "📤 TX Power: -- dBm"
                );


        wavelengthLabels[index] =
                new Label(
                        "〰 Wavelength: -- nm"
                );


        Label[] telemetryLabels = {

                temperatureLabels[index],

                voltageLabels[index],

                rxPowerLabels[index],

                txPowerLabels[index],

                wavelengthLabels[index]
        };


        for (Label label : telemetryLabels) {

            label.setStyle(

                    "-fx-font-size: 14px;"
                            + "-fx-text-fill: #34495e;"
            );
        }


        // =================================================
        // STATUS
        // =================================================

        statusLabels[index] =
                new Label(
                        "● WAITING"
                );


        statusLabels[index].setStyle(

                "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        // =================================================
        // ADD COMPONENTS
        // =================================================

        card.getChildren().addAll(

                moduleId,

                model,

                new Separator(),

                temperatureLabels[index],

                voltageLabels[index],

                rxPowerLabels[index],

                txPowerLabels[index],

                wavelengthLabels[index],

                new Separator(),

                statusLabels[index]
        );


        return card;
    }


    // =====================================================
    // ALARM PANEL
    // =====================================================

    private VBox createAlarmPanel() {


        alarmPanel =
                new VBox(10);


        alarmPanel.setPadding(
                new Insets(18)
        );


        alarmPanel.setStyle(

                "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 12;"
                        + "-fx-background-radius: 12;"
        );


        Label title =
                new Label(
                        "🚨 ACTIVE ALARMS"
                );


        title.setStyle(

                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        alarmMessageLabel =
                new Label(
                        "✓ No Active Alarm"
                );


        alarmMessageLabel.setStyle(

                "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #27ae60;"
        );


        alarmPanel.getChildren().addAll(

                title,

                new Separator(),

                alarmMessageLabel
        );


        return alarmPanel;
    }


    // =====================================================
    // VOLTAGE CHART
    // =====================================================

    private LineChart<Number, Number> createVoltageChart() {


        voltageXAxis =
                new NumberAxis(
                        0,
                        MAX_DATA_POINTS,
                        5
                );


        voltageXAxis.setAutoRanging(
                false
        );


        voltageXAxis.setLabel(
                "Time (Seconds)"
        );


        NumberAxis yAxis =
                new NumberAxis(
                        2.8,
                        3.8,
                        0.1
                );


        yAxis.setAutoRanging(
                false
        );


        yAxis.setLabel(
                "Voltage (V)"
        );


        LineChart<Number, Number> chart =
                new LineChart<>(

                        voltageXAxis,

                        yAxis
                );


        chart.setTitle(
                "⚡ REAL-TIME VOLTAGE MONITORING"
        );


        chart.setAnimated(
                false
        );


        chart.setCreateSymbols(
                false
        );


        chart.setPrefHeight(
                350
        );


        voltageSeries1 =
                new XYChart.Series<>();

        voltageSeries1.setName(
                "TX001"
        );


        voltageSeries2 =
                new XYChart.Series<>();

        voltageSeries2.setName(
                "TX002"
        );


        voltageSeries3 =
                new XYChart.Series<>();

        voltageSeries3.setName(
                "TX003"
        );


        chart.getData().addAll(

                voltageSeries1,

                voltageSeries2,

                voltageSeries3
        );


        return chart;
    }


    // =====================================================
    // WAVELENGTH CHART
    // =====================================================

    private LineChart<Number, Number> createWavelengthChart() {


        wavelengthXAxis =
                new NumberAxis(
                        0,
                        MAX_DATA_POINTS,
                        5
                );


        wavelengthXAxis.setAutoRanging(
                false
        );


        wavelengthXAxis.setLabel(
                "Time (Seconds)"
        );


        NumberAxis yAxis =
                new NumberAxis(
                        800,
                        1400,
                        50
                );


        yAxis.setAutoRanging(
                false
        );


        yAxis.setLabel(
                "Wavelength (nm)"
        );


        LineChart<Number, Number> chart =
                new LineChart<>(

                        wavelengthXAxis,

                        yAxis
                );


        chart.setTitle(
                "📡 REAL-TIME CENTER WAVELENGTH"
        );


        chart.setAnimated(
                false
        );


        chart.setCreateSymbols(
                false
        );


        chart.setPrefHeight(
                350
        );


        wavelengthSeries1 =
                new XYChart.Series<>();

        wavelengthSeries1.setName(
                "TX001"
        );


        wavelengthSeries2 =
                new XYChart.Series<>();

        wavelengthSeries2.setName(
                "TX002"
        );


        wavelengthSeries3 =
                new XYChart.Series<>();

        wavelengthSeries3.setName(
                "TX003"
        );


        chart.getData().addAll(

                wavelengthSeries1,

                wavelengthSeries2,

                wavelengthSeries3
        );


        return chart;
    }


    // =====================================================
    // OPTICAL SPECTRUM
    // =====================================================

    private LineChart<Number, Number> createSpectrumChart() {


        NumberAxis xAxis =
                new NumberAxis(
                        1280,
                        1340,
                        5
                );


        xAxis.setAutoRanging(
                false
        );


        xAxis.setLabel(
                "Wavelength (nm)"
        );


        NumberAxis yAxis =
                new NumberAxis(
                        -60,
                        5,
                        5
                );


        yAxis.setAutoRanging(
                false
        );


        yAxis.setLabel(
                "Optical Power (dBm)"
        );


        LineChart<Number, Number> chart =
                new LineChart<>(

                        xAxis,

                        yAxis
                );


        chart.setTitle(
                "🔬 OPTICAL SPECTRUM ANALYZER"
        );


        chart.setAnimated(
                false
        );


        chart.setCreateSymbols(
                false
        );


        chart.setPrefHeight(
                400
        );


        spectrumSeries =
                new XYChart.Series<>();


        spectrumSeries.setName(
                "Optical Spectrum"
        );


        chart.getData().add(
                spectrumSeries
        );


        return chart;
    }


    // =====================================================
    // CREATE MONITORING TIMELINE
    // =====================================================

    private void startRealTimeMonitoring() {


        monitoringTimeline =
                new Timeline(

                        new KeyFrame(

                                Duration.seconds(1),

                                event -> updateDashboard()
                        )
                );


        monitoringTimeline.setCycleCount(
                Timeline.INDEFINITE
        );
    }


    // =====================================================
    // START MONITORING
    // =====================================================

    private void startMonitoring() {


        if (monitoringTimeline == null) {

            startRealTimeMonitoring();
        }


        monitoringTimeline.play();


        startButton.setDisable(
                true
        );


        stopButton.setDisable(
                false
        );


        monitoringStatusLabel.setText(
                "● MONITORING RUNNING"
        );


        monitoringStatusLabel.setStyle(

                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #27ae60;"
        );


        // First update immediately

        updateDashboard();
    }


    // =====================================================
    // STOP MONITORING
    // =====================================================

    private void stopMonitoring() {


        if (monitoringTimeline != null) {

            monitoringTimeline.stop();
        }


        startButton.setDisable(
                false
        );


        stopButton.setDisable(
                true
        );


        monitoringStatusLabel.setText(
                "● MONITORING STOPPED"
        );


        monitoringStatusLabel.setStyle(

                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #e74c3c;"
        );
    }


    // =====================================================
    // REFRESH DASHBOARD
    // =====================================================

    private void refreshDashboard() {

        updateDashboard();
    }


    // =====================================================
    // UPDATE DASHBOARD
    // =====================================================

    private void updateDashboard() {


        time++;


        boolean hasAlarm =
                false;


        StringBuilder alarmText =
                new StringBuilder();


        // =================================================
        // LOOP MODULES
        // =================================================

        for (

                int i = 0;

                i < modules.size();

                i++
        ) {


            Transceiver module =
                    modules.get(i);


            // =============================================
            // GENERATE TELEMETRY
            // =============================================

            Telemetry telemetry =
                    simulator.generateTelemetry(

                            module.getModel()
                    );


            module.updateTelemetry(
                    telemetry
            );


            // =============================================
            // MONITOR MODULE
            // =============================================

            MonitoringReport report =
                    monitoringService.monitor(
                            module
                    );


            // =============================================
            // SAVE MONITORING HISTORY
            // =============================================

            monitoringHistoryService.addHistory(

                    module,

                    telemetry,

                    report
            );


            // =============================================
            // SAVE ALARM HISTORY
            // =============================================

            if (

                    report.getAlarms() != null

                            &&

                            !report.getAlarms().isEmpty()

            ) {


                for (

                        Alarm alarm :

                        report.getAlarms()

                ) {


                    if (

                            !alarmHistoryService
                                    .hasSimilarActiveAlarm(

                                            module.getModuleId(),

                                            alarm.getType()
                                    )

                    ) {


                        AlarmRecord alarmRecord =
                                new AlarmRecord(

                                        module.getModuleId(),

                                        java.time.LocalDateTime.now(),

                                        alarm.getType(),

                                        alarm.getSeverity(),

                                        alarm.getMessage(),

                                        alarm.getActualValue(),

                                        alarm.getThreshold(),

                                        AlarmSource.MONITORING
                                );


                        alarmHistoryService.addAlarm(
                                alarmRecord
                        );
                    }
                }
            }


            // =============================================
            // UPDATE CARD
            // =============================================

            updateCard(

                    i,

                    module,

                    report
            );


            // =============================================
            // UPDATE VOLTAGE GRAPH
            // =============================================

            updateVoltageGraph(

                    i,

                    telemetry.getVoltage()
            );


            // =============================================
            // UPDATE WAVELENGTH GRAPH
            // =============================================

            updateWavelengthGraph(

                    i,

                    telemetry.getWavelength()
            );


            // =============================================
            // CHECK ALARM
            // =============================================

            if (

                    !report.getStatus()
                            .toString()
                            .equals("NORMAL")

            ) {


                hasAlarm =
                        true;


                alarmText.append(
                        "⚠ "
                );


                alarmText.append(
                        module.getModuleId()
                );


                alarmText.append(
                        " | "
                );


                for (

                        Alarm alarm :

                        report.getAlarms()

                ) {


                    alarmText.append(

                            alarm.getType()
                    );


                    alarmText.append(
                            ": "
                    );


                    alarmText.append(

                            String.format(

                                    "%.2f",

                                    alarm.getActualValue()
                            )
                    );


                    alarmText.append(
                            "\n"
                    );
                }
            }
        }


        // =================================================
        // UPDATE ALARM PANEL
        // =================================================

        updateAlarmPanel(

                hasAlarm,

                alarmText.toString()
        );


        // =================================================
        // UPDATE SPECTRUM
        // =================================================

        updateSpectrum();


        // =================================================
        // UPDATE DASHBOARD SUMMARY
        // =================================================

        updateDashboardSummary();
    }


    // =====================================================
    // UPDATE MODULE CARD
    // =====================================================

    private void updateCard(

            int index,

            Transceiver module,

            MonitoringReport report
    ) {


        Telemetry telemetry =
                module.getTelemetry();


        temperatureLabels[index].setText(

                String.format(

                        "🌡 Temperature: %.2f °C",

                        telemetry.getTemperature()
                )
        );


        voltageLabels[index].setText(

                String.format(

                        "⚡ Voltage: %.3f V",

                        telemetry.getVoltage()
                )
        );


        rxPowerLabels[index].setText(

                String.format(

                        "📥 RX Power: %.2f dBm",

                        telemetry.getRxPower()
                )
        );


        txPowerLabels[index].setText(

                String.format(

                        "📤 TX Power: %.2f dBm",

                        telemetry.getTxPower()
                )
        );


        wavelengthLabels[index].setText(

                String.format(

                        "〰 Wavelength: %.2f nm",

                        telemetry.getWavelength()
                )
        );


        String status =
                report.getStatus().toString();


        if (status.equals("NORMAL")) {


            statusLabels[index].setText(
                    "● NORMAL"
            );


            statusLabels[index].setStyle(

                    "-fx-font-size: 15px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #27ae60;"
            );


            moduleCards[index].setStyle(

                    "-fx-background-color: white;"
                            + "-fx-border-color: #dcdde1;"
                            + "-fx-border-radius: 12;"
                            + "-fx-background-radius: 12;"
            );
        }

        else {


            statusLabels[index].setText(
                    "● ALARM"
            );


            statusLabels[index].setStyle(

                    "-fx-font-size: 15px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #e74c3c;"
            );


            moduleCards[index].setStyle(

                    "-fx-background-color: #fff5f5;"
                            + "-fx-border-color: #e74c3c;"
                            + "-fx-border-width: 2;"
                            + "-fx-border-radius: 12;"
                            + "-fx-background-radius: 12;"
            );
        }
    }


    // =====================================================
    // UPDATE ALARM PANEL
    // =====================================================

    private void updateAlarmPanel(

            boolean hasAlarm,

            String alarmText
    ) {


        if (hasAlarm) {


            alarmMessageLabel.setText(
                    alarmText
            );


            alarmMessageLabel.setStyle(

                    "-fx-font-size: 15px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #e74c3c;"
            );


            alarmPanel.setStyle(

                    "-fx-background-color: #fff5f5;"
                            + "-fx-border-color: #e74c3c;"
                            + "-fx-border-width: 2;"
                            + "-fx-border-radius: 12;"
                            + "-fx-background-radius: 12;"
            );
        }

        else {


            alarmMessageLabel.setText(
                    "✓ No Active Alarm"
            );


            alarmMessageLabel.setStyle(

                    "-fx-font-size: 15px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: #27ae60;"
            );


            alarmPanel.setStyle(

                    "-fx-background-color: white;"
                            + "-fx-border-color: #dcdde1;"
                            + "-fx-border-radius: 12;"
                            + "-fx-background-radius: 12;"
            );
        }
    }


    // =====================================================
    // UPDATE VOLTAGE GRAPH
    // =====================================================

    private void updateVoltageGraph(

            int moduleIndex,

            double voltage
    ) {


        XYChart.Series<Number, Number> series;


        if (moduleIndex == 0) {

            series =
                    voltageSeries1;
        }

        else if (moduleIndex == 1) {

            series =
                    voltageSeries2;
        }

        else {

            series =
                    voltageSeries3;
        }


        series.getData().add(

                new XYChart.Data<>(

                        time,

                        voltage
                )
        );


        if (

                series.getData().size()

                        >

                        MAX_DATA_POINTS

        ) {

            series.getData().removeFirst();
        }


        updateVoltageXAxis();
    }


    // =====================================================
    // UPDATE VOLTAGE X AXIS
    // =====================================================

    private void updateVoltageXAxis() {


        if (time <= MAX_DATA_POINTS) {


            voltageXAxis.setLowerBound(
                    0
            );


            voltageXAxis.setUpperBound(
                    MAX_DATA_POINTS
            );
        }

        else {


            voltageXAxis.setLowerBound(

                    time - MAX_DATA_POINTS
            );


            voltageXAxis.setUpperBound(

                    time + 1
            );
        }
    }


    // =====================================================
    // UPDATE WAVELENGTH GRAPH
    // =====================================================

    private void updateWavelengthGraph(

            int moduleIndex,

            double wavelength
    ) {


        XYChart.Series<Number, Number> series;


        if (moduleIndex == 0) {

            series =
                    wavelengthSeries1;
        }

        else if (moduleIndex == 1) {

            series =
                    wavelengthSeries2;
        }

        else {

            series =
                    wavelengthSeries3;
        }


        series.getData().add(

                new XYChart.Data<>(

                        time,

                        wavelength
                )
        );


        if (

                series.getData().size()

                        >

                        MAX_DATA_POINTS

        ) {

            series.getData().removeFirst();
        }


        updateWavelengthXAxis();
    }


    // =====================================================
    // UPDATE WAVELENGTH X AXIS
    // =====================================================

    private void updateWavelengthXAxis() {


        if (time <= MAX_DATA_POINTS) {


            wavelengthXAxis.setLowerBound(
                    0
            );


            wavelengthXAxis.setUpperBound(
                    MAX_DATA_POINTS
            );
        }

        else {


            wavelengthXAxis.setLowerBound(

                    time - MAX_DATA_POINTS
            );


            wavelengthXAxis.setUpperBound(

                    time + 1
            );
        }
    }


    // =====================================================
    // UPDATE OPTICAL SPECTRUM
    // =====================================================

    private void updateSpectrum() {


        spectrumSeries.getData().clear();


        double centerWavelength =

                1310
                        + (
                        Math.random() * 2
                                - 1
                );


        for (

                double wavelength = 1280;

                wavelength <= 1340;

                wavelength += 0.5
        ) {


            double distance =

                    wavelength
                            - centerWavelength;


            double mainPeak =

                    -(distance * distance);


            double noise =

                    -55
                            + Math.random() * 5;


            double power =

                    Math.max(

                            mainPeak,

                            noise
                    );


            spectrumSeries.getData().add(

                    new XYChart.Data<>(

                            wavelength,

                            power
                    )
            );
        }
    }
}