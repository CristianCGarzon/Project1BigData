package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/21/17.
 */
public class KeywordsCounterReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int countKeyword = 0;

        for(IntWritable value : values){
            countKeyword += value.get();
        }

        context.write(key, new IntWritable(countKeyword));
    }
}
