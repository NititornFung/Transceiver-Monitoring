import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MonitoringHistory {


    // =====================================================
    // DATA
    // =====================================================

    private final String moduleId;

    private final String model;

    private final LocalDateTime timestamp;

    private final double temperature;

    private final double voltage;

    private final double txPower;

    private final double rxPower;

    private final double laserCurrent;

    private final String status;

    private final String source;


    // =====================================================
    // FORMATTER
    // =====================================================

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public MonitoringHistory(

            String moduleId,

            String model,

            LocalDateTime timestamp,

            double temperature,

            double voltage,

            double txPower,

            double rxPower,

            double laserCurrent,

            String status,

            String source
    ) {


        this.moduleId =
                moduleId;

        this.model =
                model;

        this.timestamp =
                timestamp;

        this.temperature =
                temperature;

        this.voltage =
                voltage;

        this.txPower =
                txPower;

        this.rxPower =
                rxPower;

        this.laserCurrent =
                laserCurrent;

        this.status =
                status;

        this.source =
                source;
    }


    // =====================================================
    // GETTERS
    // =====================================================

    public String getModuleId() {

        return moduleId;
    }


    public String getModel() {

        return model;
    }


    public LocalDateTime getTimestamp() {

        return timestamp;
    }


    public double getTemperature() {

        return temperature;
    }


    public double getVoltage() {

        return voltage;
    }


    public double getTxPower() {

        return txPower;
    }


    public double getRxPower() {

        return rxPower;
    }


    public double getLaserCurrent() {

        return laserCurrent;
    }


    public String getStatus() {

        return status;
    }


    public String getSource() {

        return source;
    }


    // =====================================================
    // FORMATTED TIME
    // =====================================================

    public String getFormattedTime() {

        return FORMATTER.format(
                timestamp
        );
    }


    // =====================================================
    // DISPLAY
    // =====================================================

    @Override
    public String toString() {


        return

                "Time: "
                        + getFormattedTime()

                        + " | Module: "
                        + moduleId

                        + " | Model: "
                        + model

                        + " | Temp: "
                        + String.format(
                        "%.2f °C",
                        temperature
                )

                        + " | Voltage: "
                        + String.format(
                        "%.3f V",
                        voltage
                )

                        + " | TX: "
                        + String.format(
                        "%.2f dBm",
                        txPower
                )

                        + " | RX: "
                        + String.format(
                        "%.2f dBm",
                        rxPower
                )

                        + " | Laser: "
                        + String.format(
                        "%.2f mA",
                        laserCurrent
                )

                        + " | Status: "
                        + status

                        + " | Source: "
                        + source;
    }
}