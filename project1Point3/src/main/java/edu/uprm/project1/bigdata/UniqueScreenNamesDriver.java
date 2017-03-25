package edu.uprm.project1.bigdata;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by ccgarzona on 3/21/17.
 */
public class UniqueScreenNamesDriver {

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.err.println("Usage: Count Keywords in Tweet <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(UniqueScreenNamesDriver.class);
        job.setJobName("Job distinct screen names");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(UniqueScreenNamesMapper.class);
        job.setReducerClass(UniqueScreenNamesReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
