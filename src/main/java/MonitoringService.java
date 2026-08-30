import java.util.ArrayList;
import java.util.List;

public class MonitoringService {

    // =================================
    // Temperature Threshold
    // =================================

    private static final double MAX_TEMPERATURE = 85.0;
    private static final double CRITICAL_TEMPERATURE = 95.0;


    // =================================
    // Voltage Threshold
    // =================================

    private static final double MIN_VOLTAGE = 3.0;
    private static final double MAX_VOLTAGE = 3.6;

    private static final double CRITICAL_VOLTAGE_LOW = 2.8;
    private static final double CRITICAL_VOLTAGE_HIGH = 3.8;


    // =================================
    // RX Power Threshold
    // =================================

    private static final double MIN_RX_POWER = -10.0;


    // =================================
    // TX Power Threshold
    // =================================

    private static final double MIN_TX_POWER = -5.0;


    // =================================
    // Laser Current Threshold
    // =================================

    private static final double MAX_LASER_CURRENT = 80.0;
    private static final double CRITICAL_LASER_CURRENT = 95.0;


    // =================================
    // Monitoring Report
    // =================================

    public MonitoringReport monitor(Transceiver module) {

        // ตรวจ Alarm ครั้งเดียว
        List<Alarm> alarms =
                checkAlarms(module);

        // Default Status
        String status = "NORMAL";


        // =================================
        // Determine Status
        // =================================

        for (Alarm alarm : alarms) {

            if (alarm.getSeverity()
                    == AlarmSeverity.CRITICAL) {

                status = "CRITICAL";

                break;
            }

            if (alarm.getSeverity()
                    == AlarmSeverity.WARNING) {

                status = "WARNING";
            }
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
    // Temperature Check
    // =================================

    public boolean checkTemperature(
            Transceiver module
    ) {

        return module.getTelemetry()
                .getTemperature()
                <= MAX_TEMPERATURE;
    }


    // =================================
    // Voltage Check
    // =================================

    public boolean checkVoltage(
            Transceiver module
    ) {

        double voltage =
                module.getTelemetry()
                        .getVoltage();

        return voltage >= MIN_VOLTAGE
                && voltage <= MAX_VOLTAGE;
    }


    // =================================
    // RX Power Check
    // =================================

    public boolean checkRxPower(
            Transceiver module
    ) {

        return module.getTelemetry()
                .getRxPower()
                >= MIN_RX_POWER;
    }


    // =================================
    // TX Power Check
    // =================================

    public boolean checkTxPower(
            Transceiver module
    ) {

        return module.getTelemetry()
                .getTxPower()
                >= MIN_TX_POWER;
    }


    // =================================
    // Laser Current Check
    // =================================

    public boolean checkLaserCurrent(
            Transceiver module
    ) {

        return module.getTelemetry()
                .getLaserCurrent()
                <= MAX_LASER_CURRENT;
    }


    // =================================
    // Overall Health
    // =================================

    public boolean isHealthy(
            Transceiver module
    ) {

        return checkTemperature(module)
                && checkVoltage(module)
                && checkRxPower(module)
                && checkTxPower(module)
                && checkLaserCurrent(module);
    }


    // =================================
    // Health Status
    // =================================

    public String getHealthStatus(
            Transceiver module
    ) {

        List<Alarm> alarms =
                checkAlarms(module);


        if (alarms.isEmpty()) {

            return "NORMAL";
        }


        for (Alarm alarm : alarms) {

            if (alarm.getSeverity()
                    == AlarmSeverity.CRITICAL) {

                return "CRITICAL";
            }
        }


        return "WARNING";
    }


    // =================================
    // Alarm Detection
    // =================================

    public List<Alarm> checkAlarms(
            Transceiver module
    ) {

        List<Alarm> alarms =
                new ArrayList<>();


        Telemetry telemetry =
                module.getTelemetry();


        // =================================
        // Temperature
        // =================================

        double temperature =
                telemetry.getTemperature();


        if (temperature >= CRITICAL_TEMPERATURE) {

            alarms.add(
                    new Alarm(
                            AlarmType.TEMPERATURE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Temperature is critically high",
                            temperature,
                            CRITICAL_TEMPERATURE
                    )
            );

        } else if (temperature > MAX_TEMPERATURE) {

            alarms.add(
                    new Alarm(
                            AlarmType.TEMPERATURE_HIGH,
                            AlarmSeverity.WARNING,
                            "Temperature exceeds limit",
                            temperature,
                            MAX_TEMPERATURE
                    )
            );
        }


        // =================================
        // Voltage
        // =================================

        double voltage =
                telemetry.getVoltage();


        // Voltage LOW - Critical

        if (voltage < CRITICAL_VOLTAGE_LOW) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_LOW,
                            AlarmSeverity.CRITICAL,
                            "Voltage is critically low",
                            voltage,
                            CRITICAL_VOLTAGE_LOW
                    )
            );

            // Voltage LOW - Warning

        } else if (voltage < MIN_VOLTAGE) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_LOW,
                            AlarmSeverity.WARNING,
                            "Voltage is too low",
                            voltage,
                            MIN_VOLTAGE
                    )
            );
        }


        // Voltage HIGH - Critical

        if (voltage > CRITICAL_VOLTAGE_HIGH) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Voltage is critically high",
                            voltage,
                            CRITICAL_VOLTAGE_HIGH
                    )
            );

            // Voltage HIGH - Warning

        } else if (voltage > MAX_VOLTAGE) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_HIGH,
                            AlarmSeverity.WARNING,
                            "Voltage is too high",
                            voltage,
                            MAX_VOLTAGE
                    )
            );
        }


        // =================================
        // RX Power
        // =================================

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


        // =================================
        // TX Power
        // =================================

        double txPower =
                telemetry.getTxPower();


        if (txPower < MIN_TX_POWER) {

            alarms.add(
                    new Alarm(
                            AlarmType.TX_POWER_LOW,
                            AlarmSeverity.WARNING,
                            "TX Power is too low",
                            txPower,
                            MIN_TX_POWER
                    )
            );
        }


        // =================================
        // Laser Current
        // =================================

        double laserCurrent =
                telemetry.getLaserCurrent();


        // Critical

        if (laserCurrent >= CRITICAL_LASER_CURRENT) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Laser current is critically high",
                            laserCurrent,
                            CRITICAL_LASER_CURRENT
                    )
            );

            // Warning

        } else if (laserCurrent > MAX_LASER_CURRENT) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_HIGH,
                            AlarmSeverity.WARNING,
                            "Laser current is too high",
                            laserCurrent,
                            MAX_LASER_CURRENT
                    )
            );
        }


        // =================================
        // Return Alarm List
        // =================================

        return alarms;
    }
    // =================================
// Create Monitoring Summary
// =================================

    public MonitoringSummary createSummary(
            List<Transceiver> modules
    ) {

        int normalCount = 0;
        int warningCount = 0;
        int criticalCount = 0;


        // =================================
        // Check Every Module
        // =================================

        for (Transceiver module : modules) {

            String status =
                    getHealthStatus(module);


            if (status.equals("NORMAL")) {

                normalCount++;

            }
            else if (status.equals("WARNING")) {

                warningCount++;

            }
            else if (status.equals("CRITICAL")) {

                criticalCount++;
            }
        }


        // =================================
        // Create Summary
        // =================================

        return new MonitoringSummary(
                modules.size(),
                normalCount,
                warningCount,
                criticalCount
        );
    }
}