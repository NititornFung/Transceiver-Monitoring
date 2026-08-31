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

    private static final double MIN_LASER_CURRENT = 10.0;
    private static final double MAX_LASER_CURRENT = 100.0;


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
        // Temperature
        // =================================

        checkTemperature(
                telemetry,
                alarms
        );


        // =================================
        // Voltage
        // =================================

        checkVoltage(
                telemetry,
                alarms
        );


        // =================================
        // RX Power
        // =================================

        checkRxPower(
                telemetry,
                alarms
        );


        // =================================
        // Laser Current
        // =================================

        checkLaserCurrent(
                telemetry,
                alarms
        );


        // =================================
        // Status
        // =================================

        String status;

        if (alarms.isEmpty()) {

            status = "NORMAL";

        } else {

            status = "ALARM";
        }


        // =================================
        // Report
        // =================================

        return new MonitoringReport(
                module,
                status,
                alarms
        );
    }


    // =================================
    // Temperature Check
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
    // Voltage Check
    // =================================

    private void checkVoltage(
            Telemetry telemetry,
            List<Alarm> alarms
    ) {

        double voltage =
                telemetry.getVoltage();


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
    // RX Power Check
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


    // =================================
    // Laser Current Check
    // =================================

    private void checkLaserCurrent(
            Telemetry telemetry,
            List<Alarm> alarms
    ) {

        double laserCurrent =
                telemetry.getLaserCurrent();


        // =================================
        // LASER CURRENT LOW
        // =================================

        if (laserCurrent < MIN_LASER_CURRENT) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_LOW,
                            AlarmSeverity.WARNING,
                            "Laser Current is too low",
                            laserCurrent,
                            MIN_LASER_CURRENT
                    )
            );
        }


        // =================================
        // LASER CURRENT HIGH
        // =================================

        if (laserCurrent > MAX_LASER_CURRENT) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_HIGH,
                            AlarmSeverity.WARNING,
                            "Laser Current is too high",
                            laserCurrent,
                            MAX_LASER_CURRENT
                    )
            );
        }
    }
}