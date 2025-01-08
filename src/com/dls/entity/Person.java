package com.dls.entity;

public class Person {

	private String name;
	private long contact;

	public Person() {

	}

	public Person(String Name, long contact) {
		this.name = Name;
		this.contact = contact;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

}
