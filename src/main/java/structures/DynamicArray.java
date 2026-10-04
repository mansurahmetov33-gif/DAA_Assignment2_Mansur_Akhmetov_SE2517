package structures;

import metrics.Metrics;

public class DynamicArray {

    private int[] data;
    private int size;
    private Metrics metrics;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        metrics = null;
    }

    public DynamicArray(Metrics metrics) {
        data = new int[10];
        size = 0;
        this.metrics = metrics;
    }

    public void add(int value) {
        if (size == data.length) {
            resize();
        }

        data[size] = value;
        size++;

        step();
        move();
    }

    public void add(int index, int value) {
        checkPositionIndex(index);

        if (size == data.length) {
            resize();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];

            step();
            move();
        }

        data[index] = value;
        size++;

        step();
        move();
    }

    public int remove(int index) {
        checkElementIndex(index);

        int removedValue = data[index];

        step();

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];

            step();
            move();
        }

        size--;

        return removedValue;
    }

    public int get(int index) {
        checkElementIndex(index);

        step();

        return data[index];
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            step();
            comparison();

            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];

            step();
            move();
        }

        data = newData;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "invalid index: " + index
            );
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "invalid index: " + index
            );
        }
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