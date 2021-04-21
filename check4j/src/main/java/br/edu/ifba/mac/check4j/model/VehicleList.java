package br.edu.ifba.mac.check4j.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class VehicleList extends ArrayList<Vehicle> {
	private static final long serialVersionUID = -4336412521491777728L;
	
	public VehicleList(String src) {
		super();
		JSONArray array = new JSONArray(src);
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = array.getJSONObject(i);
			
			this.add(new Vehicle(
					json.getString("_id"), 
					json.getString("plate"), 
					json.getString("brand"), 
					json.getString("model"), 
					json.getString("owner"), 
					json.getString("address"), 
					json.getString("phone"), 
					json.getBoolean("allowed")));
		}
	}
	
	public VehicleList() {
		super();
	}

}
