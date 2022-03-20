package org.csu.input;

import javax.swing.*;

class Test_anonymous_class {

	public static void pattern(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final JFrame f = new JFrame("Main Window");
				// add components
				f.pack();
				f.setVisible(true);
			}
		});
	}
}