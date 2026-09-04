import java.util.List;
import java.util.Scanner;


public class MonitoringConsole {

    private final MonitoringHistoryService historyService;

    private final List<Transceiver> modules;

    private final Scanner scanner;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public MonitoringConsole(

            MonitoringHistoryService historyService,

            List<Transceiver> modules
    ) {

        this.historyService =
                historyService;

        this.modules =
                modules;

        this.scanner =
                new Scanner(System.in);
    }


    // =========================================
    // HISTORY MENU
    // =========================================

    public void showHistoryMenu() {

        while (true) {

            System.out.println(
                    "\n========================================"
            );

            System.out.println(
                    "             HISTORY MENU"
            );

            System.out.println(
                    "========================================"
            );


            // =====================================
            // SHOW MODULES
            // =====================================

            for (
                    int i = 0;
                    i < modules.size();
                    i++
            ) {

                Transceiver module =
                        modules.get(i);


                System.out.println(

                        (i + 1)

                                + ". "

                                + module.getModuleId()

                                + " - "

                                + module.getModel()
                );
            }


            System.out.println(
                    "\n0. Back to Monitoring"
            );


            // =====================================
            // USER INPUT
            // =====================================

            System.out.print(
                    "\nSelect module: "
            );


            String input =
                    scanner.nextLine();


            int choice;


            try {

                choice =
                        Integer.parseInt(
                                input.trim()
                        );

            }

            catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input."
                );

                continue;
            }


            // =====================================
            // BACK
            // =====================================

            if (choice == 0) {

                return;
            }


            // =====================================
            // INVALID CHOICE
            // =====================================

            if (
                    choice < 1

                            ||

                            choice > modules.size()
            ) {

                System.out.println(
                        "Invalid module."
                );

                continue;
            }


            // =====================================
            // SELECT MODULE
            // =====================================

            Transceiver selectedModule =
                    modules.get(
                            choice - 1
                    );


            showModuleHistory(
                    selectedModule
            );


            // =====================================
            // AFTER HISTORY MENU
            // =====================================

            while (true) {

                System.out.println(
                        "\n========================================"
                );

                System.out.println(
                        "1. Continue Monitoring"
                );

                System.out.println(
                        "2. View Another Module"
                );

                System.out.println(
                        "0. Exit History Menu"
                );


                System.out.print(
                        "\nSelect: "
                );


                String menuInput =
                        scanner.nextLine();


                // =================================
                // CONTINUE MONITORING
                // =================================

                if (menuInput.equals("1")) {

                    return;
                }


                // =================================
                // VIEW ANOTHER MODULE
                // =================================

                if (menuInput.equals("2")) {

                    break;
                }


                // =================================
                // EXIT
                // =================================

                if (menuInput.equals("0")) {

                    return;
                }


                System.out.println(
                        "Invalid choice."
                );
            }
        }
    }


    // =========================================
    // SHOW MODULE HISTORY
    // =========================================

    private void showModuleHistory(

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


        // =====================================
        // GET HISTORY
        // =====================================

        List<MonitoringRecord> records =
                historyService.getHistory(
                        module
                );


        // =====================================
        // NO HISTORY
        // =====================================

        if (records.isEmpty()) {

            System.out.println(
                    "\nNo history available."
            );

            return;
        }


        // =====================================
        // DISPLAY RECORDS
        // =====================================

        System.out.println();


        for (

                MonitoringRecord record :

                records
        ) {


            System.out.println(
                    "Time        : "
                            + record.getTimestamp()
            );


            System.out.println(
                    "Temperature : "
                            + record.getTemperature()
                            + " °C"
            );


            System.out.println(
                    "Voltage     : "
                            + record.getVoltage()
                            + " V"
            );


            System.out.println(
                    "RX Power    : "
                            + record.getRxPower()
                            + " dBm"
            );


            System.out.println(
                    "TX Power    : "
                            + record.getTxPower()
                            + " dBm"
            );


            System.out.println(
                    "Laser Current : "
                            + record.getLaserCurrent()
                            + " mA"
            );


            System.out.println(
                    "Status      : "
                            + record.getStatus()
            );


            System.out.println(
                    "----------------------------------------"
            );
        }


        // =====================================
        // TOTAL RECORDS
        // =====================================

        System.out.println(
                "\nTotal Records : "
                        + records.size()
        );
    }
}