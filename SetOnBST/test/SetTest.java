import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Nicholas McCracken and Jack Mikesell
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.constructorTest();
        Set<String> setExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testAddEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> setExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        set.add("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    @Test
    public final void testAddEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> setExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        set.add("red");
        set.add("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    @Test
    public final void testAddNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        Set<String> setExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        set.add("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    @Test
    public final void testAddNonEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        Set<String> setExpected = this.createFromArgsRef("red", "blue",
                "green");
        /*
         * Call method under test
         */
        set.add("blue");
        set.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    @Test
    public final void testRemoveEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        Set<String> setExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String element = set.remove("red");
        String elementExpected = "red";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
        assertEquals(elementExpected, element);
    }

    @Test
    public final void testRemoveEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        Set<String> setExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String element1 = set.remove("blue");
        String elementExpected1 = "blue";
        String element2 = set.remove("red");
        String elementExpected2 = "red";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
        assertEquals(elementExpected1, element1);
        assertEquals(elementExpected2, element2);
    }

    @Test
    public final void testRemoveNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        Set<String> setExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String element = set.remove("red");
        String elementExpected = "red";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
        assertEquals(elementExpected, element);
    }

    @Test
    public final void testRemoveNonEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue", "green");
        Set<String> setExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String element1 = set.remove("red");
        String elementExpected1 = "red";
        String element2 = set.remove("green");
        String elementExpected2 = "green";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
        assertEquals(elementExpected1, element1);
        assertEquals(elementExpected2, element2);
    }

    @Test
    public final void testRemoveAnyEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        Set<String> setExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String element = set.removeAny();
        String elementExpected = "red";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
        assertEquals(elementExpected, element);
    }

    @Test
    public final void testRemoveAnyEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        Set<String> setExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        set.removeAny();
        set.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(setExpected, set);
    }

    @Test
    public final void testRemoveAnyNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        Set<String> setExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        String element = set.removeAny();
        /*
         * Assert that removed element is contained in expected set
         */
        assertTrue(setExpected.contains(element));
        /*
         * Assert that values of variables match expectations
         */
        setExpected.remove(element);
        assertEquals(setExpected, set);
    }

    @Test
    public final void testRemoveAnyNonEmptyMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue", "green");
        Set<String> setExpected = this.createFromArgsRef("red", "blue",
                "green");
        /*
         * Call method under test
         */
        String element = set.removeAny();
        /*
         * Assert that removed element is contained in expected set
         */
        assertTrue(setExpected.contains(element));
        /*
         * Assert that values of variables match expectations
         */
        setExpected.remove(element);
        assertEquals(setExpected, set);

        /*
         * Call method under test
         */
        element = set.removeAny();
        /*
         * Assert that removed element is contained in expected set
         */
        assertTrue(setExpected.contains(element));

        /*
         * Assert that values of variables match expectations
         */
        setExpected.remove(element);
        assertEquals(setExpected, set);
    }

    @Test
    public final void testContainsAllOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        /*
         * Call method under test
         */
        Boolean contained = set.contains("red");
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(contained);
    }

    @Test
    public final void testContainsAllMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        /*
         * Call method under test
         */
        Boolean contained1 = set.contains("red");
        Boolean contained2 = set.contains("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(contained1);
        assertTrue(contained2);
    }

    @Test
    public final void testContainsSomeOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "blue");
        /*
         * Call method under test
         */
        Boolean contained = set.contains("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(contained);
    }

    @Test
    public final void testContainsSomeMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "green", "blue");
        /*
         * Call method under test
         */
        Boolean contained1 = set.contains("red");
        Boolean contained2 = set.contains("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(contained1);
        assertTrue(contained2);
    }

    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest();
        /*
         * Call method under test
         */
        int setLength = set.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, setLength);
    }

    @Test
    public final void testSizeOne() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red");
        /*
         * Call method under test
         */
        int setLength = set.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(1, setLength);
    }

    @Test
    public final void testSizeMultiple() {
        /*
         * Set up variables
         */
        Set<String> set = this.createFromArgsTest("red", "green", "blue");
        /*
         * Call method under test
         */
        int setLength = set.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(3, setLength);
    }

}
