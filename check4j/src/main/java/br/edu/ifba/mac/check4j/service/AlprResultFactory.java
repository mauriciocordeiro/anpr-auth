package br.edu.ifba.mac.check4j.service;

import java.io.File;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Value;

import br.edu.ifba.mac.check4j.model.AlprResult;
import br.edu.ifba.mac.check4j.model.AlprService;
import br.edu.ifba.mac.check4j.model.CatEyeResult;
import br.edu.ifba.mac.check4j.model.Result;
import br.edu.ifba.mac.check4j.model.VrpdrResult;

public class AlprResultFactory {

	@Value("${check4j.service}")
	private static String service;

    private AlprResultFactory() { }

    public static Result build(File file) throws UnirestException {

        if(AlprService.OPENALPR.id.equals(service)) {
			return openAlpr(file);
		} else if(AlprService.CATEYE.id.equals(service)) {
            return cateye(file);
		} else if(AlprService.VRPDR.id.equals(service)) {
            return vrpdr(file);
		} else {
            return null;
        }

    }
	
	private static Result openAlpr(File file) throws UnirestException {		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.post("http://alpr4j:8080/v1/find")
				.field("image", file)
				.asString();	
		
		return new AlprResult(response.getBody());
	}

	private static Result vrpdr(File file) throws UnirestException {		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.post("http://vrpdr:5000")
				.field("file", file)
				.asString();	
		
		return new VrpdrResult(response.getBody());
	}

	private static Result cateye(File file) throws UnirestException {		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.post("http://cateye:5000/alpr")
				.field("file", file)
				.asString();	
		
		return new CatEyeResult(response.getBody());
	}


}
