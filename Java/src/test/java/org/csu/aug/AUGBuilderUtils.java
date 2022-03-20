package org.csu.aug;

import org.csu.AUG.model.APIUsageExample;
import org.csu.src2aug.egroum.AUGBuilder;
import org.csu.src2aug.egroum.AUGConfiguration;

import java.util.ArrayList;
import java.util.Collection;

public class AUGBuilderUtils {
    public static APIUsageExample buildAUG(String code) {
        return buildAUGForMethod(code);
    }

    public static APIUsageExample buildAUG(String code, AUGConfiguration configuration) {
        return buildAUGForMethod(code, configuration);
    }

    public static APIUsageExample buildAUGForMethod(String code) {
        return buildAUGForMethod(code, new AUGConfiguration());
    }

    public static APIUsageExample buildAUGForMethod(String code, AUGConfiguration configuration) {
        String classCode = "class C { " + code + "}";
        Collection<APIUsageExample> groums = buildAUGsForClass(classCode, configuration);
        if (groums.size() == 1){
            return groums.iterator().next();
        }
        else{
            System.out.println("Not null");
            return null;
        }

    }

    public static Collection<APIUsageExample> buildAUGsForClass(String classCode) {
        return buildAUGsForClass(classCode, new AUGConfiguration());
    }

    private static Collection<APIUsageExample> buildAUGsForClass(String classCode, AUGConfiguration configuration) {
        AUGBuilder builder = new AUGBuilder(configuration);
        String projectName = "test";
        String basePath = getTestFilePath("/") + projectName;
        return builder.build(classCode, basePath, projectName, null);
    }

    private static String getTestFilePath(String relativePath) {
        if (!relativePath.startsWith("/")) {
            relativePath = "/" + relativePath;
        }
//        System.out.print(AUGBuilderUtils.class.getResource(relativePath).getFile());
        return AUGBuilderUtils.class.getResource(relativePath).getFile();
    }

    public static Collection<APIUsageExample> buildAUGsFromFile(String path) {
        return buildAUGsFromFile(path, new AUGConfiguration() {{
            removeImplementationCode = 2;
        }});
    }

    static Collection<APIUsageExample> buildAUGsFromFile(String path, AUGConfiguration configuration) {
        if(path.matches("input(.*)")){
            return new AUGBuilder(configuration).build(getTestFilePath(path), null);
        }
        else{
            return new AUGBuilder(configuration).build((path), null);
        }
    }

    public static ArrayList<APIUsageExample> buildAUGsForClasses(String[] sourceCodes) {
        ArrayList<APIUsageExample> groums = new ArrayList<>();
        for (String sourceCode : sourceCodes) {
            groums.addAll(buildAUGsForClass(sourceCode));
        }
        return groums;
    }

    public static ArrayList<APIUsageExample> buildAUGsForMethods(String... sourceCodes) {
        ArrayList<APIUsageExample> groums = new ArrayList<>();
        for (String sourceCode : sourceCodes) {
            groums.add(buildAUGForMethod(sourceCode));
        }
        return groums;
    }
}
