package edu.uprm.project1.bigdata;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.List;

/**
 * Created by ccgarzona on 3/22/17.
 */
public class ReplyEachMessageDriver {

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.err.println("Usage: Id's Retweets for each message <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(ReplyEachMessageDriver.class);
        job.setJobName("Job ids retweets for ecah message");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(ReplyEachMessageMapper.class);
        job.setReducerClass(ReplyEachMessageReducer.class);

        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(List.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
