package com.aman.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MyMenu implements Breverage {

    COFFEE("coffee",5.2) {
       @Override
       public Breverage validateOrder() {
           // 1. toRemovemap ele - toadd ele > 1 ... otherwise expection. i.e; keep adding ingred, we dont mind , but can't do reverse.
           // 2. some items can never be removed.. (milk || water)
           final List<Ingredients> mandatoryIngredients = Arrays.asList(Ingredients.MILK, Ingredients.WATER);
           this.validation(mandatoryIngredients);
           checkIngredients();
           return this;
       }
   },
   CHAI("chai", 5) {
       @Override
       public Breverage validateOrder() {
           final List<Ingredients> mandatoryIngredients = Arrays.asList(Ingredients.MILK, Ingredients.WATER);
           this.validation(mandatoryIngredients);
           checkIngredients();
           return this;
           
       }
   },
   BANANA_SMOOTHIE("bananaSmoothie", 5) {
       @Override
       public Breverage validateOrder() {
           // final List<Ingredients> mandatoryIngredients = List.of(Ingredients.MILK, Ingredients.WATER);
    	   final List<Ingredients> mandatoryIngredients = Arrays.asList(Ingredients.MILK, Ingredients.WATER);
           this.validation(mandatoryIngredients);
           checkIngredients();
           return this;
       }
   },
   STRAWBERRY_SHAKE("strawberryShake", 5) {
       @Override
       public Breverage validateOrder() {
           final List<Ingredients> mandatoryIngredients = Arrays.asList(Ingredients.SODA, Ingredients.WATER);
           this.validation(mandatoryIngredients);
           checkIngredients();
           return this;
       }
   },
   MOJITO("mojito", 5) {
       @Override
       public Breverage validateOrder() {
           final List<Ingredients> mandatoryIngredients = Arrays.asList(Ingredients.MILK, Ingredients.WATER);
           this.validation(mandatoryIngredients);
           checkIngredients();
           return this;
       }
   };

   static {
       menuResolver = Collections.unmodifiableMap(
               Stream.of(MyMenu.values()).collect(Collectors.toMap(e -> e.getName().toUpperCase(), Function.identity())));
   }

   private final String name;
   private final double cost;
   // private final List<Ingredients> toAdd = new ArrayList<>();
   private final List<Ingredients> mandatoryMissing = new ArrayList<>();
   private Map<Ingredients, int[]> ingredientsDetails = new HashMap<>();
   private List<Ingredients> invalidQunatityIngredients = new ArrayList<>();
   public static final Map<String, Breverage> menuResolver;

   MyMenu(final String name, final double cost) {
       this.name = name;
       this.cost = cost;
   }

   // @Override
   // public double getPrice() {
   // final double addedPrice = toAdd.stream().mapToDouble(ingredient ->
   // ingredient.getPrice()).sum();
   // final double reducedPrice = toRemove.stream().mapToDouble(ingredient ->
   // ingredient.getPrice()).sum();
   // return getCost() + addedPrice - reducedPrice;
   // }

   @Override
   public double getPrice() throws Exception {
	   if(Objects.isNull(ingredientsDetails.get(Ingredients.DUMMY))) {
		   // Not creating a custom exception here. Time constraint.
		   throw new Exception("Please call updateIngredients2 followed by validate method before getting price.");
	   }
	   if(ingredientsDetails.get(Ingredients.DUMMY)[0] != 1) {throw new Exception("Please call  validate method before getting price.");}
	   ingredientsDetails.remove(Ingredients.DUMMY);
       final double price = ingredientsDetails.entrySet().stream()
               .mapToDouble(ing -> (ing.getValue()[0] - ing.getValue()[1]) * ing.getKey().getPrice()).sum();
       return getCost() + price;
   }

   // public void updateIngredients(final List<Ingredients> toAdd, final List<Ingredients> toRemove) {
   //     final Map<Ingredients, Long> toAddMap = toAdd.stream()
   //             .collect(Collectors.groupingBy(ele -> ele, Collectors.counting()));
   //     final Map<Ingredients, Long> toremoveMap = toRemove.stream()
   //             .collect(Collectors.groupingBy(ele -> ele, Collectors.counting()));
   //     this.validateOrder(toAddMap, toremoveMap);
   //     this.toAdd.addAll(toAdd);
   //     this.toRemove.addAll(toRemove);

   // }

   // Exception Handling can be improved. Not refactoring now. Time constarint.
   public void updateIngredients2(final Map<Ingredients, int[]> ingredientDetailMap) {
	   ingredientDetailMap.put(Ingredients.DUMMY, new int[] {0,0});
       this.ingredientsDetails = ingredientDetailMap;
   }

	protected void checkIngredients() {
		// throw exception
		final String msg = invalidQunatityIngredients.stream().map(e -> e.getName()).collect(Collectors.joining(","));
		final String mandatoryItems = mandatoryMissing.stream().map(e -> e.getName()).collect(Collectors.joining(","));

		if (!msg.isEmpty())
			throw new UnsupportedOperationException("Please correct the quantity of these Items. " + msg);
		if (!mandatoryItems.isEmpty())
			throw new UnsupportedOperationException(
					"Please correct the quantity of these mandatory Items. " + mandatoryItems);
	}

   public String getName() {
       return name;
   }

   public double getCost() {
       return cost;
   }

   // 
   protected boolean validation(final List<Ingredients> mandatoryIngredients) {
       final Map<Ingredients, Integer> collect = ingredientsDetails.entrySet().stream()
               .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()[0] - e.getValue()[1]));
       
       List<Ingredients> quantityCheckIfMandatoryItemsPresent = new ArrayList<>();
       if(collect.keySet().containsAll(mandatoryIngredients)) {
        quantityCheckIfMandatoryItemsPresent = collect.entrySet().stream()
               .filter(entry -> mandatoryIngredients.contains(entry.getKey()) && entry.getValue() < 0)
               .map(entry -> entry.getKey())
               .collect(Collectors.toList());
       }

       final List<Ingredients> invalidQuantityIngredList = collect.entrySet().stream()
               .filter(entry -> entry.getValue() < -1).map(e -> e.getKey()).collect(Collectors.toList());
       
       //hack
       ingredientsDetails.put(Ingredients.DUMMY, new int[] {1,1});
       if ((Objects.isNull(invalidQuantityIngredList) || invalidQuantityIngredList.isEmpty()) && quantityCheckIfMandatoryItemsPresent.isEmpty()){
           return true;
       } else {
           invalidQunatityIngredients.addAll(invalidQuantityIngredList);
           mandatoryMissing.addAll(quantityCheckIfMandatoryItemsPresent);
           return false;
       }
   }
   
}
