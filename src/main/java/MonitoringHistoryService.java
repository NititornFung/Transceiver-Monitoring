import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MonitoringHistoryService {


    // =====================================================
    // HISTORY STORAGE
    // =====================================================

    private final List<MonitoringHistory> historyList =
            new ArrayList<>();


    // =====================================================
    // ADD NORMAL MONITORING HISTORY
    // =====================================================

    public void addHistory(

            Transceiver module,

            Telemetry telemetry,

            MonitoringReport report
    ) {


        MonitoringHistory history =
                new MonitoringHistory(

                        module.getModuleId(),

                        module.getModel(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getTxPower(),

                        telemetry.getRxPower(),

                        telemetry.getLaserCurrent(),

                        report.getStatus().toString(),

                        "MONITORING"
                );


        historyList.add(
                history
        );
    }


    // =====================================================
    // ADD TEST HISTORY
    // =====================================================

    public void addTestHistory(

            Transceiver module,

            Telemetry telemetry,

            MonitoringReport report
    ) {


        MonitoringHistory history =
                new MonitoringHistory(

                        module.getModuleId(),

                        module.getModel(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getTxPower(),

                        telemetry.getRxPower(),

                        telemetry.getLaserCurrent(),

                        report.getStatus().toString(),

                        "TEST"
                );


        historyList.add(
                history
        );
    }


    // =====================================================
    // COMPATIBILITY METHOD
    // =====================================================

    public void addHistory(

            Transceiver module,

            Telemetry telemetry,

            String status
    ) {


        MonitoringHistory history =
                new MonitoringHistory(

                        module.getModuleId(),

                        module.getModel(),

                        LocalDateTime.now(),

                        telemetry.getTemperature(),

                        telemetry.getVoltage(),

                        telemetry.getTxPower(),

                        telemetry.getRxPower(),

                        telemetry.getLaserCurrent(),

                        status,

                        "MONITORING"
                );


        historyList.add(
                history
        );
    }


    // =====================================================
    // GET ALL HISTORY
    // =====================================================

    public List<MonitoringHistory> getAllHistory() {


        return new ArrayList<>(
                historyList
        );
    }


    // =====================================================
    // GET HISTORY BY MODULE
    // =====================================================

    public List<MonitoringHistory> getHistory(

            Transceiver module
    ) {


        return getHistory(
                module.getModuleId()
        );
    }


    // =====================================================
    // GET HISTORY BY MODULE ID
    // =====================================================

    public List<MonitoringHistory> getHistory(

            String moduleId
    ) {


        return historyList.stream()

                .filter(

                        history ->

                                history.getModuleId()
                                        .equals(moduleId)
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =====================================================
    // FILTER HISTORY
    // =====================================================

    public List<MonitoringHistory> filterHistory(

            List<String> selectedModules,

            LocalDateTime from,

            LocalDateTime to,

            boolean includeMonitoring,

            boolean includeTest
    ) {


        return historyList.stream()

                // =========================================
                // MODULE FILTER
                // =========================================

                .filter(

                        history ->

                                selectedModules == null

                                        ||

                                        selectedModules.isEmpty()

                                        ||

                                        selectedModules.contains(
                                                history.getModuleId()
                                        )
                )


                // =========================================
                // FROM TIME
                // =========================================

                .filter(

                        history ->

                                from == null

                                        ||

                                        !history.getTimestamp()
                                                .isBefore(from)
                )


                // =========================================
                // TO TIME
                // =========================================

                .filter(

                        history ->

                                to == null

                                        ||

                                        !history.getTimestamp()
                                                .isAfter(to)
                )


                // =========================================
                // SOURCE FILTER
                // =========================================

                .filter(

                        history ->

                                (

                                        includeMonitoring

                                                &&

                                                history.getSource()
                                                        .equals(
                                                                "MONITORING"
                                                        )

                                )

                                        ||

                                        (

                                                includeTest

                                                        &&

                                                        history.getSource()
                                                                .equals(
                                                                        "TEST"
                                                                )

                                        )
                )


                .collect(
                        Collectors.toList()
                );
    }


    // =====================================================
    // TOTAL RECORDS
    // =====================================================

    public int getTotalRecords() {

        return historyList.size();
    }


    // =====================================================
    // CLEAR MODULE HISTORY
    // =====================================================

    public void clearHistory(

            Transceiver module
    ) {


        historyList.removeIf(

                history ->

                        history.getModuleId()
                                .equals(
                                        module.getModuleId()
                                )
        );
    }


    // =====================================================
    // CLEAR ALL HISTORY
    // =====================================================

    public void clearAllHistory() {

        historyList.clear();
    }


    // =====================================================
    // PRINT HISTORY
    // =====================================================

    public void printHistory(

            Transceiver module
    ) {


        List<MonitoringHistory> histories =
                getHistory(
                        module
                );


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "MONITORING HISTORY"
        );

        System.out.println(
                "Module: "
                        + module.getModuleId()
        );

        System.out.println(
                "Model: "
                        + module.getModel()
        );

        System.out.println(
                "======================================"
        );


        if (histories.isEmpty()) {


            System.out.println(
                    "No history available."
            );

            return;
        }


        for (

                MonitoringHistory history :
                histories

        ) {

            System.out.println(
                    history
            );
        }
    }


    // =====================================================
    // PRINT ALL HISTORY
    // =====================================================

    public void printAllHistory() {


        for (

                MonitoringHistory history :
                historyList

        ) {

            System.out.println(
                    history
            );
        }
    }
}