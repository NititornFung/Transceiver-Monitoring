import java.util.ArrayList;
import java.util.List;


public class MonitoringHistoryService {


    // =========================================
    // HISTORY STORAGE
    // =========================================

    private final List<MonitoringHistory> historyList;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public MonitoringHistoryService() {

        historyList =
                new ArrayList<>();
    }


    // =========================================
    // ADD HISTORY
    // =========================================

    public void addHistory(
            Transceiver module,
            String status
    ) {

        MonitoringHistory history =
                new MonitoringHistory(
                        module,
                        status
                );


        historyList.add(
                history
        );
    }


    // =========================================
    // GET ALL HISTORY
    // =========================================

    public List<MonitoringHistory> getHistoryList() {

        return historyList;
    }


    // =========================================
    // GET HISTORY BY MODULE
    // =========================================

    public List<MonitoringHistory> getHistory(
            Transceiver module
    ) {

        List<MonitoringHistory> moduleHistory =
                new ArrayList<>();


        for (
                MonitoringHistory history :
                historyList
        ) {

            if (
                    history.getModule()
                            .getModuleId()
                            .equals(
                                    module.getModuleId()
                            )
            ) {

                moduleHistory.add(
                        history
                );
            }
        }


        return moduleHistory;
    }


    // =========================================
    // PRINT HISTORY
    // =========================================

    public void printHistory(
            Transceiver module
    ) {

        List<MonitoringHistory> moduleHistory =
                getHistory(module);


        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "       MONITORING HISTORY"
        );

        System.out.println(
                "========================================"
        );


        if (moduleHistory.isEmpty()) {

            System.out.println(
                    "No monitoring history found."
            );

        } else {

            for (
                    MonitoringHistory history :
                    moduleHistory
            ) {

                System.out.println(
                        "Module : "
                                + history.getModule()
                                .getModuleId()
                );

                System.out.println(
                        "Model  : "
                                + history.getModule()
                                .getModel()
                );

                System.out.println(
                        "Status : "
                                + history.getStatus()
                );

                System.out.println(
                        "Time   : "
                                + history.getFormattedTime()
                );

                System.out.println(
                        "----------------------------------------"
                );
            }
        }
    }


    // =========================================
    // GET COUNT
    // =========================================

    public int getHistoryCount() {

        return historyList.size();
    }


    // =========================================
    // CLEAR ALL HISTORY
    // =========================================

    public void clearHistory() {

        historyList.clear();
    }


    // =========================================
    // CLEAR HISTORY BY MODULE
    // =========================================

    public void clearHistory(
            Transceiver module
    ) {

        historyList.removeIf(

                history ->

                        history.getModule()
                                .getModuleId()
                                .equals(
                                        module.getModuleId()
                                )
        );
    }
}