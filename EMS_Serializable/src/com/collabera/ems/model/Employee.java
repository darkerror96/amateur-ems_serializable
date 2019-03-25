/**
 * 
 */
package com.collabera.ems.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author rutpatel
 *
 */
public class Employee extends Person implements Serializable, Comparable<Employee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int count = getCount();
	private static final String EMSdb = "EMSdb.txt";

	private int empId;
	private int ssn;
	private String email;
	private String jobTitle;
	private Department dept;
	private double salary;
	private int reportTo;
	private boolean isManager;

	public Employee() {

	}

	public Employee(String name, int age, Gender gender, long contactNo, Address address, int ssn, String email,
			String jobTitle, Department dept, double salary, int reportTo, boolean isManager) {
		super(name, age, gender, contactNo, address);
		this.empId = count++;
		this.ssn = ssn;
		this.email = email;
		this.jobTitle = jobTitle;
		this.dept = dept;
		this.salary = salary;
		this.reportTo = reportTo;
		this.isManager = isManager;
	}

	@SuppressWarnings("unchecked")
	public static int getCount() {
		if (new File(EMSdb).exists()) {
			try (ObjectInputStream oisDb = new ObjectInputStream(new FileInputStream(new File(EMSdb)));) {
				HashMap<Integer, Employee> empDbTemp = (HashMap<Integer, Employee>) oisDb.readObject();
				int maxEmpId = 0;
				for (Employee empTemp : empDbTemp.values()) {
					if (empTemp.getEmpId() > maxEmpId) {
						maxEmpId = empTemp.getEmpId();
					}
				}
				return (maxEmpId + 1);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return 1;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getReportTo() {
		return reportTo;
	}

	public void setReportTo(int reportTo) {
		this.reportTo = reportTo;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Employee && this.getSsn() == ((Employee) obj).getSsn()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getSsn();
	}

	@Override
	public int compareTo(Employee o) {
		return (this.getName().length() - o.getName().length());
	}

	@Override
	public String toString() {
		return "(*)EmployeeId = " + empId + " <===> Name = " + getName() + ", Age = " + getAge() + ", Gender = "
				+ getGender() + ", ContactNo = " + getContactNo() + ",\n                        Address = "
				+ getAddress() + ",\n                        SSN = " + ssn + ", eMail = " + email + ", Job Title = "
				+ jobTitle + ", Department = " + dept + ", Salary = " + salary + ", reportTo = " + reportTo
				+ ", isManager = " + isManager;
	}
}