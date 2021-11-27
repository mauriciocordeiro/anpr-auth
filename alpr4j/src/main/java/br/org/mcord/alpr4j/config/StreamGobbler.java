package br.org.mcord.alpr4j.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler implements Runnable {

	private InputStream inputStream;
    private StringBuilder builder;
     
    public StreamGobbler(InputStream inputStream, StringBuilder builder) {
        this.inputStream = inputStream;
        this.builder = builder;
    }
 
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream))
        	.lines()
        		.forEach(line -> {
        			builder.append(line);
        		});
    }

}
