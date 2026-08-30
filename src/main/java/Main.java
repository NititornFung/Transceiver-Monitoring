import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =================================
        // Create Transceiver
        // =================================

        Transceiver tx001 =
                new Transceiver(
                        "TX001",
                        "400G COSA"
                );

        Transceiver tx002 =
                new Transceiver(
                        "TX002",
                        "800G COSAz"
                );

        Transceiver tx003 =
                new Transceiver(
                        "TX003",
                        "800G SR8"
                );


        // =================================
        // Services
        // =================================

        TelemetrySimulator simulator =
                new TelemetrySimulator();

        MonitoringService monitoringService =
                new MonitoringService();

        MonitoringHistoryService historyService =
                new MonitoringHistoryService();

        InputHandler inputHandler =
                new InputHandler();


        // =================================
        // Module List
        // =================================

        List<Transceiver> modules =
                new ArrayList<>();

        modules.add(tx001);
        modules.add(tx002);
        modules.add(tx003);


        // =================================
        // Start Input Listener
        // =================================

        inputHandler.start();


        // =================================
        // Program Running
        // =================================

        boolean running = true;


        while (running) {

            // =================================
            // Check User Command
            // =================================

            String command =
                    inputHandler.getCommand();


            // =================================
            // H = History
            // =================================

            if (command != null
                    && command.equals("H")) {

                System.out.println();

                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "       MONITORING PAUSED"
                );

                System.out.println(
                        "       HISTORY MENU"
                );

                System.out.println(
                        "========================================"
                );


                boolean historyMenu = true;


                while (historyMenu) {

                    System.out.println();

                    System.out.println(
                            "[1] TX001 - 400G COSA"
                    );

                    System.out.println(
                            "[2] TX002 - 800G COSAz"
                    );

                    System.out.println(
                            "[3] TX003 - 800G SR8"
                    );

                    System.out.println();

                    System.out.println(
                            "[0] Continue Monitoring"
                    );

                    System.out.println(
                            "[Q] Quit Program"
                    );

                    System.out.print(
                            "Select : "
                    );


                    String historyCommand;


                    // =================================
                    // Wait for Command
                    // =================================

                    do {

                        historyCommand =
                                inputHandler.getCommand();

                        try {

                            Thread.sleep(100);

                        } catch (InterruptedException e) {

                            Thread.currentThread()
                                    .interrupt();

                            return;
                        }

                    } while (historyCommand == null);


                    // =================================
                    // TX001
                    // =================================

                    if (historyCommand.equals("1")) {

                        historyService.printHistory(
                                tx001
                        );
                    }


                    // =================================
                    // TX002
                    // =================================

                    else if (historyCommand.equals("2")) {

                        historyService.printHistory(
                                tx002
                        );
                    }


                    // =================================
                    // TX003
                    // =================================

                    else if (historyCommand.equals("3")) {

                        historyService.printHistory(
                                tx003
                        );
                    }


                    // =================================
                    // Continue
                    // =================================

                    else if (historyCommand.equals("0")) {

                        historyMenu = false;

                        System.out.println();

                        System.out.println(
                                "========================================"
                        );

                        System.out.println(
                                "       MONITORING RESUMED"
                        );

                        System.out.println(
                                "========================================"
                        );
                    }


                    // =================================
                    // Quit
                    // =================================

                    else if (historyCommand.equals("Q")) {

                        running = false;

                        historyMenu = false;
                    }


                    // =================================
                    // Invalid
                    // =================================

                    else {

                        System.out.println(
                                "Invalid command."
                        );
                    }
                }

                continue;
            }


            // =================================
            // Q = Quit
            // =================================

            if (command != null
                    && command.equals("Q")) {

                running = false;

                break;
            }


            // =================================
            // Monitoring Header
            // =================================

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "       TRANSCEIVER MONITORING"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "[H] History"
            );

            System.out.println(
                    "[Q] Quit"
            );

            System.out.println(
                    "========================================"
            );


            // =================================
            // Update Telemetry
            // =================================

            for (Transceiver module : modules) {

                Telemetry telemetry =
                        simulator.generateTelemetry(
                                module.getModel()
                        );

                module.updateTelemetry(
                        telemetry
                );
            }


            // =================================
            // Monitoring
            // =================================

            for (Transceiver module : modules) {

                MonitoringReport report =
                        monitoringService.monitor(
                                module
                        );


                // =================================
                // Save History
                // =================================

                historyService.addHistory(
                        module,
                        report.getStatus()
                );


                // =================================
                // Module Information
                // =================================

                System.out.println();

                System.out.println(
                        "------------------------------"
                );

                System.out.println(
                        "Module ID : "
                                + module.getModuleId()
                );

                System.out.println(
                        "Model     : "
                                + module.getModel()
                );


                // =================================
                // Telemetry
                // =================================

                System.out.println(
                        "Temperature : "
                                + module.getTelemetry()
                                .getTemperature()
                                + " °C"
                );

                System.out.println(
                        "Voltage : "
                                + module.getTelemetry()
                                .getVoltage()
                                + " V"
                );

                System.out.println(
                        "RX Power : "
                                + module.getTelemetry()
                                .getRxPower()
                                + " dBm"
                );

                System.out.println(
                        "TX Power : "
                                + module.getTelemetry()
                                .getTxPower()
                                + " dBm"
                );

                System.out.println(
                        "Laser Current : "
                                + module.getTelemetry()
                                .getLaserCurrent()
                                + " mA"
                );

                System.out.println(
                        "Wavelength : "
                                + module.getTelemetry()
                                .getWavelength()
                                + " nm"
                );


                // =================================
                // Status
                // =================================

                System.out.println(
                        "Status : "
                                + report.getStatus()
                );


                // =================================
                // Alarm
                // =================================

                List<Alarm> alarms =
                        report.getAlarms();


                if (alarms.isEmpty()) {

                    System.out.println(
                            "Alarm : None"
                    );

                } else {

                    for (Alarm alarm : alarms) {

                        System.out.println(
                                "⚠ Alarm Type : "
                                        + alarm.getType()
                        );

                        System.out.println(
                                "  Severity   : "
                                        + alarm.getSeverity()
                        );

                        System.out.println(
                                "  Message    : "
                                        + alarm.getMessage()
                        );

                        System.out.println(
                                "  Actual     : "
                                        + alarm.getActualValue()
                        );

                        System.out.println(
                                "  Threshold  : "
                                        + alarm.getThreshold()
                        );
                    }
                }
            }


            // =================================
            // Wait 1 Second
            // =================================

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                break;
            }
        }


        // =================================
        // Stop Input
        // =================================

        inputHandler.stop();


        // =================================
        // Program Closed
        // =================================

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "       MONITORING STOPPED"
        );

        System.out.println(
                "       PROGRAM CLOSED"
        );

        System.out.println(
                "========================================"
        );
    }
}