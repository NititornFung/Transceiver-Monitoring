import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class MonitoringHistoryWindow {


    private final MonitoringHistoryService historyService;


    public MonitoringHistoryWindow(
            MonitoringHistoryService historyService
    ) {

        this.historyService =
                historyService;
    }


    public void show() {


        Stage stage =
                new Stage();


        stage.setTitle(
                "Monitoring History"
        );


        Label title =
                new Label(
                        "MONITORING HISTORY"
                );


        title.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );


        VBox historyBox =
                new VBox(
                        10
                );


        historyBox.setPadding(
                new Insets(20)
        );


        List<MonitoringHistory> historyList =
                historyService.getHistoryList();


        if (historyList.isEmpty()) {


            Label emptyLabel =
                    new Label(
                            "No monitoring history available."
                    );


            historyBox.getChildren().add(
                    emptyLabel
            );


        } else {


            for (
                    MonitoringHistory history :
                    historyList
            ) {


                historyBox.getChildren().add(
                        createHistoryCard(
                                history
                        )
                );
            }
        }


        ScrollPane scrollPane =
                new ScrollPane(
                        historyBox
                );


        scrollPane.setFitToWidth(
                true
        );


        Button closeButton =
                new Button(
                        "CLOSE"
                );


        closeButton.setOnAction(
                ignored -> stage.close()
        );


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


    private VBox createHistoryCard(
            MonitoringHistory history
    ) {


        Transceiver module =
                history.getModule();


        Label moduleLabel =
                new Label(
                        "Module : "
                                + module.getModuleId()
                );


        Label modelLabel =
                new Label(
                        "Model : "
                                + module.getModel()
                );


        Label statusLabel =
                new Label(
                        "Status : "
                                + history.getStatus()
                );


        Label timeLabel =
                new Label(
                        "Time : "
                                + history.getFormattedTime()
                );


        VBox card =
                new VBox(
                        6,
                        moduleLabel,
                        modelLabel,
                        statusLabel,
                        timeLabel
                );


        card.setPadding(
                new Insets(15)
        );


        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;"
        );


        return card;
    }
}