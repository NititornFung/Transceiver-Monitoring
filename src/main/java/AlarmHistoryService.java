import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AlarmHistoryService {


    // =========================================
    // ALARM HISTORY STORAGE
    // =========================================

    private final Map<
            Transceiver,
            List<AlarmRecord>
            > alarmHistory;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public AlarmHistoryService() {

        alarmHistory =
                new HashMap<>();
    }


    // =========================================
    // ADD ALARMS
    // =========================================

    public void addAlarms(

            Transceiver module,

            List<AlarmRecord> alarms

    ) {

        if (alarms == null || alarms.isEmpty()) {

            return;
        }


        List<AlarmRecord> moduleAlarms =
                alarmHistory.getOrDefault(

                        module,

                        new ArrayList<>()
                );


        moduleAlarms.addAll(
                alarms
        );


        alarmHistory.put(

                module,

                moduleAlarms
        );
    }


    // =========================================
    // GET ALARM HISTORY BY MODULE
    // =========================================

    public List<AlarmRecord> getAlarmHistory(

            Transceiver module

    ) {

        return alarmHistory.getOrDefault(

                module,

                new ArrayList<>()
        );
    }


    // =========================================
    // GET ALL ALARM HISTORY
    // =========================================

    public List<AlarmRecord> getAllAlarmHistory() {

        List<AlarmRecord> allAlarms =
                new ArrayList<>();


        for (

                List<AlarmRecord> alarms :
                alarmHistory.values()

        ) {

            allAlarms.addAll(
                    alarms
            );
        }


        return allAlarms;
    }


    // =========================================
    // ACKNOWLEDGE ALARM
    // =========================================

    public boolean acknowledgeAlarm(

            Transceiver module,

            int index

    ) {

        List<AlarmRecord> alarms =
                alarmHistory.get(
                        module
                );


        // ไม่มี Module นี้
        if (alarms == null) {

            return false;
        }


        // Index ไม่ถูกต้อง
        if (

                index < 0

                        ||

                        index >= alarms.size()

        ) {

            return false;
        }


        AlarmRecord alarm =
                alarms.get(
                        index
                );


        // Acknowledge
        alarm.acknowledge();


        return true;
    }


    // =========================================
    // CLEAR ALARM
    // =========================================

    public boolean clearAlarm(

            Transceiver module,

            int index

    ) {

        List<AlarmRecord> alarms =
                alarmHistory.get(
                        module
                );


        if (alarms == null) {

            return false;
        }


        if (

                index < 0

                        ||

                        index >= alarms.size()

        ) {

            return false;
        }


        AlarmRecord alarm =
                alarms.get(
                        index
                );


        alarm.clear();


        return true;
    }


    // =========================================
    // GET TOTAL ALARM COUNT
    // =========================================

    public int getTotalAlarmCount() {

        int count = 0;


        for (

                List<AlarmRecord> alarms :
                alarmHistory.values()

        ) {

            count += alarms.size();
        }


        return count;
    }


    // =========================================
    // GET ACTIVE ALARM COUNT
    // =========================================

    public int getActiveAlarmCount() {

        int count = 0;


        for (

                List<AlarmRecord> alarms :
                alarmHistory.values()

        ) {

            for (AlarmRecord alarm : alarms) {

                if (

                        alarm.getStatus()
                                == AlarmStatus.ACTIVE

                ) {

                    count++;
                }
            }
        }


        return count;
    }


    // =========================================
    // CLEAR ALL HISTORY
    // =========================================

    public void clearHistory() {

        alarmHistory.clear();
    }


    // =========================================
    // CLEAR HISTORY BY MODULE
    // =========================================

    public void clearHistory(

            Transceiver module

    ) {

        alarmHistory.remove(
                module
        );
    }
}