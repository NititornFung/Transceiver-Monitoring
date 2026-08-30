import java.util.List;
import java.util.Scanner;

public class HistoryViewer {

    private MonitoringHistoryService historyService;
    private List<Transceiver> modules;
    private Scanner scanner;


    // =================================
    // Constructor
    // =================================

    public HistoryViewer(
            MonitoringHistoryService historyService,
            List<Transceiver> modules
    ) {

        this.historyService = historyService;
        this.modules = modules;
        this.scanner = new Scanner(System.in);
    }


    // =================================
    // Show History Menu
    // =================================

    public void showHistoryMenu() {

        System.out.println(
                "\n================================"
        );

        System.out.println(
                "       HISTORY VIEWER"
        );

        System.out.println(
                "================================"
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
                "0. Back"
        );


        // =================================
        // Select Module
        // =================================

        System.out.print(
                "\nSelect module: "
        );

        int choice =
                scanner.nextInt();


        if (choice == 0) {
            return;
        }


        if (choice < 1
                || choice > modules.size()) {

            System.out.println(
                    "Invalid selection."
            );

            return;
        }


        Transceiver selectedModule =
                modules.get(choice - 1);


        // =================================
        // Show History
        // =================================

        historyService.printHistory(
                selectedModule
        );


        System.out.println(
                "\nPress ENTER to return..."
        );

        scanner.nextLine();
        scanner.nextLine();
    }
}