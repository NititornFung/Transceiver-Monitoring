import java.util.Random;


public class TelemetrySimulator {


    // =========================================
    // RANDOM
    // =========================================

    private final Random random =
            new Random();


    // =========================================
    // CURRENT VALUES
    // =========================================

    private double temperature;

    private double voltage;

    private double txPower;

    private double rxPower;

    private double laserCurrent;

    private double wavelength;


    // =========================================
    // ALARM SIMULATION
    // =========================================

    private int alarmDuration = 0;

    private int alarmType = 0;


    // =========================================
    // DEFAULT CONSTRUCTOR
    // รองรับ Main.java เวอร์ชันเดิม
    // =========================================

    public TelemetrySimulator() {

        temperature = 45.0;

        voltage = 3.30;

        txPower = -2.0;

        rxPower = -4.0;

        laserCurrent = 50.0;

        wavelength = 1310.0;
    }


    // =========================================
    // MODEL CONSTRUCTOR
    // รองรับ DashboardApplication
    // =========================================

    public TelemetrySimulator(
            String model
    ) {

        temperature = 45.0;

        voltage = 3.30;

        txPower = -2.0;

        rxPower = -4.0;

        laserCurrent = 50.0;

        wavelength =
                getBaseWavelength(model);
    }


    // =========================================
    // GENERATE COMPLETE TELEMETRY
    // =========================================

    public Telemetry generateTelemetry(
            String model
    ) {


        // =====================================
        // CHECK ALARM EVENT
        // =====================================

        generateAlarmEvent();


        // =====================================
        // GENERATE NORMAL VALUES
        // =====================================

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
                generateWavelength(model);


        // =====================================
        // APPLY ALARM
        // =====================================

        applyAlarm(model);


        // =====================================
        // RETURN TELEMETRY
        // =====================================

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
    // GENERATE RANDOM ALARM EVENT
    // =========================================

    private void generateAlarmEvent() {


        // =====================================
        // ALARM CURRENTLY ACTIVE
        // =====================================

        if (alarmDuration > 0) {

            alarmDuration--;

            return;
        }


        // =====================================
        // RANDOM CHANCE
        // 1 ใน 20 รอบ
        // =====================================

        int chance =
                random.nextInt(20);


        if (chance == 0) {


            // =================================
            // SELECT ALARM TYPE
            // =================================

            alarmType =
                    random.nextInt(5) + 1;


            // =================================
            // ALARM DURATION
            // 2 - 4 รอบ
            // =================================

            alarmDuration =
                    random.nextInt(3) + 2;
        }
    }


    // =========================================
    // APPLY ALARM
    // =========================================

    private void applyAlarm(
            String model
    ) {


        if (alarmDuration <= 0) {

            return;
        }


        switch (alarmType) {


            // =================================
            // HIGH TEMPERATURE
            // =================================

            case 1 ->

                    temperature =
                            90.0
                                    + random.nextDouble() * 5.0;


            // =================================
            // LOW VOLTAGE
            // =================================

            case 2 ->

                    voltage =
                            2.8
                                    + random.nextDouble() * 0.1;


            // =================================
            // LOW RX POWER
            // =================================

            case 3 ->

                    rxPower =
                            -12.0
                                    - random.nextDouble() * 3.0;


            // =================================
            // HIGH LASER CURRENT
            // =================================

            case 4 ->

                    laserCurrent =
                            105.0
                                    + random.nextDouble() * 10.0;


            // =================================
            // WAVELENGTH SHIFT
            // =================================

            case 5 -> {


                double base =
                        getBaseWavelength(model);


                wavelength =
                        base + 5.0;
            }
        }
    }


    // =========================================
    // GENERATE TEMPERATURE
    // =========================================

    private double generateTemperature() {


        double change =
                random.nextDouble() * 1.5
                        - 0.75;


        temperature += change;


        return limit(

                temperature,

                35.0,

                75.0
        );
    }


    // =========================================
    // GENERATE VOLTAGE
    // =========================================

    private double generateVoltage() {


        double change =
                random.nextDouble() * 0.01
                        - 0.005;


        voltage += change;


        return limit(

                voltage,

                3.15,

                3.45
        );
    }


    // =========================================
    // GENERATE TX POWER
    // =========================================

    private double generateTxPower() {


        double change =
                random.nextDouble() * 0.2
                        - 0.1;


        txPower += change;


        return limit(

                txPower,

                -4.0,

                0.0
        );
    }


    // =========================================
    // GENERATE RX POWER
    // =========================================

    private double generateRxPower() {


        double change =
                random.nextDouble() * 0.2
                        - 0.1;


        rxPower += change;


        return limit(

                rxPower,

                -8.0,

                -2.0
        );
    }


    // =========================================
    // GENERATE LASER CURRENT
    // =========================================

    private double generateLaserCurrent() {


        double change =
                random.nextDouble() * 1.0
                        - 0.5;


        laserCurrent += change;


        return limit(

                laserCurrent,

                30.0,

                80.0
        );
    }


    // =========================================
    // GENERATE WAVELENGTH
    // =========================================

    private double generateWavelength(
            String model
    ) {


        double base =
                getBaseWavelength(model);


        double change =
                random.nextDouble() * 0.04
                        - 0.02;


        wavelength =
                base + change;


        return wavelength;
    }


    // =========================================
    // GET BASE WAVELENGTH
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
    // LIMIT VALUE
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