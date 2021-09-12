package br.edu.ifba.mac.check4j.model;

import java.io.Serializable;
import java.util.List;

public abstract class Result implements Serializable {
	private static final long serialVersionUID = -8749711486654791142L;

    private Integer imgWidth;
	private Integer imgHeight;
	private Long processingTimeMillis;
	
	private List<Plate> results;

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
