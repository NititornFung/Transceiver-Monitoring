import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmHistoryService {

    // =================================
    // Alarm History Storage
    // =================================

    private final Map<String, List<AlarmRecord>> history =
            new HashMap<>();


    // =================================
    // Add Alarm History
    // =================================

    public void addAlarmHistory(
            Transceiver module,
            Alarm alarm
    ) {

        String moduleId =
                module.getModuleId();


        // =================================
        // Create Module History
        // =================================

        if (!history.containsKey(moduleId)) {

            history.put(
                    moduleId,
                    new ArrayList<>()
            );
        }


        List<AlarmRecord> records =
                history.get(moduleId);


        // =================================
        // Check Existing ACTIVE Alarm
        // =================================

        for (AlarmRecord record : records) {

            if (record.getType()
                    == alarm.getType()) {

                if (record.getStatus()
                        == AlarmStatus.ACTIVE) {

                    // Alarm เดิมยัง ACTIVE
                    // ไม่สร้างซ้ำ
                    return;
                }


                if (record.getStatus()
                        == AlarmStatus.ACKNOWLEDGED) {

                    // Alarm เดิมยังไม่ได้ Clear
                    // ไม่สร้างซ้ำ
                    return;
                }
            }
        }


        // =================================
        // Create NEW Alarm Event
        // =================================

        AlarmRecord record =
                new AlarmRecord(
                        moduleId,
                        LocalDateTime.now(),
                        alarm.getType(),
                        alarm.getSeverity(),
                        alarm.getMessage(),
                        alarm.getActualValue(),
                        alarm.getThreshold()
                );


        // =================================
        // Save NEW Event
        // =================================

        records.add(record);
    }


    // =================================
    // Get Alarm History
    // =================================

    public List<AlarmRecord> getAlarmHistory(
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
    // Acknowledge Alarm
    // =================================

    public boolean acknowledgeAlarm(
            Transceiver module,
            int alarmNumber
    ) {

        List<AlarmRecord> records =
                getAlarmHistory(module);


        if (records.isEmpty()) {

            return false;
        }


        if (alarmNumber < 1
                || alarmNumber > records.size()) {

            return false;
        }


        AlarmRecord record =
                records.get(
                        alarmNumber - 1
                );


        // =================================
        // Only ACTIVE can be acknowledged
        // =================================

        if (record.getStatus()
                != AlarmStatus.ACTIVE) {

            return false;
        }


        record.acknowledge();

        return true;
    }


    // =================================
    // Clear Alarm
    // =================================

    public void clearAlarm(
            Transceiver module,
            AlarmType alarmType
    ) {

        List<AlarmRecord> records =
                getAlarmHistory(module);


        // Search from newest → oldest
        for (int i = records.size() - 1;
             i >= 0;
             i--) {

            AlarmRecord record =
                    records.get(i);


            if (record.getType()
                    == alarmType) {


                // =================================
                // ACTIVE → CLEARED
                // =================================

                if (record.getStatus()
                        == AlarmStatus.ACTIVE) {

                    record.clear();

                    return;
                }


                // =================================
                // ACKNOWLEDGED → CLEARED
                // =================================

                if (record.getStatus()
                        == AlarmStatus.ACKNOWLEDGED) {

                    record.clear();

                    return;
                }
            }
        }
    }


    // =================================
    // Process Alarm
    // =================================

    public void processAlarms(
            Transceiver module,
            List<Alarm> currentAlarms
    ) {

        // =================================
        // 1. Create NEW alarms
        // =================================

        for (Alarm alarm : currentAlarms) {

            addAlarmHistory(
                    module,
                    alarm
            );
        }


        // =================================
        // 2. Check Old ACTIVE Alarms
        // =================================

        List<AlarmRecord> records =
                getAlarmHistory(module);


        for (AlarmRecord record : records) {

            // ข้าม Alarm ที่ Clear แล้ว
            if (record.getStatus()
                    == AlarmStatus.CLEARED) {

                continue;
            }


            // =================================
            // Check whether alarm still exists
            // =================================

            boolean stillActive = false;


            for (Alarm alarm : currentAlarms) {

                if (alarm.getType()
                        == record.getType()) {

                    stillActive = true;

                    break;
                }
            }


            // =================================
            // Alarm disappeared
            // =================================

            if (!stillActive) {

                record.clear();
            }
        }
    }


    // =================================
    // Get Active Alarms
    // =================================

    public List<AlarmRecord> getActiveAlarms(
            Transceiver module
    ) {

        List<AlarmRecord> activeAlarms =
                new ArrayList<>();


        List<AlarmRecord> records =
                getAlarmHistory(module);


        for (AlarmRecord record : records) {

            if (record.getStatus()
                    == AlarmStatus.ACTIVE) {

                activeAlarms.add(record);
            }
        }


        return activeAlarms;
    }


    // =================================
    // Get Acknowledged Alarms
    // =================================

    public List<AlarmRecord> getAcknowledgedAlarms(
            Transceiver module
    ) {

        List<AlarmRecord> acknowledgedAlarms =
                new ArrayList<>();


        List<AlarmRecord> records =
                getAlarmHistory(module);


        for (AlarmRecord record : records) {

            if (record.getStatus()
                    == AlarmStatus.ACKNOWLEDGED) {

                acknowledgedAlarms.add(record);
            }
        }


        return acknowledgedAlarms;
    }


    // =================================
    // Get Cleared Alarms
    // =================================

    public List<AlarmRecord> getClearedAlarms(
            Transceiver module
    ) {

        List<AlarmRecord> clearedAlarms =
                new ArrayList<>();


        List<AlarmRecord> records =
                getAlarmHistory(module);


        for (AlarmRecord record : records) {

            if (record.getStatus()
                    == AlarmStatus.CLEARED) {

                clearedAlarms.add(record);
            }
        }


        return clearedAlarms;
    }


    // =================================
    // Print Alarm History
    // =================================

    public void printAlarmHistory(
            Transceiver module
    ) {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "             ALARM HISTORY"
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

        System.out.println(
                "========================================"
        );


        List<AlarmRecord> records =
                getAlarmHistory(module);


        if (records.isEmpty()) {

            System.out.println(
                    "No alarm history."
            );

            return;
        }


        // =================================
        // Display History
        // =================================

        for (int i = 0;
             i < records.size();
             i++) {

            AlarmRecord record =
                    records.get(i);


            System.out.println();

            System.out.println(
                    "[" + (i + 1) + "]"
            );

            System.out.println(
                    "Time      : "
                            + record.getTimestamp()
            );

            System.out.println(
                    "Type      : "
                            + record.getType()
            );

            System.out.println(
                    "Severity  : "
                            + record.getSeverity()
            );

            System.out.println(
                    "Message   : "
                            + record.getMessage()
            );

            System.out.println(
                    "Actual    : "
                            + record.getActualValue()
            );

            System.out.println(
                    "Threshold : "
                            + record.getThreshold()
            );

            System.out.println(
                    "Status    : "
                            + record.getStatus()
            );


            if (record.getAcknowledgedTime()
                    != null) {

                System.out.println(
                        "ACK Time  : "
                                + record.getAcknowledgedTime()
                );
            }


            if (record.getClearedTime()
                    != null) {

                System.out.println(
                        "Clear Time: "
                                + record.getClearedTime()
                );
            }


            System.out.println(
                    "Duration  : "
                            + record.getDurationSeconds()
                            + " sec"
            );


            System.out.println(
                    "----------------------------------------"
            );
        }
    }
}