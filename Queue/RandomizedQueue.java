import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Node head;
  private int size;

  // construct an empty randomized queue
  public RandomizedQueue() {

    head = new Node();
    head.prev = null;
    head.next = null;
    size = 0;
  }

  private class Node {

    private Item item;
    private Node next;
    private Node prev;
  }

  // is the queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();

    Node oldNode = head.next;
    head.next = new Node();
    head.next.item = item;
    head.next.prev = head;
    head.next.next = oldNode;
    if (oldNode != null) oldNode.prev = head.next;
    oldNode = null;
    size++;
  }

  // remove and return a random item
  public Item dequeue() {

    if (isEmpty()) throw new NoSuchElementException();

    int rand = StdRandom.uniform(1, size + 1);
    Node current = head.next;

    for (int i = 1; i < rand; i++) {
      current = current.next;
    }

    Item item = current.item;

    if (current.prev != null) current.prev.next = current.next;
    if (current.next != null) current.next.prev = current.prev;
    current.next = null;
    current.prev = null;
    current = null;
    size--;

    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {

    if (isEmpty()) throw new NoSuchElementException();

    int rand = StdRandom.uniform(1, size + 1);
    Node current = head.next;

    for (int i = 1; i < rand; i++) {
      current = current.next;
    }

    return current.item;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private Node current;

    public RandomizedQueueIterator() {
      current = head;
    }
    public boolean hasNext() {
      return current.next != null;
    }
    public Item next() {
      if (this.hasNext()) {
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

    RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();

    print("Check initialization");
    assert (r.isEmpty());
    assert (r.size() == 0);
    print("Passed");

    print("Check if it is actually empty");
    for (int it : r) {
      assert (false);
    }
    print("Passed");

    print("Check if adding item is successful");
    r.enqueue(0);
    assert (r.isEmpty());
    assert (r.size() == 1);
    print("Passed");

    int item = -1;

    print("Check if sample() gives us that item back");
    item = r.sample();
    assert (item == 0);
    print("Passed");

    print("Check if dequeue() gives the item and decrements properly");
    item = r.dequeue();
    assert (r.isEmpty());
    assert (r.size() == 0);
    assert (item == 0);
    print("Passed");

    print("Check if it is actually empty");
    for (int it : r) {
      assert (false);
    }
    print("Passed");

    print("Check if adding another item is successful");
    r.enqueue(0);
    assert (r.isEmpty());
    assert (r.size() == 1);
    print("Passed");

    print("Check if adding a second item is successful");
    r.enqueue(1);
    assert (r.isEmpty());
    assert (r.size() == 2);
    print("Passed");

    print("Check if either item is returned");
    item = r.sample();
    assert (item == 0 || item == 1);
    print("Passed");

    r.dequeue();
    r.dequeue();

    print("Check if it is actually empty");
    for (int it : r) {
      assert (false);
    }
    print("Passed");

    assert (r.isEmpty());

    for (int i = 0; i < 10; i++) {
      assert (r.size() == i);
      r.enqueue(i);
    }

    print("Check if items are in the right order");
    for (int it : r) {
      print("\t" + Integer.toString(it));
    }

    print("Delete all items from the RandomizedQueue");
    for (int i = 0; i < 10; i++) {
      r.dequeue();
    }
    print("Successful");

    print("Check if it is actually empty");
    for (int it : r) {
      assert (false);
    }
    print("Passed");

    print("Operations Successful");
  }
}
