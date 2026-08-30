import java.util.List;

public class MonitoringReport {

    private Transceiver module;
    private String status;
    private List<Alarm> alarms;


    // =================================
    // Constructor
    // =================================

    public MonitoringReport(
            Transceiver module,
            String status,
            List<Alarm> alarms
    ) {

        this.module = module;
        this.status = status;
        this.alarms = alarms;
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


    public List<Alarm> getAlarms() {
        return alarms;
    }


    // =================================
    // Check Alarm
    // =================================

    public boolean hasAlarm() {

        return !alarms.isEmpty();
    }
}