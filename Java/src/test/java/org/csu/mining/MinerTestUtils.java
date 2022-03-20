package org.csu.mining;



import org.csu.AUG.model.APIUsageExample;
import org.csu.AUG.model.dot.DisplayAUGDotExporter;
import org.csu.AUG.model.patterns.APIUsagePattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.csu.aug.AUGBuilderUtils.buildAUGsForMethods;


public class MinerTestUtils {
    private static List<APIUsagePattern> mine(Collection<APIUsageExample> examples, Configuration config) {
        AUGMiner miner = new DefaultAUGMiner(config);
        return new ArrayList<>(miner.mine(examples).getPatterns());
    }

    public static List<APIUsagePattern> mineMethods(Configuration config, String... sourceCodes) {
        return mine(buildAUGsForMethods(sourceCodes), config);
    }

    static List<APIUsagePattern> mineWithMinSupport(Collection<APIUsageExample> examples, int minSupport) {
        Configuration config = new Configuration() {{
            minPatternSupport = minSupport;
            maxPatternSize = 300;
        }};
        return mine(examples, config);
    }

    static List<APIUsagePattern> mineWithMinSupport2(Collection<APIUsageExample> examples) {
        return mineWithMinSupport(examples, 2);
    }

    static List<APIUsagePattern> mineMethodsWithMinSupport2(String... sourceCodes) {
        return mineWithMinSupport2(buildAUGsForMethods(sourceCodes));
    }

    static void print(APIUsagePattern pattern) {
        System.out.println(new DisplayAUGDotExporter().toDotGraph(pattern));
    }

    static void print(List<APIUsagePattern> patterns) {
        for (APIUsagePattern pattern : patterns) {
            print(pattern);
        }
    }
}
