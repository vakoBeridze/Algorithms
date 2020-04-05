import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int count;

	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		count = 0;
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<String> strings = new Deque<>();
		System.out.println(strings.isEmpty());
		System.out.println(strings.size());

		strings.addLast("a");
		strings.addFirst("v");
		strings.addFirst("b");
		strings.removeFirst();
		strings.addLast("k");
		strings.addLast("o");
		strings.addLast("j");
		strings.removeLast();

		System.out.println(strings.isEmpty());
		System.out.println(strings.size());


		for (String string : strings) {
			System.out.print(string + " ");
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
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node node = new Node();
		node.item = item;
		node.next = first;
		node.previous = null;

		if (first == null) {
			last = node;
		} else {
			first.previous = node;
		}
		first = node;
		count++;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node node = new Node();
		node.item = item;
		node.previous = last;
		node.next = null;

		if (last == null) {
			first = node;
		} else {
			last.next = node;
		}
		last = node;
		count++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = first.item;
		if (first.next == null) {
			first = null;
			last = null;
		} else {
			first = first.next;
			first.previous = null;
		}
		count--;
		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = last.item;
		if (last.previous == null) {
			first = null;
			last = null;
		} else {
			last = last.previous;
			last.next = null;
		}
		count--;
		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new MyIterator();
	}

	private class Node {
		private Item item;
		private Node next;
		private Node previous;
	}

	private class MyIterator implements Iterator<Item> {
		private Deque<Item>.Node currentItem = first;

		@Override
		public boolean hasNext() {
			return currentItem != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = currentItem.item;
			currentItem = currentItem.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}