package com.aman.interview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MyMenuTest {

	@Test(expected=Exception.class)
	public void testGetPrice_CallOrderException() throws Exception {
		MyMenu.CHAI.getPrice();
	}
	
	@Test(expected=Exception.class)
	public void testGetPrice_withoutIngredients_callOrderException() throws Exception {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.DUMMY, new int[] {0,0});
		// MyMenu.CHAI.ingredientsDetails = ap;
		MyMenu.CHAI.updateIngredients2(ap);
		assertEquals(5, MyMenu.CHAI.getPrice(), 0.0);
	}
	
	@Test
	public void testGetPrice_withoutIngredients() throws Exception {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.DUMMY, new int[] {0,0});
		// MyMenu.CHAI.ingredientsDetails = ap;
		MyMenu.CHAI.updateIngredients2(ap);
		MyMenu.CHAI.validateOrder();
		assertEquals(5, MyMenu.CHAI.getPrice(), 0.0);
	}

	
	@Test(expected=Exception.class)
	public void testGetPrice_withIngredients_callOrderExeption() throws Exception {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.SUGAR, new int[] {1,0});
		ap.put(Ingredients.MILK, new int[] {0,1});
		MyMenu.CHAI.updateIngredients2(ap);
		assertEquals(4.5, MyMenu.CHAI.getPrice(), 0.0);
	}
	
	@Test
	public void testGetPrice_withIngredients() throws Exception {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.SUGAR, new int[] {1,0});
		ap.put(Ingredients.MILK, new int[] {0,1});
		MyMenu.CHAI.updateIngredients2(ap);
		MyMenu.CHAI.validateOrder();
		assertEquals(4.5, MyMenu.CHAI.getPrice(), 0.0);
	}
	
	//testGetPrice_withIngredients_QuantityMandatoryItemsException
	@Test(expected=Exception.class)
	public void testGetPrice_withIngredients_QuantityMandatoryItemsException() throws Exception {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.SUGAR, new int[] {1,0});
		ap.put(Ingredients.MILK, new int[] {0,2});
		MyMenu.CHAI.updateIngredients2(ap);
		MyMenu.CHAI.validateOrder();
		assertEquals(4.5, MyMenu.CHAI.getPrice(), 0.0);
	}
	
	

	@Test
	public void testGetName() {
		String name = MyMenu.STRAWBERRY_SHAKE.getName();
		assertEquals("strawberryShake", name);
	}

	@Test
	public void testGetCost() {
		assertEquals(5, MyMenu.CHAI.getCost(),0.0);
	}

	@Test
	public void testValidation() {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.SUGAR, new int[] {1,0});
		//ap.put(Ingredients.MILK, new int[] {0,2});
		MyMenu.CHAI.updateIngredients2(ap);
		assertTrue(MyMenu.CHAI.validation(Arrays.asList(Ingredients.SUGAR))) ;
	}
	
	@Test(expected=Exception.class)
	// can't order a chai of 5$ with five -sugar(1$) to reduce price to 0.
	public void testValidation_exception_CantHaveMultipleSubtractOfItem() {
		Map<Ingredients, int[]> ap = new HashMap<>();
		ap.put(Ingredients.SUGAR, new int[] {0,5});
		//ap.put(Ingredients.MILK, new int[] {0,2});
		MyMenu.CHAI.updateIngredients2(ap);
		MyMenu.CHAI.validateOrder();
		//assertTrue(MyMenu.CHAI.validation(Arrays.asList(Ingredients.SUGAR))) ;
	}

}
