package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/24/17.
 */
public class FindAllTweetsMapper extends Mapper<LongWritable, Text, LongWritable, LongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String originalTweet = value.toString();
        try {
            Status status = TwitterObjectFactory.createStatus(originalTweet);
            context.write(new LongWritable(status.getUser().getId()), new LongWritable(status.getId()));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
