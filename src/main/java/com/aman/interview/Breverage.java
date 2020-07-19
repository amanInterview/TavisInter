package com.aman.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface Breverage {

    double getPrice() throws Exception;

    String getName();
    Breverage validateOrder(); // returning Breverage , just for chaining
    
    static Breverage placeOrder(final String orderItems) {
    	Objects.requireNonNull(orderItems, "order can not be null");
        final String[] detailedOrder = orderItems.split(",");
        String orderedBreverage = detailedOrder[0].toUpperCase();
        final Breverage breverage = MyMenu.menuResolver.get(orderedBreverage);
        
        if(Objects.isNull(breverage)) {
         throw new UnsupportedOperationException(orderedBreverage +" is not supported yet.");	
        }

        final List<String> listofIngred = new ArrayList<>(Arrays.asList(detailedOrder));
        listofIngred.remove(0);

        // arr[0] points to "+"
        // arr[1] points to "-"
        // this place need a refactoring. Try to extract out operators , this way if we try to add a new operator like '/' or '*'
        // this won't be a problem. It doesn't follow'O' of solid.
        // ENUM would suit this. But doing it right now.
        final Map<Ingredients, int[]> ingredQuantityDetails = new HashMap<>();
        for (final String ing : listofIngred) {
            if (ing.startsWith("+") || ing.startsWith("-")) {
                final String ingred = ing.substring(1).toUpperCase();
                final char operator = ing.charAt(0);
                final Ingredients ingredient = Ingredients.ingredientResolver.get(ingred);
                int[] quantityDetails = ingredQuantityDetails.get(ingredient);
                if (Objects.isNull(quantityDetails)) {
                    final int[] arr = { 0, 0 };
                    quantityDetails = arr;
                }
                if (operator == '+') {
                    quantityDetails[0] = quantityDetails[0] + 1;
                } else {
                    quantityDetails[1] = quantityDetails[1] + 1;
                }
                ingredQuantityDetails.put(ingredient, quantityDetails);
            }
        }

        ((MyMenu)breverage).updateIngredients2(ingredQuantityDetails);

        return breverage;
    };
    
}
