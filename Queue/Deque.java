import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head;
  private Node tail;
  private int size;

  // construct an empty deque
  public Deque() {

    head = null;
    tail = null;
    size = 0;
  }

  private class Node {

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

    Node oldNode = head;
    head = new Node();
    head.item = item;
    head.next = oldNode;
    head.prev = null;
    if (isEmpty()) {
       tail = head;
    } else {
      oldNode.prev = head;
    }
    size++;
  }

  // add the item to the end
  public void addLast(Item item) {

    if (item == null) throw new NullPointerException();

    Node oldNode = tail;
    tail = new Node();
    tail.item = item;
    tail.prev = oldNode;
    tail.next = null;
    if (isEmpty()) {
      head = tail;
    } else {
      oldNode.next = tail;
    }
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {

    if (isEmpty()) throw new NoSuchElementException();

    Item item = head.item;
    head = head.next;
    size--;
    if (head == null) tail = null;
    return item;
  }

  // remove and return the item from the end
  public Item removeLast() {

    if (isEmpty()) throw new NoSuchElementException();

    Item item = tail.item;
    tail = tail.prev;
    size--;
    if (tail == null) head = null;
    return item;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new dequeIterator();
  }

  private class dequeIterator implements Iterator<Item> {

    private Node current;

    public dequeIterator() {
      current = new Node();
      current.prev = null;
      current.next = head;
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

    Deque<Integer> d = new Deque<Integer>();

    print("Initialization was correct");
    assert(d.isEmpty() == true);
    assert(d.size() == 0);
    print("Passed");

    print("Check if it is actually empty");
    for( int it : d ) {
      assert(false);
    }
    print("Passed");

    print("Check if the first item added to the front was added successfully");
    d.addFirst(1);
    assert(d.isEmpty() == false);
    assert(d.size() == 1);
    print("Passed");

    print("Check if a second item added to the back was added succesfully");
    d.addLast(2);
    assert(d.isEmpty() == false);
    assert(d.size() == 2);
    print("Passed");

    print("Check if a third item added to the front was added successfully");
    d.addFirst(0);
    assert(d.isEmpty() == false);
    assert(d.size() == 3);
    print("Passed");


    int num = 0;

    print("Check if removeFirst actually removes the first item");
    num = d.removeFirst();
    assert(d.isEmpty() == false);
    assert(d.size() == 2);
    assert(num == 0);
    print("Passed");

    print("Check if removeLast actually removes the second item");
    num = d.removeLast();
    assert(d.isEmpty() == false);
    assert(d.size() == 1);
    assert(num == 2);
    print("Passed");

    print("Check if removing the last item from the front is successful");
    num = d.removeFirst();
    assert(d.isEmpty() == true);
    assert(d.size() == 0);
    assert(num == 1);
    print("Passed");

    print("Check if it is actually empty");
    for( int it : d ) {
      assert(false);
    }
    print("Passed");

    print("Add 10 items and check the size");
    for (int i = 0; i < 10; i++) {
      assert(d.size() == i);
      print("\tValue inputted: " + i);
      d.addLast(i);
    }
    print("Passed");

    assert(d.size() == 10);

    print("Check if the iterator iterates from the front");
    int i = 0;
    for (int currentItem : d ) {
      print("\tIteration: " + i);
      print("\tValue: " + currentItem);
      assert(currentItem == i);
      i++;
    }
    print("Passed");


    print("Operations Successful");

  }
}
