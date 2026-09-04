import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {


    public static void main(String[] args) {


        // =========================================
        // Create Transceivers
        // =========================================

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


        // =========================================
        // Create Services
        // =========================================

        TelemetrySimulator simulator =
                new TelemetrySimulator();


        MonitoringService monitoringService =
                new MonitoringService();


        MonitoringHistoryService historyService =
                new MonitoringHistoryService();


        AlarmHistoryService alarmHistoryService =
                new AlarmHistoryService();


        InputHandler inputHandler =
                new InputHandler();


        // =========================================
        // Create Test Mode
        // =========================================

        TestMode testMode =
                new TestMode(
                        monitoringService,
                        historyService,
                        alarmHistoryService,
                        inputHandler
                );


        // =========================================
        // Module List
        // =========================================

        List<Transceiver> modules =
                new ArrayList<>();


        modules.add(tx001);
        modules.add(tx002);
        modules.add(tx003);


        // =========================================
        // Start Input Handler
        // =========================================

        inputHandler.start();


        // =========================================
        // Main Program
        // =========================================

        boolean running = true;


        while (running) {


            // =====================================
            // Check User Command
            // =====================================

            String command =
                    inputHandler.getCommand();


            // =====================================
            // H = History / Test Center
            // =====================================

            if (
                    command != null
                            && command.equalsIgnoreCase("H")
            ) {

                boolean menuRunning = true;


                System.out.println();

                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "       MONITORING PAUSED"
                );

                System.out.println(
                        "========================================"
                );


                while (menuRunning) {


                    System.out.println();

                    System.out.println(
                            "========================================"
                    );

                    System.out.println(
                            "       HISTORY / TEST CENTER"
                    );

                    System.out.println(
                            "========================================"
                    );

                    System.out.println(
                            "[1] TX001 Monitoring History"
                    );

                    System.out.println(
                            "[2] TX002 Monitoring History"
                    );

                    System.out.println(
                            "[3] TX003 Monitoring History"
                    );

                    System.out.println();

                    System.out.println(
                            "[4] TX001 Alarm History"
                    );

                    System.out.println(
                            "[5] TX002 Alarm History"
                    );

                    System.out.println(
                            "[6] TX003 Alarm History"
                    );

                    System.out.println();

                    System.out.println(
                            "[7] TEST MODE"
                    );

                    System.out.println();

                    System.out.println(
                            "[0] Continue Monitoring"
                    );

                    System.out.println(
                            "[Q] Quit Program"
                    );

                    System.out.println(
                            "========================================"
                    );

                    System.out.print(
                            "Select : "
                    );


                    String menuCommand;


                    do {

                        menuCommand =
                                inputHandler.getCommand();

                        try {

                            Thread.sleep(100);

                        } catch (InterruptedException e) {

                            Thread.currentThread()
                                    .interrupt();

                            return;
                        }

                    } while (menuCommand == null);


                    // =================================
                    // Monitoring History
                    // =================================

                    if (menuCommand.equals("1")) {

                        historyService.printHistory(
                                tx001
                        );
                    }


                    else if (menuCommand.equals("2")) {

                        historyService.printHistory(
                                tx002
                        );
                    }


                    else if (menuCommand.equals("3")) {

                        historyService.printHistory(
                                tx003
                        );
                    }


                    // =================================
                    // Alarm History
                    // =================================

                    else if (menuCommand.equals("4")) {

                        alarmMenu(
                                tx001,
                                alarmHistoryService,
                                inputHandler
                        );
                    }


                    else if (menuCommand.equals("5")) {

                        alarmMenu(
                                tx002,
                                alarmHistoryService,
                                inputHandler
                        );
                    }


                    else if (menuCommand.equals("6")) {

                        alarmMenu(
                                tx003,
                                alarmHistoryService,
                                inputHandler
                        );
                    }


                    // =================================
                    // TEST MODE
                    // =================================

                    else if (menuCommand.equals("7")) {

                        Transceiver selectedModule =
                                selectModule(
                                        tx001,
                                        tx002,
                                        tx003,
                                        inputHandler
                                );


                        if (selectedModule != null) {

                            testMode.open(
                                    selectedModule
                            );
                        }
                    }


                    // =================================
                    // Continue Monitoring
                    // =================================

                    else if (menuCommand.equals("0")) {

                        menuRunning = false;


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

                    else if (
                            menuCommand.equalsIgnoreCase("Q")
                    ) {

                        running = false;

                        menuRunning = false;
                    }


                    // =================================
                    // Invalid
                    // =================================

                    else {

                        System.out.println();

                        System.out.println(
                                "Invalid command."
                        );
                    }
                }


                continue;
            }


            // =====================================
            // Q = Quit
            // =====================================

            if (
                    command != null
                            && command.equalsIgnoreCase("Q")
            ) {

                running = false;

                break;
            }


            // =====================================
            // Monitoring Header
            // =====================================

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


            // =====================================
            // Update Telemetry
            // =====================================

            for (Transceiver module : modules) {

                Telemetry telemetry =
                        simulator.generateTelemetry(
                                module.getModel()
                        );


                module.updateTelemetry(
                        telemetry
                );
            }


            // =====================================
            // Monitor Every Module
            // =====================================

            for (Transceiver module : modules) {


                // =================================
                // Generate Monitoring Report
                // =================================

                MonitoringReport report =
                        monitoringService.monitor(
                                module
                        );


                // =================================
                // Save Monitoring History
                // =================================

                historyService.addHistory(
                        module,
                        report.getStatus()
                );


                // =================================
                // Convert REAL Alarm
                // Alarm -> AlarmRecord
                // =================================

                List<AlarmRecord> realAlarms =
                        new ArrayList<>();


                for (Alarm alarm : report.getAlarms()) {

                    AlarmRecord alarmRecord =
                            new AlarmRecord(

                                    module.getModuleId(),

                                    LocalDateTime.now(),

                                    alarm.getType(),

                                    alarm.getSeverity(),

                                    alarm.getMessage(),

                                    alarm.getActualValue(),

                                    alarm.getThreshold(),

                                    AlarmSource.REAL
                            );


                    realAlarms.add(
                            alarmRecord
                    );
                }


                // =================================
                // Save REAL Alarm History
                // =================================

                if (!realAlarms.isEmpty()) {

                    alarmHistoryService.addAlarms(

                            module,

                            realAlarms
                    );
                }


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

                        System.out.println();

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

                        System.out.println(
                                "  Source     : REAL"
                        );
                    }
                }
            }


            // =====================================
            // Command Hint
            // =====================================

            System.out.println();

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "[H] History / Test"
            );

            System.out.println(
                    "[Q] Quit"
            );

            System.out.println(
                    "----------------------------------------"
            );


            // =====================================
            // Wait 1 Second
            // =====================================

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();

                break;
            }
        }


        // =========================================
        // Stop Input Handler
        // =========================================

        inputHandler.stop();


        // =========================================
        // Program Closed
        // =========================================

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


    // =========================================================
    // SELECT MODULE
    // =========================================================

    private static Transceiver selectModule(

            Transceiver tx001,
            Transceiver tx002,
            Transceiver tx003,
            InputHandler inputHandler

    ) {

        while (true) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "          SELECT TEST MODULE"
            );

            System.out.println(
                    "========================================"
            );

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
                    "[0] Back"
            );

            System.out.println(
                    "========================================"
            );

            System.out.print(
                    "Select : "
            );


            String command;


            do {

                command =
                        inputHandler.getCommand();

                try {

                    Thread.sleep(100);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();

                    return null;
                }

            } while (command == null);


            if (command.equals("1")) {

                return tx001;
            }


            if (command.equals("2")) {

                return tx002;
            }


            if (command.equals("3")) {

                return tx003;
            }


            if (command.equals("0")) {

                return null;
            }


            System.out.println();

            System.out.println(
                    "Invalid command."
            );
        }
    }


    // =========================================================
    // ALARM MENU
    // =========================================================

    private static void alarmMenu(

            Transceiver module,
            AlarmHistoryService alarmHistoryService,
            InputHandler inputHandler

    ) {

        boolean menu = true;


        while (menu) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "             ALARM HISTORY"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Module : "
                            + module.getModuleId()
            );

            System.out.println(
                    "Model  : "
                            + module.getModel()
            );

            System.out.println(
                    "========================================"
            );


            // =================================
            // Get Alarm Records
            // =================================

            List<AlarmRecord> records =
                    alarmHistoryService.getAlarmHistory(
                            module
                    );


            // =================================
            // Display Alarm Records
            // =================================

            if (records.isEmpty()) {

                System.out.println(
                        "No alarm history."
                );

            } else {

                for (
                        int i = 0;
                        i < records.size();
                        i++
                ) {

                    AlarmRecord record =
                            records.get(i);


                    System.out.println();

                    System.out.println(
                            "[" + (i + 1) + "]"
                    );

                    System.out.println(
                            "Time      : "
                                    + record.getTimestamp()
                    );

                    System.out.println(
                            "Type      : "
                                    + record.getType()
                    );

                    System.out.println(
                            "Severity  : "
                                    + record.getSeverity()
                    );

                    System.out.println(
                            "Message   : "
                                    + record.getMessage()
                    );

                    System.out.println(
                            "Actual    : "
                                    + record.getActualValue()
                    );

                    System.out.println(
                            "Threshold : "
                                    + record.getThreshold()
                    );

                    System.out.println(
                            "Status    : "
                                    + record.getStatus()
                    );


                    if (
                            record.getAcknowledgedTime()
                                    != null
                    ) {

                        System.out.println(
                                "ACK Time  : "
                                        + record.getAcknowledgedTime()
                        );
                    }


                    if (
                            record.getClearedTime()
                                    != null
                    ) {

                        System.out.println(
                                "Clear Time: "
                                        + record.getClearedTime()
                        );
                    }


                    System.out.println(
                            "Duration  : "
                                    + record.getDurationSeconds()
                                    + " sec"
                    );

                    System.out.println(
                            "----------------------------------------"
                    );
                }
            }


            System.out.println();

            System.out.println(
                    "[A] Acknowledge Alarm"
            );

            System.out.println(
                    "[0] Back"
            );

            System.out.println(
                    "========================================"
            );

            System.out.print(
                    "Select : "
            );


            String command;


            do {

                command =
                        inputHandler.getCommand();

                try {

                    Thread.sleep(100);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();

                    return;
                }

            } while (command == null);


            // =================================
            // ACKNOWLEDGE ALARM
            // =================================

            if (command.equalsIgnoreCase("A")) {


                if (records.isEmpty()) {

                    System.out.println();

                    System.out.println(
                            "No alarm available."
                    );

                    continue;
                }


                System.out.print(
                        "Enter Alarm Number : "
                );


                String numberInput;


                do {

                    numberInput =
                            inputHandler.getCommand();

                    try {

                        Thread.sleep(100);

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();

                        return;
                    }

                } while (numberInput == null);


                try {

                    int alarmNumber =
                            Integer.parseInt(
                                    numberInput
                            );


                    boolean success =
                            alarmHistoryService
                                    .acknowledgeAlarm(
                                            module,
                                            alarmNumber - 1
                                    );


                    if (success) {

                        System.out.println();

                        System.out.println(
                                "Alarm acknowledged successfully."
                        );

                    } else {

                        System.out.println();

                        System.out.println(
                                "Unable to acknowledge alarm."
                        );
                    }

                } catch (NumberFormatException e) {

                    System.out.println();

                    System.out.println(
                            "Please enter a valid number."
                    );
                }
            }


            // =================================
            // BACK
            // =================================

            else if (command.equals("0")) {

                menu = false;
            }


            // =================================
            // INVALID
            // =================================

            else {

                System.out.println();

                System.out.println(
                        "Invalid command."
                );
            }
        }
    }
}