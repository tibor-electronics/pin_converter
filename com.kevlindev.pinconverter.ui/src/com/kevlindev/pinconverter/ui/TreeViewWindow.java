/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.ui;

import java.io.InputStream;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kevlindev.pinconverter.BoardRegistry;
import com.kevlindev.pinconverter.PinConverter;
import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.pinconverter.commands.AbstractLoadCommand;

/**
 * BlankWindow
 */
public class TreeViewWindow {
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new TreeViewWindow().run();
	}

	public void run() {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Pin Converter - 0.2");

		createContents(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	/**
	 * @param shell
	 */
	private void createContents(Shell shell) {
		shell.setLayout(new GridLayout(1, true));

		loadBoards();
		Object[] input = new Object[] { Registrar.BOARD_REGISTRY,
				Registrar.WING_REGISTRY };

		TreeViewer treeViewer = new TreeViewer(shell);
		treeViewer.setContentProvider(new BoardContentProvider());
		treeViewer.setLabelProvider(new BoardLabelProvider());
		treeViewer.setInput(input);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		// treeViewer.setSorter(new ViewerSorter() {
		// @Override
		// public int compare(Viewer viewer, Object e1, Object e2) {
		// String name1 = null;
		// String name2 = null;
		//
		// if (viewer != null && viewer instanceof ContentViewer) {
		// IBaseLabelProvider provider = ((ContentViewer)
		// viewer).getLabelProvider();
		//
		// if (provider instanceof ILabelProvider) {
		// ILabelProvider labelProvider = (ILabelProvider) provider;
		//
		// name1 = labelProvider.getText(e1);
		// name2 = labelProvider.getText(e2);
		// }
		// }
		//
		// if (name1 == null) {
		// name1 = e1.toString();
		// }
		// if (name2 == null) {
		// name2 = e2.toString();
		// }
		//
		// return name1.compareToIgnoreCase(name2);
		// }
		// });
	}

	private void loadBoards() {
		new AbstractLoadCommand(null) {
			@Override
			protected InputStream getInputStream() {
				return PinConverter.class
						.getResourceAsStream("/com/kevlindev/pinconverter/resources/boards.txt");
			}

			@Override
			protected BoardRegistry getRegistry() {
				return Registrar.BOARD_REGISTRY;
			}
		}.execute(null);
		new AbstractLoadCommand(null) {
			@Override
			protected InputStream getInputStream() {
				return PinConverter.class
						.getResourceAsStream("/com/kevlindev/pinconverter/resources/wings.txt");
			}

			@Override
			protected BoardRegistry getRegistry() {
				return Registrar.WING_REGISTRY;
			}
		}.execute(null);
	}
}
