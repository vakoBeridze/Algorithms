import java.util.Iterator;
import java.util.NoSuchElementException;

// FIXME finish it.
public class DequeWithArray<Item> implements Iterable<Item> {

	private int head;
	private int tail;
	private Item[] data;
	private int count;

	// construct an empty deque
	public DequeWithArray() {
		head = 0;
		tail = 0;
		count = 0;
		data = (Item[]) new Object[1];
	}

	// unit testing (required)
	public static void main(String[] args) {
		DequeWithArray<String> strings = new DequeWithArray<>();
		System.out.println(strings.isEmpty());
		strings.addFirst("-1");
		strings.removeLast();
		strings.addFirst("5");
		System.out.println(strings.isEmpty());
		for (String string : strings) {
			System.out.println(string);
		}

	}

	// is the deque empty?
	public boolean isEmpty() {
		return count == 0;
	}

	// return the number of items on the deque
	public int size() {
		return count;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (head == 0) {
			increaseHeadSize(2 * data.length);
		}
		int i = --head;
		data[i] = item;
		count++;
	}

	private void increaseHeadSize(int capacity) {
		int newHead = data.length;
		int newTail = newHead + tail;
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = newHead; i < newTail; i++) {
			copy[i] = data[i - newHead];
		}
		data = copy;
		head = newHead;
		tail = newTail;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (tail == data.length)
			increaseTailSize(2 * data.length);
		data[tail++] = item;
		count++;
	}
/*
	// add the item to the back
	public void addLast(Item item) {
		if (head + count == data.length)
			increaseTailSize(2 * data.length);
		data[tail++] = item;
		count++;
	}*/

	private void increaseTailSize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < data.length; i++) {
			copy[i] = data[i];
		}
		data = copy;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = data[head];
		data[head] = null;
		head++;
		count--;
		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = data[--tail];
		data[tail] = null;
		count--;
		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<Item> {
		int currentIndex = head;

		@Override
		public boolean hasNext() {
			if (currentIndex >= data.length)
				return false;
			return data[currentIndex] != null;
		}

		@Override
		public Item next() {
			Item item = data[currentIndex];
			currentIndex = currentIndex + 1;
			return item;
		}
	}
}