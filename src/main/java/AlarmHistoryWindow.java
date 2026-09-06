import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class AlarmHistoryWindow {


    // =====================================================
    // SERVICES
    // =====================================================

    private final AlarmHistoryService alarmHistoryService;


    // =====================================================
    // MODULES
    // =====================================================

    private final List<Transceiver> modules;


    // =====================================================
    // TABLE
    // =====================================================

    private TableView<AlarmRecord> table;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public AlarmHistoryWindow(

            AlarmHistoryService alarmHistoryService,

            List<Transceiver> modules
    ) {

        this.alarmHistoryService =
                alarmHistoryService;

        this.modules =
                modules;
    }


    // =====================================================
    // SHOW WINDOW
    // =====================================================

    public void show() {


        Stage stage =
                new Stage();


        // =================================================
        // ROOT
        // =================================================

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
                        700
                );


        stage.setTitle(
                "Alarm History"
        );

        stage.setScene(
                scene
        );

        stage.show();


        // =================================================
        // LOAD DATA
        // =================================================

        loadHistory();
    }


    // =====================================================
    // CREATE HEADER
    // =====================================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "🚨 ALARM HISTORY"
                );

        title.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "Historical alarm records from monitoring and test operations"
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


    // =====================================================
    // CREATE TABLE
    // =====================================================

    private TableView<AlarmRecord> createTable() {


        TableView<AlarmRecord> alarmTable =
                new TableView<>();


        // =================================================
        // TIME COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> timeColumn =
                new TableColumn<>(
                        "Time"
                );


        timeColumn.setPrefWidth(
                180
        );


        timeColumn.setCellValueFactory(

                cellData -> {

                    if (
                            cellData.getValue()
                                    .getTimestamp()
                                    == null
                    ) {

                        return new SimpleStringProperty(
                                ""
                        );
                    }


                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd HH:mm:ss"
                            );


                    return new SimpleStringProperty(

                            cellData.getValue()
                                    .getTimestamp()
                                    .format(
                                            formatter
                                    )
                    );
                }
        );


        // =================================================
        // MODULE ID COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> moduleColumn =
                new TableColumn<>(
                        "Module ID"
                );


        moduleColumn.setPrefWidth(
                100
        );


        moduleColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "moduleId"
                )
        );


        // =================================================
        // ALARM TYPE COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> typeColumn =
                new TableColumn<>(
                        "Alarm Type"
                );


        typeColumn.setPrefWidth(
                150
        );


        typeColumn.setCellValueFactory(

                cellData -> {

                    AlarmType type =
                            cellData.getValue()
                                    .getType();


                    String value =
                            type != null
                                    ? type.toString()
                                    : "";


                    return new SimpleStringProperty(
                            value
                    );
                }
        );


        // =================================================
        // SEVERITY COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> severityColumn =
                new TableColumn<>(
                        "Severity"
                );


        severityColumn.setPrefWidth(
                100
        );


        severityColumn.setCellValueFactory(

                cellData -> {

                    AlarmSeverity severity =
                            cellData.getValue()
                                    .getSeverity();


                    String value =
                            severity != null
                                    ? severity.toString()
                                    : "";


                    return new SimpleStringProperty(
                            value
                    );
                }
        );


        // =================================================
        // MESSAGE COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> messageColumn =
                new TableColumn<>(
                        "Message"
                );


        messageColumn.setPrefWidth(
                280
        );


        messageColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "message"
                )
        );


        // =================================================
        // ACTUAL VALUE COLUMN
        // =================================================

        TableColumn<AlarmRecord, Double> actualValueColumn =
                new TableColumn<>(
                        "Actual Value"
                );


        actualValueColumn.setPrefWidth(
                110
        );


        actualValueColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "actualValue"
                )
        );


        // =================================================
        // THRESHOLD COLUMN
        // =================================================

        TableColumn<AlarmRecord, Double> thresholdColumn =
                new TableColumn<>(
                        "Threshold"
                );


        thresholdColumn.setPrefWidth(
                100
        );


        thresholdColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "threshold"
                )
        );


        // =================================================
        // SOURCE COLUMN
        // =================================================

        TableColumn<AlarmRecord, String> sourceColumn =
                new TableColumn<>(
                        "Source"
                );


        sourceColumn.setPrefWidth(
                120
        );


        sourceColumn.setCellValueFactory(

                cellData -> {

                    AlarmSource source =
                            cellData.getValue()
                                    .getSource();


                    String value =
                            source != null
                                    ? source.toString()
                                    : "";


                    return new SimpleStringProperty(
                            value
                    );
                }
        );


        // =================================================
        // ADD COLUMNS
        // =================================================

        alarmTable.getColumns().addAll(

                timeColumn,

                moduleColumn,

                typeColumn,

                severityColumn,

                messageColumn,

                actualValueColumn,

                thresholdColumn,

                sourceColumn
        );


        return alarmTable;
    }


    // =====================================================
    // CREATE BOTTOM PANEL
    // =====================================================

    private HBox createBottomPanel(
            Stage stage
    ) {


        Button refreshButton =
                new Button(
                        "🔄 Refresh"
                );


        Button exportButton =
                new Button(
                        "📁 Export CSV"
                );


        Button closeButton =
                new Button(
                        "Close"
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


        refreshButton.setStyle(
                buttonStyle
        );

        exportButton.setStyle(
                buttonStyle
        );

        closeButton.setStyle(
                buttonStyle
        );


        // =================================================
        // BUTTON ACTIONS
        // =================================================

        refreshButton.setOnAction(
                event -> loadHistory()
        );


        exportButton.setOnAction(
                event -> openExportWindow()
        );


        closeButton.setOnAction(
                event -> stage.close()
        );


        HBox panel =
                new HBox(
                        10,
                        refreshButton,
                        exportButton,
                        closeButton
                );


        panel.setAlignment(
                Pos.CENTER_RIGHT
        );


        panel.setPadding(
                new Insets(
                        20,
                        0,
                        0,
                        0
                )
        );


        return panel;
    }


    // =====================================================
    // LOAD HISTORY
    // =====================================================

    private void loadHistory() {


        if (alarmHistoryService == null) {

            return;
        }


        List<AlarmRecord> history =

                alarmHistoryService
                        .getAllAlarmHistory();


        ObservableList<AlarmRecord> data =

                FXCollections.observableArrayList(
                        history
                );


        table.setItems(
                data
        );
    }


    // =====================================================
    // OPEN EXPORT WINDOW
    // =====================================================

    private void openExportWindow() {


        ExportAlarmHistoryWindow exportWindow =
                new ExportAlarmHistoryWindow(

                        alarmHistoryService,

                        modules
                );


        exportWindow.show();
    }
}