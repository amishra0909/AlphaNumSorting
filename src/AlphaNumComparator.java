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

            if (s1Sections[index].compareTo(s2Sections[index]) == 0) {
                index++;
                continue;
            }
            if (isIntSection(s1Sections[index]) && isIntSection(s2Sections[index])) {
                try {
                    int s1Integer = Integer.parseInt(s1Sections[index]);
                    int s2Integer = Integer.parseInt(s2Sections[index]);

                    int diff = s1Integer - s2Integer;
                    if (diff != 0) {
                    	return diff;
                    }
                    index++;
                    continue;
                } catch (NumberFormatException e) {
                	// naive logging : just put a warning statement on console and throw exception;
                    System.out.println("ERROR: one of string cannot be parsed as number (execution should not reach here).");
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            } else {
                return s1Sections[index].compareTo(s2Sections[index]);
            }

        }

        // In case when one string is a prefix of other string,
        // while loop will not fetch a way to compare the strings.
        // Thus length of the string will determine the order.
        return s1.length() - s2.length();
    }

    private boolean isIntSection(String s) {
        return (s.charAt(0) >= '0' && s.charAt(0) <= '9');
    }
}
