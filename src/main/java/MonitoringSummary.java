public class MonitoringSummary {

    private int totalModules;
    private int normalCount;
    private int warningCount;
    private int criticalCount;


    // =================================
    // Constructor
    // =================================

    public MonitoringSummary(
            int totalModules,
            int normalCount,
            int warningCount,
            int criticalCount
    ) {

        this.totalModules = totalModules;
        this.normalCount = normalCount;
        this.warningCount = warningCount;
        this.criticalCount = criticalCount;
    }


    // =================================
    // Getter
    // =================================

    public int getTotalModules() {
        return totalModules;
    }

    public int getNormalCount() {
        return normalCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public int getCriticalCount() {
        return criticalCount;
    }


    // =================================
    // Highest Severity
    // =================================

    public AlarmSeverity getHighestSeverity() {

        if (criticalCount > 0) {

            return AlarmSeverity.CRITICAL;
        }

        if (warningCount > 0) {

            return AlarmSeverity.WARNING;
        }

        return null;
    }


    // =================================
    // Display Summary
    // =================================

    public void printSummary() {

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "          MONITORING SUMMARY"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Total Modules : "
                        + totalModules
        );

        System.out.println(
                "NORMAL   : "
                        + normalCount
        );

        System.out.println(
                "WARNING  : "
                        + warningCount
        );

        System.out.println(
                "CRITICAL : "
                        + criticalCount
        );

        System.out.println(
                "Highest Severity : "
                        + getHighestSeverity()
        );
    }
}