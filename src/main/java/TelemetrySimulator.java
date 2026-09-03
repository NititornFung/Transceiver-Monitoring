import java.util.Random;


public class TelemetrySimulator {


    // =========================================
    // Random Generator
    // =========================================

    private final Random random;


    // =========================================
    // Current Simulated Values
    // =========================================

    private double temperature;

    private double voltage;

    private double txPower;

    private double rxPower;

    private double laserCurrent;

    private double wavelength;


    // =========================================
    // Constructor
    // =========================================

    public TelemetrySimulator() {

        random = new Random();


        // =========================================
        // Initial Values
        // =========================================

        temperature = 45.0;

        voltage = 3.30;

        txPower = -2.0;

        rxPower = -4.0;

        laserCurrent = 50.0;

        wavelength = 1310.0;
    }


    // =========================================
    // Generate Complete Telemetry
    // =========================================

    public Telemetry generateTelemetry(
            String model
    ) {


        temperature =
                generateTemperature();

        voltage =
                generateVoltage();

        txPower =
                generateTxPower();

        rxPower =
                generateRxPower();

        laserCurrent =
                generateLaserCurrent();

        wavelength =
                generateWavelength(
                        model
                );


        return new Telemetry(
                wavelength,
                temperature,
                voltage,
                txPower,
                rxPower,
                laserCurrent
        );
    }


    // =========================================
    // Generate Temperature
    // =========================================

    public double generateTemperature() {

        double change =
                random.nextDouble() * 2 - 1;


        temperature += change;


        temperature =
                limit(
                        temperature,
                        30.0,
                        95.0
                );


        return temperature;
    }


    // =========================================
    // Generate Voltage
    // =========================================

    public double generateVoltage() {

        double change =
                random.nextDouble() * 0.02 - 0.01;


        voltage += change;


        voltage =
                limit(
                        voltage,
                        3.0,
                        3.6
                );


        return voltage;
    }


    // =========================================
    // Generate TX Power
    // =========================================

    public double generateTxPower() {

        double change =
                random.nextDouble() * 0.4 - 0.2;


        txPower += change;


        txPower =
                limit(
                        txPower,
                        -5.0,
                        1.0
                );


        return txPower;
    }


    // =========================================
    // Generate RX Power
    // =========================================

    public double generateRxPower() {

        double change =
                random.nextDouble() * 0.4 - 0.2;


        rxPower += change;


        rxPower =
                limit(
                        rxPower,
                        -15.0,
                        1.0
                );


        return rxPower;
    }


    // =========================================
    // Generate Laser Current
    // =========================================

    public double generateLaserCurrent() {

        double change =
                random.nextDouble() * 2 - 1;


        laserCurrent += change;


        laserCurrent =
                limit(
                        laserCurrent,
                        10.0,
                        100.0
                );


        return laserCurrent;
    }


    // =========================================
    // Generate Wavelength
    // =========================================

    public double generateWavelength(
            String model
    ) {


        double baseWavelength =
                getBaseWavelength(
                        model
                );


        double change =
                random.nextDouble() * 0.1 - 0.05;


        wavelength =
                baseWavelength + change;


        return wavelength;
    }


    // =========================================
    // Get Base Wavelength
    // =========================================

    private double getBaseWavelength(
            String model
    ) {

        if (model.equals("400G COSA")) {

            return 1310.0;
        }


        if (model.equals("800G COSAz")) {

            return 1310.0;
        }


        if (model.equals("800G SR8")) {

            return 850.0;
        }


        return 1310.0;
    }


    // =========================================
    // Limit Value
    // =========================================

    private double limit(
            double value,
            double min,
            double max
    ) {

        if (value < min) {

            return min;
        }


        if (value > max) {

            return max;
        }


        return value;
    }
}