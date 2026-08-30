import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringHistoryService {

    // =================================
    // History Storage
    // =================================

    private final Map<String, List<MonitoringRecord>> history =
            new HashMap<>();


    // =================================
    // Add History
    // =================================

    public void addHistory(
            Transceiver module,
            String status
    ) {

        String moduleId =
                module.getModuleId();


        // =================================
        // Create Monitoring Record
        // =================================

        MonitoringRecord record =
                new MonitoringRecord(
                        module.getModuleId(),
                        LocalDateTime.now(),
                        module.getTelemetry().getTemperature(),
                        module.getTelemetry().getVoltage(),
                        module.getTelemetry().getRxPower(),
                        module.getTelemetry().getTxPower(),
                        module.getTelemetry().getLaserCurrent(),
                        status
                );


        // =================================
        // Create History List
        // =================================

        if (!history.containsKey(moduleId)) {

            history.put(
                    moduleId,
                    new ArrayList<>()
            );
        }


        // =================================
        // Save Record
        // =================================

        history.get(moduleId)
                .add(record);
    }


    // =================================
    // Get History
    // =================================

    public List<MonitoringRecord> getHistory(
            Transceiver module
    ) {

        String moduleId =
                module.getModuleId();


        if (!history.containsKey(moduleId)) {

            return new ArrayList<>();
        }


        return history.get(moduleId);
    }


    // =================================
    // Print History
    // =================================

    public void printHistory(
            Transceiver module
    ) {

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "          MONITORING HISTORY"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Module : "
                        + module.getModuleId()
        );

        System.out.println(
                "Model  : "
                        + module.getModel()
        );


        List<MonitoringRecord> records =
                getHistory(module);


        // =================================
        // No History
        // =================================

        if (records.isEmpty()) {

            System.out.println(
                    "\nNo history available."
            );

            return;
        }


        // =================================
        // Display History
        // =================================

        for (MonitoringRecord record : records) {

            System.out.println(
                    record
            );
        }


        System.out.println(
                "\nTotal Records : "
                        + records.size()
        );
    }
}