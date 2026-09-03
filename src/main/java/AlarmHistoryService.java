import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AlarmHistoryService {


    // =========================================
    // ALARM HISTORY STORAGE
    // =========================================

    private final List<AlarmRecord> alarmHistory;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public AlarmHistoryService() {

        alarmHistory =
                new ArrayList<>();
    }


    // =========================================
    // PROCESS ALARMS
    // Convert Alarm -> AlarmRecord
    // =========================================

    public void processAlarms(
            Transceiver module,
            List<Alarm> alarms
    ) {

        // ไม่มี Alarm
        if (alarms == null || alarms.isEmpty()) {

            return;
        }


        // แปลง Alarm เป็น AlarmRecord
        for (Alarm alarm : alarms) {


            AlarmRecord record =
                    new AlarmRecord(

                            module.getModuleId(),

                            LocalDateTime.now(),

                            alarm.getType(),

                            alarm.getSeverity(),

                            alarm.getMessage(),

                            alarm.getActualValue(),

                            alarm.getThreshold()
                    );


            alarmHistory.add(
                    record
            );
        }
    }


    // =========================================
    // GET ALL ALARM HISTORY
    // =========================================

    public List<AlarmRecord> getAllAlarmHistory() {

        return alarmHistory;
    }


    // =========================================
    // GET ALARM HISTORY BY MODULE
    // =========================================

    public List<AlarmRecord> getAlarmHistory(
            Transceiver module
    ) {

        List<AlarmRecord> moduleAlarms =
                new ArrayList<>();


        for (AlarmRecord record : alarmHistory) {

            if (
                    record.getModuleId()
                            .equals(
                                    module.getModuleId()
                            )
            ) {

                moduleAlarms.add(
                        record
                );
            }
        }


        return moduleAlarms;
    }


    // =========================================
    // ACKNOWLEDGE ALARM
    // =========================================

    public boolean acknowledgeAlarm(
            Transceiver module,
            int index
    ) {

        List<AlarmRecord> moduleAlarms =
                getAlarmHistory(
                        module
                );


        // ไม่มี Alarm
        if (moduleAlarms.isEmpty()) {

            return false;
        }


        // ตรวจสอบ Index
        if (
                index < 0
                        || index >= moduleAlarms.size()
        ) {

            return false;
        }


        // ดึง AlarmRecord
        AlarmRecord record =
                moduleAlarms.get(
                        index
                );


        // Acknowledge
        record.acknowledge();


        return true;
    }


    // =========================================
    // CLEAR ALARM
    // =========================================

    public boolean clearAlarm(
            Transceiver module,
            int index
    ) {

        List<AlarmRecord> moduleAlarms =
                getAlarmHistory(
                        module
                );


        // ไม่มี Alarm
        if (moduleAlarms.isEmpty()) {

            return false;
        }


        // ตรวจสอบ Index
        if (
                index < 0
                        || index >= moduleAlarms.size()
        ) {

            return false;
        }


        // ดึง AlarmRecord
        AlarmRecord record =
                moduleAlarms.get(
                        index
                );


        // Clear
        record.clear();


        return true;
    }


    // =========================================
    // GET TOTAL ALARM COUNT
    // =========================================

    public int getTotalAlarmCount() {

        return alarmHistory.size();
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

        alarmHistory.removeIf(

                record ->

                        record.getModuleId()
                                .equals(
                                        module.getModuleId()
                                )
        );
    }
}