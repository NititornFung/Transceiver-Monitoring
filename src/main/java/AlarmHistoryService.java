import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AlarmHistoryService {


    // =========================================
    // Alarm History Storage
    // =========================================

    private final List<AlarmRecord> alarmHistory =
            new ArrayList<>();


    // =========================================
    // Add Single Alarm
    // =========================================

    public void addAlarm(
            AlarmRecord alarm
    ) {

        if (alarm == null) {
            return;
        }

        alarmHistory.add(
                alarm
        );
    }


    // =========================================
    // Add Multiple Alarms
    // =========================================

    public void addAlarms(
            Transceiver module,
            List<AlarmRecord> alarms
    ) {

        if (
                alarms == null
                        || alarms.isEmpty()
        ) {
            return;
        }


        for (AlarmRecord alarm : alarms) {

            if (alarm != null) {

                alarmHistory.add(
                        alarm
                );
            }
        }
    }


    // =========================================
    // Get All History
    // =========================================

    public List<AlarmRecord> getAllHistory() {

        return new ArrayList<>(
                alarmHistory
        );
    }


    // =========================================
    // Compatibility Method
    // =========================================

    public List<AlarmRecord> getAllAlarmHistory() {

        return getAllHistory();
    }


    // =========================================
    // Get History By Module
    // =========================================

    public List<AlarmRecord> getHistory(
            Transceiver module
    ) {

        if (module == null) {

            return new ArrayList<>();
        }


        return alarmHistory
                .stream()

                .filter(
                        alarm ->
                                module.getModuleId()
                                        .equals(
                                                alarm.getModuleId()
                                        )
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Compatibility Method
    // =========================================

    public List<AlarmRecord> getAlarmHistory(
            Transceiver module
    ) {

        return getHistory(
                module
        );
    }


    // =========================================
    // Get History By Module ID
    // =========================================

    public List<AlarmRecord> getHistory(
            String moduleId
    ) {

        if (moduleId == null) {

            return new ArrayList<>();
        }


        return alarmHistory
                .stream()

                .filter(
                        alarm ->
                                moduleId.equals(
                                        alarm.getModuleId()
                                )
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Filter Alarms
    //
    // Used by ExportAlarmHistoryWindow
    // =========================================

    public List<AlarmRecord> filterAlarms(

            List<String> moduleIds,

            AlarmType type,

            AlarmSeverity severity,

            AlarmSource source,

            LocalDateTime startDate,

            LocalDateTime endDate
    ) {

        return alarmHistory

                .stream()


                // =================================
                // Filter Module
                // =================================

                .filter(
                        alarm -> {

                            if (
                                    moduleIds == null
                                            || moduleIds.isEmpty()
                            ) {

                                return true;
                            }


                            return moduleIds.contains(
                                    alarm.getModuleId()
                            );
                        }
                )


                // =================================
                // Filter Type
                // =================================

                .filter(
                        alarm -> {

                            if (type == null) {

                                return true;
                            }


                            return alarm.getType()
                                    == type;
                        }
                )


                // =================================
                // Filter Severity
                // =================================

                .filter(
                        alarm -> {

                            if (severity == null) {

                                return true;
                            }


                            return alarm.getSeverity()
                                    == severity;
                        }
                )


                // =================================
                // Filter Source
                // =================================

                .filter(
                        alarm -> {

                            if (source == null) {

                                return true;
                            }


                            return alarm.getSource()
                                    == source;
                        }
                )


                // =================================
                // Filter Start Date
                // =================================

                .filter(
                        alarm -> {

                            if (startDate == null) {

                                return true;
                            }


                            if (
                                    alarm.getTimestamp()
                                            == null
                            ) {

                                return false;
                            }


                            return !alarm.getTimestamp()
                                    .isBefore(
                                            startDate
                                    );
                        }
                )


                // =================================
                // Filter End Date
                // =================================

                .filter(
                        alarm -> {

                            if (endDate == null) {

                                return true;
                            }


                            if (
                                    alarm.getTimestamp()
                                            == null
                            ) {

                                return false;
                            }


                            return !alarm.getTimestamp()
                                    .isAfter(
                                            endDate
                                    );
                        }
                )


                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Compatibility Filter Method
    // =========================================

    public List<AlarmRecord> filterAlarms(

            List<String> moduleIds,

            LocalDateTime startDate,

            LocalDateTime endDate,

            boolean includeMonitoring,

            boolean includeTest
    ) {

        return alarmHistory

                .stream()

                .filter(
                        alarm -> {

                            if (
                                    moduleIds == null
                                            || moduleIds.isEmpty()
                            ) {

                                return true;
                            }


                            return moduleIds.contains(
                                    alarm.getModuleId()
                            );
                        }
                )

                .filter(
                        alarm -> {

                            if (startDate == null) {

                                return true;
                            }


                            return !alarm.getTimestamp()
                                    .isBefore(
                                            startDate
                                    );
                        }
                )

                .filter(
                        alarm -> {

                            if (endDate == null) {

                                return true;
                            }


                            return !alarm.getTimestamp()
                                    .isAfter(
                                            endDate
                                    );
                        }
                )

                .filter(
                        alarm -> {

                            if (
                                    alarm.getSource()
                                            == AlarmSource.MONITORING
                            ) {

                                return includeMonitoring;
                            }


                            if (
                                    alarm.getSource()
                                            == AlarmSource.TEST
                            ) {

                                return includeTest;
                            }


                            return false;
                        }
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Get Active Alarms
    // =========================================

    public List<AlarmRecord> getActiveAlarms() {

        return alarmHistory

                .stream()

                .filter(
                        alarm ->
                                alarm.getStatus()
                                        == AlarmStatus.ACTIVE
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Get Alarms By Type
    // =========================================

    public List<AlarmRecord> getAlarmsByType(
            AlarmType type
    ) {

        if (type == null) {

            return getAllHistory();
        }


        return alarmHistory

                .stream()

                .filter(
                        alarm ->
                                alarm.getType()
                                        == type
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Get Alarms By Severity
    // =========================================

    public List<AlarmRecord> getAlarmsBySeverity(
            AlarmSeverity severity
    ) {

        if (severity == null) {

            return getAllHistory();
        }


        return alarmHistory

                .stream()

                .filter(
                        alarm ->
                                alarm.getSeverity()
                                        == severity
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Get Alarms By Source
    // =========================================

    public List<AlarmRecord> getAlarmsBySource(
            AlarmSource source
    ) {

        if (source == null) {

            return getAllHistory();
        }


        return alarmHistory

                .stream()

                .filter(
                        alarm ->
                                alarm.getSource()
                                        == source
                )

                .collect(
                        Collectors.toList()
                );
    }


    // =========================================
    // Acknowledge Alarm
    //
    // Uses AlarmStatus enum
    // =========================================

    public boolean acknowledgeAlarm(

            Transceiver module,

            int index
    ) {

        if (module == null) {

            return false;
        }


        List<AlarmRecord> moduleAlarms =
                getHistory(
                        module
                );


        if (
                index < 0
                        || index >= moduleAlarms.size()
        ) {

            return false;
        }


        AlarmRecord alarm =
                moduleAlarms.get(
                        index
                );


        if (
                alarm.getStatus()
                        != AlarmStatus.ACTIVE
        ) {

            return false;
        }


        alarm.acknowledge();


        return true;
    }


    // =========================================
    // Acknowledge Alarm Object
    // =========================================

    public boolean acknowledgeAlarm(
            AlarmRecord alarm
    ) {

        if (alarm == null) {

            return false;
        }


        if (
                alarm.getStatus()
                        != AlarmStatus.ACTIVE
        ) {

            return false;
        }


        alarm.acknowledge();


        return true;
    }


    // =========================================
    // Acknowledge All Active Alarms
    // =========================================

    public void acknowledgeAll() {

        for (AlarmRecord alarm : alarmHistory) {

            if (
                    alarm != null
                            && alarm.getStatus()
                            == AlarmStatus.ACTIVE
            ) {

                alarm.acknowledge();
            }
        }
    }


    // =========================================
    // Clear All History
    // =========================================

    public void clearHistory() {

        alarmHistory.clear();
    }


    // =========================================
    // Clear History By Module
    // =========================================

    public void clearHistory(
            Transceiver module
    ) {

        if (module == null) {

            return;
        }


        alarmHistory.removeIf(

                alarm ->

                        module.getModuleId()
                                .equals(
                                        alarm.getModuleId()
                                )
        );
    }


    // =========================================
    // Total Alarm Count
    // =========================================

    public int getTotalAlarms() {

        return alarmHistory.size();
    }


    // =========================================
    // Active Alarm Count
    // =========================================

    public int getActiveAlarmCount() {

        return (int)

                alarmHistory

                        .stream()

                        .filter(
                                alarm ->
                                        alarm.getStatus()
                                                == AlarmStatus.ACTIVE
                        )

                        .count();
    }


    // =========================================
    // Check Similar Active Alarm
    // =========================================

    public boolean hasSimilarActiveAlarm(

            String moduleId,

            AlarmType alarmType
    ) {

        if (
                moduleId == null
                        || alarmType == null
        ) {

            return false;
        }


        return alarmHistory

                .stream()

                .anyMatch(

                        alarm ->

                                moduleId.equals(
                                        alarm.getModuleId()
                                )

                                        && alarm.getType()
                                        == alarmType

                                        && alarm.getStatus()
                                        == AlarmStatus.ACTIVE
                );
    }
}