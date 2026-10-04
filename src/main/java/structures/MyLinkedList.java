package structures;

import metrics.Metrics;

public class MyLinkedList {

    private Node head;
    private int size;
    private Metrics metrics;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public MyLinkedList() {
        head = null;
        size = 0;
        metrics = null;
    }

    public MyLinkedList(Metrics metrics) {
        head = null;
        size = 0;
        this.metrics = metrics;
    }

    public void add(int value) {
        Node newNode = new Node(value);
        step();

        if (head == null) {
            comparison();

            head = newNode;
            move();
        } else {
            Node current = head;

            while (current.next != null) {
                comparison();
                step();

                current = current.next;
            }

            comparison();

            current.next = newNode;
            move();
        }

        size++;
        step();
    }

    public void add(int index, int value) {
        checkPositionIndex(index);

        Node newNode = new Node(value);
        step();

        if (index == 0) {
            comparison();

            newNode.next = head;
            move();

            head = newNode;
            move();
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                comparison();
                step();

                current = current.next;
                move();
            }

            comparison();

            newNode.next = current.next;
            move();

            current.next = newNode;
            move();
        }

        size++;
        step();
    }

    public int remove(int index) {
        checkElementIndex(index);

        int removedValue;

        if (index == 0) {
            comparison();

            removedValue = head.data;
            step();

            head = head.next;
            move();
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                comparison();
                step();

                current = current.next;
                move();
            }

            comparison();

            removedValue = current.next.data;
            step();

            current.next = current.next.next;
            move();
        }

        size--;
        step();

        return removedValue;
    }

    public int get(int index) {
        checkElementIndex(index);

        Node current = head;
        step();

        for (int i = 0; i < index; i++) {
            comparison();
            step();

            current = current.next;
            move();
        }

        return current.data;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            comparison();
            step();

            if (current.data == value) {
                comparison();
                return true;
            }

            current = current.next;
            move();
        }

        comparison();

        return false;
    }

    public int size() {
        return size;
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