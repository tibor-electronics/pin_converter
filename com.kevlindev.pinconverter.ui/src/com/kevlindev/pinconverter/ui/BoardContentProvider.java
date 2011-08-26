/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.model.Bus;
import com.kevlindev.pinconverter.model.Pin;

/**
 * BoardContentProvider
 */
public class BoardContentProvider implements ITreeContentProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// possibly load new data
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object arg0) {
		Object[] result = new Object[0];

		if (arg0 == Registrar.BOARD_REGISTRY) {
			List<Board> boards = Registrar.BOARD_REGISTRY.getBoards();

			result = boards.toArray(new Object[boards.size()]);
		} else if (arg0 == Registrar.WING_REGISTRY) {
			List<Board> boards = Registrar.WING_REGISTRY.getBoards();

			result = boards.toArray(new Object[boards.size()]);
		} else if (arg0 instanceof Board) {
			Board board = (Board) arg0;
			List<Bus> buses = board.getBuses();

			result = buses.toArray(new Object[buses.size()]);
		} else if (arg0 instanceof Bus) {
			Bus bus = (Bus) arg0;
			List<Pin> pins = bus.getPins();

			result = pins.toArray(new Object[pins.size()]);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getElements(Object arg0) {
		return (Object[]) arg0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object arg0) {
		boolean result = false;

		if (arg0 == Registrar.BOARD_REGISTRY) {
			result = Registrar.BOARD_REGISTRY.getBoards().size() > 0;
		} else if (arg0 == Registrar.WING_REGISTRY) {
			result = Registrar.WING_REGISTRY.getBoards().size() > 0;
		} else if (arg0 instanceof Board) {
			Board board = (Board) arg0;

			result = board.getBusCount() > 0;
		} else if (arg0 instanceof Bus) {
			Bus bus = (Bus) arg0;

			result = bus.getPinCount() > 0;
		}

		return result;
	}
}
