package structures;

import metrics.Metrics;

public class MinHeap {

    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        metrics = null;
    }

    public MinHeap(Metrics metrics) {
        heap = new int[10];
        size = 0;
        this.metrics = metrics;
    }

    public void insert(int value) {
        if (size == heap.length) {
            resize();
        }

        heap[size] = value;
        size++;

        step();
        move();

        bubbleUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }

        step();

        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }

        int min = heap[0];
        step();

        heap[0] = heap[size - 1];
        move();

        size--;
        step();

        if (size > 0) {
            comparison();
            bubbleDown(0);
        }

        return min;
    }

    public int size() {
        return size;
    }

    private void bubbleUp(int index) {

        while (index > 0) {
            comparison();

            int parent = (index - 1) / 2;
            step();

            comparison();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);

            index = parent;
            step();
        }
    }

    private void bubbleDown(int index) {

        while (true) {

            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            int smallest = index;

            step();

            if (leftChild < size) {
                comparison();

                if (heap[leftChild] < heap[smallest]) {
                    smallest = leftChild;
                    step();
                }
            }

            if (rightChild < size) {
                comparison();

                if (heap[rightChild] < heap[smallest]) {
                    smallest = rightChild;
                    step();
                }
            }

            if (smallest == index) {
                comparison();
                break;
            }

            swap(index, smallest);

            index = smallest;
            step();
        }
    }

    private void swap(int first, int second) {

        int temp = heap[first];
        move();

        heap[first] = heap[second];
        move();

        heap[second] = temp;
        move();
    }

    private void resize() {

        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];

            step();
            move();
        }

        heap = newHeap;
    }

    private void step() {
        if (metrics != null) {
            metrics.addStep();
        }
    }

    private void move() {
        if (metrics != null) {
            metrics.addMove();
        }
    }

    private void comparison() {
        if (metrics != null) {
            metrics.addComparison();
        }
    }
}