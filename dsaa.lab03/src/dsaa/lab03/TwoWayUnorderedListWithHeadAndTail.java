package dsaa.lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException; //

public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E> {

	private class Element {

		public Element(E e) {
			// TODO -
			this.object = e; //
		}

		public Element(E e, Element next, Element prev) {
			// TODO
			this.object = e;
			this.next = next;
			this.prev = prev;
		}

		E object;
		Element next = null;
		Element prev = null;

	}

	Element head; //poinrs first element (head) of the list
	Element tail;//poinrs last element (tail) of the list
	// can be realization with the field size or without
	int size; //int of the number of  elemnts on the list

	private class InnerIterator implements Iterator<E> {
		Element pos;
		// TODO maybe more fields....?

		public InnerIterator() {
			// TODO
			pos = head;//
		}

		@Override
		public boolean hasNext() {
			// TODO
			return pos != null;//
		}

		@Override
		public E next() {
			// TODO
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E temp = pos.object;
			pos = pos.next;
			return temp;
		}
	}

	private class InnerListIterator implements ListIterator<E> {
		Element pos = head;
		// TODO maybe more fields....
		Element lastReturned = null;
		int index = 0;

		@Override
		public void add(E e) {
			Element newElement = new Element(e);
			if (head == null) {
				head = newElement;
				tail = newElement;
			} else if (pos == head) {
				newElement.next = head;
				head.prev = newElement;
				head = newElement;
			} else if (pos == null) {
				tail.next = newElement;
				newElement.next = tail;
				tail = newElement;
			} else {
				newElement.prev = pos.prev;
				newElement.next = pos;
				pos.prev.next = newElement;
				pos.prev = newElement;
			}
			size++;
			index++;
			lastReturned = null;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return pos != null;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return pos != head;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E temp = pos.object;
			lastReturned = pos;
			pos = pos.next;
			index++;
			return temp;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (pos == null) {
				pos = tail;
			} else {
				pos = pos.prev;
			}
			index--;
			lastReturned = pos;
			return pos.object;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			if (lastReturned == head) {
				head = head.next;
				if (head != null) {
					head.prev = null;
				}
			} else if (lastReturned == tail) {
				tail = tail.prev;
				if (tail != null) {
					tail.next = null;
				}
			} else {
				lastReturned.prev.next = lastReturned.next;
				lastReturned.next.prev = lastReturned.prev;
			}
			size--;
			index--;
			lastReturned = null;

		}

		@Override
		public void set(E e) {
			// TODO Auto-generated method stub
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.object = e;

		}
	}

	public TwoWayUnorderedListWithHeadAndTail() { // constructor
		// make a head and a tail in null (initializate an empty list)
		head = null;
		tail = null;
	}

	@Override
	public boolean add(E e) { //insert an element at a specified index
		// TODO
		Element newElement = new Element(e); //create a new element with the given data e
		if (head == null) { //if its empty we implement head and tail
			head = newElement;
			tail = newElement;
		} else { //if is not empty
			tail.next = newElement; //adding newElement to the end of the list.
			newElement.prev = tail; //set the prev reference of newElement to the current tail
			tail = newElement; //Updates the tail reference to th newElement making it the new tail 
		}
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) { //Inserts an element at a specified index.
	    if (index < 0 || index > size) {
	        throw new NoSuchElementException("Invalid index");
	    }
	    if (index == size) { //if index is equal to size
	        add(element); //calls method to add the element at the end of the list
	        return;
	    }
	    Element newElement = new Element(element); //creates a new instance
	    if (index == 0) { //checks if the element should be added at the beginning of the list.
	        newElement.next = head; //set next reference of the new element to the current head
	        head.prev = newElement; //sets the prev reference of the current head to new element
	        head = newElement;//updates the head to point new element making it the new head of the list
	    } else { //executes this if the element should be added somewhere in the middle of the list.
	        Element current = head; //initilize variable current to traverse the list starting from the head
	        for (int i = 0; i < index; i++) { //iterates reaching the specified index
	            current = current.next;
	        }
	        newElement.prev = current.prev; //sets the prev reference of newElement to the prev of current
	        newElement.next = current; //sets the next reference of newElement to current
	        current.prev.next = newElement; //updates the next reference of current.prev to newElement
	        current.prev = newElement; //updates the prev reference of current to newElement
	    }
	    size++;
	}


	@Override
	public void clear() { //basically clear the list
		// TODO
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean contains(E element) { //check if the list contains a specified element
		// TODO
		Element current = head; //variable current to traverse the list starting from the head
		while (current != null) { //are still elements to check in the list?
			if (current.object.equals(element)) { //element is equal to current (reach)
				return true;
			}
			current = current.next; //updates current to point to the next element in the list
		}
		return false; //element not found
	}

	@Override
	public E get(int index) { //return the element at a specified index
		// TODO
		if (index < 0 || index >= size) {
			throw new NoSuchElementException("Invalid index");
		}
		Element current = head; //temporary variable current to traverse the list starting from the hea
		for (int i=0; i < index ; i++) { //Iterates in list until reachthe specified index
			current = current.next; //current to point to the next element in the list during each iteration
		}
		return current.object; //return the object of the current element(the element at the specified index)
	}

	@Override
	public E set(int index, E element) { //replaces the element at a specified index with a new element
		if (index < 0 || index >= size) {
			throw new NoSuchElementException("Invalid index");
		}
		Element current = head; //variable current to traverse the list starting from the head
		for (int i =0; i<index; i++) { //Iterates in list until reachthe specified index
			current = current.next; //current to point to the next element in the list during each iteration
		}
		E oldElement = current.object; //Store the current elements object (value) in the oldElement variable before replacing.
		current.object = element; //replaces the current element with the new one
		return oldElement; //return what was replaces
	}

	@Override
	public int indexOf(E element) { //returns the index of the first 
		// TODO
		Element current = head;//variable current to traverse the list starting from the head
		int index = 0; //index variable to keep track of the current index in the list
		while (current != null) { //if is not null
			if (current.object.equals(element)) { //the object stored in the current element is equal to the specified element?
				return index;
			}
			current = current.next; //updates current to point to the next element in the list during each iteration.
			index++;//increments the index to move to the next position
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
		//return new InnerListIterator();
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		// TODO
		if (index < 0 || index >= size) {
			throw new NoSuchElementException("Invalid index");
		}
		E removedElement; //variable that hold the removed element
		if (index == 0) {  //if the index is 0, removes the head.
			removedElement = head.object; //store the object of the head element
			head = head.next;//move the head pointer to the next element
			if (head != null) { //check if the new head is not null
				head.prev = null;
			}
		}else if (index == size -1) { //if the index is the last element, removes the tail.
			removedElement = tail.object; 
			tail = tail.prev; //Move the tail pointer to the previous element
			if (tail != null) {
				tail.next = null;
			}
		}else { //For any other index, traverses the list to find the element and removes it.
			Element current = head;
			for (int i = 0; i<index; i++) {
				current = current.next;
			}
			removedElement = current.object;
			current.prev.next = current.next;
			current.next.prev = current.prev;
		}
		size --;
		return removedElement;
	}

	@Override
	public boolean remove(E e) {
		// TODO
		int index = indexOf(e);//find the index of the specified element
		if (index == -1) { ////if the element is not found, returns false.
			return false;
		}
		remove (index); //remove the found index
		return true;
	}

	@Override
	public int size() {
		// TODO
		return size;
	}

	public String toStringReverse() {
	    StringBuilder retStr = new StringBuilder(); //to construct the reverse string
	    ListIterator<E> iter = new InnerListIterator(); //creates a ListIterator starting from the head.

	    //move the iterator to the end of the list
	    while (iter.hasNext()) { //through the list until the last element is reached
	        iter.next(); //next
	    }

	    // Traverse the list backwards and append each element to the StringBuilder
	    while (iter.hasPrevious()) { //iterates backward through the list
	        retStr.append(iter.previous()).append("\n"); //appends each element to the StringBuilder followe by a jumpline
	    }

	    return retStr.toString().trim(); //trim= removes any trailing whitespace.
	}


	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
	    // Check if the lists are not the same
	    if (other == this) {
	        return;
	    }

	    // Check if the other list is not empty
	    if (other.head != null) {
	        // If the current list is empty, set its head and tail to the other list head and tail
	        if (head == null) {
	            head = other.head;
	            tail = other.tail;
	        } else {
	            // Append the other list's elements to the current list
	            tail.next = other.head; //appends the other lis elements to the current list by setting the next pointer of the current list tail to the head of the other list
	            other.head.prev = tail; //sets the prev pointer of the other list head to the current list's tail.
	            tail = other.tail; //updates the current list's tail to the tail of the other list.
	        }

	        // Update the size of the current list
	        size += other.size;
	    }

	    // Clear the other list
	    other.clear();
	}

	
}
