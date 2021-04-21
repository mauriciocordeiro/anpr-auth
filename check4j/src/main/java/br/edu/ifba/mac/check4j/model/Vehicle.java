package br.edu.ifba.mac.check4j.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = -1613419102939855921L;
	
	private String _id;
	private String plate;
	private String brand;
	private String model;
	private String owner;
	private String address;
	private String phone;
	private Boolean allowed;
	
	public Vehicle(String _id, String plate, String brand, String model, String owner, String address, String phone,
			Boolean allowed) {
		super();
		this._id = _id;
		this.plate = plate;
		this.brand = brand;
		this.model = model;
		this.owner = owner;
		this.address = address;
		this.phone = phone;
		this.allowed = allowed;
	}

	public Vehicle() {
		super();
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getAllowed() {
		return allowed;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}
	
	

}
