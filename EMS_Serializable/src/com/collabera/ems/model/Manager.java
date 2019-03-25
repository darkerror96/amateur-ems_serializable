/**
 * 
 */
package com.collabera.ems.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rutpatel
 *
 */
public class Manager extends Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, Employee> team = new HashMap<Integer, Employee>();

	public Manager(String name, int age, Gender gender, long contactNo, Address address, int ssn, String email,
			String jobTitle, Department dept, double salary, int reportTo, boolean isManager) {
		super(name, age, gender, contactNo, address, ssn, email, jobTitle, dept, salary, reportTo, isManager);
	}

	public Map<Integer, Employee> getTeam() {
		return team;
	}

	public void setTeam(Map<Integer, Employee> team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "(*)ManagerId = " + getEmpId() + " <===> Name = " + getName() + ", Age = " + getAge() + ", Gender = "
				+ getGender() + ", ContactNo = " + getContactNo() + ",\n                       Address = "
				+ getAddress() + ",\n                       SSN = " + getSsn() + ", eMail = " + getEmail()
				+ ", Job Title = " + getJobTitle() + ", Department = " + getDept() + ", Salary = " + getSalary()
				+ ", reportTo = " + getReportTo() + ", isManager = " + isManager() + ",\n    " + team.size()
				+ " Employee/s <===>  Team = " + team;
	}
}
