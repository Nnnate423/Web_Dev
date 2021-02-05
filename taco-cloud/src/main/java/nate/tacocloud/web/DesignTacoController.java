package nate.tacocloud.web;

import nate.tacocloud.Ingredient;
import nate.tacocloud.Ingredient.Type;


import nate.tacocloud.Taco;
import nate.tacocloud.data.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Errors;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepo ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepo ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(x->ingredients.add(x));
//                Arrays.asList(
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );
        Type[] types = Type.values();
        for (Type t : types){
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients,t));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors){
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing Design: ", design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }
}
