public class Subset {

  public static void main(String[] args) {

    RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      randQueue.enqueue(StdIn.readString());
    }

    for (int i = 0; i < Integer.parseInt(args[0]); i++) {
      System.out.println(randQueue.dequeue());
    }
  }
}
