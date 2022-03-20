package org.csu.input;

import java.io.File;

class Test_anonymous_class {

	public void target() {
		// create a lock file
		final File lockFile = new File("");
		new Object() {
			public boolean obtain() throws IOException {
				if (DISABLE_LOCKS) {
					return true;
				}
				return lockFile.createNewFile();
			}
		};
	}
}