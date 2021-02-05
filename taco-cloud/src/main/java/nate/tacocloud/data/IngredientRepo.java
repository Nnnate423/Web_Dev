package nate.tacocloud.data;

import nate.tacocloud.Ingredient;

import java.util.Iterator;

public interface IngredientRepo {
    Iterable <Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
