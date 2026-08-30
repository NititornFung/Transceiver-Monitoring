import java.time.Duration;
import java.time.LocalDateTime;

public class AlarmRecord {

    private String moduleId;

    private LocalDateTime timestamp;

    private AlarmType type;

    private AlarmSeverity severity;

    private String message;

    private double actualValue;

    private double threshold;

    private AlarmStatus status;

    // เวลา Acknowledge
    private LocalDateTime acknowledgedTime;

    // เวลา Clear
    private LocalDateTime clearedTime;


    // =================================
    // Constructor
    // =================================

    public AlarmRecord(
            String moduleId,
            LocalDateTime timestamp,
            AlarmType type,
            AlarmSeverity severity,
            String message,
            double actualValue,
            double threshold
    ) {

        this.moduleId = moduleId;

        this.timestamp = timestamp;

        this.type = type;

        this.severity = severity;

        this.message = message;

        this.actualValue = actualValue;

        this.threshold = threshold;

        // Alarm ใหม่
        this.status = AlarmStatus.ACTIVE;

        this.acknowledgedTime = null;

        this.clearedTime = null;
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


    public AlarmStatus getStatus() {

        return status;
    }


    public LocalDateTime getAcknowledgedTime() {

        return acknowledgedTime;
    }


    public LocalDateTime getClearedTime() {

        return clearedTime;
    }


    // =================================
    // Acknowledge
    // =================================

    public void acknowledge() {

        if (status == AlarmStatus.ACTIVE) {

            status = AlarmStatus.ACKNOWLEDGED;

            acknowledgedTime =
                    LocalDateTime.now();
        }
    }


    // =================================
    // Clear Alarm
    // =================================

    public void clear() {

        if (status == AlarmStatus.ACTIVE
                || status == AlarmStatus.ACKNOWLEDGED) {

            status = AlarmStatus.CLEARED;

            clearedTime =
                    LocalDateTime.now();
        }
    }


    // =================================
    // Alarm Duration
    // =================================

    public long getDurationSeconds() {

        LocalDateTime endTime;


        if (clearedTime != null) {

            endTime = clearedTime;

        } else {

            endTime = LocalDateTime.now();
        }


        return Duration.between(
                timestamp,
                endTime
        ).getSeconds();
    }


    // =================================
    // Display
    // =================================

    @Override
    public String toString() {

        return timestamp
                + " | "
                + moduleId
                + " | "
                + severity
                + " | "
                + type
                + " | "
                + message
                + " | Actual: "
                + actualValue
                + " | Threshold: "
                + threshold
                + " | Status: "
                + status;
    }
}