package com.dls.controller.Test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminControllerTest {
	
	@BeforeEach
	public void setup() {
		
		System.out.println("ABC");
		
	}
	@Test
	public void Test1() {
		System.out.println("Test1");
	}
	@AfterEach
	public void teardown() {
	System.out.println("BCGH");
	}
	
	public void Test2() {
		System.out.println("kilj");
	}
}
