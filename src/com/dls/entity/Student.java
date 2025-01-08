package com.dls.entity;

public class Student extends Person implements Comparable<Object> {

	private int year;
	private int studentId;

	public Student() {
		super();
	}

	public Student(String name, long contact, int year, int studentId) {
		super(name, contact);
		this.year = year;
		this.studentId = studentId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Student)) {
			return false;
		}
		Student student = (Student) object;
		return this.studentId == student.studentId;
	}

	@Override
	public String toString() {
		return Integer.toString(studentId);
	}

	@Override
	public int compareTo(Object object) {
		Student student = (Student) object;
		return Integer.compare(studentId, student.getStudentId());
	}

}
