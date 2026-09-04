import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class AlarmHistoryWindow {


    // =========================================
    // SERVICE
    // =========================================

    private final AlarmHistoryService alarmHistoryService;


    // =========================================
    // TABLE
    // =========================================

    private TableView<AlarmRecord> table;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public AlarmHistoryWindow(
            AlarmHistoryService alarmHistoryService
    ) {

        this.alarmHistoryService =
                alarmHistoryService;
    }


    // =========================================
    // SHOW WINDOW
    // =========================================

    public void show() {


        Stage stage =
                new Stage();


        // =====================================
        // ROOT
        // =====================================

        BorderPane root =
                new BorderPane();

        root.setPadding(
                new Insets(20)
        );

        root.setStyle(
                "-fx-background-color: #f4f6f9;"
        );


        // =====================================
        // HEADER
        // =====================================

        VBox header =
                createHeader();


        root.setTop(
                header
        );


        // =====================================
        // TABLE
        // =====================================

        table =
                createAlarmTable();


        loadAlarmData();


        root.setCenter(
                table
        );


        // =====================================
        // BUTTON PANEL
        // =====================================

        HBox buttonPanel =
                createButtonPanel();


        root.setBottom(
                buttonPanel
        );


        // =====================================
        // SCENE
        // =====================================

        Scene scene =
                new Scene(
                        root,
                        1200,
                        650
                );


        stage.setTitle(
                "Alarm History"
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
                        "🚨 ALARM HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "Alarm Events and Monitoring History"
                );


        subtitle.setStyle(
                "-fx-font-size: 14px;"
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
    // CREATE TABLE
    // =========================================

    private TableView<AlarmRecord> createAlarmTable() {


        TableView<AlarmRecord> alarmTable =
                new TableView<>();


        alarmTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );


        // =====================================
        // TIME COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > timeColumn =
                new TableColumn<>(
                        "TIME"
                );


        timeColumn.setCellValueFactory(
                data -> {

                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd HH:mm:ss"
                            );


                    return new SimpleStringProperty(

                            data.getValue()
                                    .getTimestamp()
                                    .format(formatter)
                    );
                }
        );


        // =====================================
        // MODULE COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > moduleColumn =
                new TableColumn<>(
                        "MODULE"
                );


        moduleColumn.setCellValueFactory(
                data ->

                        new SimpleStringProperty(

                                data.getValue()
                                        .getModuleId()
                        )
        );


        // =====================================
        // TYPE COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > typeColumn =
                new TableColumn<>(
                        "ALARM TYPE"
                );


        typeColumn.setCellValueFactory(
                data ->

                        new SimpleStringProperty(

                                data.getValue()
                                        .getType()
                                        .toString()
                        )
        );


        // =====================================
        // SEVERITY COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > severityColumn =
                new TableColumn<>(
                        "SEVERITY"
                );


        severityColumn.setCellValueFactory(
                data -> {

                    AlarmSeverity severity =
                            data.getValue()
                                    .getSeverity();


                    String display;


                    if (severity == AlarmSeverity.CRITICAL) {

                        display =
                                "🔴 CRITICAL";

                    }

                    else {

                        display =
                                "🟠 WARNING";
                    }


                    return new SimpleStringProperty(
                            display
                    );
                }
        );


        // =====================================
        // SOURCE COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > sourceColumn =
                new TableColumn<>(
                        "SOURCE"
                );


        sourceColumn.setCellValueFactory(
                data -> {

                    AlarmSource source =
                            data.getValue()
                                    .getSource();


                    String display;


                    if (source == AlarmSource.TEST) {

                        display =
                                "🧪 TEST";

                    }

                    else {

                        display =
                                "🔴 REAL";
                    }


                    return new SimpleStringProperty(
                            display
                    );
                }
        );


        // =====================================
        // MESSAGE COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > messageColumn =
                new TableColumn<>(
                        "MESSAGE"
                );


        messageColumn.setCellValueFactory(
                data ->

                        new SimpleStringProperty(

                                data.getValue()
                                        .getMessage()
                        )
        );


        // =====================================
        // ACTUAL VALUE COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > actualValueColumn =
                new TableColumn<>(
                        "ACTUAL"
                );


        actualValueColumn.setCellValueFactory(
                data ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        data.getValue()
                                                .getActualValue()
                                )
                        )
        );


        // =====================================
        // THRESHOLD COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > thresholdColumn =
                new TableColumn<>(
                        "THRESHOLD"
                );


        thresholdColumn.setCellValueFactory(
                data ->

                        new SimpleStringProperty(

                                String.format(

                                        "%.2f",

                                        data.getValue()
                                                .getThreshold()
                                )
                        )
        );


        // =====================================
        // STATUS COLUMN
        // =====================================

        TableColumn<
                AlarmRecord,
                String
                > statusColumn =
                new TableColumn<>(
                        "STATUS"
                );


        statusColumn.setCellValueFactory(
                data -> {

                    AlarmStatus status =
                            data.getValue()
                                    .getStatus();


                    String display;


                    if (status == AlarmStatus.ACTIVE) {

                        display =
                                "🔴 ACTIVE";

                    }

                    else if (
                            status
                                    == AlarmStatus.ACKNOWLEDGED
                    ) {

                        display =
                                "🟡 ACKNOWLEDGED";

                    }

                    else {

                        display =
                                "🟢 CLEARED";
                    }


                    return new SimpleStringProperty(
                            display
                    );
                }
        );


        // =====================================
        // ADD COLUMNS
        // =====================================

        alarmTable.getColumns().addAll(

                timeColumn,

                moduleColumn,

                typeColumn,

                severityColumn,

                sourceColumn,

                messageColumn,

                actualValueColumn,

                thresholdColumn,

                statusColumn
        );


        return alarmTable;
    }


    // =========================================
    // LOAD ALARM DATA
    // =========================================

    private void loadAlarmData() {


        List<AlarmRecord> alarmList =
                alarmHistoryService
                        .getAllAlarmHistory();


        ObservableList<AlarmRecord> data =
                FXCollections.observableArrayList(
                        alarmList
                );


        table.setItems(
                data
        );
    }


    // =========================================
    // BUTTON PANEL
    // =========================================

    private HBox createButtonPanel() {


        Button refreshButton =
                new Button(
                        "🔄 Refresh"
                );


        Button acknowledgeButton =
                new Button(
                        "✓ Acknowledge"
                );


        Button clearButton =
                new Button(
                        "Clear Alarm"
                );


        Button closeButton =
                new Button(
                        "Close"
                );


        String buttonStyle =
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 15;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 8;";


        refreshButton.setStyle(
                buttonStyle
        );

        acknowledgeButton.setStyle(
                buttonStyle
        );

        clearButton.setStyle(
                buttonStyle
        );

        closeButton.setStyle(
                buttonStyle
        );


        // =====================================
        // REFRESH
        // =====================================

        refreshButton.setOnAction(
                event -> loadAlarmData()
        );


        // =====================================
        // ACKNOWLEDGE
        // =====================================

        acknowledgeButton.setOnAction(
                event -> acknowledgeSelectedAlarm()
        );


        // =====================================
        // CLEAR
        // =====================================

        clearButton.setOnAction(
                event -> clearSelectedAlarm()
        );


        // =====================================
        // CLOSE
        // =====================================

        closeButton.setOnAction(
                event -> {

                    Stage stage =
                            (Stage) closeButton
                                    .getScene()
                                    .getWindow();


                    stage.close();
                }
        );


        HBox panel =
                new HBox(
                        10,

                        refreshButton,

                        acknowledgeButton,

                        clearButton,

                        closeButton
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


    // =========================================
    // ACKNOWLEDGE SELECTED ALARM
    // =========================================

    private void acknowledgeSelectedAlarm() {


        AlarmRecord selectedAlarm =
                table.getSelectionModel()
                        .getSelectedItem();


        if (selectedAlarm == null) {

            return;
        }


        selectedAlarm.acknowledge();


        table.refresh();
    }


    // =========================================
    // CLEAR SELECTED ALARM
    // =========================================

    private void clearSelectedAlarm() {


        AlarmRecord selectedAlarm =
                table.getSelectionModel()
                        .getSelectedItem();


        if (selectedAlarm == null) {

            return;
        }


        selectedAlarm.clear();


        table.refresh();
    }
}