/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * CaselessSet
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class CaselessSet implements Set<String> {
	/**
	 * This is the backing store for the case-less set.
	 */
	private Set<CaselessString> set;

	/**
	 * The default constructor
	 */
	public CaselessSet() {
	}

	/**
	 * This constructor allows the backing store to be specified during
	 * instantiation. This is useful in cases where the default HashSet is not
	 * desired. For example, a LinkedHashSet may be used to preserve order. The
	 * biggest negative is that the Set has to specify its generic type as
	 * CaselessString, exposing some of the internals of this implementation.
	 * 
	 * @param set
	 *            The set to use as this instances backing store
	 */
	public CaselessSet(Set<CaselessString> set) {
		this.set = set;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(String e) {
		if (set == null) {
			set = new HashSet<CaselessString>();
		}

		return set.add(new CaselessString(e));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends String> c) {
		boolean result = true;

		for (String item : c) {
			if (!add(item)) {
				result = false;
				break;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		if (set != null) {
			set.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		boolean result = false;

		if (set != null) {
			if (o instanceof CaselessString) {
				result = set.contains((CaselessString) o);
			} else if (o instanceof String) {
				result = set.contains(new CaselessString((String) o));
			} else {
				result = set.contains(o);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		boolean result = false;

		if (set != null) {
			result = set.containsAll(c);
		}

		return result;
	}

	/**
	 * getItemList
	 * 
	 * @return List<String>
	 */
	private List<String> getItemList() {
		List<String> items = new ArrayList<String>();

		if (set != null) {
			for (CaselessString item : set) {
				items.add(item.toString());
			}
		}

		return items;
	}

	/**
	 * getItemList
	 * 
	 * @param c
	 * @return List<?>
	 */
	private List<?> getItemList(Collection<?> c) {
		List<Object> items = new ArrayList<Object>();

		for (Object item : c) {
			if (item instanceof String) {
				items.add(new CaselessString((String) item));
			} else {
				items.add(item);
			}
		}

		return items;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (set != null) ? set.isEmpty() : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return getItemList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		boolean result = false;

		if (set != null) {
			result = set.remove(o);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;

		if (set != null) {
			result = set.removeAll(getItemList(c));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;

		if (set != null) {
			result = set.retainAll(getItemList(c));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return (set != null) ? set.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		return getItemList().toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return getItemList().toArray(a);
	}
}
