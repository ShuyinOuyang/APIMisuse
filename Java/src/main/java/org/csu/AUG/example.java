package org.csu.AUG;

import java.io.FileNotFoundException;

public class example {
    public String code() {
        StringBuilder sb = new StringBuilder();
        sb.append("foo").append("bar");
        return sb.toString();
    }
    public void m(){
        try{
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
