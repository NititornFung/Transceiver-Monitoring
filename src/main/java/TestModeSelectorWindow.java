import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TestModeSelectorWindow {

    private final List<Transceiver> modules;

    private final MonitoringService monitoringService;

    private final MonitoringHistoryService monitoringHistoryService;

    private final AlarmHistoryService alarmHistoryService;

    private final Runnable refreshDashboard;


    public TestModeSelectorWindow(
            List<Transceiver> modules,
            MonitoringService monitoringService,
            MonitoringHistoryService monitoringHistoryService,
            AlarmHistoryService alarmHistoryService,
            Runnable refreshDashboard
    ) {

        this.modules = modules;

        this.monitoringService = monitoringService;

        this.monitoringHistoryService = monitoringHistoryService;

        this.alarmHistoryService = alarmHistoryService;

        this.refreshDashboard = refreshDashboard;
    }


    public void show() {

        Stage stage = new Stage();


        // ==============================
        // TITLE
        // ==============================

        Label title = new Label(
                "TEST MODE SELECTOR"
        );

        title.setStyle(
                "-fx-font-size: 24px;"
                        + "-fx-font-weight: bold;"
        );


        Label description = new Label(
                "Select an Optical Transceiver Module"
        );

        description.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        // ==============================
        // MODULE SELECTOR
        // ==============================

        Label selectLabel = new Label(
                "Select Module:"
        );


        ComboBox<Transceiver> moduleComboBox =
                new ComboBox<>();


        moduleComboBox.getItems().addAll(
                modules
        );


        moduleComboBox.setPrefWidth(
                350
        );


        // Default เลือกตัวแรก

        if (!modules.isEmpty()) {

            moduleComboBox.getSelectionModel()
                    .select(0);
        }


        // ==============================
        // OPEN BUTTON
        // ==============================

        Button openButton =
                new Button(
                        "OPEN TEST MODE"
                );


        openButton.setPrefWidth(
                350
        );


        openButton.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 12;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
        );


        openButton.setOnAction(event -> {

            Transceiver selectedModule =
                    moduleComboBox.getValue();


            if (selectedModule == null) {

                return;
            }


            TestModeWindow testModeWindow =
                    new TestModeWindow(

                            selectedModule,

                            monitoringService,

                            monitoringHistoryService,

                            alarmHistoryService,

                            refreshDashboard
                    );


            testModeWindow.show();


            stage.close();
        });


        // ==============================
        // ROOT
        // ==============================

        VBox root =
                new VBox(
                        15,

                        title,

                        description,

                        selectLabel,

                        moduleComboBox,

                        openButton
                );


        root.setPadding(
                new Insets(
                        30
                )
        );


        root.setAlignment(
                Pos.CENTER
        );


        root.setStyle(
                "-fx-background-color: white;"
        );


        Scene scene =
                new Scene(
                        root,
                        450,
                        350
                );


        stage.setTitle(
                "Test Mode Selector"
        );


        stage.setScene(
                scene
        );


        stage.show();
    }
}