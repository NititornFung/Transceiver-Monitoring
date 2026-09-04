import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class MonitoringHistoryWindow {


    // =====================================================
    // SERVICES
    // =====================================================

    private final MonitoringHistoryService historyService;

    private final List<Transceiver> modules;


    // =====================================================
    // UI COMPONENTS
    // =====================================================

    private TableView<MonitoringRecord> table;

    private ComboBox<String> moduleSelector;

    private Label recordCountLabel;


    // =====================================================
    // DATE FORMAT
    // =====================================================

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public MonitoringHistoryWindow(

            MonitoringHistoryService historyService,

            List<Transceiver> modules
    ) {

        this.historyService =
                historyService;

        this.modules =
                modules;
    }


    // =====================================================
    // SHOW WINDOW
    // =====================================================

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


        // =================================================
        // HEADER
        // =================================================

        root.setTop(
                createHeader()
        );


        // =================================================
        // TABLE
        // =================================================

        table =
                createTable();


        root.setCenter(
                table
        );


        // =================================================
        // BOTTOM PANEL
        // =================================================

        root.setBottom(
                createBottomPanel(
                        stage
                )
        );


        // =================================================
        // SCENE
        // =================================================

        Scene scene =
                new Scene(

                        root,

                        1250,

                        650
                );


        stage.setTitle(
                "Monitoring History"
        );


        stage.setScene(
                scene
        );


        stage.show();


        // =================================================
        // LOAD HISTORY
        // =================================================

        loadAllHistory();
    }


    // =====================================================
    // HEADER
    // =====================================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "📊 MONITORING HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "View telemetry history for each optical transceiver module"
                );


        subtitle.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        // =================================================
        // MODULE SELECTOR
        // =================================================

        moduleSelector =
                new ComboBox<>();


        moduleSelector.getItems().add(
                "ALL MODULES"
        );


        for (Transceiver module : modules) {

            moduleSelector.getItems().add(

                    module.getModuleId()

                            + " - "

                            + module.getModel()
            );
        }


        moduleSelector.setValue(
                "ALL MODULES"
        );


        moduleSelector.setPrefWidth(
                300
        );


        moduleSelector.setOnAction(
                event -> loadSelectedHistory()
        );


        Label selectorLabel =
                new Label(
                        "Select Module:"
                );


        selectorLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
        );


        HBox selectorBox =
                new HBox(

                        10,

                        selectorLabel,

                        moduleSelector
                );


        selectorBox.setAlignment(
                Pos.CENTER_LEFT
        );


        VBox header =
                new VBox(

                        8,

                        title,

                        subtitle,

                        selectorBox
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


    // =====================================================
    // CREATE TABLE
    // =====================================================

    private TableView<MonitoringRecord> createTable() {


        TableView<MonitoringRecord> historyTable =
                new TableView<>();


        historyTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );


        // =================================================
        // TIMESTAMP
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > timeColumn =
                new TableColumn<>(
                        "Timestamp"
                );


        timeColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                cellData.getValue()
                                        .getTimestamp()
                                        .format(
                                                dateFormatter
                                        )
                        )
        );


        // =================================================
        // MODULE
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > moduleColumn =
                new TableColumn<>(
                        "Module ID"
                );


        moduleColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                cellData.getValue()
                                        .getModuleId()
                        )
        );


        // =================================================
        // TEMPERATURE
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > temperatureColumn =
                new TableColumn<>(
                        "Temperature (°C)"
                );


        temperatureColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        cellData.getValue()
                                                .getTemperature()
                                )
                        )
        );


        // =================================================
        // VOLTAGE
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > voltageColumn =
                new TableColumn<>(
                        "Voltage (V)"
                );


        voltageColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.3f",

                                        cellData.getValue()
                                                .getVoltage()
                                )
                        )
        );


        // =================================================
        // RX POWER
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > rxPowerColumn =
                new TableColumn<>(
                        "RX Power (dBm)"
                );


        rxPowerColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        cellData.getValue()
                                                .getRxPower()
                                )
                        )
        );


        // =================================================
        // TX POWER
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > txPowerColumn =
                new TableColumn<>(
                        "TX Power (dBm)"
                );


        txPowerColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        cellData.getValue()
                                                .getTxPower()
                                )
                        )
        );


        // =================================================
        // LASER CURRENT
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > laserCurrentColumn =
                new TableColumn<>(
                        "Laser Current (mA)"
                );


        laserCurrentColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        cellData.getValue()
                                                .getLaserCurrent()
                                )
                        )
        );


        // =================================================
        // STATUS
        // =================================================

        TableColumn<
                MonitoringRecord,
                String
                > statusColumn =
                new TableColumn<>(
                        "Status"
                );


        statusColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                cellData.getValue()
                                        .getStatus()
                        )
        );


        // =================================================
        // ADD COLUMNS
        // =================================================

        historyTable.getColumns().addAll(

                timeColumn,

                moduleColumn,

                temperatureColumn,

                voltageColumn,

                rxPowerColumn,

                txPowerColumn,

                laserCurrentColumn,

                statusColumn
        );


        return historyTable;
    }


    // =====================================================
    // LOAD ALL HISTORY
    // =====================================================

    private void loadAllHistory() {


        List<MonitoringRecord> records =
                historyService.getAllHistory();


        ObservableList<MonitoringRecord> data =
                FXCollections.observableArrayList(
                        records
                );


        table.setItems(
                data
        );


        updateRecordCount(
                records.size()
        );
    }


    // =====================================================
    // LOAD SELECTED HISTORY
    // =====================================================

    private void loadSelectedHistory() {


        String selected =
                moduleSelector.getValue();


        if (

                selected == null

                        ||

                        selected.equals(
                                "ALL MODULES"
                        )
        ) {

            loadAllHistory();

            return;
        }


        // =================================================
        // GET MODULE ID
        // =================================================

        String moduleId =
                selected.split(
                        " - "
                )[0];


        Transceiver selectedModule =
                null;


        for (Transceiver module : modules) {

            if (

                    module.getModuleId()
                            .equals(
                                    moduleId
                            )
            ) {

                selectedModule =
                        module;

                break;
            }
        }


        if (selectedModule == null) {

            return;
        }


        // =================================================
        // GET HISTORY
        // =================================================

        List<MonitoringRecord> records =
                historyService.getHistory(
                        selectedModule
                );


        table.setItems(

                FXCollections.observableArrayList(
                        records
                )
        );


        updateRecordCount(
                records.size()
        );
    }


    // =====================================================
    // UPDATE RECORD COUNT
    // =====================================================

    private void updateRecordCount(
            int count
    ) {


        if (recordCountLabel != null) {

            recordCountLabel.setText(

                    "Total Records: "

                            + count
            );
        }
    }


    // =====================================================
    // BOTTOM PANEL
    // =====================================================

    private HBox createBottomPanel(
            Stage stage
    ) {


        recordCountLabel =
                new Label(
                        "Total Records: 0"
                );


        recordCountLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #34495e;"
        );


        Button refreshButton =
                new Button(
                        "🔄 Refresh"
                );


        Button closeButton =
                new Button(
                        "Close"
                );


        String buttonStyle =
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 8 16;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        refreshButton.setStyle(
                buttonStyle
        );

        closeButton.setStyle(
                buttonStyle
        );


        // =================================================
        // REFRESH
        // =================================================

        refreshButton.setOnAction(
                event -> loadSelectedHistory()
        );


        // =================================================
        // CLOSE
        // =================================================

        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox left =
                new HBox(
                        recordCountLabel
                );


        HBox right =
                new HBox(

                        10,

                        refreshButton,

                        closeButton
                );


        HBox bottom =
                new HBox(

                        10,

                        left,

                        right
                );


        bottom.setAlignment(
                Pos.CENTER_LEFT
        );


        bottom.setPadding(
                new Insets(

                        20,

                        0,

                        0,

                        0
                )
        );


        HBox.setHgrow(

                left,

                Priority.ALWAYS
        );


        return bottom;
    }
}