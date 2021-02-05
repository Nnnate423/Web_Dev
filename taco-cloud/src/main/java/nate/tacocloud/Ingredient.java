package nate.tacocloud;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String Name;
    private final String ID;
    private final Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
