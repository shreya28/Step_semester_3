public class Problem3_VitalsMonitoringEncapsulationGuard {

    static class PatientVitals {

        private double[] readings;
        private int count;

        PatientVitals(double[] initialReadings) {

            readings = new double[500];

            if (initialReadings != null) {
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        void recordReading(double reading) {

            if (reading <= 0 || reading > 45) {
                return;
            }

            if (count < readings.length) {
                readings[count] = reading;
                count++;
            }
        }

        double getAverage() {

            if (count == 0) {
                return 0.0;
            }

            double sum = 0;

            for (int i = 0; i < count; i++) {
                sum += readings[i];
            }

            return sum / count;
        }

        double[] getAllReadings() {

            double[] result = new double[count];

            for (int i = 0; i < count; i++) {
                result[i] = readings[i];
            }

            return result;
        }
    }

    public static void main(String[] args) {

        PatientVitals v =
                new PatientVitals(
                        new double[]{36.5, -2, 37.1}
                );

        double[] readings = v.getAllReadings();

        System.out.println("Readings:");

        for (double reading : readings) {
            System.out.println(reading);
        }

        readings[0] = 999;

        System.out.println(
                "First reading after modification: "
                        + v.getAllReadings()[0]
        );

        System.out.println(
                "Average: " + v.getAverage()
        );
    }
}