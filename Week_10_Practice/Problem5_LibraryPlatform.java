public class Problem5_LibraryPlatform {

    static abstract class Resource {

        protected final String id;

        Resource(String id) {
            this.id = id;
        }
    }

    interface Reservable {
        void reserve(String memberId);
    }

    interface Downloadable {
        void download();
    }

    static class Book extends Resource implements Reservable {

        Book(String id) {
            super(id);
        }

        public void reserve(String memberId) {
            System.out.println(id + " reserved for " + memberId);
        }
    }

    static class EBook extends Resource implements Downloadable {

        EBook(String id) {
            super(id);
        }

        public void download() {
            System.out.println(id + " downloaded");
        }
    }

    // Resources are kept in an array sorted by ID so FIND can use binary search.
    static Resource[] resources = new Resource[10];
    static int size = 0;

    // Returns the index of id, or -(insertionPoint + 1) if it is not present.
    static int search(String id) {

        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = resources[mid].id.compareTo(id);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return -(lo + 1);
    }

    static void add(Resource resource) {

        int index = search(resource.id);

        if (index >= 0) {
            System.out.println("duplicate rejected");
            return;
        }

        if (size == resources.length) {
            Resource[] bigger = new Resource[resources.length * 2];
            System.arraycopy(resources, 0, bigger, 0, size);
            resources = bigger;
        }

        // Shift elements right to keep the array ordered by ID.
        int insertAt = -(index + 1);
        for (int i = size; i > insertAt; i--) {
            resources[i] = resources[i - 1];
        }
        resources[insertAt] = resource;
        size++;

        System.out.println(resource.id + " added");
    }

    static Resource find(String id) {
        int index = search(id);
        return index >= 0 ? resources[index] : null;
    }

    static void reserve(String id, String memberId) {

        Resource resource = find(id);

        if (resource == null) {
            System.out.println(id + " not found");
        } else if (resource instanceof Reservable) {
            ((Reservable) resource).reserve(memberId);
        } else {
            System.out.println(id + " rejected: reserve unsupported");
        }
    }

    static void download(String id) {

        Resource resource = find(id);

        if (resource == null) {
            System.out.println(id + " not found");
        } else if (resource instanceof Downloadable) {
            ((Downloadable) resource).download();
        } else {
            System.out.println(id + " rejected: download unsupported");
        }
    }

    static void findAndReport(String id) {

        int index = search(id);

        if (index >= 0) {
            System.out.println(id + " found at index " + index);
        } else {
            System.out.println(id + " not found");
        }
    }

    public static void main(String[] args) {

        add(new Book("B1"));
        add(new Book("B1"));
        add(new EBook("E1"));
        reserve("B1", "M1");
        download("B1");
        download("E1");
        findAndReport("E1");
    }
}
