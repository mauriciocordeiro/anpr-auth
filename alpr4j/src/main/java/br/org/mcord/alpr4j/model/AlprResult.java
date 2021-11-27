package br.org.mcord.alpr4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class AlprResult implements Serializable {
	private static final long serialVersionUID = -8749711486654791142L;
	
	private Integer imgWidth;
	private Integer imgHeight;
	private Long processingTimeMillis;
	
	private List<Plate> results;

	public AlprResult(Integer imgWidth, Integer imgHeight, Long processingTimeMillis, List<Plate> results) {
		super();
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.processingTimeMillis = processingTimeMillis;
		this.results = results;
	}
	
	public AlprResult(String src) {
		super();
		
		JSONObject json = new JSONObject(src);
		
		this.imgWidth = json.getInt("img_width");
		this.imgHeight = json.getInt("img_height");
		this.processingTimeMillis = json.getLong("processing_time_ms");
		this.results = new ArrayList<Plate>();
		
		if(json.getJSONArray("results").length() > 0) {
			json.getJSONArray("results")
			.getJSONObject(0)
			.getJSONArray("candidates")
			.forEach(r -> {
				JSONObject candidate = (JSONObject) r;
				results.add(
						new Plate(
							candidate.getString("plate").replaceAll("\n", ""), 
							candidate.getDouble("confidence"), 
							json.getJSONArray("results").getJSONObject(0).getString("region"),
							candidate.getInt("matches_template")==1));
			});
		}
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
