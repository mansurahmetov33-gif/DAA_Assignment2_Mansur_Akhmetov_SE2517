import org.junit.jupiter.api.Test;
import structures.MyLinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    void emptyListShouldHaveSizeZero() {
        MyLinkedList list = new MyLinkedList();

        assertEquals(0, list.size());
    }

    @Test
    void addAndGetShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtIndexShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);

        list.add(1, 20);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtFirstIndexShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(20);
        list.add(30);

        list.add(0, 10);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtLastIndexShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        list.add(2, 30);

        assertEquals(30, list.get(2));
    }

    @Test
    void removeShouldReturnRemovedElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void removeFirstElementShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        int removed = list.remove(0);

        assertEquals(10, removed);
        assertEquals(20, list.get(0));
    }

    @Test
    void removeLastElementShouldWork() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(2);

        assertEquals(30, removed);
        assertEquals(2, list.size());
    }

    @Test
    void containsShouldFindExistingElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(20));
    }

    @Test
    void containsShouldReturnFalseForMissingElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        assertFalse(list.contains(100));
    }

    @Test
    void duplicatesShouldBeAllowed() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(10);
        list.add(10);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(10, list.get(2));
    }

    @Test
    void getInvalidIndexShouldThrowException() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(1)
        );
    }

    @Test
    void removeInvalidIndexShouldThrowException() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(1)
        );
    }

    @Test
    void addInvalidIndexShouldThrowException() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(2, 20)
        );
    }
}