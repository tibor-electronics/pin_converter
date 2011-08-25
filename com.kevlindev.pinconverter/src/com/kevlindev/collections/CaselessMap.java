/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * PinMap
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <V>
 *            any type of object
 */
public class CaselessMap<V> implements Map<String, V> {
	/**
	 * This is the backing store for the case-less map. The key is always a
	 * CaselessString and the value is determined by the generic parameter V
	 */
	private Map<CaselessString, V> map;

	/**
	 * The default constructor
	 */
	public CaselessMap() {
	}

	/**
	 * This constructor allows the backing store to be specified during
	 * instantiation. This is useful in cases where the default HashMap is not
	 * desired. For example, a LinkedHashMap may be used to preserve order. The
	 * biggest negative is that the Map has to specify its key as a
	 * CaselessString, exposing some of the internals of this implementation.
	 * 
	 * @param map
	 *            The map to use as this instances backing store
	 */
	public CaselessMap(Map<CaselessString, V> map) {
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		if (map != null) {
			map.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		boolean result = false;

		if (map != null) {
			if (key instanceof CaselessString) {
				result = map.containsKey((CaselessString) key);
			} else if (key instanceof String) {
				result = map.containsKey(new CaselessString((String) key));
			} else {
				result = map.containsKey(key);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, V>> entrySet() {
		Set<Entry<String, V>> result = Collections.emptySet();

		if (map != null) {
			Map<String, V> temp = new LinkedHashMap<String, V>();

			for (Entry<CaselessString, V> entry : map.entrySet()) {
				temp.put(entry.getKey().toString(), entry.getValue());
			}

			result = temp.entrySet();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		V result = null;

		if (map != null) {
			if (key instanceof CaselessString) {
				result = map.get((CaselessString) key);
			} else if (key instanceof String) {
				result = map.get(new CaselessString((String) key));
			} else {
				result = map.get(key);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (map != null) ? map.isEmpty() : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	public Set<String> keySet() {
		Set<String> result = new HashSet<String>();

		if (map != null) {
			for (CaselessString string : map.keySet()) {
				result.add(string.toString());
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(String key, V value) {
		if (map == null) {
			map = new HashMap<CaselessString, V>();
		}

		return map.put(new CaselessString(key), value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		V result = null;

		if (map != null) {
			if (key instanceof CaselessString) {
				result = map.remove((CaselessString) key);
			} else if (key instanceof String) {
				result = map.remove(new CaselessString((String) key));
			} else {
				result = map.remove(key);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	public int size() {
		return (map != null) ? map.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		Collection<V> result = Collections.emptySet();

		if (map != null) {
			result = map.values();
		}

		return result;
	}
}
