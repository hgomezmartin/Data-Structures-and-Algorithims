package dsaa.lab07;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E> {

	private class Element {
		public Element(E e) {
			// TODO
			this.object = e;
		}

		public Element(E e, Element next, Element prev) {
			// TODO
			this.object = e;
			this.next = next;
			this.prev = prev;
		}

		// add element e after this
		public void addAfter(Element elem) {
			// TODO
			elem.next = this.next;
			elem.prev = this;
			this.next.prev = elem;
			this.next = elem;
		}

		// assert it is NOT a sentinel
		public void remove() {
			// TODO
			this.prev.next = this.next;
			this.next.prev = this.prev;
		}

		E object;
		Element next = null;
		Element prev = null;
	}

	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E> {
		// TODO
		Element current;

		public InnerIterator() {
			// TODO
			current = sentinel.next; // start iterating from the first element of the list
		}

		@Override
		public boolean hasNext() {
			// TODO
			return current != sentinel; // true if is not the sentinel
		}

		@Override
		public E next() {
			// TODO
			if (current == sentinel) { // if is the sentinel
				throw new NoSuchElementException();
			}
			E toReturn = current.object; // retrieve the objct stored in the current elmnt
			current = current.next; // move to the next elelemtt
			return toReturn;
		}
	}

	private class InnerListIterator implements ListIterator<E> {
		// TODO
		Element current;

		public InnerListIterator() {
			// TODO
			current = sentinel.prev;
		}

		@Override
		public boolean hasNext() {
			// TODO
			return current != sentinel;
		}

		@Override
		public E next() {
			// TODO
			if (current == sentinel) {
				throw new NoSuchElementException();
			}
			E toReturn = current.object; // retrieve the objct stored in the current elmnt
			current = current.next;// move to the next elelemtt
			return toReturn;
		}

		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			// TODO
			return current != sentinel; // true if the current element is not the sentinel
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			// TODO
			if (current == sentinel) {
				throw new NoSuchElementException();
			}
			E toReturn = current.object; // retrieve the objct stored in the current elmnt
			current = current.prev;// move to the prev elelemtt
			return toReturn;
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}

	public TwoWayCycledOrderedListWithSentinel() {
		// TODO
		sentinel = new Element(null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
		// TODO
		size++;

		if (isEmpty()) { // check if the list is empty and puts the element in the first position
			sentinel.addAfter(new Element(e));
			return true;
		}

		// start iterating from the first element after the sentinel
		Element current = sentinel.next;
		// iterates through the list to find first element greater than e and puts e
		// before it
		while (current != sentinel && ((Comparable<E>) current.object).compareTo(e) <= 0) {
			current = current.next;
		}
		// if current is sentinel then e is the greatest element in the list, and it is
		// put at the end
		current.prev.addAfter(new Element(e));
		return true;
	}

	private Element getElement(int index) {
		// TODO
		if (index < 0 || index >= size) {
			throw new UnsupportedOperationException();
		}
		Element current = sentinel.next; // start from the first element after the sentinel

		// iterate through the list to find the element at the specified index
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current; // return the element in the specific index
	}

	private Element getElement(E obj) {
		// TODO
		Element current = sentinel.next;// start from the first element after the sentinel

		while (current != sentinel) {
			if (current.object.equals(obj)) { // iterate through the list to find the first occurrence of the specified
												// element
				return current;
			}
			current = current.next;
		}
		return null; // null if is not found
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clear() {
		// TODO
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		// TODO
		return indexOf(element) != -1;
	}

	@Override
	public E get(int index) {
		// TODO
		return getElement(index).object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
		// TODO
		Element current = sentinel.next;
		int index = 0;
		while (current != sentinel) {
			if (current.object.equals(element)) {
				return index;
			}
			current = current.next;
			index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		// TODO
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) {
		// TODO
		if (index < 0 || index >= size) {
			throw new UnsupportedOperationException();
		}
		Element toRemove = getElement(index);
		toRemove.remove();
		size--;
		return toRemove.object;
	}

	@Override
	public boolean remove(E e) {
		// TODO
		Element toRemove = getElement(e);
		if (toRemove != null) {
			toRemove.remove();
			size--;
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		// TODO
		return size;
	}

	// @SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		// TODO
		for (E e : other) { // iterate throeug each element in other list
			add(e); // add the current element to this list
		}
		other.clear(); // clear the other list
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		// TODO
		Element current = sentinel.next;
		while (current != sentinel) {
			if (current.object.equals(e)) {
				Element toRemove = current;
				current = current.next;
				toRemove.remove();
				size--;
			} else {
				current = current.next;
			}
		}
	}

}
