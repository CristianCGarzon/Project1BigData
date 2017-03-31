package edu.uprm.project1.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.net.URI;

/**
 * Created by ccgarzona on 3/21/17.
 */
public class KeywordsCounterDriver {

    public static void main(String[] args) throws Exception{
        String nameFolder = "Problem2";
        String nameCSVFile = "NonStopWords";
        String columns = "Keywords,Value";
        String programDescription = "Count the number of different keywords";

        Configuration conf = new Configuration();
        FileSystem hdfsFileSystem = FileSystem.get(conf);
        Path pathToUse = new Path(args[1]);

        if(args.length == 2 && (args[0].matches(".*hdfs.*") || args[1].matches(".*hdfs.*"))){
            System.err.println("Usage fo HDFS file: " + programDescription + " <input path> <output path> <output path in HDFS> <HADOOP_HOME path>");
            System.exit(-1);
        } else if(args.length != 2 && args.length != 4){
            System.err.println("Usage for HDFS File: " + programDescription + " <input path> <output path> <output path in HDFS> <HADOOP_HOME path>");
            System.err.println("Usage for local File: " + programDescription + " <input path> <output path>");
            System.exit(-1);
        }

        if(args[1].matches(".*hdfs.*")) {
            conf.addResource(new Path(args[3] + "/core-site.xml"));
            conf.addResource(new Path(args[3] + "/hdfs-site.xml"));
            hdfsFileSystem = FileSystem.get(conf);
            pathToUse = new Path("/" + args[2] + "/" + nameFolder);

            if(hdfsFileSystem.exists(pathToUse)){
                hdfsFileSystem.delete(pathToUse, true);
                System.out.println("Existing Folder in your HDFS: " + args[1] + "/" + nameFolder + " was deleted.");
            }
        } else {
            FileSystem fs = FileSystem.get(new Configuration());
            Path localPath = new Path(args[1] + "/" + nameFolder);
            if(fs.exists(localPath)) {
                fs.delete(localPath, true);
                System.out.println("Existing Folder in your Local File System: " + args[1] + "/" + nameFolder + " was deleted.");
            }
        }

        String fileName = args[0];
        URI fileUri = URI.create(fileName);
        Configuration configuration = new Configuration();
        configuration.set("mapred.textoutputformat.separator", ",");

        Job job = new Job(configuration);
        Path path = new Path(fileUri);
        FileInputFormat.addInputPath(job, path);
        job.setJarByClass(KeywordsCounterDriver.class);
        job.setJobName("Job keywords tweet counter");

        FileOutputFormat.setOutputPath(job, new Path(args[1] + "/" + nameFolder));
        FileInputFormat.addInputPath(job, path);

        job.setMapperClass(KeywordsCounterMapper.class);
        job.setReducerClass(KeywordsCounterReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        if(args[1].matches(".*hdfs.*") && job.waitForCompletion(true)) {
            FSDataInputStream fsDataInputStream = hdfsFileSystem.open(new Path(pathToUse.toString() + "/part-r-00000"));
            InputStreamReader inputStreamReader = new InputStreamReader(fsDataInputStream);
            BufferedReader bufferedReaderHDFS = new BufferedReader(inputStreamReader);

            FSDataOutputStream fsDataOutputStream = hdfsFileSystem.create(new Path(pathToUse.toString() + "/" + nameCSVFile + ".csv"));

            fsDataOutputStream.writeBytes(columns + " \n");
            String line = bufferedReaderHDFS.readLine();

            while(line != null){
                fsDataOutputStream.writeBytes(line+ "\n");
                line = bufferedReaderHDFS.readLine();
            }

            bufferedReaderHDFS.close();
            fsDataOutputStream.close();
        } else if (job.waitForCompletion(true)) {
            File file = new File(args[1] + "/" + nameFolder + "/" + nameCSVFile + ".csv");
            FileReader fileReader = new FileReader(new File(args[1] + "/" + nameFolder + "/part-r-00000"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(columns + " \n");
            String line = bufferedReader.readLine();
            while (line != null) {
                fileWriter.write(line + "\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileWriter.close();
        }
        System.out.println("Job finish correctly. Your data is avaliable now in: " + args[1] + "/" + nameFolder);
    }

}
