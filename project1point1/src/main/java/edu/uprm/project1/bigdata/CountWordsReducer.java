package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/20/17.
 */
public class CountWordsReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int wordCount = 0;

        for(IntWritable value : values){
            wordCount += value.get();
        }
        context.write(key, new IntWritable(wordCount));
    }
}
