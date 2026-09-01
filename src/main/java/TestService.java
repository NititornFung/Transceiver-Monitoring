import java.util.Scanner;

public class TestService {

    private final Scanner scanner;
    private final MonitoringService monitoringService;

    // =================================
    // Constructor
    // =================================

    public TestService(
            Scanner scanner,
            MonitoringService monitoringService
    ) {

        this.scanner = scanner;
        this.monitoringService = monitoringService;
    }


    // =================================
    // Start Test
    // =================================

    public void startTest(
            Transceiver module
    ) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              TEST MODE");
        System.out.println("========================================");

        System.out.println(
                "Module : "
                        + module.getModuleId()
        );

        System.out.println(
                "Model  : "
                        + module.getModel()
        );

        System.out.println();


        // =================================
        // Get Threshold
        // =================================

        ThresholdManager threshold =
                ThresholdFactory.getThreshold(
                        module.getModel()
                );


        // =================================
        // Show Threshold
        // =================================

        System.out.println("----------------------------------------");
        System.out.println("          MODEL THRESHOLD");
        System.out.println("----------------------------------------");

        System.out.println(
                "Max Temperature   : "
                        + threshold.getMaxTemperature()
                        + " °C"
        );

        System.out.println(
                "Voltage Range     : "
                        + threshold.getMinVoltage()
                        + " - "
                        + threshold.getMaxVoltage()
                        + " V"
        );

        System.out.println(
                "Min RX Power      : "
                        + threshold.getMinRxPower()
                        + " dBm"
        );

        System.out.println(
                "TX Power Range    : "
                        + threshold.getMinTxPower()
                        + " - "
                        + threshold.getMaxTxPower()
                        + " dBm"
        );

        System.out.println(
                "Laser Current     : "
                        + threshold.getMinLaserCurrent()
                        + " - "
                        + threshold.getMaxLaserCurrent()
                        + " mA"
        );

        System.out.println(
                "Wavelength Range  : "
                        + threshold.getMinWavelength()
                        + " - "
                        + threshold.getMaxWavelength()
                        + " nm"
        );

        System.out.println("----------------------------------------");


        // =================================
        // Get Current Telemetry
        // =================================

        Telemetry telemetry =
                module.getTelemetry();


        if (telemetry == null) {

            System.out.println();

            System.out.println(
                    "No telemetry available."
            );

            return;
        }


        // =================================
        // Monitoring
        // =================================

        MonitoringReport report =
                monitoringService.monitor(
                        module
                );


        // =================================
        // Result
        // =================================

        System.out.println();

        System.out.println("----------------------------------------");
        System.out.println("              TEST RESULT");
        System.out.println("----------------------------------------");

        System.out.println(
                "Temperature   : "
                        + telemetry.getTemperature()
                        + " °C"
        );

        System.out.println(
                "Voltage       : "
                        + telemetry.getVoltage()
                        + " V"
        );

        System.out.println(
                "RX Power      : "
                        + telemetry.getRxPower()
                        + " dBm"
        );

        System.out.println(
                "TX Power      : "
                        + telemetry.getTxPower()
                        + " dBm"
        );

        System.out.println(
                "Laser Current : "
                        + telemetry.getLaserCurrent()
                        + " mA"
        );

        System.out.println(
                "Wavelength    : "
                        + telemetry.getWavelength()
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
    }
}