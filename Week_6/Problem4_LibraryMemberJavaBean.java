public class Problem4_LibraryMemberJavaBean {

    static class LibraryMember {

        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswer;

        // No-argument constructor
        LibraryMember() {
            this(null, null);
        }

        // Name-only constructor
        LibraryMember(String name) {
            this(null, name);
        }

        // Main constructor
        LibraryMember(String membershipId, String name) {
            this.membershipId = membershipId;
            this.name = name;
        }

        String getMembershipId() {
            return membershipId;
        }

        void setMembershipId(String id) {

            // Write-once property
            if (membershipId == null) {
                membershipId = id;
            }
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        boolean isPremiumMember() {
            return premiumMember;
        }

        void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        // Write-only property
        void setSecurityAnswer(String answer) {

            if (answer != null) {
                // Deterministic one-way transformation
                securityAnswer = Integer.toHexString(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {

        LibraryMember member1 =
                new LibraryMember("Priya Nair");

        System.out.println(
                "Name-only ID: "
                + member1.getMembershipId()
        );

        LibraryMember member2 =
                new LibraryMember("LIB-8841", "Priya Nair");

        System.out.println(
                "Full constructor ID: "
                + member2.getMembershipId()
        );

        LibraryMember member3 =
                new LibraryMember();

        member3.setMembershipId("LIB-8841");
        member3.setMembershipId("FAKE-0000");

        System.out.println(
                "Write-once ID: "
                + member3.getMembershipId()
        );

        member3.setSecurityAnswer("blue");

        System.out.println(
                "Premium before: "
                + member3.isPremiumMember()
        );

        member3.setPremiumMember(true);

        System.out.println(
                "Premium after: "
                + member3.isPremiumMember()
        );
    }
}