package com.core;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BigramDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		
		if(args.length !=2){
			System.out.println("Invalid Command, Requires 2 params");
			return -1;
		}
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(getClass());
		job.setJobName("Bigram Count");
		
		job.setMapperClass(BigramMapper.class);
		job.setReducerClass(BigramReducer.class);
		
		job.setMapOutputKeyClass(TextPair.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		return job.waitForCompletion(true)? 0:1;
		
	}
	
	public static void main(String[] args) throws Exception{
		int exitCode = ToolRunner.run(new BigramDriver(), args);
		System.exit(exitCode);
	}

}
