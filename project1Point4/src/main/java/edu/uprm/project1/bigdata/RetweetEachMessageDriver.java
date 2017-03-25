package edu.uprm.project1.bigdata;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Created by ccgarzona on 3/22/17.
 */
public class RetweetEachMessageDriver {

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.err.println("Usage: Id's Retweets for each message <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(RetweetEachMessageDriver.class);
        job.setJobName("Job ids retweets for ecah message");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(RetweetEachMessageMapper.class);
        job.setReducerClass(RetweetEachMessageReducer.class);

        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
