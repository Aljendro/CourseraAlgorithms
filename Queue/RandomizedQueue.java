import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] queueArray;
  private int currentCapacity;
  private int amountItems;

  // construct an empty randomized queue
  public RandomizedQueue() {

    amountItems = 0;
    currentCapacity = 2;
    queueArray = (Item[]) new Object[currentCapacity];
  }

  // is the queue empty?
  public boolean isEmpty() {
    return amountItems == 0;
  }

  // return the number of items on the queue
  public int size() {
    return amountItems;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();

    if (amountItems == currentCapacity) {
      changeCapacityTo(2 * currentCapacity);
    }
    queueArray[amountItems++] = item;
  }

  // remove and return a random item
  public Item dequeue() {

    if (isEmpty()) throw new NoSuchElementException();

    int rand = StdRandom.uniform(0, amountItems);

    Item item = queueArray[rand];
    queueArray[rand] = queueArray[amountItems - 1];
    queueArray[amountItems - 1] = null;
    amountItems--;

    if (amountItems < (currentCapacity / 4)) {
      changeCapacityTo(currentCapacity / 2);
    }
    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {

    if (isEmpty()) throw new NoSuchElementException();

    int rand = StdRandom.uniform(0, amountItems);

    return queueArray[rand];
  }

  private void changeCapacityTo(int newCapacity) {

    assert (amountItems < newCapacity);
    if (newCapacity > 2) {

      currentCapacity = newCapacity;
      Item[] temp = (Item[]) new Object[currentCapacity];

      for (int i = 0; i < amountItems; i++) {
        temp[i] = queueArray[i];
      }
      queueArray = temp;
    }
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private int position;

    public RandomizedQueueIterator() {
      position = 0;
    }
    public boolean hasNext() {
      return position < amountItems;
    }
    public Item next() {
      if (this.hasNext()) {
        return queueArray[position++];
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
    assert (!r.isEmpty());
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
    assert (!r.isEmpty());
    assert (r.size() == 1);
    print("Passed");

    print("Check if adding a second item is successful");
    r.enqueue(1);
    assert (!r.isEmpty());
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

    print("Check if changeCapacityTo can resize array properly");
    int mediumNum = 1000000;
    for (int i = 0; i < mediumNum; i++) {
      r.enqueue(i);
    }
    for (int i = 0; i < mediumNum; i++) {
      r.dequeue();
    }
    for (int i = 0; i < mediumNum; i++) {
      r.enqueue(i);
    }
    assert (!r.isEmpty());
    assert (r.size() == mediumNum);
    for (int i = 0; i < mediumNum; i++) {
      r.dequeue();
    }
    print("Passed");

    print("Test sequence");
    assert (r.isEmpty());
    assert (r.isEmpty());
    r.enqueue(34);
    assert (r.dequeue() == 34);
    assert (r.size() == 0);
    assert (r.size() == 0);
    r.enqueue(45);
    assert (r.dequeue() == 45);
    assert (r.isEmpty());
    r.enqueue(28);
    print("Passed");

    print("Operations Successful");
  }
}
