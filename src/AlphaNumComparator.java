import java.util.Comparator;
import java.util.regex.Pattern;

public class AlphaNumComparator implements Comparator<String> {

    // referred to following stackoverflow link to get pattern string:
    // http://stackoverflow.com/questions/8270784/how-to-split-a-string-between-letters-and-digits-or-between-digits-and-letters
    private static final Pattern regex = Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    @Override
    public int compare(String s1, String s2) {

        String[] s1Sections = regex.split(s1);
        String[] s2Sections = regex.split(s2);

        int index = 0;
        while (index < s1Sections.length && index < s2Sections.length) {

            String section1 = s1Sections[index];
            String section2 = s2Sections[index];
            if (section1.compareTo(section2) == 0) {
                index++;
                continue;
            }
            if (isNumSection(section1) && isNumSection(section2)) {
                int diff = 0;
                // check for special case of floating point
                if (index > 0 && s1Sections[index-1].endsWith(".") && s2Sections[index-1].endsWith(".")) {
                    diff = diffAsFloats(section1, section2);
                } else {
                    diff = diffAsIntegers(section1, section2);
                }

                return diff;
            } else {
                return section1.compareTo(section2);
            }

        }

        // In case when one string is a prefix of other string,
        // while loop will not fetch a way to compare the strings.
        // Thus length of the string will determine the order.
        return s1.length() - s2.length();
    }

    private boolean isNumSection(String s) {
        return (s.charAt(0) >= '0' && s.charAt(0) <= '9');
    }

    private int diffAsFloats(String s1, String s2) {
        try {
            float f1 = Float.parseFloat("." + s1);
            float f2 = Float.parseFloat("." + s2);
            float diff = f1 - f2;
            return diff > 0 ? 1 : -1;
        } catch (NumberFormatException e) {
           System.out.println("ERROR: one of string cannot be parsed as number (execution should not reach here).");
           System.out.println(e.getMessage());
           throw new RuntimeException(e);
        }
    }

    private int diffAsIntegers(String s1, String s2) {
        try {
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            int diff = i1 - i2;

            // In case when integer value of both string are same ascii comparison should be returned,
            // like in case of "1" and "01", "01" should appear first.
            if (diff == 0) {
                diff = s1.compareTo(s2);
            }
            return diff;
        } catch (NumberFormatException e) {
           System.out.println("ERROR: one of string cannot be parsed as number (execution should not reach here).");
           System.out.println(e.getMessage());
           throw new RuntimeException(e);
        }
    }
}
