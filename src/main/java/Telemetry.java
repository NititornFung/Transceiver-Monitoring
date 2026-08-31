public class Telemetry {

    private double wavelength;
    private double temperature;
    private double voltage;

    private double txPower;
    private double rxPower;

    private double laserCurrent;


    // =================================
    // Constructor
    // =================================

    public Telemetry(
            double wavelength,
            double temperature,
            double voltage,
            double txPower,
            double rxPower,
            double laserCurrent
    ) {

        this.wavelength = wavelength;
        this.temperature = temperature;
        this.voltage = voltage;

        this.txPower = txPower;
        this.rxPower = rxPower;

        this.laserCurrent = laserCurrent;
    }


    // =================================
    // Getter
    // =================================

    public double getWavelength() {
        return wavelength;
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


    // =================================
    // Setter
    // =================================

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void setTxPower(double txPower) {
        this.txPower = txPower;
    }

    public void setRxPower(double rxPower) {
        this.rxPower = rxPower;
    }

    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
    }

    public void setLaserCurrent(double laserCurrent) {
        this.laserCurrent = laserCurrent;
    }
}