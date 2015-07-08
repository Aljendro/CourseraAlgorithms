import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head;
  private Node tail;
  private int size;

  // construct an empty deque
  public Deque() {

    // Initialize head and tail dummy nodes
    // They will not contain data.
    head = new Node();
    tail = new Node();

    head.next = tail;
    head.prev = null;

    tail.next = null;
    tail.prev = head;

    size = 0;
  }

  private class Node {

    // We need a Node to contain the data
    // as well as references to the front and
    // back using the next and prev variables
    private Item item;
    private Node next;
    private Node prev;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {

    if (item == null) throw new NullPointerException();

    // Create the new node and assign its pointers
    // accordingly
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = head.next;
    newNode.prev = head;

    // Make the head and old node point to the new node
    // For the first item in the Deque, the tail should be the
    // next item after head.
    assert (head.next != null);
    head.next.prev = newNode;
    head.next = newNode;
    newNode = null;
    size++;
  }

  // add the item to the end
  public void addLast(Item item) {

    if (item == null) throw new NullPointerException();

    // Create the new node and assign its pointers
    // accordingly
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = tail;
    newNode.prev = tail.prev;

    // Make the tail and old node point to the new node
    // For the last item in the Deque, the head should be the
    // prev item before tail.
    assert (tail.prev != null);
    tail.prev.next = newNode;
    tail.prev = newNode;
    newNode = null;
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {

    if (isEmpty()) throw new NoSuchElementException();

    // Have the second item or tail, point back to head
    Item item = head.next.item;
    head.next.next.prev = head;
    head.next = head.next.next;
    size--;
    return item;
  }

  // remove and return the item from the end
  public Item removeLast() {

    if (isEmpty()) throw new NoSuchElementException();

    Item item = tail.prev.item;
    tail.prev.prev.next = tail;
    tail.prev = tail.prev.prev;
    size--;
    return item;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    private Node current;

    public DequeIterator() {
      assert (head != null);
      assert (head.prev == null);
      current = head;
    }
    public boolean hasNext() {
      assert (tail != null);
      assert (tail.next == null);
      return current.next != tail;
    }
    public Item next() {
      if (hasNext()) {
        current = current.next;
        return current.item;
      }
      throw new NoSuchElementException();
    }
    public void remove() {
      // Does not support this operation
      throw new UnsupportedOperationException();
    }
  }

  private static void print(String str) {
    System.out.println(str);
  }

  // unit testing
  public static void main(String[] args) {

    Deque<Integer> d = new Deque<Integer>();

    print("Initialization was correct");
    assert (d.isEmpty());
    assert (d.size() == 0);
    print("Passed");

    print("Check if it is actually empty");
    for (int it : d) {
      assert (false);
    }
    print("Passed");

    print("Check if the first item added to the front was added successfully");
    d.addFirst(1);
    assert (!d.isEmpty());
    assert (d.size() == 1);
    print("Passed");

    print("Check if a second item added to the back was added succesfully");
    d.addLast(2);
    assert (!d.isEmpty());
    assert (d.size() == 2);
    print("Passed");

    print("Check if a third item added to the front was added successfully");
    d.addFirst(0);
    assert (!d.isEmpty());
    assert (d.size() == 3);
    print("Passed");


    int num = 0;

    print("Check if removeFirst actually removes the first item");
    num = d.removeFirst();
    assert (!d.isEmpty());
    assert (d.size() == 2);
    assert (num == 0);
    print("Passed");

    print("Check if removeLast actually removes the second item");
    num = d.removeLast();
    assert (!d.isEmpty());
    assert (d.size() == 1);
    assert (num == 2);
    print("Passed");

    print("Check if removing the last item from the front is successful");
    num = d.removeFirst();
    assert (d.isEmpty());
    assert (d.size() == 0);
    assert (num == 1);
    print("Passed");

    print("Check if it is actually empty");
    for (int it : d) {
      assert (false);
    }
    print("Passed");

    print("Add 10 items and check the size");
    for (int i = 0; i < 10; i++) {
      assert (d.size() == i);
      print("\tValue inputted: " + i);
      d.addLast(i);
    }
    print("Passed");

    assert (d.size() == 10);

    print("Check if the iterator iterates from the front");
    int i = 0;
    for (int currentItem : d) {
      print("\tIteration: " + i);
      print("\tValue: " + currentItem);
      assert (currentItem == i);
      i++;
    }
    print("Passed");

    print("Check if removing all items passes");
    for (int j = 0; j < 10; j++) {
      assert (d.size() == 10 - j);
      d.removeFirst();
    }
    print("Passed");

    print("Check if intermixed method calls pass");
    d.addFirst(10);
    assert (d.size() == 1);
    d.removeLast();
    assert (d.size() == 0);
    d.addLast(20);
    assert (d.size() == 1);
    d.addFirst(30);
    assert (d.size() == 2);
    d.addLast(40);
    assert (d.size() == 3);
    d.removeLast();
    assert (d.size() == 2);
    d.removeFirst();
    assert (d.size() == 1);

    int size = 1;
    for (int j = 100; j < 150; j++) {
      if (j % 2 == 0) {
        d.addFirst(j);
        assert (d.size() == ++size);
      } else {
        d.addLast(j);
        assert (d.size() == ++size);
      }
    }

    for (int j = 0; j < 50; j++) {
      if (j % 2 != 0) {
        d.removeFirst();
        assert (d.size() == --size);
      } else {
        d.removeLast();
        assert (d.size() == --size);
      }
    }
    assert (d.size() == 1);
    print("Passed");


    print("Operations Successful");

  }
}
