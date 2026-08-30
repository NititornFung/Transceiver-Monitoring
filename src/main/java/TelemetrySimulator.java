public class TelemetrySimulator {
    public double generateTemperature() {
        double temperature = 40 + Math.random() * 70;
        return temperature;
    }

    public double generateVoltage() {
        double voltage = 3 + Math.random() * 0.6;
        return voltage;
    }

    public double generateTxPower() {
        double txPower = -5 + Math.random() * 6;
        return txPower;
    }

    public double generateRxPower() {
        double rxPower = -15 + Math.random() * 16;
        return rxPower;
    }

    public double generateWavelength(String model) {
        double wavelength = 0;
        if(model.equals("400G COSA")) {
             wavelength = 1310;
        }else if(model.equals("800G COSAz")) {
            wavelength = 1310;
        }else if(model.equals("800G SR8")) {
            wavelength = 850;
        }

        return wavelength;

    }

    public double generateLaserCurrent() {
        double laserCurrent = 10 +Math.random() * 90;
        return laserCurrent;
    }

    public Telemetry generateTelemetry(String model) {
        double wavelength = generateWavelength(model);
        double temperature = generateTemperature();
        double voltage = generateVoltage();
        double txPower = generateTxPower();
        double rxPower = generateRxPower();
        double laserCurrent = generateLaserCurrent();

        Telemetry  telemetry = new Telemetry(
                wavelength,
                temperature,
                voltage,
                txPower,
                rxPower,
                laserCurrent
        );
        return telemetry;
    }
}
