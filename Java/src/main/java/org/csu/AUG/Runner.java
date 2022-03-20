package org.csu.AUG;

import org.csu.AUG.model.APIUsageGraph;
import org.csu.AUG.persistence.AUGReader;
import org.csu.AUG.persistence.PersistenceAUGDotImporter;
import org.jgrapht.ext.ImportException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;

public class Runner {
    public static void main(String[] args){
//        APIUsageExample aug = buildAUG(new Location("aug",
//                "src/main/java/org/csu/APIMisUse/example.java",
//                "code()"))
//                .withConstructorCall("createSB", "java.lang.StringBuilder", 11)
//                .withVariable("sb", "java.lang.StringBuilder", "sb")
//                .withDefinitionEdge("createSB", "sb")
//                .withLiteral("str1", "java.lang.String", "\"foo\"")
//                .withMethodCall("append1", "java.lang.AbstractStringBuilder", "append()", 12)
//                .withParameterEdge("str1", "append1")
//                .withReceiverEdge("sb", "append1")
//                .withLiteral("str2", "java.lang.String", "\"bar\"")
//                .withMethodCall("append2", "java.lang.AbstractStringBuilder", "append()", 12)
//                .withOrderEdge("append1", "append2")
//                .withParameterEdge("str2", "append2")
//                .withReceiverEdge("sb", "append2")
//                .withMethodCall("toStr", "java.lang.AbstractStringBuilder", "toString()", 13)
//                .withOrderEdge("append2", "toStr")
//                .withReceiverEdge("sb", "toStr")
//                .withAnonymousObject("result", "java.lang.String")
//                .withDefinitionEdge("toStr", "result")
//                .withReturn("ret", 13)
//                .withParameterEdge("result", "ret")
//                .build();
//        System.out.println(c);

        File file = new File("src/main/java/org/csu/APIMisUse/example.java");

        try {
            FileInputStream inputStream = new FileInputStream(file);
//            System.out.println(inputStream);
            PersistenceAUGDotImporter importer = new PersistenceAUGDotImporter();
            Supplier<APIUsageGraph> emptyGraphFactory = APIUsageGraph::new;
            AUGReader<APIUsageGraph> reader = new AUGReader<>(inputStream, importer, emptyGraphFactory);
            APIUsageGraph aug = reader.read();
            System.out.println(aug);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImportException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
