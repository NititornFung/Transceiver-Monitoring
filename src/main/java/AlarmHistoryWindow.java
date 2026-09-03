import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class AlarmHistoryWindow {


    // =========================================
    // SERVICE
    // =========================================

    private final AlarmHistoryService alarmHistoryService;


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


        stage.setTitle(
                "Alarm History"
        );


        // =========================================
        // TITLE
        // =========================================

        Label title =
                new Label(
                        "ALARM HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );


        // =========================================
        // ALARM CONTAINER
        // =========================================

        VBox alarmBox =
                new VBox(
                        10
                );


        alarmBox.setPadding(
                new Insets(20)
        );


        // =========================================
        // GET ALARM HISTORY
        // =========================================

        List<AlarmRecord> alarmList =
                alarmHistoryService.getAllAlarmHistory();


        // =========================================
        // EMPTY HISTORY
        // =========================================

        if (alarmList.isEmpty()) {


            Label emptyLabel =
                    new Label(
                            "No alarm history available."
                    );


            emptyLabel.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: gray;"
            );


            alarmBox.getChildren().add(
                    emptyLabel
            );


        } else {


            // =========================================
            // CREATE ALARM CARDS
            // =========================================

            for (
                    AlarmRecord alarm :
                    alarmList
            ) {


                VBox alarmCard =
                        createAlarmCard(
                                alarm
                        );


                alarmBox.getChildren().add(
                        alarmCard
                );
            }
        }


        // =========================================
        // SCROLL PANE
        // =========================================

        ScrollPane scrollPane =
                new ScrollPane(
                        alarmBox
                );


        scrollPane.setFitToWidth(
                true
        );


        scrollPane.setPrefHeight(
                450
        );


        // =========================================
        // CLOSE BUTTON
        // =========================================

        Button closeButton =
                new Button(
                        "CLOSE"
                );


        closeButton.setOnAction(
                ignored -> stage.close()
        );


        // =========================================
        // ROOT
        // =========================================

        VBox root =
                new VBox(
                        15,
                        title,
                        scrollPane,
                        closeButton
                );


        root.setPadding(
                new Insets(20)
        );


        root.setAlignment(
                Pos.CENTER
        );


        // =========================================
        // SCENE
        // =========================================

        Scene scene =
                new Scene(
                        root,
                        650,
                        600
                );


        stage.setScene(
                scene
        );


        stage.show();
    }


    // =========================================
    // CREATE ALARM CARD
    // =========================================

    private VBox createAlarmCard(
            AlarmRecord alarm
    ) {


        // =========================================
        // MODULE ID
        // =========================================

        Label moduleLabel =
                new Label(
                        "Module : "
                                + alarm.getModuleId()
                );


        // =========================================
        // ALARM TYPE
        // =========================================

        Label typeLabel =
                new Label(
                        "Alarm Type : "
                                + alarm.getType()
                );


        // =========================================
        // SEVERITY
        // =========================================

        Label severityLabel =
                new Label(
                        "Severity : "
                                + alarm.getSeverity()
                );


        // =========================================
        // MESSAGE
        // =========================================

        Label messageLabel =
                new Label(
                        "Message : "
                                + alarm.getMessage()
                );


        // =========================================
        // ACTUAL VALUE
        // =========================================

        Label actualLabel =
                new Label(
                        "Actual Value : "
                                + alarm.getActualValue()
                );


        // =========================================
        // THRESHOLD
        // =========================================

        Label thresholdLabel =
                new Label(
                        "Threshold : "
                                + alarm.getThreshold()
                );


        // =========================================
        // STATUS
        // =========================================

        Label statusLabel =
                new Label(
                        "Status : "
                                + alarm.getStatus()
                );


        // =========================================
        // TIME
        // =========================================

        Label timeLabel =
                new Label(
                        "Time : "
                                + alarm.getTimestamp()
                );


        // =========================================
        // STYLE
        // =========================================

        moduleLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;"
        );


        typeLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );


        severityLabel.setStyle(
                "-fx-font-weight: bold;"
        );


        // =========================================
        // CARD
        // =========================================

        VBox card =
                new VBox(
                        6,
                        moduleLabel,
                        typeLabel,
                        severityLabel,
                        messageLabel,
                        actualLabel,
                        thresholdLabel,
                        statusLabel,
                        timeLabel
                );


        card.setPadding(
                new Insets(15)
        );


        card.setStyle(
                "-fx-background-color: #fff5f5;" +
                        "-fx-border-color: #ff5555;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;"
        );


        return card;
    }
}