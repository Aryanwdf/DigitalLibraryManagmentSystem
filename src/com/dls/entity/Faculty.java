package com.dls.entity;

public class Faculty extends Person implements Comparable<Object> {
	private String department;
	private int employeeId;

	public Faculty() {
		super();
	}

	public Faculty(String name, long contact, int employeeId, String department) {
		super(name, contact);
		this.employeeId = employeeId;
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Faculty)) {
			return false;
		}
		Faculty faculty = (Faculty) object;
		return this.employeeId == faculty.employeeId;
	}

	@Override
	public String toString() {
		return Integer.toString(employeeId);
	}

	@Override
	public int compareTo(Object object) {
		Faculty faculty = (Faculty) object;
		return Integer.compare(employeeId, faculty.getEmployeeId());
	}

}