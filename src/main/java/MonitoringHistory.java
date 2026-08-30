import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MonitoringHistory {

    private Transceiver module;
    private String status;
    private LocalDateTime timestamp;


    // =================================
    // Constructor
    // =================================

    public MonitoringHistory(
            Transceiver module,
            String status
    ) {

        this.module = module;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }


    // =================================
    // Getter
    // =================================

    public Transceiver getModule() {
        return module;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    // =================================
    // Formatted Time
    // =================================

    public String getFormattedTime() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        return timestamp.format(formatter);
    }
}