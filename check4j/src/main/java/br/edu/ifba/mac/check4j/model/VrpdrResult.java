package br.edu.ifba.mac.check4j.model;

import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VrpdrResult extends Result {
	private static final Logger logger = LoggerFactory.getLogger(VrpdrResult.class);

    public VrpdrResult(String src) {
		super();
		
		JSONObject json = new JSONObject(src);
		
		logger.info(src);
		
		setImgWidth(0);
		setImgHeight(0);
		setProcessingTimeMillis(0L);
		setResults(new ArrayList<>());
		
		if(json.getJSONArray("detections").length() > 0) {
			json.getJSONArray("detections")
			.forEach(r -> {
				JSONObject candidate = (JSONObject) r;
				getResults().add(
						new Plate(
							candidate.getString("ocr_pred").replaceAll("\n", "").replaceAll("-", ""), 
							candidate.getDouble("bb_confidence"),
							null, true));
			});
		}
	}

	public VrpdrResult() {
		super();
	}
    
}
