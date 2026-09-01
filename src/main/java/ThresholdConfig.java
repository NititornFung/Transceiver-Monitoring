public class ThresholdConfig {

    private double maxTemperature;

    private double minVoltage;
    private double maxVoltage;

    private double minRxPower;

    private double minLaserCurrent;
    private double maxLaserCurrent;


    // =================================
    // Constructor
    // =================================

    public ThresholdConfig(
            double maxTemperature,
            double minVoltage,
            double maxVoltage,
            double minRxPower,
            double minLaserCurrent,
            double maxLaserCurrent
    ) {

        this.maxTemperature = maxTemperature;

        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;

        this.minRxPower = minRxPower;

        this.minLaserCurrent = minLaserCurrent;
        this.maxLaserCurrent = maxLaserCurrent;
    }


    // =================================
    // Getter
    // =================================

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinVoltage() {
        return minVoltage;
    }

    public double getMaxVoltage() {
        return maxVoltage;
    }

    public double getMinRxPower() {
        return minRxPower;
    }

    public double getMinLaserCurrent() {
        return minLaserCurrent;
    }

    public double getMaxLaserCurrent() {
        return maxLaserCurrent;
    }
}