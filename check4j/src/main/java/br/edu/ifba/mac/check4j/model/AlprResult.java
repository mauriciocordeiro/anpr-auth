package br.edu.ifba.mac.check4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlprResult implements Serializable {
	private static final long serialVersionUID = -8749711486654791142L;
	
	Logger logger = LoggerFactory.getLogger(AlprResult.class);
	
	private Integer imgWidth;
	private Integer imgHeight;
	private Long processingTimeMillis;
	
	private List<Plate> results;
	
	public AlprResult(String src) {
		super();
		
		JSONObject json = new JSONObject(src);
		
		logger.info(src);
		
		this.imgWidth = json.getInt("imgWidth");
		this.imgHeight = json.getInt("imgHeight");
		this.processingTimeMillis = json.getLong("processingTimeMillis");
		this.results = new ArrayList<Plate>();
		
		if(json.getJSONArray("results").length() > 0) {
			json.getJSONArray("results")
			.forEach(r -> {
				JSONObject candidate = (JSONObject) r;
				results.add(
						new Plate(
							candidate.getString("plate").replaceAll("\n", ""), 
							candidate.getDouble("confidence"),
							candidate.getString("pattern"),
							candidate.getBoolean("matchesPattern")));
			});
		}
	}

	public AlprResult(Integer imgWidth, Integer imgHeight, Long processingTimeMillis, List<Plate> results) {
		super();
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.processingTimeMillis = processingTimeMillis;
		this.results = results;
	}

	public AlprResult() {
		super();
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	public Long getProcessingTimeMillis() {
		return processingTimeMillis;
	}

	public void setProcessingTimeMillis(Long processingTimeMillis) {
		this.processingTimeMillis = processingTimeMillis;
	}

	public List<Plate> getResults() {
		return results;
	}

	public void setResults(List<Plate> results) {
		this.results = results;
	}
	
	public Boolean contains(String plate) {
		Boolean contains = false;
		
		for(Plate result : getResults()) {
			if(result.getPlate().equals(plate)) {
				contains = true;
				break;
			}
		}
		
		return contains;
	}

}
