import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] data;
	private int count;

	public RandomizedQueue() {
		count = 0;
		data = (Item[]) new Object[1];
	}

	// unit testing (required)
	public static void main(String[] args) {
		RandomizedQueue<String> strings = new RandomizedQueue<>();
		System.out.println(strings.isEmpty());
		System.out.println(strings.size());

		strings.enqueue("v");
		strings.enqueue("a");
		strings.enqueue("k");
		strings.enqueue("o");
		strings.dequeue();

		System.out.println(strings.sample());

		System.out.println(strings.isEmpty());
		System.out.println(strings.size());


		for (String string : strings) {
			System.out.print(string + " ");
		}
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return count == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return count;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (count == data.length) {
			resize(data.length * 2);
		}

		data[count++] = item;
	}

	private void resize(int newCapacity) {
		Item[] copy = (Item[]) new Object[newCapacity];
		for (int i = 0; i < data.length; i++) {
			copy[i] = data[i];
		}
		data = copy;
	}

	// remove and return a random item
	public Item dequeue() {
		if (count == 0) {
			throw new NoSuchElementException();
		}
		int randomIndex = StdRandom.uniform(count);
		Item item = data[randomIndex];
		data[randomIndex] = data[--count];
		data[count] = null;

		if (data.length > 4 && count <= data.length / 4) {
			resize(data.length / 2);
		}
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (count == 0) {
			throw new NoSuchElementException();
		}
		int randomIndex = StdRandom.uniform(count);
		return data[randomIndex];
	}

	// return an independent iterator over items in random order
	@Override
	public Iterator<Item> iterator() {
		return new RandomIterator();
	}

	private class RandomIterator implements Iterator<Item> {

		private int[] randomOrder;
		private int currentIndex = 0;

		public RandomIterator() {
			randomOrder = new int[count];
			for (int i = 0; i < count; i++) {
				randomOrder[i] = i;
			}
			StdRandom.shuffle(randomOrder);
		}

		@Override
		public boolean hasNext() {
			return currentIndex < count;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return data[randomOrder[currentIndex++]];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
