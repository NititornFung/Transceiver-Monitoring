import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class OpticalSpectrumSimulator {


    // =========================================
    // RANDOM
    // =========================================

    private final Random random =
            new Random();


    // =========================================
    // GENERATE OPTICAL SPECTRUM
    // =========================================

    public List<SpectrumPoint> generateSpectrum(
            double centerWavelength
    ) {


        List<SpectrumPoint> spectrum =
                new ArrayList<>();


        // =========================================
        // SPECTRUM RANGE
        // =========================================

        double start =
                centerWavelength - 3.0;

        double end =
                centerWavelength + 3.0;

        double step =
                0.02;


        // =========================================
        // MAIN PEAK PARAMETERS
        // =========================================

        double mainPeakPower =
                -1.0
                        + random.nextDouble() * 1.0;


        double mainPeakWidth =
                0.35;


        // =========================================
        // SIDE MODE PARAMETERS
        // =========================================

        double sideModeOffset =
                1.2;


        double sideModePower =
                -25.0
                        + random.nextDouble() * 3.0;


        // =========================================
        // GENERATE DATA
        // =========================================

        for (
                double wavelength = start;
                wavelength <= end;
                wavelength += step
        ) {


            // =====================================
            // NOISE FLOOR
            // =====================================

            double power =
                    -45.0
                            + random.nextDouble() * 3.0;


            // =====================================
            // MAIN PEAK
            // =====================================

            power += gaussian(
                    wavelength,
                    centerWavelength,
                    mainPeakWidth,
                    mainPeakPower
            );


            // =====================================
            // LEFT SIDE MODE
            // =====================================

            power += gaussian(
                    wavelength,
                    centerWavelength
                            - sideModeOffset,
                    0.18,
                    sideModePower
            );


            // =====================================
            // RIGHT SIDE MODE
            // =====================================

            power += gaussian(
                    wavelength,
                    centerWavelength
                            + sideModeOffset,
                    0.18,
                    sideModePower
            );


            spectrum.add(
                    new SpectrumPoint(
                            wavelength,
                            power
                    )
            );
        }


        return spectrum;
    }


    // =========================================
    // GAUSSIAN FUNCTION
    // =========================================

    private double gaussian(
            double x,
            double center,
            double width,
            double peakPower
    ) {


        double exponent =
                -Math.pow(
                        x - center,
                        2
                )
                        /
                        (
                                2
                                        * width
                                        * width
                        );


        return peakPower
                *
                Math.exp(
                        exponent
                );
    }
}