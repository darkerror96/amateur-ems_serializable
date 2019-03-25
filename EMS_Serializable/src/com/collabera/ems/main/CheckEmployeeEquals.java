/**
 * 
 */
package com.collabera.ems.main;

import java.util.Set;
import java.util.TreeSet;

import com.collabera.ems.model.Employee;

/**
 * @author rutpatel
 *
 */
public class CheckEmployeeEquals {

	public static void main(String[] args) {

		Employee emp1 = new Employee();
		emp1.setSsn(1);
		Employee emp2 = new Employee();
		emp2.setSsn(1);

		if (emp1.equals(emp2)) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}

		Employee emp3 = new Employee();
		emp3.setSsn(1);
		Employee emp4 = new Employee();
		emp4.setSsn(6);
		Employee emp5 = new Employee();
		emp5.setSsn(9);
		Employee emp6 = new Employee();
		emp6.setSsn(4);

		Set<Employee> empTree = new TreeSet<Employee>();
		empTree.add(emp3);
		empTree.add(emp4);
		empTree.add(emp5);
		empTree.add(emp6);

		for (Employee empTemp : empTree) {
			System.out.println(empTemp.getSsn());
		}
	}
}
