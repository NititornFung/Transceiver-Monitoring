public class SpectrumPoint {


    // =========================================
    // VARIABLES
    // =========================================

    private final double wavelength;

    private final double power;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public SpectrumPoint(
            double wavelength,
            double power
    ) {

        this.wavelength =
                wavelength;

        this.power =
                power;
    }


    // =========================================
    // GETTERS
    // =========================================

    public double getWavelength() {

        return wavelength;
    }


    public double getPower() {

        return power;
    }
}