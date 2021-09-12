package br.edu.ifba.mac.check4j.model;

import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlprResult extends Result {	
	Logger logger = LoggerFactory.getLogger(AlprResult.class);
	
	public AlprResult(String src) {
		super();
		
		JSONObject json = new JSONObject(src);
		
		logger.info(src);
		
		setImgWidth(json.getInt("imgWidth"));
		setImgHeight(json.getInt("imgHeight"));
		setProcessingTimeMillis(json.getLong("processingTimeMillis"));
		setResults(new ArrayList<>());
		
		if(json.getJSONArray("results").length() > 0) {
			json.getJSONArray("results")
			.forEach(r -> {
				JSONObject candidate = (JSONObject) r;
				getResults().add(
						new Plate(
							candidate.getString("plate").replaceAll("\n", ""), 
							candidate.getDouble("confidence"),
							candidate.getString("pattern"),
							candidate.getBoolean("matchesPattern")));
			});
		}
	}

	public AlprResult() {
		super();
	}

}
