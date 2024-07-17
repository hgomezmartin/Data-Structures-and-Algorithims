package dsaa.lab02;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E> {

	// intern class that represents a node in the list
	private class Element {
		public Element(E e) {
			this.object = e;
		}

		E object; // object storaged in this element
		Element next = null; // reference to the next node on the list
	}

	Element sentinel;
	private int size; // size of the list

	// intern class taht implements the interator of the linked list
	private class InnerIterator implements Iterator<E> {
		// TODO/
		private Element current; // actual node in the go over the list

		// constructor
		public InnerIterator() {
			// TODO/
			current = sentinel.next;
		}

		// method to verify if thwew are more elements on the list
		@Override
		public boolean hasNext() {
			// TODO/
			return current != null; // retunr true if there is a next node
		}

		// method to obtaain the next element on the list
		@Override
		public E next() {
			// TODO/
			if (!hasNext()) { // if there is no next node (final node)
				throw new NoSuchElementException(); // throw exception
			}

			E data = current.object; // obtains the object stored in the actual node
			current = current.next; // goes to the next node
			return data; // return the object obtained form that node

		}
	}

	public OneWayLinkedList() { // constructor
		// TODO/
		sentinel = new Element(null); // node centinela no contiene un objeto
		size = 0; // list empty at the start
	}

	// method to obtain an iterator over the elements of the list
	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	// method that is not implemented in this linked list
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	// method to agreate an element to the final of the list
	@Override
	public boolean add(E e) {
		// TODO/
		Element newElement = new Element(e);
		Element current = sentinel; // starts from sentinel

		// go to the last node in the list
		while (current.next != null) {
			current = current.next;
		}

		current.next = newElement;// aggregate new node to the final of the list
		size++; // size of the list +1
		
		return true;

	}

	// method to agregate an element in a specific position in the list
	@Override
	public void add(int index, E element) throws NoSuchElementException {
		// TODO/
		if (index < 0 || index > size) { // verifu if the index is not valid
			throw new NoSuchElementException();
		}
		Element newElement = new Element(element); // creates new node
		Element current = sentinel; // start from sentinel position

		// go to the node before of the position of insertion
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		newElement.next = current.next; // conect the new node to the rest of the list
		current.next = newElement; // insert that new node in that position
		size++; // increment +1 the size of the list

	}

	// method to delete all elements of the list
	@Override
	public void clear() {
		// TODO/
		sentinel.next = null; // disconnect all the nodes from th list
		size = 0; // stablish size to 0 (no nodes on the list)

	}

	// method to verify if the lsit contains an element
	@Override
	public boolean contains(E element) {
		// TODO Auto-generated method stub/
		Element current = sentinel.next; // start from the first nodo after centinela one
		// Recorrer la lista y verificar si alguno de los nodos contiene el elemento
		// dado
		while (current != null) {
			if (current.object.equals(element)) {
				return true;
			}
			current = current.next; // goes to the next node
		}
		return false;
	}

	// method to obtain the element in a position of the list
	@Override
	public E get(int index) throws NoSuchElementException {
		// TODO Auto-generated method stub/
		if (index < 0 || index >= size) { // verify if the index is valid
			throw new NoSuchElementException();
		}
		Element current = sentinel.next; // start from the first node after sentinels one

		// goes to the node of that position
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.object;
	}

	// method to cahnge an element in a position of the list
	@Override
	public E set(int index, E element) throws NoSuchElementException {
		// TODO Auto-generated method stub/
		if (index < 0 || index >= size) { // verify if the index is valid
			throw new NoSuchElementException();
		}
		Element current = sentinel.next; // start from the first node after sentinels one

		// goes to the node of that position
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		E oldValue = current.object; // save old value of the object
		current.object = element; // establish the new value of the object
		return oldValue; // return the old value
	}

	// method to obtain the index of an element
	@Override
	public int indexOf(E element) {
		// TODO Auto-generated method stub/
		int index = 0; // we inizialize index to 0
		Element current = sentinel.next;// we start from the node before sentinel

		// go over the list and find the element
		while (current != null) {
			if (current.object.equals(element)) {
				return index; // retun index if the element have been found
			}

			current = current.next; // go to the next node
			index++; // index +1

		}
		return -1; // retrun -1 if th element is not in the list

	}

	// method to verify if the list is empty
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub/
		return size == 0; // sit empty if the size is 0
	}

	// method to delete the element in a position of the list
	@Override
	public E remove(int index) throws NoSuchElementException {
		// TODO Auto-generated method stub/
		if (index < 0 || index >= size) { // verify if the index is valid
			throw new NoSuchElementException();
		}
		Element current = sentinel;// we start from the node sentinel
		// goes to the node before of the node that we want to delete
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		Element removedElement = current.next; // node that is going to be deleted
		current.next = removedElement.next; // disconect that node
		size--; // decrement the size of the list by one
		return removedElement.object; // return the object deleted in the node deleted
	}

	// delete the first ocurrence of an element in the list
	@Override
	public boolean remove(E e) {
		// TODO Auto-generated method stub/
		Element current = sentinel;// we start from the node sentinel

		// go over the list and find the element
		while (current.next != null) {
			if (current.next.object.equals(e)) {
				current.next = current.next.next;// disconect the node that contains the element
				size--; // decrement the size of the list by one
				return true;
			}
			current = current.next; // goes to the next node
		}
		return false; // false if the element is not on the list

	}

	// method to obtain the size of the list
	@Override
	public int size() {
		// TODO Auto-generated method stub/
		return size;
	}
}
