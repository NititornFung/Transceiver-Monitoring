public class Alarm {

    private AlarmType type;
    private AlarmSeverity severity;
    private String message;
    private double actualValue;
    private double threshold;


    // =================================
    // Constructor
    // =================================

    public Alarm(
            AlarmType type,
            AlarmSeverity severity,
            String message,
            double actualValue,
            double threshold
    ) {

        this.type = type;
        this.severity = severity;
        this.message = message;
        this.actualValue = actualValue;
        this.threshold = threshold;
    }


    // =================================
    // Getter
    // =================================

    public AlarmType getType() {

        return type;
    }


    public AlarmSeverity getSeverity() {

        return severity;
    }


    public String getMessage() {

        return message;
    }


    public double getActualValue() {

        return actualValue;
    }


    public double getThreshold() {

        return threshold;
    }
}