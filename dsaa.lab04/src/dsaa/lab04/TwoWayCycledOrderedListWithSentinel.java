package dsaa.lab04;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

    private class Element{
        public Element(E e) {
            this.object = e;
        }
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }
        // add element e after this
        public void addAfter(Element elem) {
            Element next = this.next; ///stores a reference to the next element
            elem.next=next; //sets the next element of the new element to this element's next
            elem.prev=this; //sets the previous element of the new element to this element
            this.next=elem; //sets this elements next to the new element
            next.prev=elem; //sets the previous element of the next element to the new element
        }
        // assert it is NOT a sentinel
        public void remove() {
            assert this != sentinel; //nsures that this element is not the sentinel
            Element prev=this.prev; //ref to the prev
            Element next=this.next; //ref to the next
            prev.next=next; //sets the next element of the previous element to this elements next
            next.prev=prev; //sets the previous element of the next element to this element's previous
        }
        E object;
        Element next=null;
        Element prev=null;
    }


    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E>{
        Element current;
        public InnerIterator() {
            current = sentinel.next; //start iterating from the first element of the list
        }
        @Override
        public boolean hasNext() {
            return current != sentinel; //true if is not the sentinel
        }

        @Override
        public E next() {
            if (current == sentinel) //if is the sentinel
                throw new NoSuchElementException();
            E toReturn = current.object; //retrieve the objct stored in the current elmnt
            current = current.next; //move to the next elelemtt
            return toReturn; 
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        // used for reverse iteration
        // sets initial node to be the last one -> no need to call next before previous
        Element current;
        public InnerListIterator() {
            current = sentinel.prev; //start iterating from the last element
        }
        @Override
        public boolean hasNext() {
            return current != sentinel; //true if is not the sentinel
        }

        @Override
        public E next() {
            if (current == sentinel)
                throw new NoSuchElementException();
            E toReturn = current.object; //retrieve the objct stored in the current elmnt
            current = current.next;//move to the next elelemtt
            return toReturn;
        }
        @Override
        public boolean hasPrevious() {
            return current != sentinel; //true if the current element is not the sentinel
        }
        @Override
        public E previous() {
            if (current == sentinel)
                throw new NoSuchElementException();
            E toReturn = current.object; //retrieve the objct stored in the current elmnt
            current = current.prev;//move to the prev elelemtt
            return toReturn;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
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
        sentinel = new Element(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        size++;
        
        if (isEmpty()) { //check if the list is empty and puts the element in the first position
            sentinel.addAfter(new Element(e));
            return true;
        }
        
        //start iterating from the first element after the sentinel
        Element current = sentinel.next;
        // iterates through the list to find first element greater than e and puts e before it
        while (current != sentinel && ((Comparable<E>) current.object).compareTo(e) <= 0) {
            current = current.next;
        }
        // if current is sentinel then e is the greatest element in the list, and it is put at the end
        current.prev.addAfter(new Element(e));
        return true;
    }

    private Element getElement(int index) {
		//TODO
		if (index < 0 || index >=size) {
			throw new IndexOutOfBoundsException();
		}
		
		//start from the first element after the sentinel
		Element current = sentinel.next;
		
		//iterate through the list to find the element at the specified index
		for (int i = 0; i< index; i++) {
			current = current.next;
		}
		return current; //return the element in the specific index
	}

	private Element getElement(E obj) {
		//TODO
		
		//start from the first element after the sentinel
		Element current = sentinel.next;
		while (current != sentinel) {
			if (current.object.equals(obj)) { //iterate through the list to find the first occurrence of the specified element
				return current;
			}
			current = current.next;
		}
		return null; //null if is not found
	}

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        Element elem = getElement(element);
        return elem != null;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new NoSuchElementException();
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        
        //start from the first element after the sentinel
        Element current = sentinel.next;
        while (current != sentinel) { // Loop until the end of the list is reached
            if (current.object.equals(element)) //check if the current element is equal to the specified element
                return index; //return if its found
            current = current.next; //move to the next element in the list
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
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
        if (index < 0 || index >= size)
            throw new NoSuchElementException();
        Element elem = getElement(index); //get the element at the specified index
        elem.remove();  // rem0ve the element from the list
        size--; 
        return elem.object; //return the removed obj
    }

    @Override
    public boolean remove(E e) {
        Element elem = getElement(e); //get the elemnt from the list
        if (elem != null) { //if exist
            elem.remove(); //remove the elemnt from the list
            size--; //decrease size list
            return true; //was removed
        }
        return false; //was not removd
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		//TODO
		for (E e : other) { //iterate throeug each element in other list
			add(e); //add the current element to this list
		}
		other.clear(); //clear the other list
	}

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
		//TODO
		Element current = sentinel.next; //start from the first element after sentinel
		while (current != sentinel) { //iterate until the sentinel is reached
			if (current.object.equals(e)) {  //check if the current elemnt is equal to the given element
				Element toRemove = current; //move to the next elemnt before removing the current one
				current = current.next; //remove it form the list
				toRemove.remove(); //decrease the size of the list
				size--;
			}else {
				current = current.next; //move to the next elmnt
			}
		}
	}
}