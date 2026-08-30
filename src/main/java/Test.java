public class Test {

    private static final double CRITICAL_VOLTAGE_LOW = 2.8;
    private static final double MIN_VOLTAGE = 3.0;

    private static final double MAX_VOLTAGE = 3.6;
    private static final double CRITICAL_VOLTAGE_HIGH = 3.8;


    public static void main(String[] args) {

        double voltage = 2.9;


        // =========================
        // Voltage LOW
        // =========================

        if (voltage < CRITICAL_VOLTAGE_LOW) {

            System.out.println(
                    "CRITICAL | VOLTAGE_LOW"
            );

        }
        else if (voltage < MIN_VOLTAGE) {

            System.out.println(
                    "WARNING | VOLTAGE_LOW"
            );
        }


        // =========================
        // Voltage HIGH
        // =========================

        if (voltage > CRITICAL_VOLTAGE_HIGH) {

            System.out.println(
                    "CRITICAL | VOLTAGE_HIGH"
            );

        }
        else if (voltage > MAX_VOLTAGE) {

            System.out.println(
                    "WARNING | VOLTAGE_HIGH"
            );
        }
    }
}