/**
 * 
 */
package com.collabera.ems.model;

import java.io.Serializable;

/**
 * @author rutpatel
 *
 */
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String streetAddress;
	private String city;
	private String state;
	private String country;
	private int zipCode;

	public Address(String streetAddress, String city, String state, String country, int zipCode) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return streetAddress + ", " + city + ", " + state + ", " + country + " [" + zipCode + "]";
	}
}