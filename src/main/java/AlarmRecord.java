import java.time.Duration;
import java.time.LocalDateTime;

public class AlarmRecord {

    private final String moduleId;

    private final LocalDateTime timestamp;

    private final AlarmType type;

    private final AlarmSeverity severity;

    private final String message;

    private final double actualValue;

    private final double threshold;

    private final AlarmSource source;

    private AlarmStatus status;

    private LocalDateTime acknowledgedTime;

    private LocalDateTime clearedTime;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public AlarmRecord(

            String moduleId,

            LocalDateTime timestamp,

            AlarmType type,

            AlarmSeverity severity,

            String message,

            double actualValue,

            double threshold,

            AlarmSource source
    ) {

        this.moduleId = moduleId;

        this.timestamp = timestamp;

        this.type = type;

        this.severity = severity;

        this.message = message;

        this.actualValue = actualValue;

        this.threshold = threshold;

        this.source = source;

        this.status = AlarmStatus.ACTIVE;

        this.acknowledgedTime = null;

        this.clearedTime = null;
    }


    // =========================================
    // GETTERS
    // =========================================

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


    public AlarmSource getSource() {

        return source;
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


    // =========================================
    // ACKNOWLEDGE
    // =========================================

    public void acknowledge() {

        if (status == AlarmStatus.ACTIVE) {

            status = AlarmStatus.ACKNOWLEDGED;

            acknowledgedTime = LocalDateTime.now();
        }
    }


    // =========================================
    // CLEAR
    // =========================================

    public void clear() {

        if (status == AlarmStatus.ACTIVE
                || status == AlarmStatus.ACKNOWLEDGED) {

            status = AlarmStatus.CLEARED;

            clearedTime = LocalDateTime.now();
        }
    }


    // =========================================
    // DURATION
    // =========================================

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


    // =========================================
    // DISPLAY
    // =========================================

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
                + source
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