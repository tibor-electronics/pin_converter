/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.kevlindev.pinconverter.BoardRegistry;
import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.model.Bus;
import com.kevlindev.pinconverter.model.Pin;
import com.kevlindev.utils.StringUtils;

/**
 * BoardLabelProvider
 */
public class BoardLabelProvider implements ILabelProvider {
	private static final Image FOLDER_ICON = getImage("/com/kevlindev/pinconverter/ui/resources/folder.png");
	private static final Image BOARD_ICON = getImage("/com/kevlindev/pinconverter/ui/resources/board.png");
	private static final Image WING_ICON = getImage("/com/kevlindev/pinconverter/ui/resources/wing.png");
	private static final Image BUS_ICON = getImage("/com/kevlindev/pinconverter/ui/resources/bus.png");
	private static final Image PIN_ICON = getImage("/com/kevlindev/pinconverter/ui/resources/pin.png");

	static Image getImage(String resource) {
		ImageDescriptor descriptor = ImageDescriptor.createFromFile(BoardLabelProvider.class, resource);

		return descriptor.createImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object arg0) {
		if (arg0 instanceof BoardRegistry) {
			return FOLDER_ICON;
		} else if (arg0 instanceof Board) {
			return ((Board) arg0).isWing() ? WING_ICON : BOARD_ICON;
		} else if (arg0 instanceof Bus) {
			return BUS_ICON;
		} else if (arg0 instanceof Pin) {
			return PIN_ICON;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object arg0) {
		String result = StringUtils.EMPTY;

		if (arg0 == Registrar.BOARD_REGISTRY) {
			result = "Boards";
		} else if (arg0 == Registrar.WING_REGISTRY) {
			result = "Wings";
		} else if (arg0 instanceof Board) {
			Board board = (Board) arg0;

			result = board.getNames().get(0);
		} else if (arg0 instanceof Bus) {
			Bus bus = (Bus) arg0;

			result = bus.getName();
		} else if (arg0 instanceof Pin) {
			Pin pin = (Pin) arg0;

			result = pin.getName();
			result = (result == null || result.isEmpty()) ? "n/a" : result;
		}

		return result;
	}
}
