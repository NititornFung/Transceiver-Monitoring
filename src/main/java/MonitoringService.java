import java.util.ArrayList;
import java.util.List;

public class MonitoringService {


    // =================================
    // Monitor
    // =================================

    public MonitoringReport monitor(
            Transceiver module
    ) {

        List<Alarm> alarms =
                new ArrayList<>();


        // =================================
        // Check Module
        // =================================

        if (module == null) {

            throw new IllegalArgumentException(
                    "Transceiver module cannot be null."
            );
        }


        // =================================
        // Get Telemetry
        // =================================

        Telemetry telemetry =
                module.getTelemetry();


        if (telemetry == null) {

            return new MonitoringReport(
                    module,
                    "ALARM",
                    alarms
            );
        }


        // =================================
        // Get Model Threshold
        // =================================

        ThresholdManager threshold =
                ThresholdFactory.getThreshold(
                        module.getModel()
                );


        // =================================
        // Temperature
        // =================================

        checkTemperature(
                telemetry,
                threshold,
                alarms
        );


        // =================================
        // Voltage
        // =================================

        checkVoltage(
                telemetry,
                threshold,
                alarms
        );


        // =================================
        // RX Power
        // =================================

        checkRxPower(
                telemetry,
                threshold,
                alarms
        );


        // =================================
        // TX Power
        // =================================

        checkTxPower(
                telemetry,
                threshold,
                alarms
        );


        // =================================
        // Laser Current
        // =================================

        checkLaserCurrent(
                telemetry,
                threshold,
                alarms
        );


        // =================================
        // Wavelength
        // =================================

        checkWavelength(
                telemetry,
                threshold,
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
    // Temperature
    // =================================

    private void checkTemperature(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double temperature =
                telemetry.getTemperature();


        if (
                temperature >
                        threshold.getMaxTemperature()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.TEMPERATURE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Temperature is too high",
                            temperature,
                            threshold.getMaxTemperature()
                    )
            );
        }
    }


    // =================================
    // Voltage
    // =================================

    private void checkVoltage(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double voltage =
                telemetry.getVoltage();


        if (
                voltage <
                        threshold.getMinVoltage()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_LOW,
                            AlarmSeverity.CRITICAL,
                            "Voltage is too low",
                            voltage,
                            threshold.getMinVoltage()
                    )
            );
        }


        if (
                voltage >
                        threshold.getMaxVoltage()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.VOLTAGE_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Voltage is too high",
                            voltage,
                            threshold.getMaxVoltage()
                    )
            );
        }
    }


    // =================================
    // RX Power
    // =================================

    private void checkRxPower(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double rxPower =
                telemetry.getRxPower();


        if (
                rxPower <
                        threshold.getMinRxPower()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.RX_POWER_LOW,
                            AlarmSeverity.WARNING,
                            "RX Power is too low",
                            rxPower,
                            threshold.getMinRxPower()
                    )
            );
        }
    }


    // =================================
    // TX Power
    // =================================

    private void checkTxPower(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double txPower =
                telemetry.getTxPower();


        if (
                txPower <
                        threshold.getMinTxPower()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.TX_POWER_LOW,
                            AlarmSeverity.WARNING,
                            "TX Power is too low",
                            txPower,
                            threshold.getMinTxPower()
                    )
            );
        }


        if (
                txPower >
                        threshold.getMaxTxPower()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.TX_POWER_HIGH,
                            AlarmSeverity.WARNING,
                            "TX Power is too high",
                            txPower,
                            threshold.getMaxTxPower()
                    )
            );
        }
    }


    // =================================
    // Laser Current
    // =================================

    private void checkLaserCurrent(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double laserCurrent =
                telemetry.getLaserCurrent();


        if (
                laserCurrent <
                        threshold.getMinLaserCurrent()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_LOW,
                            AlarmSeverity.CRITICAL,
                            "Laser Current is too low",
                            laserCurrent,
                            threshold.getMinLaserCurrent()
                    )
            );
        }


        if (
                laserCurrent >
                        threshold.getMaxLaserCurrent()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.LASER_CURRENT_HIGH,
                            AlarmSeverity.CRITICAL,
                            "Laser Current is too high",
                            laserCurrent,
                            threshold.getMaxLaserCurrent()
                    )
            );
        }
    }


    // =================================
    // Wavelength
    // =================================

    private void checkWavelength(
            Telemetry telemetry,
            ThresholdManager threshold,
            List<Alarm> alarms
    ) {

        double wavelength =
                telemetry.getWavelength();


        if (
                wavelength <
                        threshold.getMinWavelength()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.WAVELENGTH_LOW,
                            AlarmSeverity.WARNING,
                            "Wavelength is too low",
                            wavelength,
                            threshold.getMinWavelength()
                    )
            );
        }


        if (
                wavelength >
                        threshold.getMaxWavelength()
        ) {

            alarms.add(
                    new Alarm(
                            AlarmType.WAVELENGTH_HIGH,
                            AlarmSeverity.WARNING,
                            "Wavelength is too high",
                            wavelength,
                            threshold.getMaxWavelength()
                    )
            );
        }
    }
}