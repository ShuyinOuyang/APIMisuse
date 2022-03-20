package org.csu.mining;

import org.csu.AUG.model.APIUsageExample;
import org.csu.AUG.model.APIUsageGraph;
import org.csu.AUG.model.dot.DisplayAUGDotExporter;
import org.csu.AUG.model.patterns.APIUsagePattern;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.csu.aug.AUGBuilderUtils.buildAUGsFromFile;
import static org.csu.mining.MinerTestUtils.mineWithMinSupport;
import static org.csu.mining.MinerTestUtils.mineWithMinSupport2;

public class MinerTest {
    @Test
    public void mineOrderedNodesOfSameReceiver() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsFromFile("input/Test_mine_ordered_nodes_of_same_receiver.java");

        List<APIUsagePattern> patterns = mineWithMinSupport(groums, 1);

//		assertThat(patterns.size(), is(1));
        print(patterns);
    }

    @Test
    public void mineExceptionNodes() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsFromFile("input/Test_try.java");

        List<APIUsagePattern> patterns = mineWithMinSupport2(groums);

        print(patterns.get(0));
    }

    @Test
    public void mineKeepDataNodes() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsFromFile("input/Test_keep_data.java");

        List<APIUsagePattern> patterns = mineWithMinSupport2(groums);

        print(patterns.get(0));
    }
    private void print(APIUsageGraph graph) throws IOException, InterruptedException {
        DisplayAUGDotExporter exporter = new DisplayAUGDotExporter();
        exporter.toPNGFile(graph, new File("output"));
        System.out.println(new DisplayAUGDotExporter().toDotGraph(graph));
    }

    private void print(Collection<? extends APIUsageGraph> graphs) throws IOException, InterruptedException {
        for (APIUsageGraph graph : graphs) {
            print(graph);
        }
    }
}
