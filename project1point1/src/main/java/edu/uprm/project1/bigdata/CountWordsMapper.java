package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/20/17.
 */
public class CountWordsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String originalTweet = value.toString();

        try {
            String textTweet = TwitterObjectFactory.createStatus(originalTweet).getText().toUpperCase();
            if(textTweet.contains("TRUMP")){
                context.write((new Text("TRUMP")), new IntWritable(1));
            }
            if(textTweet.contains("MAGA")){
                context.write((new Text("MAGA")), new IntWritable(1));
            }
            if(textTweet.contains("DICTATOR")){
                context.write((new Text("DICTATOR")), new IntWritable(1));
            }
            if(textTweet.contains("IMPEACH")){
                context.write((new Text("IMPEACH")), new IntWritable(1));
            }
            if(textTweet.contains("DRAIN")){
                context.write((new Text("DRAIN")), new IntWritable(1));
            }
            if(textTweet.contains("SWAMP")){
                context.write((new Text("SWAMP")), new IntWritable(1));
            }
            if(textTweet.contains("CHANGE")){
                context.write((new Text("CHANGE")), new IntWritable(1));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }
}
