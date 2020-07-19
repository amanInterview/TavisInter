package com.aman.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BreverageTest {

	@Test
	public void testCorrectBreverageOrdered() {
		Breverage breverage = Breverage.placeOrder("chai,-sugar,+milk,-sugar,+sugar");
		assertEquals(MyMenu.CHAI, breverage);
	}
	
	@Test
	public void testCorrectBreverageOrdered_NoIngredients() {
		Breverage breverage = Breverage.placeOrder("chai");
		assertEquals(MyMenu.CHAI, breverage);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBreverageOrderWithNullException() {
		Breverage breverage = Breverage.placeOrder(null);
	}
	
	@Test
	public void testCorrectBreverageOrdered_caseInsensitive() {
		Breverage breverage = Breverage.placeOrder("cHaI,-sugar,+milk,-sugar,+sugar");
		breverage.getName();
		assertEquals(MyMenu.CHAI, breverage);
	}
	
	@Test
	public void testCorrectBreverageOrdered_IngredcaseInsensitive() {
		Breverage breverage = Breverage.placeOrder("cHaI,-sUgar,-suGar,-sugaR,+sugar");
		assertEquals(MyMenu.CHAI, breverage);
	}

}
