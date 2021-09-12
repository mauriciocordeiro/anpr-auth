package br.edu.ifba.mac.check4j.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.edu.ifba.mac.check4j.model.AlprResult;
import br.edu.ifba.mac.check4j.model.Result;
import br.edu.ifba.mac.check4j.model.Vehicle;
import br.edu.ifba.mac.check4j.model.VehicleList;
import br.edu.ifba.mac.check4j.model.VrpdrResult;

@Service
public class CheckService {

	private static final String VRPDR = "vrpdr";
	
	@Value("${check4j.workdir}")
	private String workdir;

	@Value("${check4j.service}")
	private String service;
	
	public Vehicle check(byte[] file, String filename) throws IOException, UnirestException {	
		String filepath = this.workdir+filename;
		Path path = save(file, filepath);
		
		Result alpr = VRPDR.equals(service) ? vrpdr(path.toFile()) : openAlpr(path.toFile());	

		delete(path);
		
		VehicleList _vehicles = alpr.getResults().isEmpty() ? new VehicleList() : getVehicle(alpr.getResults().get(0).getPlate());		
		
		return _vehicles.isEmpty() ? new Vehicle() : _vehicles.get(0);
	}
	
	private Result openAlpr(File file) throws UnirestException {
		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.post("http://alpr4j:8080/v1/find")
				.field("image", file)
				.asString();	
		
		return new AlprResult(response.getBody());
	}

	private Result vrpdr(File file) throws UnirestException {
		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.post("http://vrpdr:5000")
				.field("file", file)
				.asString();	
		
		return new VrpdrResult(response.getBody());
	}
	
	private VehicleList getVehicle(String plate) throws UnirestException {
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest
				.get("http://vehiclespy:5001/vehicles?plate="+plate)
				.asString();
		
		return new VehicleList(response.getBody());
	}
	
	private Path save(byte[] file, String name) throws IOException {
		Path path = Paths.get(name);
        return Files.write(path, file);
	}
	
	private boolean delete(Path path) {
		try {
			return path.toFile().delete();
		} catch (Exception e) {
			return false;
		}
	}

}
