import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class TestModeSelectionWindow {


    // =========================================
    // DATA
    // =========================================

    private final List<Transceiver> modules;


    // =========================================
    // SERVICES
    // =========================================

    private final MonitoringService monitoringService;

    private final MonitoringHistoryService monitoringHistoryService;

    private final AlarmHistoryService alarmHistoryService;

    private final Runnable refreshDashboard;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public TestModeSelectionWindow(

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


    // =========================================
    // SHOW WINDOW
    // =========================================

    public void show() {


        Stage stage =
                new Stage();


        // =====================================
        // ROOT
        // =====================================

        VBox root =
                new VBox(15);


        root.setPadding(
                new Insets(30)
        );


        root.setAlignment(
                Pos.TOP_CENTER
        );


        root.setStyle(
                "-fx-background-color: #f4f6f9;"
        );


        // =====================================
        // HEADER
        // =====================================

        VBox header =
                createHeader();


        // =====================================
        // MODULE LIST
        // =====================================

        VBox moduleContainer =
                createModuleList(stage);


        // =====================================
        // CLOSE BUTTON
        // =====================================

        Button closeButton =
                createCloseButton(stage);


        // =====================================
        // ADD ALL COMPONENTS
        // =====================================

        root.getChildren().addAll(

                header,

                moduleContainer,

                new Separator(),

                closeButton
        );


        // =====================================
        // SCENE
        // =====================================

        Scene scene =
                new Scene(
                        root,
                        550,
                        550
                );


        stage.setTitle(
                "Select Module for Test"
        );


        stage.setScene(
                scene
        );


        stage.show();
    }


    // =========================================
    // CREATE HEADER
    // =========================================

    private VBox createHeader() {


        Label title =
                new Label(
                        "🧪 SELECT MODULE"
                );


        title.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label subtitle =
                new Label(
                        "Select a transceiver module to enter Test Mode"
                );


        subtitle.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        VBox header =
                new VBox(
                        8,
                        title,
                        subtitle,
                        new Separator()
                );


        header.setAlignment(
                Pos.CENTER
        );


        header.setPadding(
                new Insets(
                        0,
                        0,
                        15,
                        0
                )
        );


        return header;
    }


    // =========================================
    // CREATE MODULE LIST
    // =========================================

    private VBox createModuleList(
            Stage stage
    ) {


        VBox moduleContainer =
                new VBox(12);


        moduleContainer.setAlignment(
                Pos.CENTER
        );


        // =====================================
        // CHECK MODULE
        // =====================================

        if (modules == null
                || modules.isEmpty()) {


            Label emptyLabel =
                    new Label(
                            "No modules available"
                    );


            emptyLabel.setStyle(
                    "-fx-font-size: 16px;"
                            + "-fx-text-fill: #e74c3c;"
            );


            moduleContainer.getChildren().add(
                    emptyLabel
            );


            return moduleContainer;
        }


        // =====================================
        // CREATE BUTTON FROM REAL DATA
        // =====================================

        for (Transceiver module : modules) {


            Button moduleButton =
                    createModuleButton(
                            module,
                            stage
                    );


            moduleContainer.getChildren().add(
                    moduleButton
            );
        }


        return moduleContainer;
    }


    // =========================================
    // CREATE MODULE BUTTON
    // =========================================

    private Button createModuleButton(

            Transceiver module,

            Stage selectionStage
    ) {


        // =====================================
        // IMPORTANT
        //
        // ใช้ข้อมูลจริงจาก Transceiver
        // ไม่ Hardcode ชื่อ Model
        // =====================================

        String moduleId =
                module.getModuleId();


        String model =
                module.getModel();


        Label moduleIdLabel =
                new Label(
                        moduleId
                );


        moduleIdLabel.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2c3e50;"
        );


        Label modelLabel =
                new Label(
                        model
                );


        modelLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #7f8c8d;"
        );


        VBox textBox =
                new VBox(
                        3,
                        moduleIdLabel,
                        modelLabel
                );


        HBox content =
                new HBox(
                        15,
                        textBox
                );


        content.setAlignment(
                Pos.CENTER_LEFT
        );


        HBox.setHgrow(
                textBox,
                Priority.ALWAYS
        );


        Button moduleButton =
                new Button();


        moduleButton.setGraphic(
                content
        );


        moduleButton.setPrefWidth(
                450
        );


        moduleButton.setPrefHeight(
                65
        );


        moduleButton.setAlignment(
                Pos.CENTER_LEFT
        );


        String normalStyle =
                "-fx-background-color: white;"
                        + "-fx-border-color: #dcdde1;"
                        + "-fx-border-radius: 10;"
                        + "-fx-background-radius: 10;"
                        + "-fx-cursor: hand;"
                        + "-fx-padding: 10 20;";


        String hoverStyle =
                "-fx-background-color: #ecf5ff;"
                        + "-fx-border-color: #3498db;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-radius: 10;"
                        + "-fx-background-radius: 10;"
                        + "-fx-cursor: hand;"
                        + "-fx-padding: 10 20;";


        moduleButton.setStyle(
                normalStyle
        );


        // =====================================
        // HOVER
        // =====================================

        moduleButton.setOnMouseEntered(
                event -> moduleButton.setStyle(
                        hoverStyle
                )
        );


        moduleButton.setOnMouseExited(
                event -> moduleButton.setStyle(
                        normalStyle
                )
        );


        // =====================================
        // OPEN TEST MODE
        // =====================================

        moduleButton.setOnAction(
                event -> {


                    TestModeWindow testWindow =
                            new TestModeWindow(

                                    module,

                                    monitoringService,

                                    monitoringHistoryService,

                                    alarmHistoryService,

                                    refreshDashboard
                            );


                    testWindow.show();


                    selectionStage.close();
                }
        );


        return moduleButton;
    }


    // =========================================
    // CREATE CLOSE BUTTON
    // =========================================

    private Button createCloseButton(
            Stage stage
    ) {


        Button closeButton =
                new Button(
                        "CLOSE"
                );


        closeButton.setPrefWidth(
                150
        );


        closeButton.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 10 20;"
                        + "-fx-background-color: white;"
                        + "-fx-text-fill: #e74c3c;"
                        + "-fx-border-color: #e74c3c;"
                        + "-fx-border-radius: 8;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
        );


        closeButton.setOnAction(
                event -> stage.close()
        );


        return closeButton;
    }
}