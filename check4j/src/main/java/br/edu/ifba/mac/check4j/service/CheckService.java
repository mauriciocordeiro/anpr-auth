package br.edu.ifba.mac.check4j.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.edu.ifba.mac.check4j.model.Result;
import br.edu.ifba.mac.check4j.model.Vehicle;
import br.edu.ifba.mac.check4j.model.VehicleList;

@Service
public class CheckService {
	
	@Value("${check4j.workdir}")
	private String workdir;
	
	public Vehicle check(byte[] file, String filename) throws IOException, UnirestException {	
		String filepath = this.workdir+filename;
		Path path = save(file, filepath);
		
		Result alpr = AlprResultFactory.build(path.toFile());

		delete(path);
		
		VehicleList vehicles = alpr==null || alpr.getResults().isEmpty() ? new VehicleList() : getVehicle(alpr.getResults().get(0).getPlate());		
		
		return vehicles.isEmpty() ? new Vehicle() : vehicles.get(0);
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
