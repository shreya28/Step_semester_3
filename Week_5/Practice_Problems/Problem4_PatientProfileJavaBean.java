public class Problem4_PatientProfileJavaBean {

    static class PatientProfile {

        private String patientId;
        private String name;
        private boolean discharged;
        private String lockerPinHash;

        public PatientProfile() {
            this(null);
        }

        public PatientProfile(String name) {
            this(null, name);
        }

        public PatientProfile(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
            this.discharged = false;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String id) {
            if (this.patientId == null) {
                this.patientId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDischarged() {
            return discharged;
        }

        public void setDischarged(boolean discharged) {
            this.discharged = discharged;
        }

        public void setLockerPin(String pin) {
            if (pin != null && pin.matches("\\d{4,6}")) {
                lockerPinHash = Integer.toString(pin.hashCode());
            }
        }
    }

    public static void main(String[] args) {

        PatientProfile p1 =
                new PatientProfile("Arjun Iyer");

        System.out.println(
                "Name-only ID: " + p1.getPatientId()
        );

        PatientProfile p2 =
                new PatientProfile(
                        "MT2026-0142",
                        "Arjun Iyer"
                );

        System.out.println(
                "Full constructor ID: "
                        + p2.getPatientId()
        );

        p2.setPatientId("HACKED-0000");

        System.out.println(
                "Write-once ID: "
                        + p2.getPatientId()
        );

        System.out.println(
                "Discharged before: "
                        + p2.isDischarged()
        );

        p2.setDischarged(true);

        System.out.println(
                "Discharged after: "
                        + p2.isDischarged()
        );

        p2.setLockerPin("1234");

        System.out.println("Locker PIN set successfully");
    }
}