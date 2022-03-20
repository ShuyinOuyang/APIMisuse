package org.csu.mining;

import org.csu.AUG.model.APIUsageExample;

import java.util.Collection;

public interface AUGMiner {
    Model mine(Collection<APIUsageExample> examples);
}
