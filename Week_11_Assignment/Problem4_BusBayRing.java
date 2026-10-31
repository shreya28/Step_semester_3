public class Problem4_BusBayRing {

    // Double-ended queue on a fixed-size int array used as a ring buffer.
    // Only front and size are stored; indices wrap with % capacity, so no element
    // is ever shifted. Every method is O(1) time; space is O(capacity).
    static class BusBay {

        private final int[] bays;
        private final int capacity;
        private int front;
        private int size;

        BusBay(int capacity) {
            this.capacity = capacity;
            this.bays = new int[capacity];
            this.front = 0;
            this.size = 0;
        }

        boolean insertFront(int bus) {

            if (isFull()) {
                return false;
            }

            // Step front one slot backwards, wrapping to the end of the array.
            front = (front - 1 + capacity) % capacity;
            bays[front] = bus;
            size++;
            return true;
        }

        boolean insertLast(int bus) {

            if (isFull()) {
                return false;
            }

            bays[(front + size) % capacity] = bus;
            size++;
            return true;
        }

        boolean deleteFront() {

            if (isEmpty()) {
                return false;
            }

            front = (front + 1) % capacity;
            size--;
            return true;
        }

        boolean deleteLast() {

            if (isEmpty()) {
                return false;
            }

            size--;
            return true;
        }

        int getFront() {
            return isEmpty() ? -1 : bays[front];
        }

        int getRear() {
            return isEmpty() ? -1 : bays[(front + size - 1) % capacity];
        }

        boolean isEmpty() {
            return size == 0;
        }

        boolean isFull() {
            return size == capacity;
        }
    }

    public static void main(String[] args) {

        BusBay b = new BusBay(3);

        System.out.println(b.insertLast(101) + ", " + b.insertLast(102) + ", " + b.insertFront(100) + ",");
        System.out.println(b.insertFront(99) + ", " + b.getRear() + ", " + b.isFull() + ", " + b.deleteLast() + ",");
        System.out.println(b.insertFront(98) + ", " + b.getFront());
    }
}
