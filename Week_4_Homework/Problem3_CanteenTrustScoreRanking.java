public class Problem3_CanteenTrustScoreRanking {

    static class Canteen {

        String canteenCode;
        String canteenName;
        int trustScore;

        Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        int compareTo(Canteen other) {

            // Higher trust score comes first
            if (this.trustScore != other.trustScore) {
                return other.trustScore - this.trustScore;
            }

            // If scores are equal, compare codes
            int codeResult =
                    this.canteenCode.compareToIgnoreCase(other.canteenCode);

            if (codeResult != 0) {
                return codeResult;
            }

            // If codes are also equal, shorter name comes first
            return this.canteenName.length() - other.canteenName.length();
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        // Bubble sort
        for (int i = 0; i < canteens.length - 1; i++) {

            for (int j = 0; j < canteens.length - 1 - i; j++) {

                if (canteens[j].compareTo(canteens[j + 1]) > 0) {

                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }

        return canteens;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        rankCanteens(canteens);

        for (Canteen canteen : canteens) {
            System.out.println(canteen.canteenCode);
        }
    }
}