package org.csu.aug;

import org.csu.AUG.model.APIUsageExample;
import org.csu.AUG.model.APIUsageGraph;
import org.csu.AUG.model.dot.DisplayAUGDotExporter;
import org.csu.src2aug.egroum.AUGConfiguration;
import org.csu.src2aug.egroum.EGroumBuilder;
import org.csu.src2aug.egroum.EGroumGraph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


import static org.csu.aug.AUGBuilderUtils.*;


public class AUGBuilderUtilsTest {
    final static String FilePath = "F:\\API_datasets\\";
    @Test
    public void test1() throws IOException, InterruptedException {
//        print(buildAUGForMethod("void m() {"
//                + " while(true) {"
//                + "  if (1 == 2) continue;"
//                + "  else break; this.m();"
//                + "  super.m();"
//                + "  assert true;"
//                + "  throw new RuntimeException(); }"));
//        print(buildAUGForMethod("void demo() {"
//                + "  try {"
//                + "  FileInputStream fis = new FileInputStream(file);"
//                + "  return fis.read();"
//                + "  } catch(FileNotFoundException e) {"
//                + "  handle(e);} "));
    }

    @Test
    public void test2() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsForClass("class A {" +
                "  void m(C c) { c.foo(); }" +
                "  void n(C c) { c.foo(); }" +
                "}");
        int n =0;
        for(APIUsageGraph aug :groums){
            String name = String.valueOf(n);
            print(aug,name);
            n++;
        }
    }

    @Test
    public void test3() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsFromFile("input/Test_mine_ordered_nodes_of_same_receiver.java");
        int n =0;
        for(APIUsageGraph aug :groums){
            String name = String.valueOf(n);
            print(aug,name);
            n++;
        }
    }

    @Test
    public void test4() throws IOException, InterruptedException {
        Collection<APIUsageExample> groums = buildAUGsFromFile("train_dataset/Secure.java");
        for(APIUsageGraph aug :groums){
            print(aug);
        }
    }

    @Test
    public void test5() throws IOException, InterruptedException {
        String dir = "battleforge\\";
        String filename = dir+"misuses\\4\\patterns-src\\SpecifyDecryptEncoding.java";
        EGroumBuilder builder = new EGroumBuilder(new AUGConfiguration() {{
            removeImplementationCode = 2;
        }});
        ArrayList<EGroumGraph> groums = builder.buildBatchGroums(new File(FilePath+filename),null);

        Collection<APIUsageExample> augs = buildAUGsFromFile(FilePath+filename);
        int i = 0;
        for(APIUsageGraph aug :augs){
            print(aug,FilePath + dir + groums.get(i).getName());
        }
    }

    @Test
    public void test6() throws IOException, InterruptedException {
        String inputPath = "F:\\project\\jce-dataset\\KeyAgreementSpi";
        String outputPath = "F:\\project\\train_data";
        int count = 0;
        EGroumBuilder builder = new EGroumBuilder(new AUGConfiguration() {{
            removeImplementationCode = 2;
        }});
        File file = new File(inputPath);
        File[] fs = file.listFiles();
        for(File f: fs){
//            System.out.println(f.getName().replaceAll("[.][^.]+$", ""));
            ArrayList<EGroumGraph> groums = builder.buildBatchGroums(f,null);
            Collection<APIUsageExample> augs = buildAUGsFromFile(inputPath+"\\"+f.getName());
            int i = 0;
            for(APIUsageGraph aug :augs){
                File f2 = new File(outputPath+"\\"+f.getName().replaceAll("[.][^.]+$", ""));
                if(!f2.exists()){
                    f2.mkdir();
                }
                print2(aug,outputPath+"\\"+f.getName().replaceAll("[.][^.]+$", "")+"\\"+String.valueOf(i));
                i++;
                count++;
            }

        }
        System.out.println(count);
    }


    @Test
    public void test7() throws IOException, InterruptedException {
        String inputPath = "D:\\A_Document\\Adapter_project\\train\\0.java";
        String outputPath = "D:\\A_Document\\Adapter_project\\graph";
        Collection<APIUsageExample> groums = buildAUGsFromFile(inputPath);
        int n = 0;
        for(APIUsageGraph aug :groums){
            String name = String.valueOf(n);
            print(aug,name);
            n++;
        }
    }

    @Test
    public void test8() throws IOException, InterruptedException {
        String inputPath = "F:\\project\\train\\10.java";
        Collection<APIUsageExample> groums = buildAUGsFromFile(inputPath);
        int n =0;
        for(APIUsageGraph aug :groums){
            String name = String.valueOf(n);
            print(aug,name);
            n++;
        }
    }


    private static void print(APIUsageGraph groum, String name) throws IOException, InterruptedException {
        DisplayAUGDotExporter exporter = new DisplayAUGDotExporter();
        exporter.toPNGFile(groum, new File(name));
    }
    private static void print(APIUsageGraph groum)throws IOException, InterruptedException {
        DisplayAUGDotExporter exporter = new DisplayAUGDotExporter();
        exporter.toPNGFile(groum, new File(String.valueOf(groum.hashCode())));
    }
    private static void print2(APIUsageGraph groum)throws IOException, InterruptedException {
        DisplayAUGDotExporter exporter = new DisplayAUGDotExporter();
        exporter.toPNGFile(groum, new File(String.valueOf(groum.hashCode())));
    }
    private static void print2(APIUsageGraph groum, String name)throws IOException, InterruptedException {
        DisplayAUGDotExporter exporter = new DisplayAUGDotExporter();
        exporter.toDotFile(groum, new File(name));
    }
}
