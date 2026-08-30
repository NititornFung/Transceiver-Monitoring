import java.time.LocalDateTime;

public class MonitoringRecord {

    private String moduleId;
    private LocalDateTime timestamp;

    private double temperature;
    private double voltage;
    private double rxPower;
    private double txPower;
    private double laserCurrent;

    private String status;


    // =================================
    // Constructor
    // =================================

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

        this.moduleId = moduleId;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.voltage = voltage;
        this.rxPower = rxPower;
        this.txPower = txPower;
        this.laserCurrent = laserCurrent;
        this.status = status;
    }


    // =================================
    // Getter
    // =================================

    public String getModuleId() {
        return moduleId;
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

    public double getLaserCurrent() {
        return laserCurrent;
    }

    public String getStatus() {
        return status;
    }


    // =================================
    // Display
    // =================================

    @Override
    public String toString() {

        return timestamp
                + " | "
                + moduleId
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
                + " | Laser: "
                + laserCurrent
                + " mA"
                + " | Status: "
                + status;
    }
}