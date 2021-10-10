package br.edu.ifba.mac.check4j.model;

import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CatEyeResult extends Result {
	private static final Logger logger = LoggerFactory.getLogger(CatEyeResult.class);

    public CatEyeResult(String src) {
        super();

        JSONObject json = new JSONObject(src);

        logger.info(src);

		setImgWidth(0);
		setImgHeight(0);
		setProcessingTimeMillis(0L);
		setResults(new ArrayList<>());

        if(json.has("number")) {
            getResults().add(
                new Plate(json.getString("number"), 
                    json.getDouble("confidence"), 
                    null, true)
            );
        }

    }
    
}
