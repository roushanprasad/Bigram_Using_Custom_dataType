package com.core;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class BigramMapper extends Mapper<LongWritable, Text, TextPair, IntWritable> {
	private Text lastWord = null;
	private Text currentWord = new Text();

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		line = line.replace(",", "");
		line = line.replace(".", "");
		
		for(String word: line.split(" ")){
			if(lastWord == null){
				lastWord = new Text(word);
			}else{
				currentWord.set(word);
				context.write(new TextPair(lastWord, currentWord),  new IntWritable(1));
				lastWord.set(currentWord.toString());
			}
		}
	}
}
