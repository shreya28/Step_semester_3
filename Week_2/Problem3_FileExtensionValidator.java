public class Problem3_FileExtensionValidator {

    static String validateFileExtension(String filename) {

        int dot = filename.lastIndexOf('.');

        if (dot == -1) {
            return "Rejected — invalid file type";
        }

        String extension = filename.substring(dot + 1);

        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        }

        return "Rejected — invalid file type";
    }

    public static void main(String[] args) {

        System.out.println(validateFileExtension("Assignment1.PDF"));
        System.out.println(validateFileExtension("notes.txt"));
    }
}