package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccgarzona on 3/22/17.
 */
public class ReplyEachMessageReducer extends Reducer<LongWritable, LongWritable, LongWritable, List<LongWritable>>{

    @Override
    protected void reduce(LongWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        List<LongWritable> replies = new ArrayList<LongWritable>();
        for(LongWritable value : values){
            replies.add(value);
        }
        context.write(key, replies);
    }
}
