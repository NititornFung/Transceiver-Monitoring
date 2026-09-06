import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;

import java.util.List;


public class CsvExportService {


    // =========================================
    // DATE FORMAT
    // =========================================

    private static final DateTimeFormatter DATE_FORMAT =

            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );


    // =========================================
    // EXPORT TEST RECORDS
    // =========================================

    public boolean exportTestRecords(

            Window owner,

            List<MonitoringRecord> records
    ) {

        File file =
                chooseFile(

                        owner,

                        "Export Test Session",

                        "test_session.csv"
                );


        if (file == null) {

            return false;
        }


        try (

                FileWriter writer =
                        new FileWriter(file)
        ) {


            // HEADER

            writer.write(

                    "Timestamp,"

                            + "Module ID,"

                            + "Model,"

                            + "Temperature (C),"

                            + "Voltage (V),"

                            + "TX Power (dBm),"

                            + "RX Power (dBm),"

                            + "Wavelength (nm),"

                            + "Laser Current (mA),"

                            + "Status,"

                            + "Source\n"
            );


            // DATA

            for (MonitoringRecord record : records) {


                writer.write(

                        escape(
                                DATE_FORMAT.format(
                                        record.getTimestamp()
                                )
                        )

                                + ","

                                + escape(
                                record.getModuleId()
                        )

                                + ","

                                + escape(
                                record.getModel()
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

                                + record.getWavelength()

                                + ","

                                + record.getLaserCurrent()

                                + ","

                                + escape(
                                record.getStatus()
                        )

                                + ","

                                + record.getSource()

                                + "\n"
                );
            }


            return true;

        }

        catch (IOException exception) {

            exception.printStackTrace();

            return false;
        }
    }


    // =========================================
    // EXPORT CUSTOM HISTORY
    // =========================================

    public boolean exportHistory(

            Window owner,

            List<MonitoringRecord> records,

            boolean exportTemperature,

            boolean exportVoltage,

            boolean exportTxPower,

            boolean exportRxPower,

            boolean exportWavelength,

            boolean exportLaserCurrent,

            boolean exportStatus,

            boolean exportSource
    ) {

        File file =
                chooseFile(

                        owner,

                        "Export Monitoring History",

                        "monitoring_history.csv"
                );


        if (file == null) {

            return false;
        }


        try (

                FileWriter writer =
                        new FileWriter(file)
        ) {


            // =====================================
            // HEADER
            // =====================================

            StringBuilder header =
                    new StringBuilder();


            header.append(
                    "Timestamp,Module ID,Model"
            );


            if (exportTemperature) {

                header.append(
                        ",Temperature (C)"
                );
            }


            if (exportVoltage) {

                header.append(
                        ",Voltage (V)"
                );
            }


            if (exportTxPower) {

                header.append(
                        ",TX Power (dBm)"
                );
            }


            if (exportRxPower) {

                header.append(
                        ",RX Power (dBm)"
                );
            }


            if (exportWavelength) {

                header.append(
                        ",Wavelength (nm)"
                );
            }


            if (exportLaserCurrent) {

                header.append(
                        ",Laser Current (mA)"
                );
            }


            if (exportStatus) {

                header.append(
                        ",Status"
                );
            }


            if (exportSource) {

                header.append(
                        ",Source"
                );
            }


            header.append("\n");


            writer.write(
                    header.toString()
            );


            // =====================================
            // DATA
            // =====================================

            for (MonitoringRecord record : records) {


                StringBuilder row =
                        new StringBuilder();


                row.append(

                        escape(
                                DATE_FORMAT.format(
                                        record.getTimestamp()
                                )
                        )
                );


                row.append(",");

                row.append(
                        escape(record.getModuleId())
                );


                row.append(",");

                row.append(
                        escape(record.getModel())
                );


                if (exportTemperature) {

                    row.append(",");
                    row.append(
                            record.getTemperature()
                    );
                }


                if (exportVoltage) {

                    row.append(",");
                    row.append(
                            record.getVoltage()
                    );
                }


                if (exportTxPower) {

                    row.append(",");
                    row.append(
                            record.getTxPower()
                    );
                }


                if (exportRxPower) {

                    row.append(",");
                    row.append(
                            record.getRxPower()
                    );
                }


                if (exportWavelength) {

                    row.append(",");
                    row.append(
                            record.getWavelength()
                    );
                }


                if (exportLaserCurrent) {

                    row.append(",");
                    row.append(
                            record.getLaserCurrent()
                    );
                }


                if (exportStatus) {

                    row.append(",");

                    row.append(
                            escape(
                                    record.getStatus()
                            )
                    );
                }


                if (exportSource) {

                    row.append(",");

                    row.append(
                            record.getSource()
                    );
                }


                row.append("\n");


                writer.write(
                        row.toString()
                );
            }


            return true;

        }

        catch (IOException exception) {

            exception.printStackTrace();

            return false;
        }
    }


    // =========================================
    // FILE CHOOSER
    // =========================================

    private File chooseFile(

            Window owner,

            String title,

            String defaultFileName
    ) {

        FileChooser fileChooser =
                new FileChooser();


        fileChooser.setTitle(
                title
        );


        fileChooser.setInitialFileName(
                defaultFileName
        );


        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(

                        "CSV Files",

                        "*.csv"
                )
        );


        return fileChooser.showSaveDialog(
                owner
        );
    }


    // =========================================
    // CSV ESCAPE
    // =========================================

    private String escape(
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


        return "\""
                + escaped
                + "\"";
    }
}