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
 * Created by ccgarzona on 3/21/17.
 */
public class KeywordsCounterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String originalTweet = value.toString();

        try {
            String textTweet = TwitterObjectFactory.createStatus(originalTweet).getText().toUpperCase();
            String tweetWords[] = textTweet.split(" ");

            for(String tweetWord : tweetWords){
                if(     !tweetWord.contains("A") && !tweetWord.contains("AN") && !tweetWord.contains("AND") && !tweetWord.contains("ARE") &&
                        !tweetWord.contains("AS") && !tweetWord.contains("AT") && !tweetWord.contains("BE") && !tweetWord.contains("BY") &&
                        !tweetWord.contains("FOR") && !tweetWord.contains("FROM") && !tweetWord.contains("HAS") && !tweetWord.contains("HE") &&
                        !tweetWord.contains("IN") && !tweetWord.contains("IS") && !tweetWord.contains("IT") && !tweetWord.contains("ITS") &&
                        !tweetWord.contains("OF") && !tweetWord.contains("ON") && !tweetWord.contains("THAT") && !tweetWord.contains("THE") &&
                        !tweetWord.contains("TO") && !tweetWord.contains("WAS") && !tweetWord.contains("WERE") && !tweetWord.contains("WILL") &&
                        !tweetWord.contains("WITH")
                ){
                    context.write(new Text(tweetWord.toString()), new IntWritable(1));
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

}
