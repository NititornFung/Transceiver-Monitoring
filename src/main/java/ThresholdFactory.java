public class ThresholdFactory {

    public static ThresholdManager getThreshold(
            String model
    ) {

        if (model == null) {

            throw new IllegalArgumentException(
                    "Model cannot be null."
            );
        }


        switch (model.toUpperCase()) {


            // =================================
            // 400G COSA
            // =================================

            case "400G COSA":

                return new ThresholdManager(
                        85.0,

                        3.0,
                        3.6,

                        -10.0,

                        -5.0,
                        3.0,

                        10.0,
                        100.0,

                        1290.0,
                        1330.0
                );


            // =================================
            // 800G COSAz
            // =================================

            case "800G COSAZ":

                return new ThresholdManager(
                        85.0,

                        3.0,
                        3.6,

                        -10.0,

                        -5.0,
                        3.0,

                        10.0,
                        100.0,

                        1290.0,
                        1330.0
                );


            // =================================
            // 800G SR8
            // =================================

            case "800G SR8":

                return new ThresholdManager(
                        85.0,

                        3.0,
                        3.6,

                        -12.0,

                        -6.0,
                        3.0,

                        5.0,
                        80.0,

                        840.0,
                        860.0
                );


            // =================================
            // Unknown Model
            // =================================

            default:

                throw new IllegalArgumentException(
                        "Unknown transceiver model: "
                                + model
                );
        }
    }
}