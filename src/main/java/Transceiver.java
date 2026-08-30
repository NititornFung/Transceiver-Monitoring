public class Transceiver {
    private String moduleId;
    private String model;
    private Telemetry telemetry;

    // =========================
    // Constructor
    // =========================

    public Transceiver(String moduleId, String model) {

        this.moduleId = moduleId;
        this.model = model;
    }


    // =========================
    // Getter
    // =========================

    public String getModuleId() {
        return moduleId;
    }

    public String getModel() {
        return model;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }



    public void updateTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }



}