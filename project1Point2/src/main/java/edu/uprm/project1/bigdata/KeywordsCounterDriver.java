package edu.uprm.project1.bigdata;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by ccgarzona on 3/21/17.
 */
public class KeywordsCounterDriver {

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.err.println("Usage: Count Keywords 2 in Tweet <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(KeywordsCounterDriver.class);
        job.setJobName("Job keywords tweet counter");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        job.setMapperClass(KeywordsCounterMapper.class);
        job.setReducerClass(KeywordsCounterReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0:1);
    }

}
