import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CsvExporter {


    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );


    // =================================
    // Export Monitoring History
    // =================================

    public static void exportMonitoringHistory(
            File file,
            List<MonitoringHistory> records
    ) throws IOException {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(file)
                        )
        ) {

            writer.write(
                    "Timestamp,"
                            + "Module ID,"
                            + "Temperature (C),"
                            + "Voltage (V),"
                            + "TX Power (dBm),"
                            + "RX Power (dBm),"
                            + "Laser Current (mA),"
                            + "Status"
            );

            writer.newLine();


            for (
                    MonitoringHistory record :
                    records
            ) {

                writer.write(
                        formatDate(
                                record.getTimestamp()
                        )
                                + ","
                                + escape(
                                record.getModuleId()
                        )
                                + ","
                                + record.getTemperature()
                                + ","
                                + record.getVoltage()
                                + ","
                                + record.getTxPower()
                                + ","
                                + record.getRxPower()
                                + ","
                                + record.getLaserCurrent()
                                + ","
                                + escape(
                                record.getStatus()
                        )
                );

                writer.newLine();
            }
        }
    }


    // =================================
    // Export Alarm History
    // =================================

    public static void exportAlarmHistory(
            File file,
            List<AlarmRecord> records
    ) throws IOException {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(file)
                        )
        ) {

            writer.write(
                    "Timestamp,"
                            + "Module ID,"
                            + "Alarm Type,"
                            + "Severity,"
                            + "Message,"
                            + "Actual Value,"
                            + "Threshold,"
                            + "Source,"
                            + "Status"
            );

            writer.newLine();


            for (
                    AlarmRecord alarm :
                    records
            ) {

                writer.write(
                        formatDate(
                                alarm.getTimestamp()
                        )
                                + ","
                                + escape(
                                alarm.getModuleId()
                        )
                                + ","
                                + escape(
                                String.valueOf(
                                        alarm.getType()
                                )
                        )
                                + ","
                                + escape(
                                String.valueOf(
                                        alarm.getSeverity()
                                )
                        )
                                + ","
                                + escape(
                                alarm.getMessage()
                        )
                                + ","
                                + alarm.getActualValue()
                                + ","
                                + alarm.getThreshold()
                                + ","
                                + escape(
                                String.valueOf(
                                        alarm.getSource()
                                )
                        )
                                + ","
                                + escape(
                                String.valueOf(
                                        alarm.getStatus()
                                )
                        )
                );

                writer.newLine();
            }
        }
    }


    // =================================
    // Format Date
    // =================================

    private static String formatDate(
            java.time.LocalDateTime dateTime
    ) {

        if (dateTime == null) {

            return "";
        }


        return dateTime.format(
                DATE_FORMAT
        );
    }


    // =================================
    // Escape CSV Value
    // =================================

    private static String escape(
            String value
    ) {

        if (value == null) {

            return "";
        }


        String escaped =
                value.replace(
                        "\"",
                        "\"\""
                );


        if (
                escaped.contains(",")
                        || escaped.contains("\"")
                        || escaped.contains("\n")
        ) {

            return "\""
                    + escaped
                    + "\"";
        }


        return escaped;
    }
}