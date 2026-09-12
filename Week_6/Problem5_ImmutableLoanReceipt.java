public class Problem5_ImmutableLoanReceipt {

    static class LoanReceipt {

        private final String memberId;
        private final String[] bookIds;

        static {
            System.out.println("Circulation ledger initialized");
        }

        LoanReceipt(String memberId, String[] bookIds) {

            if (bookIds == null) {
                throw new IllegalArgumentException(
                        "Book IDs cannot be null");
            }

            for (String id : bookIds) {
                if (id == null || !id.matches("BK-\\d{3}")) {
                    throw new IllegalArgumentException(
                            "Invalid book ID: " + id);
                }
            }

            this.memberId = memberId;
            this.bookIds = bookIds.clone();
        }

        String getMemberId() {
            return memberId;
        }

        String[] getBookIds() {
            return bookIds.clone();
        }

        LoanReceipt withCorrectedBookId(int index, String newId) {

            if (index < 0 || index >= bookIds.length) {
                throw new IllegalArgumentException("Invalid index");
            }

            if (newId == null || !newId.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException("Invalid book ID");
            }

            String[] correctedIds = bookIds.clone();
            correctedIds[index] = newId;

            return new LoanReceipt(memberId, correctedIds);
        }
    }

    static class ReferenceOnlyLoanReceipt extends LoanReceipt {

        String roomNumber;

        ReferenceOnlyLoanReceipt(
                String memberId,
                String[] bookIds,
                String roomNumber) {

            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        try {
            new LoanReceipt(
                    "LIB-8841",
                    new String[]{"BK-100", "bad"}
            );
        } catch (Exception e) {
            System.out.println("construction rejected");
        }

        LoanReceipt receipt =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{"BK-100", "BK-101"}
                );

        String[] ids = receipt.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
                "First book ID: "
                + receipt.getBookIds()[0]
        );

        LoanReceipt corrected =
                receipt.withCorrectedBookId(0, "BK-999");

        System.out.println(
                "Corrected ID: "
                + corrected.getBookIds()[0]
        );

        LoanReceipt[] receipts = {
            new ReferenceOnlyLoanReceipt(
                    "LIB-001",
                    new String[]{"BK-200"},
                    "Reading Room 3"
            ),
            null,
            new LoanReceipt(
                    "LIB-002",
                    new String[]{"BK-201"}
            )
        };

        System.out.println(
                processNightlyCirculation(receipts)
        );
    }
}