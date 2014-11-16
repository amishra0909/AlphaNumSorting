import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AlphaNumComparatorTest {

    private AlphaNumComparator comparator = new AlphaNumComparator();

    // basic tests with same type of strings

    @Test
    public void testCompare_withAlphabets_returnASCIIOrder() {
        String testString1 = "abc";
        String testString2 = "cde";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
	}

    @Test
    public void testCompare_withNumerics_returnsValueOrder() {
        String testString1 = "1";
        String testString2 = "2";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test(expected=NullPointerException.class)
    public void testCompare_withNullFirstString_throwsException() {
        String testString = "testString";
        comparator.compare(null, testString);
    }

    @Test(expected=NullPointerException.class)
    public void testCompare_withNullSecondString_throwsException() {
        String testString = "testString";
        comparator.compare(testString, null);
    }

    @Test
    public void testCompare_withSameStrings_returnsNoOrder() {
        String testString1 = "testString123SameString";
        String testString2 = "testString123SameString";
        int comparison = comparator.compare(testString1, testString2);
        assertEquals(comparison, 0);
    }

    // tests with basic alpha numeric strings

    @Test
    public void testCompare_withCommonAlphabetPrefix_returnsOrderBasedOnNumericSuffix() {
        String testString1 = "a1";
        String testString2 = "a2";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withCommonNumericPrefix_returnsOrderBasedOnSuffixASCII() {
        String testString1 = "1a";
        String testString2 = "1b";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withCommonAlphabetSuffix_returnsOrderBasedOnNumericPrefix() {
        String testString1 = "1a";
        String testString2 = "2a";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withCommonNumericSuffix_returnsOrderBasedOnPrefixASCII() {
        String testString1 = "a1";
        String testString2 = "b1";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    // tests where commonly used special character as prefix are used,
    // should appear first, for human readability.

    @Test
    public void testCompare_withUnderScorePrefixed_returnsOrderBasedOnASCII() {
        String testString1 = "_a123.txt";
        String testString2 = "a123.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withDashPrefixed_returnsOrderBasedOnASCII() {
        String testString1 = "-123.txt";
        String testString2 = "123.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withSpacePrefixed_returnsSpaceThenNoSpaceOrder() {
        String testString1 = " abc.txt";
        String testString2 = "abc.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withSpaceInterleaved_returnsSpaceThenNoSpaceOrder() {
        String testString1 = "1 2.txt";
        String testString2 = "12.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    // case sensitivity tests

    @Test
    public void testCompare_withDifferentCases_returnsCapitalToSmallOrder() {
        String testString1 = "abC";
        String testString2 = "abc";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withDifferentCasesInterleaved_returnsCapitalToSmallOrder() {
    	String testString1 = "same123B_Same";
    	String testString2 = "same123b_Same";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }
    
    // special case of numerics starting with 0, for example 1 should appear before 02

    @Test
    public void testCompare_withNumericsStartingWithZero_returnsOrderBasedOnNumericValue() {
        String testString1 = "1.txt";
        String testString2 = "02.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withInterleavedNumericsStartingWithZero_returnsOrderBasedOnNumericValue() {
        String testString1 = "same1.txt";
        String testString2 = "same02.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withNumericsStartingWithZeroButSameValue_returnsOrderBasedOnLength() {
        String testString1 = "01.txt";
        String testString2 = "1.txt";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    // special cases with decimal point and numeric starting with 0

    @Test
    public void testCompare_withZeroAfterDecimalPoint_returnsOrderBasedOnNumericDecimalValue() {
        String testString1 = "1.02";
        String testString2 = "1.11";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }

    @Test
    public void testCompare_withZeroAfterDecimalPointButSameNumericValueAfterDecimalPoint_returnsOrderWithZeroContainingElementFirst() {
        String testString1 = "1.01";
        String testString2 = "1.1";
        int comparison = comparator.compare(testString1, testString2);
        assertTrue(comparison < 0);
    }
}
