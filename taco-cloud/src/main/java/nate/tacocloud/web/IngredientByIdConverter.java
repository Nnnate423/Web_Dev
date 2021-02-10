package nate.tacocloud.web;

import nate.tacocloud.Ingredient;
import nate.tacocloud.data.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepo ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findOne(id);
    }

}
