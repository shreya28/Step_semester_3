import java.util.LinkedHashMap;
import java.util.Map;

public class Problem1_SmartHomePlatform {

    // IS-A: every device shares an ID and an on/off state.
    static abstract class Device {

        protected final String id;
        protected boolean on;

        Device(String id) {
            this.id = id;
        }

        void turnOn() {
            on = true;
            System.out.println(id + " is ON");
        }

        void turnOff() {
            on = false;
            System.out.println(id + " is OFF");
        }
    }

    // CAN-DO: capabilities that only some devices support.
    interface Dimmable {
        void dim(int level);
    }

    interface Schedulable {
        void schedule(String time);
    }

    interface EnergyMonitorable {
        void reportEnergy();
    }

    static class Light extends Device implements Dimmable, Schedulable {

        Light(String id) {
            super(id);
        }

        public void dim(int level) {
            System.out.println(id + " dimmed to " + level);
        }

        public void schedule(String time) {
            System.out.println(id + " scheduled " + time);
        }
    }

    static class Fan extends Device implements Schedulable {

        Fan(String id) {
            super(id);
        }

        public void schedule(String time) {
            System.out.println(id + " scheduled " + time);
        }
    }

    static class Plug extends Device implements EnergyMonitorable {

        private final int energyKwh;

        Plug(String id, int energyKwh) {
            super(id);
            this.energyKwh = energyKwh;
        }

        public void reportEnergy() {
            System.out.println(id + " energy " + energyKwh + " kWh");
        }
    }

    static Map<String, Device> devices = new LinkedHashMap<>();

    static void reject(Device device, String command) {
        System.out.println(device.id + " rejected: " + command + " unsupported");
    }

    static void process(String command) {

        String[] parts = command.split(" ");
        String action = parts[0];
        Device device = devices.get(parts[1]);

        if (device == null) {
            System.out.println(parts[1] + " rejected: unknown device");
            return;
        }

        switch (action) {
            case "ON":
                device.turnOn();
                break;
            case "OFF":
                device.turnOff();
                break;
            case "DIM":
                if (device instanceof Dimmable) {
                    ((Dimmable) device).dim(Integer.parseInt(parts[2]));
                } else {
                    reject(device, action);
                }
                break;
            case "SCHEDULE":
                if (device instanceof Schedulable) {
                    ((Schedulable) device).schedule(parts[2]);
                } else {
                    reject(device, action);
                }
                break;
            case "ENERGY":
                if (device instanceof EnergyMonitorable) {
                    ((EnergyMonitorable) device).reportEnergy();
                } else {
                    reject(device, action);
                }
                break;
            default:
                System.out.println(device.id + " rejected: unknown command " + action);
        }
    }

    public static void main(String[] args) {

        devices.put("L1", new Light("L1"));
        devices.put("F1", new Fan("F1"));
        devices.put("P1", new Plug("P1", 12));

        String[] commands = {
            "ON L1",
            "DIM L1 40",
            "DIM F1 30",
            "SCHEDULE F1 22:00",
            "ENERGY P1",
            "ENERGY L1"
        };

        for (String command : commands) {
            process(command);
        }
    }
}
