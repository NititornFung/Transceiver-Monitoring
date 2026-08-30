import java.util.ArrayList;
import java.util.List;

public class MonitoringService {

    // =================================
    // Threshold
    // =================================

    private static final double MAX_TEMPERATURE = 85.0;

    private static final double MIN_VOLTAGE = 3.0;
    private static final double MAX_VOLTAGE = 3.6;

    private static final double MIN_RX_POWER = -10.0;


    // =================================
    // Monitor
    // =================================

    public MonitoringReport monitor(
            Transceiver module
    ) {

        List<Alarm> alarms =
                new ArrayList<>();


        Telemetry telemetry =
                module.getTelemetry();


        // =================================
        // Temperature Check
        // =================================

        checkTemperature(
                telemetry,
                alarms
        );


        // =================================
        // Voltage Check
        // =================================

        checkVoltage(
                telemetry,
                alarms
        );


        // =================================
        // RX Power Check
        // =================================

        checkRxPower(
                telemetry,
                alarms
        );


        // =================================
        // Determine Status
        // =================================

        String status;


        if (alarms.isEmpty()) {

            status = "NORMAL";

        } else {

            status = "ALARM";
        }


        // =================================
        // Create Report
        // =================================

        return new MonitoringReport(
                module,
                status,
                alarms
        );
    }


    // =================================
    // Temperature
    // =================================

    private void checkTemperature(
            Telemetry telemetry,
            List<Alarm> alarms
    ) {

        double temperature =
                telemetry.getTemperature();


        if (temperature > MAX_TEMPERATURE) {

            alarms.add(
                    new Alarm(
                            AlarmType.TEMPERATURE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Temperature is too high",
                            temperature,
                            MAX_TEMPERATURE
                    )
            );
        }
    }


    // =================================
    // Voltage
    // =================================

    private void checkVoltage(
            Telemetry telemetry,
            List<Alarm> alarms
    ) {

        double voltage =
                telemetry.getVoltage();


        // =================================
        // Voltage LOW
        // =================================

        if (voltage < MIN_VOLTAGE) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_LOW,
                            AlarmSeverity.CRITICAL,
                            "Voltage is too low",
                            voltage,
                            MIN_VOLTAGE
                    )
            );
        }


        // =================================
        // Voltage HIGH
        // =================================

        if (voltage > MAX_VOLTAGE) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Voltage is too high",
                            voltage,
                            MAX_VOLTAGE
                    )
            );
        }
    }


    // =================================
    // RX Power
    // =================================

    private void checkRxPower(
            Telemetry telemetry,
            List<Alarm> alarms
    ) {

        double rxPower =
                telemetry.getRxPower();


        if (rxPower < MIN_RX_POWER) {

            alarms.add(
                    new Alarm(
                            AlarmType.RX_POWER_LOW,
                            AlarmSeverity.WARNING,
                            "RX Power is too low",
                            rxPower,
                            MIN_RX_POWER
                    )
            );
        }
    }
}