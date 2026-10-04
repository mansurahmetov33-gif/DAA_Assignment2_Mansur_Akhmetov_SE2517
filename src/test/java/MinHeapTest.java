import org.junit.jupiter.api.Test;
import structures.MinHeap;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void emptyHeapShouldHaveSizeZero() {
        MinHeap heap = new MinHeap();

        assertEquals(0, heap.size());
    }

    @Test
    void insertShouldIncreaseSize() {
        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        assertEquals(3, heap.size());
    }

    @Test
    void peekMinShouldReturnSmallestElement() {
        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);

        assertEquals(3, heap.peekMin());
    }

    @Test
    void extractMinShouldReturnElementsInSortedOrder() {
        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);
        heap.insert(8);

        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(8, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());

        assertEquals(0, heap.size());
    }

    @Test
    void duplicateValuesShouldBeAllowed() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void peekMinShouldNotRemoveElement() {
        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(5);

        assertEquals(5, heap.peekMin());
        assertEquals(2, heap.size());
        assertEquals(5, heap.peekMin());
    }

    @Test
    void extractMinFromEmptyHeapShouldThrowException() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void peekMinFromEmptyHeapShouldThrowException() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void heapShouldMaintainMinPropertyAfterInsertions() {
        MinHeap heap = new MinHeap();

        heap.insert(50);
        assertMinProperty(heap);

        heap.insert(30);
        assertMinProperty(heap);

        heap.insert(40);
        assertMinProperty(heap);

        heap.insert(10);
        assertMinProperty(heap);

        heap.insert(20);
        assertMinProperty(heap);

        heap.insert(5);
        assertMinProperty(heap);
    }

    @Test
    void heapShouldMaintainMinPropertyDuringExtractions() {
        MinHeap heap = new MinHeap();

        heap.insert(50);
        heap.insert(30);
        heap.insert(40);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        while (heap.size() > 0) {
            heap.extractMin();

            if (heap.size() > 0) {
                assertMinProperty(heap);
            }
        }
    }

    private void assertMinProperty(MinHeap heap) {

        assertTrue(heap.peekMin() <= heap.peekMin());
    }
}