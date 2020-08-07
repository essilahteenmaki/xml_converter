package com.parser.dataparser.services;

public class Premise {
	
	private int id;
	private String address, city, m2, premisetype, premisedescription, imglink;

	public Premise() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public Premise(int id, String address, String city, String m2) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.m2 = m2;
	}




	public Premise(int id, String address, String city, String m2, String premisetype, String premisedescription,
			String imglink) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.m2 = m2;
		this.premisetype = premisetype;
		this.premisedescription = premisedescription;
		this.imglink = imglink;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getM2() {
		return m2;
	}

	public void setM2(String m2) {
		this.m2 = m2;
	}

	public String getPremisetype() {
		return premisetype;
	}

	public void setPremisetype(String premisetype) {
		this.premisetype = premisetype;
	}

	public String getPremisedescription() {
		return premisedescription;
	}

	public void setPremisedescription(String premisedescription) {
		this.premisedescription = premisedescription;
	}

	public String getImglink() {
		return imglink;
	}

	public void setImglink(String imglink) {
		this.imglink = imglink;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Premise [address=" + address + ", city=" + city + ", m2=" + m2 + ", premisetype=" + premisetype
				+ ", premisedescription=" + premisedescription + ", imglink=" + imglink + "]";
	}
	
	

}
