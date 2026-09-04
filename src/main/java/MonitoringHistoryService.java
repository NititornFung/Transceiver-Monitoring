import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonitoringHistoryService {


    // =========================================
    // HISTORY STORAGE
    // =========================================

    private final Map<
            Transceiver,
            List<MonitoringRecord>
            > monitoringHistory;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public MonitoringHistoryService() {

        monitoringHistory =
                new HashMap<>();
    }


    // =========================================
    // ADD HISTORY FROM MONITORING REPORT
    // =========================================

    public void addHistory(

            Transceiver module,

            MonitoringReport report
    ) {


        Telemetry telemetry =
                module.getTelemetry();


        if (telemetry == null) {

            return;
        }


        MonitoringRecord record =
                new MonitoringRecord(

                        module.getModuleId(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getRxPower(),

                        telemetry.getTxPower(),

                        telemetry.getLaserCurrent(),

                        report.getStatus().toString()
                );


        addHistory(
                module,
                record
        );
    }


    // =========================================
    // ADD HISTORY FROM RECORD
    // =========================================

    public void addHistory(

            Transceiver module,

            MonitoringRecord record
    ) {


        monitoringHistory
                .computeIfAbsent(

                        module,

                        key -> new ArrayList<>()
                )
                .add(
                        record
                );
    }


    // =========================================
    // ADD HISTORY FROM STRING
    //
    // รองรับโค้ดเก่าที่เรียก:
    // addHistory(module, "ข้อความ")
    // =========================================

    public void addHistory(

            Transceiver module,

            String message
    ) {


        Telemetry telemetry =
                module.getTelemetry();


        // ถ้ายังไม่มี Telemetry
        if (telemetry == null) {

            MonitoringRecord record =
                    new MonitoringRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            0.0,

                            0.0,

                            0.0,

                            0.0,

                            0.0,

                            message
                    );


            addHistory(
                    module,
                    record
            );

            return;
        }


        // ถ้ามี Telemetry
        MonitoringRecord record =
                new MonitoringRecord(

                        module.getModuleId(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getRxPower(),

                        telemetry.getTxPower(),

                        telemetry.getLaserCurrent(),

                        message
                );


        addHistory(
                module,
                record
        );
    }


    // =========================================
    // ADD HISTORY FROM MODULE ONLY
    // =========================================

    public void addHistory(

            Transceiver module
    ) {


        Telemetry telemetry =
                module.getTelemetry();


        if (telemetry == null) {

            return;
        }


        MonitoringRecord record =
                new MonitoringRecord(

                        module.getModuleId(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getRxPower(),

                        telemetry.getTxPower(),

                        telemetry.getLaserCurrent(),

                        "RECORDED"
                );


        addHistory(
                module,
                record
        );
    }


    // =========================================
    // GET HISTORY BY MODULE
    // =========================================

    public List<MonitoringRecord> getHistory(

            Transceiver module
    ) {


        return monitoringHistory.getOrDefault(

                module,

                new ArrayList<>()
        );
    }


    // =========================================
    // GET ALL HISTORY
    // =========================================

    public List<MonitoringRecord> getAllHistory() {


        List<MonitoringRecord> allRecords =
                new ArrayList<>();


        for (

                List<MonitoringRecord> records
                : monitoringHistory.values()
        ) {

            allRecords.addAll(
                    records
            );
        }


        return allRecords;
    }


    // =========================================
    // GET TOTAL RECORD COUNT
    // =========================================

    public int getTotalRecordCount() {

        return getAllHistory().size();
    }


    // =========================================
    // PRINT HISTORY BY MODULE
    // =========================================

    public void printHistory(

            Transceiver module
    ) {


        List<MonitoringRecord> records =
                getHistory(
                        module
                );


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
                "Module ID : "
                        + module.getModuleId()
        );

        System.out.println(
                "Model     : "
                        + module.getModel()
        );

        System.out.println(
                "========================================"
        );


        if (records.isEmpty()) {

            System.out.println(
                    "No history available."
            );

            return;
        }


        for (MonitoringRecord record : records) {

            System.out.println(
                    record
            );
        }


        System.out.println(
                "\nTotal Records: "
                        + records.size()
        );
    }


    // =========================================
    // PRINT ALL HISTORY
    // =========================================

    public void printAllHistory() {


        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "       ALL MONITORING HISTORY"
        );

        System.out.println(
                "========================================"
        );


        if (monitoringHistory.isEmpty()) {

            System.out.println(
                    "No history available."
            );

            return;
        }


        for (

                Map.Entry<
                        Transceiver,
                        List<MonitoringRecord>
                        > entry

                : monitoringHistory.entrySet()
        ) {


            Transceiver module =
                    entry.getKey();


            List<MonitoringRecord> records =
                    entry.getValue();


            System.out.println(
                    "\nModule: "
                            + module.getModuleId()
                            + " | "
                            + module.getModel()
            );


            System.out.println(
                    "----------------------------------------"
            );


            for (MonitoringRecord record : records) {

                System.out.println(
                        record
                );
            }
        }


        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "Total Records: "
                        + getTotalRecordCount()
        );
    }


    // =========================================
    // CLEAR ALL HISTORY
    // =========================================

    public void clearHistory() {

        monitoringHistory.clear();
    }


    // =========================================
    // CLEAR HISTORY BY MODULE
    // =========================================

    public void clearHistory(

            Transceiver module
    ) {

        monitoringHistory.remove(
                module
        );
    }
}