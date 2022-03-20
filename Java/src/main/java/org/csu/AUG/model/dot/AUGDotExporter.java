package org.csu.AUG.model.dot;

import com.google.common.io.Files;
import org.csu.AUG.model.APIUsageExample;
import org.csu.AUG.model.APIUsageGraph;
import org.csu.AUG.model.Edge;
import org.csu.AUG.model.Node;
import org.csu.AUG.visitor.AUGLabelProvider;
import org.jgrapht.ext.ComponentAttributeProvider;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerNameProvider;

import java.io.*;
import java.util.Map;
import java.util.function.Function;

public class AUGDotExporter {
    private static final String WINDOWS_EXEC_DOT = "D:\\graphviz-2.38\\bin\\dot.exe"; //Windows
    private static final String LINUX_EXEC_DOT = "dot"; //Linux
    private static String EXEC_DOT = null;

    static {
        if (System.getProperty("os.name").startsWith("Windows"))
            EXEC_DOT = WINDOWS_EXEC_DOT;
        else
            EXEC_DOT = LINUX_EXEC_DOT;
    }

    private static final String NEW_LINE = System.getProperty("line.separator");
    private final IntegerNameProvider<Node> nodeIdProvider = new IntegerNameProvider<>();
    private final DOTExporter<Node, Edge> exporter;
    private final AUGAttributeProvider<APIUsageGraph> graphAttributeProvider;

    public AUGDotExporter(AUGLabelProvider labelProvider,
                          ComponentAttributeProvider<Node> nodeComponentAttributeProvider ,
                          ComponentAttributeProvider<Edge> edgeComponentAttributeProvider){
        this(labelProvider::getLabel, labelProvider::getLabel, nodeComponentAttributeProvider, edgeComponentAttributeProvider);
    }

    public AUGDotExporter(Function<Node, String> nodeLabelProvider,
                          Function<Edge, String> edgeLabelProvider,
                          ComponentAttributeProvider<Node> nodeComponentAttributeProvider,
                          ComponentAttributeProvider<Edge> edgeComponentAttributeProvider) {
        this(nodeLabelProvider, edgeLabelProvider, nodeComponentAttributeProvider, edgeComponentAttributeProvider, null);
    }

    public AUGDotExporter(Function<Node, String> nodeLabelProvider,
                          Function<Edge, String> edgeLabelProvider,
                          ComponentAttributeProvider<Node> nodeComponentAttributeProvider,
                          ComponentAttributeProvider<Edge> edgeComponentAttributeProvider,
                          AUGAttributeProvider<APIUsageGraph> graphAttributeProvider) {
        this.exporter = new DOTExporter<>(nodeIdProvider,
                nodeLabelProvider::apply, edgeLabelProvider::apply,
                nodeComponentAttributeProvider, edgeComponentAttributeProvider);
        this.graphAttributeProvider = graphAttributeProvider;
    }

    public String toDotGraph(APIUsageGraph aug){
        StringWriter writer = new StringWriter();
        toDotGraph(aug, writer);
        return writer.toString();
    }

    private void toDotGraph(APIUsageGraph aug, Writer writer) {
        nodeIdProvider.clear();
        exporter.export(new PrintWriter(writer) {
            @Override
            public void write(String s, int off, int len) {
                if (s.equals("digraph G {")) {
                    String methodName = aug instanceof APIUsageExample ? ((APIUsageExample) aug).getLocation().getMethodSignature() : "AUG";
                    StringBuilder data = new StringBuilder("digraph \"").append(methodName).append("\" {").append(NEW_LINE);
                    if (graphAttributeProvider != null) {
                        for (Map.Entry<String, String> attribute : graphAttributeProvider.getAUGAttributes(aug).entrySet()) {
                            data.append(attribute.getKey()).append("=").append(attribute.getValue()).append(";").append(NEW_LINE);
                        }
                    }
                    super.write(data.toString(), 0, data.length());
                } else {
                    super.write(s, off, len);
                }
            }
        }, aug);
    }

    public void toDotFile(APIUsageGraph aug, File file) throws IOException{
        if (!file.getPath().endsWith(".dot")) {
            file = new File(file.getPath() + ".dot");
        }
        file = file.getAbsoluteFile();
        ensureDirectory(file.getParentFile());
        try (BufferedWriter fout = new BufferedWriter(new FileWriter(file))) {
            fout.append(toDotGraph(aug));
            fout.flush();
        }
    }

    public void toPNGFile(APIUsageGraph aug, File file) throws IOException, InterruptedException {
        file = file.getAbsoluteFile();
        File directory = file.getParentFile();
        ensureDirectory(directory);
        String nameWithoutExtension = new File(directory, Files.getNameWithoutExtension(file.getPath())).getPath();
        toDotFile(aug, new File(nameWithoutExtension + ".dot"));

        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(new String[]{EXEC_DOT, "-T"+ "png", nameWithoutExtension +".dot", "-o", nameWithoutExtension +"."+ "png"});
        p.waitFor();
    }


    private void ensureDirectory(File path) {
        if (!path.exists()) path.mkdirs();
    }
}
