package org.csu.input;

import java.io.File;

class Test_anonymous_class {
	
	boolean pattern(File lockDir, String lockFilename) throws IOException {
	    File lockFile = new File(lockDir, lockFilename);
	    if (!lockDir.exists()) {
	      if (!lockDir.mkdirs()) {
	        throw new IOException("Cannot create lock directory: " + lockDir);
	      }
	    }
	    return lockFile.createNewFile();
	  }
}