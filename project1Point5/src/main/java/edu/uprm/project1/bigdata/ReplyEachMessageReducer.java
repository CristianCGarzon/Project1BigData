package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/22/17.
 */
public class ReplyEachMessageReducer extends Reducer<LongWritable, LongWritable, LongWritable, Text>{

    @Override
    protected void reduce(LongWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        String replies = "";
        for(LongWritable value : values){
            replies += value.toString() + " ";
        }
        context.write(key, new Text(replies));
    }
}
