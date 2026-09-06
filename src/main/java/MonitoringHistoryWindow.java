import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class MonitoringHistoryWindow {


    // =====================================================
    // SERVICE
    // =====================================================

    private final MonitoringHistoryService historyService;

    private final List<Transceiver> modules;


    // =====================================================
    // TABLE
    // =====================================================

    private TableView<MonitoringHistory> table;


    // =====================================================
    // FILTER
    // =====================================================

    private ComboBox<String> moduleSelector;


    // =====================================================
    // DATA
    // =====================================================

    private final ObservableList<MonitoringHistory> tableData =
            FXCollections.observableArrayList();


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
                new Insets(20)
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
        // CENTER
        // =================================================

        root.setCenter(
                createTable()
        );


        // =================================================
        // BOTTOM
        // =================================================

        root.setBottom(
                createButtonPanel(stage)
        );


        // =================================================
        // SCENE
        // =================================================

        Scene scene =
                new Scene(
                        root,
                        1150,
                        650
                );


        stage.setTitle(
                "Monitoring History"
        );


        stage.setScene(scene);

        stage.show();


        // =================================================
        // LOAD DATA
        // =================================================

        loadAllHistory();
    }


    // =====================================================
    // CREATE HEADER
    // =====================================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "📊 MONITORING HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 24px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "View telemetry history for each optical transceiver module"
                );


        subtitle.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        // =================================================
        // MODULE SELECTOR
        // =================================================

        moduleSelector =
                new ComboBox<>();


        moduleSelector.setPrefWidth(
                250
        );


        moduleSelector.getItems().add(
                "ALL MODULES"
        );


        for (
                Transceiver module :
                modules
        ) {

            moduleSelector.getItems().add(

                    module.getModuleId()
                            + " - "
                            + module.getModel()
            );
        }


        moduleSelector.setValue(
                "ALL MODULES"
        );


        moduleSelector.setOnAction(
                event -> filterHistory()
        );


        HBox filterBox =
                new HBox(
                        10,

                        new Label(
                                "Select Module:"
                        ),

                        moduleSelector
                );


        filterBox.setAlignment(
                Pos.CENTER_LEFT
        );


        VBox header =
                new VBox(
                        12,

                        title,

                        subtitle,

                        filterBox
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

    private VBox createTable() {


        table =
                new TableView<>();


        table.setItems(
                tableData
        );


        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_NEXT_COLUMN
        );


        // =================================================
        // TIME COLUMN
        // =================================================

        TableColumn<MonitoringHistory, String> timeColumn =
                new TableColumn<>(
                        "Timestamp"
                );


        timeColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                cellData
                                        .getValue()
                                        .getFormattedTime()
                        )
        );


        // =================================================
        // TEMPERATURE COLUMN
        // =================================================

        TableColumn<MonitoringHistory, Number> temperatureColumn =
                new TableColumn<>(
                        "Temperature (°C)"
                );


        temperatureColumn.setCellValueFactory(
                cellData ->

                        new SimpleDoubleProperty(

                                cellData
                                        .getValue()
                                        .getTemperature()
                        )
        );


        // =================================================
        // VOLTAGE COLUMN
        // =================================================

        TableColumn<MonitoringHistory, Number> voltageColumn =
                new TableColumn<>(
                        "Voltage (V)"
                );


        voltageColumn.setCellValueFactory(
                cellData ->

                        new SimpleDoubleProperty(

                                cellData
                                        .getValue()
                                        .getVoltage()
                        )
        );


        // =================================================
        // TX POWER COLUMN
        // =================================================

        TableColumn<MonitoringHistory, Number> txPowerColumn =
                new TableColumn<>(
                        "TX Power (dBm)"
                );


        txPowerColumn.setCellValueFactory(
                cellData ->

                        new SimpleDoubleProperty(

                                cellData
                                        .getValue()
                                        .getTxPower()
                        )
        );


        // =================================================
        // RX POWER COLUMN
        // =================================================

        TableColumn<MonitoringHistory, Number> rxPowerColumn =
                new TableColumn<>(
                        "RX Power (dBm)"
                );


        rxPowerColumn.setCellValueFactory(
                cellData ->

                        new SimpleDoubleProperty(

                                cellData
                                        .getValue()
                                        .getRxPower()
                        )
        );


        // =================================================
        // LASER CURRENT COLUMN
        // =================================================

        TableColumn<MonitoringHistory, Number> laserCurrentColumn =
                new TableColumn<>(
                        "Laser Current (mA)"
                );


        laserCurrentColumn.setCellValueFactory(
                cellData ->

                        new SimpleDoubleProperty(

                                cellData
                                        .getValue()
                                        .getLaserCurrent()
                        )
        );


        // =================================================
        // STATUS COLUMN
        // =================================================

        TableColumn<MonitoringHistory, String> statusColumn =
                new TableColumn<>(
                        "Status"
                );


        statusColumn.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(

                                cellData
                                        .getValue()
                                        .getStatus()
                        )
        );


        // =================================================
        // ADD COLUMNS
        // =================================================

        table.getColumns().addAll(

                timeColumn,

                temperatureColumn,

                voltageColumn,

                txPowerColumn,

                rxPowerColumn,

                laserCurrentColumn,

                statusColumn
        );


        VBox container =
                new VBox(
                        table
                );


        VBox.setVgrow(
                table,
                javafx.scene.layout.Priority.ALWAYS
        );


        return container;
    }


    // =====================================================
    // BUTTON PANEL
    // =====================================================

    private HBox createButtonPanel(
            Stage stage
    ) {


        Button refreshButton =
                new Button(
                        "🔄 Refresh"
                );


        Button closeButton =
                new Button(
                        "Close"
                );


        String style =
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 18;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        refreshButton.setStyle(
                style
        );


        closeButton.setStyle(
                style
        );


        // =================================================
        // REFRESH
        // =================================================

        refreshButton.setOnAction(
                event -> filterHistory()
        );


        // =================================================
        // CLOSE
        // =================================================

        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox buttons =
                new HBox(
                        10,

                        refreshButton,

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


    // =====================================================
    // LOAD ALL HISTORY
    // =====================================================

    private void loadAllHistory() {


        tableData.clear();


        for (
                Transceiver module :
                modules
        ) {

            List<MonitoringHistory> history =
                    historyService.getHistory(
                            module
                    );


            tableData.addAll(
                    history
            );
        }
    }


    // =====================================================
    // FILTER HISTORY
    // =====================================================

    private void filterHistory() {


        String selected =
                moduleSelector.getValue();


        tableData.clear();


        // =================================================
        // ALL MODULES
        // =================================================

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
        // FIND MODULE
        // =================================================

        for (
                Transceiver module :
                modules
        ) {

            String displayName =
                    module.getModuleId()
                            + " - "
                            + module.getModel();


            if (
                    displayName.equals(
                            selected
                    )
            ) {

                List<MonitoringHistory> history =
                        historyService.getHistory(
                                module
                        );


                tableData.addAll(
                        history
                );


                break;
            }
        }
    }
}