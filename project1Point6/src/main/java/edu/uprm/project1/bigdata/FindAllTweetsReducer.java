package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/24/17.
 */
public class FindAllTweetsReducer extends Reducer<LongWritable, LongWritable, LongWritable, Text> {

    @Override
    protected void reduce(LongWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long countTweets = 0;
        String idsTweets = "";
        for(LongWritable value : values){
            countTweets++;
            idsTweets += value.toString() + " ";
        }
        context.write(key, new Text("Number of Tweets: " + countTweets + " Id's of Tweets: " + idsTweets));
    }
}
