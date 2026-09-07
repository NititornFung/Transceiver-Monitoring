import java.util.List;


public class MonitoringReport {

    private final Transceiver module;

    private final String status;

    private final List<Alarm> alarms;


    public MonitoringReport(
            Transceiver module,
            String status,
            List<Alarm> alarms
    ) {

        this.module = module;

        this.status = status;

        this.alarms = alarms;
    }


    public Transceiver getModule() {

        return module;
    }


    public String getStatus() {

        return status;
    }


    public List<Alarm> getAlarms() {

        return alarms;
    }
}