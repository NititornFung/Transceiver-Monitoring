import java.util.List;
import java.util.Scanner;

public class MonitoringConsole {

    private final MonitoringHistoryService historyService;
    private final List<Transceiver> modules;
    private final Scanner scanner;


    // =================================
    // Constructor
    // =================================

    public MonitoringConsole(
            MonitoringHistoryService historyService,
            List<Transceiver> modules
    ) {

        this.historyService = historyService;
        this.modules = modules;
        this.scanner = new Scanner(System.in);
    }


    // =================================
    // History Menu
    // =================================

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


            // =================================
            // Show Modules
            // =================================

            for (int i = 0; i < modules.size(); i++) {

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
                    "0. Back to Monitoring"
            );


            // =================================
            // User Input
            // =================================

            System.out.print(
                    "\nSelect module: "
            );

            String input =
                    scanner.nextLine();


            int choice;

            try {

                choice =
                        Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input."
                );

                continue;
            }


            // =================================
            // Back
            // =================================

            if (choice == 0) {

                return;
            }


            // =================================
            // Invalid Choice
            // =================================

            if (choice < 1
                    || choice > modules.size()) {

                System.out.println(
                        "Invalid module."
                );

                continue;
            }


            // =================================
            // Select Module
            // =================================

            Transceiver selectedModule =
                    modules.get(choice - 1);


            showModuleHistory(
                    selectedModule
            );


            // =================================
            // After History
            // =================================

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


                if (menuInput.equals("1")) {

                    return;
                }


                if (menuInput.equals("2")) {

                    break;
                }


                if (menuInput.equals("0")) {

                    return;
                }


                System.out.println(
                        "Invalid choice."
                );
            }
        }
    }


    // =================================
    // Show Module History
    // =================================

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


        // =================================
        // Get History
        // =================================

        List<MonitoringRecord> records =
                historyService.getHistory(
                        module
                );


        if (records.isEmpty()) {

            System.out.println(
                    "\nNo history available."
            );

            return;
        }


        // =================================
        // Display Records
        // =================================

        System.out.println();


        for (MonitoringRecord record : records) {

            System.out.println(
                    record
            );
        }


        System.out.println(
                "\nTotal Records : "
                        + records.size()
        );
    }
}