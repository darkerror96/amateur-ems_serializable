/**
 * 
 */
package com.collabera.ems.model;

import java.io.Serializable;

/**
 * @author rutpatel
 *
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Gender gender;
	private long contactNo;
	private Address address;

	public Person() {

	}

	public Person(String name, int age, Gender gender, long contactNo, Address address) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.contactNo = contactNo;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender + ", contactNo=" + contactNo
				+ ", address=" + address + "]";
	}
}
