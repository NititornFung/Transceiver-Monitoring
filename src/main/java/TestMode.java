public class TestMode {

    private final MonitoringService monitoringService;
    private final MonitoringHistoryService historyService;
    private final AlarmHistoryService alarmHistoryService;
    private final InputHandler inputHandler;

    private Transceiver selectedModule;

    // =================================
    // Test Parameters
    // =================================

    private double temperature;
    private double voltage;
    private double rxPower;
    private double txPower;
    private double laserCurrent;
    private double wavelength;


    // =================================
    // Constructor
    // =================================

    public TestMode(
            MonitoringService monitoringService,
            MonitoringHistoryService historyService,
            AlarmHistoryService alarmHistoryService,
            InputHandler inputHandler
    ) {

        this.monitoringService = monitoringService;
        this.historyService = historyService;
        this.alarmHistoryService = alarmHistoryService;
        this.inputHandler = inputHandler;
    }


    // =================================
    // Open Test Mode
    // =================================

    public void open(Transceiver module) {

        selectedModule = module;

        loadCurrentParameters();

        boolean running = true;

        while (running) {

            printMenu();

            String command =
                    waitForInput();


            switch (command.toUpperCase()) {

                case "1":
                    editTemperature();
                    break;

                case "2":
                    editVoltage();
                    break;

                case "3":
                    editRxPower();
                    break;

                case "4":
                    editTxPower();
                    break;

                case "5":
                    editLaserCurrent();
                    break;

                case "6":
                    editWavelength();
                    break;

                case "S":
                    startTest();
                    break;

                case "R":
                    resetParameters();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid command."
                    );
            }
        }
    }


    // =================================
    // Load Current Parameters
    // =================================

    private void loadCurrentParameters() {

        Telemetry telemetry =
                selectedModule.getTelemetry();


        if (telemetry == null) {

            temperature = 40.0;
            voltage = 3.30;
            rxPower = -4.0;
            txPower = -2.0;
            laserCurrent = 50.0;
            wavelength = 1310.0;

            return;
        }


        temperature =
                telemetry.getTemperature();

        voltage =
                telemetry.getVoltage();

        rxPower =
                telemetry.getRxPower();

        txPower =
                telemetry.getTxPower();

        laserCurrent =
                telemetry.getLaserCurrent();

        wavelength =
                telemetry.getWavelength();
    }


    // =================================
    // Test Menu
    // =================================

    private void printMenu() {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "              TEST MODE"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Module : "
                        + selectedModule.getModuleId()
        );

        System.out.println(
                "Model  : "
                        + selectedModule.getModel()
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "[1] Temperature   : "
                        + temperature
                        + " °C"
        );

        System.out.println(
                "[2] Voltage       : "
                        + voltage
                        + " V"
        );

        System.out.println(
                "[3] RX Power      : "
                        + rxPower
                        + " dBm"
        );

        System.out.println(
                "[4] TX Power      : "
                        + txPower
                        + " dBm"
        );

        System.out.println(
                "[5] Laser Current : "
                        + laserCurrent
                        + " mA"
        );

        System.out.println(
                "[6] Wavelength    : "
                        + wavelength
                        + " nm"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "[S] START TEST"
        );

        System.out.println(
                "[R] RESET PARAMETERS"
        );

        System.out.println(
                "[0] BACK"
        );

        System.out.println(
                "========================================"
        );

        System.out.print(
                "Select parameter : "
        );
    }


    // =================================
    // Temperature
    // =================================

    private void editTemperature() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "       EDIT TEMPERATURE"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + temperature
                        + " °C"
        );

        System.out.println();

        System.out.print(
                "Enter new Temperature (°C) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            temperature = value;


            System.out.println();

            System.out.println(
                    "Temperature updated successfully."
            );

            System.out.println(
                    "New value : "
                            + temperature
                            + " °C"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // Voltage
    // =================================

    private void editVoltage() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "          EDIT VOLTAGE"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + voltage
                        + " V"
        );

        System.out.println();

        System.out.print(
                "Enter new Voltage (V) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            voltage = value;


            System.out.println();

            System.out.println(
                    "Voltage updated successfully."
            );

            System.out.println(
                    "New value : "
                            + voltage
                            + " V"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // RX Power
    // =================================

    private void editRxPower() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "          EDIT RX POWER"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + rxPower
                        + " dBm"
        );

        System.out.println();

        System.out.print(
                "Enter new RX Power (dBm) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            rxPower = value;


            System.out.println();

            System.out.println(
                    "RX Power updated successfully."
            );

            System.out.println(
                    "New value : "
                            + rxPower
                            + " dBm"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // TX Power
    // =================================

    private void editTxPower() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "          EDIT TX POWER"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + txPower
                        + " dBm"
        );

        System.out.println();

        System.out.print(
                "Enter new TX Power (dBm) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            txPower = value;


            System.out.println();

            System.out.println(
                    "TX Power updated successfully."
            );

            System.out.println(
                    "New value : "
                            + txPower
                            + " dBm"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // Laser Current
    // =================================

    private void editLaserCurrent() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "       EDIT LASER CURRENT"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + laserCurrent
                        + " mA"
        );

        System.out.println();

        System.out.print(
                "Enter new Laser Current (mA) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            laserCurrent = value;


            System.out.println();

            System.out.println(
                    "Laser Current updated successfully."
            );

            System.out.println(
                    "New value : "
                            + laserCurrent
                            + " mA"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // Wavelength
    // =================================

    private void editWavelength() {

        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "         EDIT WAVELENGTH"
        );

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Current value : "
                        + wavelength
                        + " nm"
        );

        System.out.println();

        System.out.print(
                "Enter new Wavelength (nm) : "
        );


        String input =
                waitForInput();


        try {

            double value =
                    Double.parseDouble(input);


            wavelength = value;


            System.out.println();

            System.out.println(
                    "Wavelength updated successfully."
            );

            System.out.println(
                    "New value : "
                            + wavelength
                            + " nm"
            );

        } catch (NumberFormatException e) {

            System.out.println();

            System.out.println(
                    "Invalid number."
            );
        }
    }


    // =================================
    // Reset
    // =================================

    private void resetParameters() {

        loadCurrentParameters();

        System.out.println();

        System.out.println(
                "Parameters reset successfully."
        );
    }


    // =================================
    // START TEST
    // =================================

    private void startTest() {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "           STARTING TEST"
        );

        System.out.println(
                "========================================"
        );


        // =================================
        // Show Parameters
        // =================================

        System.out.println();

        System.out.println(
                "Test Parameters:"
        );

        System.out.println(
                "Temperature   : "
                        + temperature
                        + " °C"
        );

        System.out.println(
                "Voltage       : "
                        + voltage
                        + " V"
        );

        System.out.println(
                "RX Power      : "
                        + rxPower
                        + " dBm"
        );

        System.out.println(
                "TX Power      : "
                        + txPower
                        + " dBm"
        );

        System.out.println(
                "Laser Current : "
                        + laserCurrent
                        + " mA"
        );

        System.out.println(
                "Wavelength    : "
                        + wavelength
                        + " nm"
        );


        // =================================
        // Create Test Telemetry
        // =================================

        Telemetry testTelemetry =
                new Telemetry(
                        wavelength,
                        temperature,
                        voltage,
                        txPower,
                        rxPower,
                        laserCurrent
                );


        // =================================
        // Update Module
        // =================================

        selectedModule.updateTelemetry(
                testTelemetry
        );


        // =================================
        // Monitoring
        // =================================

        MonitoringReport report =
                monitoringService.monitor(
                        selectedModule
                );


        // =================================
        // Save History
        // =================================

        historyService.addHistory(
                selectedModule,
                report.getStatus()
        );


        // =================================
        // Process Alarm
        // =================================

        alarmHistoryService.processAlarms(
                selectedModule,
                report.getAlarms()
        );


        // =================================
        // Show Result
        // =================================

        showResult(report);
    }


    // =================================
    // Show Result
    // =================================

    private void showResult(
            MonitoringReport report
    ) {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "              TEST RESULT"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Module : "
                        + selectedModule.getModuleId()
        );

        System.out.println(
                "Model  : "
                        + selectedModule.getModel()
        );

        System.out.println();

        System.out.println(
                "Temperature   : "
                        + temperature
                        + " °C"
        );

        System.out.println(
                "Voltage       : "
                        + voltage
                        + " V"
        );

        System.out.println(
                "RX Power      : "
                        + rxPower
                        + " dBm"
        );

        System.out.println(
                "TX Power      : "
                        + txPower
                        + " dBm"
        );

        System.out.println(
                "Laser Current : "
                        + laserCurrent
                        + " mA"
        );

        System.out.println(
                "Wavelength    : "
                        + wavelength
                        + " nm"
        );

        System.out.println();

        System.out.println(
                "STATUS : "
                        + report.getStatus()
        );


        // =================================
        // Alarm
        // =================================

        if (report.getAlarms().isEmpty()) {

            System.out.println();

            System.out.println(
                    "ALARM : None"
            );

        } else {

            System.out.println();

            System.out.println(
                    "ALARM COUNT : "
                            + report.getAlarms().size()
            );


            for (Alarm alarm :
                    report.getAlarms()) {

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
            }
        }


        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "[S] Run Test Again"
        );

        System.out.println(
                "[0] Back"
        );

        System.out.println(
                "========================================"
        );
    }


    // =================================
    // Input
    // =================================

    private String waitForInput() {

        String input;

        do {

            input =
                    inputHandler.getCommand();

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();

                return "0";
            }

        } while (input == null);


        return input.trim();
    }
}