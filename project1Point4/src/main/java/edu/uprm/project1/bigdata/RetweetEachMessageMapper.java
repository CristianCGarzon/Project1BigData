package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by ccgarzona on 3/22/17.
 */
public class RetweetEachMessageMapper extends Mapper<LongWritable, Text, LongWritable, LongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String originalTweet = value.toString();
        try {
            Status status = TwitterObjectFactory.createStatus(originalTweet);
            if(status.isRetweet()){
                context.write(new LongWritable(status.getRetweetedStatus().getId()), new LongWritable(status.getId()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
