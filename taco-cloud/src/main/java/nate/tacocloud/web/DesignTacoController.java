package nate.tacocloud.web;

import nate.tacocloud.Ingredient;
import nate.tacocloud.Ingredient.Type;


import nate.tacocloud.Order;
import nate.tacocloud.Taco;
import nate.tacocloud.data.IngredientRepo;
import nate.tacocloud.data.TacoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepo ingredientRepo;
    private final TacoRepo designRepo;

    @Autowired
    public DesignTacoController(IngredientRepo ingredientRepo, TacoRepo designRepo){
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(x->ingredients.add(x));
        Type[] types = Type.values();
        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors,@ModelAttribute Order order){
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing Design: ", taco);
        Taco saved = designRepo.save(taco);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }
}
