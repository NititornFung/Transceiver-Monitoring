public class ThresholdManager {

    private final double maxTemperature;

    private final double minVoltage;
    private final double maxVoltage;

    private final double minRxPower;

    private final double minTxPower;
    private final double maxTxPower;

    private final double minLaserCurrent;
    private final double maxLaserCurrent;

    private final double minWavelength;
    private final double maxWavelength;


    // =================================
    // Constructor
    // =================================

    public ThresholdManager(
            double maxTemperature,
            double minVoltage,
            double maxVoltage,
            double minRxPower,
            double minTxPower,
            double maxTxPower,
            double minLaserCurrent,
            double maxLaserCurrent,
            double minWavelength,
            double maxWavelength
    ) {

        this.maxTemperature = maxTemperature;

        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;

        this.minRxPower = minRxPower;

        this.minTxPower = minTxPower;
        this.maxTxPower = maxTxPower;

        this.minLaserCurrent = minLaserCurrent;
        this.maxLaserCurrent = maxLaserCurrent;

        this.minWavelength = minWavelength;
        this.maxWavelength = maxWavelength;
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

    public double getMinTxPower() {
        return minTxPower;
    }

    public double getMaxTxPower() {
        return maxTxPower;
    }

    public double getMinLaserCurrent() {
        return minLaserCurrent;
    }

    public double getMaxLaserCurrent() {
        return maxLaserCurrent;
    }

    public double getMinWavelength() {
        return minWavelength;
    }

    public double getMaxWavelength() {
        return maxWavelength;
    }
}