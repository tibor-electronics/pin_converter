/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * BlankWindow
 */
public class TabbedWindow {
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new TabbedWindow().run();
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

		Composite composite = new Composite(shell, SWT.None);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new RowLayout());
		// create buttons

		CTabFolder tabFolder = new CTabFolder(shell, SWT.TOP);
		tabFolder.setBorderVisible(true);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		new CTabItem(tabFolder, SWT.NONE, 0).setText("Board");
		new CTabItem(tabFolder, SWT.NONE, 1).setText("Bus");
		new CTabItem(tabFolder, SWT.NONE, 2).setText("Pin");
	}
}
