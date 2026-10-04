import org.junit.jupiter.api.Test;
import structures.DynamicArray;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void emptyArrayShouldHaveSizeZero() {
        DynamicArray array = new DynamicArray();

        assertEquals(0, array.size());
    }

    @Test
    void addAndGetShouldWork() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtIndexShouldWork() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);

        array.add(1, 20);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtFirstIndexShouldWork() {
        DynamicArray array = new DynamicArray();

        array.add(20);
        array.add(30);

        array.add(0, 10);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtLastIndexShouldWork() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        array.add(2, 30);

        assertEquals(30, array.get(2));
    }

    @Test
    void removeShouldReturnRemovedElement() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }

    @Test
    void containsShouldFindExistingElement() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertTrue(array.contains(20));
    }

    @Test
    void containsShouldReturnFalseForMissingElement() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        assertFalse(array.contains(100));
    }

    @Test
    void duplicatesShouldBeAllowed() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(10);
        array.add(10);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(10, array.get(1));
        assertEquals(10, array.get(2));
    }

    @Test
    void getInvalidIndexShouldThrowException() {
        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );
    }

    @Test
    void removeInvalidIndexShouldThrowException() {
        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(1)
        );
    }

    @Test
    void addInvalidIndexShouldThrowException() {
        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(2, 20)
        );
    }

    @Test
    void arrayShouldResizeAutomatically() {
        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 25; i++) {
            array.add(i);
        }

        assertEquals(25, array.size());

        for (int i = 0; i < 25; i++) {
            assertEquals(i, array.get(i));
        }
    }
}