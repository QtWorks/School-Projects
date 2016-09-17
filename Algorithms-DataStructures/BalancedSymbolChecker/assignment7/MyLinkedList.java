package assignment7;


import java.util.NoSuchElementException;

public class MyLinkedList<E> implements List<E> {

	private Node first;
	private Node last;
	private int size;

	private class Node {
		E data;
		Node next;
		Node prev;

	}

	public MyLinkedList() {

	}

	/**
	 * Inserts the specified element at the beginning of the list. O(1) for a
	 * doubly-linked list.
	 */
	@Override
	public void addFirst(E element) {

		Node newFirst = new Node();
		newFirst.data = element;
		if (!this.isEmpty()) {
			newFirst.next = first;
			first.prev = newFirst;
		} else {
			last = newFirst;
		}
		first = newFirst;
		size++;
	}

	/**
	 * Inserts the specified element at the end of the list. O(1) for a
	 * doubly-linked list.
	 */
	@Override
	public void addLast(E element) {
		Node newLast = new Node();
		newLast.data = element;
		if (!this.isEmpty()) {
			newLast.prev = last;
			last.next = newLast;
		} else {
			first = newLast;
		}
		last = newLast;
		size++;
	}

	/**
	 * Inserts the specified element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 ||
	 * index > size()) O(N) for a doubly-linked list.
	 */
	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		if (this.isEmpty()) {
			this.addFirst(element);
			return;
		} else if (index == this.size()) {
			this.addLast(element);
			return;
		}
		Node temp = new Node();
		temp = first;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		temp.data = element;
		temp.prev.next = temp;
		temp.next.prev = temp;
		size++;
	}

	/**
	 * Returns the first element in the list. Throws NoSuchElementException if
	 * the list is empty. O(1) for a doubly-linked list.
	 */
	@Override
	public E getFirst() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		return this.first.data;

	}

	/**
	 * Returns the last element in the list. Throws NoSuchElementException if
	 * the list is empty. O(1) for a doubly-linked list.
	 */
	@Override
	public E getLast() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		return this.last.data;

	}

	/**
	 * Returns the element at the specified position in the list. Throws
	 * IndexOutOfBoundsException if index is out of range (index < 0 || index >=
	 * size()) O(N) for a doubly-linked list.
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		Node temp = new Node();
		temp = first;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp.data;
	}

	/**
	 * Removes and returns the first element from the list. Throws
	 * NoSuchElementException if the list is empty. O(1) for a doubly-linked
	 * list.
	 */
	@Override
	public E removeFirst() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		E temporary = first.data;
		this.first = first.next;
		first.prev = null;
		this.size--;
		return (E) temporary;
	}

	/**
	 * Removes and returns the last element from the list. Throws
	 * NoSuchElementException if the list is empty. O(1) for a doubly-linked
	 * list.
	 */
	@Override
	public E removeLast() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		E temporary = last.data;
		if(first.next == null){
			first = null;
			last = null;
		}
		else{
			last = last.prev;
			last.next = null;
		}
		this.size--;
		return (E) temporary;
	}

	/**
	 * Removes and returns the element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 ||
	 * index >= size()) O(N) for a doubly-linked list.
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return this.removeFirst();
		} else if (index == this.size()) {
			return this.removeLast();
		}
		Node temp = new Node();
		temp = this.first;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		E temporary = temp.data;
		temp.prev.next = temp.next;
		temp.next.prev = temp.prev;
		this.size--;
			
		return temporary;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in the
	 * list, or -1 if this list does not contain the element. O(N) for a
	 * doubly-linked list.
	 */
	@Override
	public int indexOf(E element) {
		Node temp = new Node();
		temp = this.first;
		for (int i = 0; i < this.size - 1; i++) {
			if (element.equals(temp.data))
				return i;
			temp = temp.next;
		}
		
		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. O(N) for a
	 * doubly-linked list.
	 */
	@Override
	public int lastIndexOf(E element) {
		Node temp = new Node();
		temp = this.last;
		for (int i = this.size - 1; i > 0; i--) {
			if (element.equals(temp.data))
				return i;
			temp = temp.prev;
		}
		
		return -1;
	}

	/**
	 * Returns the number of elements in this list. O(1) for a doubly-linked
	 * list.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns true if this collection contains no elements. O(1) for a
	 * doubly-linked list.
	 */
	@Override
	public boolean isEmpty() {
		if (this.first == (null))
			return true;
		return false;
	}

	/**
	 * Removes all of the elements from this list. O(1) for a doubly-linked
	 * list.
	 */
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		size = 0;

	}

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element). O(N) for a doubly-linked list.
	 */
	@Override
	public Object[] toArray() {
		Object [] array = new Object [this.size];
		for (int i = 0; i < this.size; i++)
			array[i] = this.get(i);
		return array;
	}

}
