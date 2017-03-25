package edu.uprm.project1.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;

/**
 * Created by ccgarzona on 3/21/17.
 */
public class UniqueScreenNamesMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String originalTweet = value.toString();
        try {
            String screenName = TwitterObjectFactory.createStatus(originalTweet).getUser().getScreenName();
            context.write(new Text(screenName), NullWritable.get());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
