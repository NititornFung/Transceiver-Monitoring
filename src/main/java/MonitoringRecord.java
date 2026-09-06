import java.time.LocalDateTime;

public class MonitoringRecord {

    // =========================================
    // MODULE INFORMATION
    // =========================================

    private final String moduleId;
    private final String model;

    private final LocalDateTime timestamp;


    // =========================================
    // TELEMETRY
    // =========================================

    private final double temperature;
    private final double voltage;
    private final double rxPower;
    private final double txPower;
    private final double wavelength;
    private final double laserCurrent;


    // =========================================
    // RESULT
    // =========================================

    private final String status;

    private final MonitoringSource source;


    // =========================================
    // MAIN CONSTRUCTOR
    // =========================================

    public MonitoringRecord(

            String moduleId,
            String model,
            LocalDateTime timestamp,

            double temperature,
            double voltage,
            double rxPower,
            double txPower,
            double wavelength,
            double laserCurrent,

            String status,

            MonitoringSource source
    ) {

        this.moduleId = moduleId;
        this.model = model;
        this.timestamp = timestamp;

        this.temperature = temperature;
        this.voltage = voltage;
        this.rxPower = rxPower;
        this.txPower = txPower;
        this.wavelength = wavelength;
        this.laserCurrent = laserCurrent;

        this.status = status;

        this.source = source;
    }


    // =========================================
    // OLD COMPATIBILITY CONSTRUCTOR
    // =========================================

    public MonitoringRecord(

            String moduleId,
            LocalDateTime timestamp,

            double temperature,
            double voltage,
            double rxPower,
            double txPower,
            double laserCurrent,

            String status
    ) {

        this(

                moduleId,
                "Unknown",
                timestamp,

                temperature,
                voltage,
                rxPower,
                txPower,
                0.0,
                laserCurrent,

                status,

                MonitoringSource.REALTIME
        );
    }


    // =========================================
    // GETTERS
    // =========================================

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


    public double getRxPower() {
        return rxPower;
    }


    public double getTxPower() {
        return txPower;
    }


    public double getWavelength() {
        return wavelength;
    }


    public double getLaserCurrent() {
        return laserCurrent;
    }


    public String getStatus() {
        return status;
    }


    public MonitoringSource getSource() {
        return source;
    }


    // =========================================
    // DISPLAY
    // =========================================

    @Override
    public String toString() {

        return timestamp
                + " | "
                + moduleId
                + " | "
                + model
                + " | Temp: "
                + temperature
                + " °C"
                + " | Voltage: "
                + voltage
                + " V"
                + " | RX: "
                + rxPower
                + " dBm"
                + " | TX: "
                + txPower
                + " dBm"
                + " | Wavelength: "
                + wavelength
                + " nm"
                + " | Laser: "
                + laserCurrent
                + " mA"
                + " | Status: "
                + status
                + " | Source: "
                + source;
    }
}