package br.org.mcord.alpr4j.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.org.mcord.alpr4j.config.StreamGobbler;
import br.org.mcord.alpr4j.model.AlprResult;
import br.org.mcord.alpr4j.model.Plate;

@Service
public class AlprService {
	
	@Value("${br.org.mcord.alpr4j.country}")
	private String country;	
	@Value("${br.org.mcord.alpr4j.pattern}")
	private String pattern;
	@Value("${br.org.mcord.alpr4j.topN}")
	private int topN;
	@Value("${br.org.mcord.alpr4j.useDefault}")
	private boolean useDefault;
	@Value("${br.org.mcord.alpr4j.onlyPatternMatches}")
	private boolean onlyPatternMatches;
	@Value("${br.org.mcord.alpr4j.wsl}")
	private boolean wsl;
	@Value("${br.org.mcord.alpr4j.workdir}")
	private String workdir;
	
	public AlprResult recognize(byte[] file, String filename) throws IOException, InterruptedException {
		
		String filepath = this.workdir+filename;
		
		AlprResult result = new AlprResult();
		
		Path path = save(file, filepath);
		
		if(this.useDefault) {
			result = new AlprResult(runAlpr(filepath));
		} else {
			result = merge(this.onlyPatternMatches, 
					new AlprResult(runAlpr("br", "br", 5, filepath)),
					new AlprResult(runAlpr("br", "mercosul", 5, filepath)),
					
					new AlprResult(runAlpr("br2", "br", 5, filepath)),
					new AlprResult(runAlpr("br2", "mercosul", 5, filepath)));
		}
		
		delete(path);
				
		return result;
	}
	
	private Path save(byte[] file, String name) throws IOException {
		Path path = Paths.get(name);
        return Files.write(path, file);
	}
	
	private String runAlpr(String filename) throws IOException, InterruptedException {
		return runAlpr(this.country, this.pattern, this.topN, filename);
	}
	
	private String runAlpr(String country, String pattern, int topN, String filename) throws IOException, InterruptedException {
		
		StringBuilder result = new StringBuilder();
		
		ProcessBuilder builder = new ProcessBuilder();
		if(wsl)
			builder.command("wsl", "alpr", "-c", country, "-p", pattern, "-n", Integer.toString(topN), "-j", filename);
		else
			builder.command("alpr", "-c", country, "-p", pattern, "-n", Integer.toString(topN), "-j", filename);
		builder.directory(new File(System.getProperty("user.dir")));
		
		Process process = builder.start();
		
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), result);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;

		return result.toString();
	}
	
	private boolean delete(Path path) {
		try {
			return path.toFile().delete();
		} catch (Exception e) {
			return false;
		}
	}
	
	private AlprResult merge(Boolean hasMatch, AlprResult... array) {
		AlprResult result = new AlprResult(
				array[0].getImgWidth(), 
				array[0].getImgHeight(), 
				array[0].getProcessingTimeMillis(), 
				null);
		
		result.setResults(new ArrayList<Plate>());
		for (AlprResult alprResult : array) {
			alprResult.getResults().forEach(p -> {
				if(hasMatch) {
					if(p.getMatchesPattern() && !result.contains(p.getPlate()))
						result.getResults().add(p);
				} else {
					if(!result.contains(p.getPlate()))
						result.getResults().add(p);
				}
			});
		}
		
		return result;
	}

}
