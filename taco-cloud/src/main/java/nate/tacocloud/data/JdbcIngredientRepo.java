package nate.tacocloud.data;

import nate.tacocloud.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepo implements IngredientRepo {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepo(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("SELECT ID, NAME, TYPE FROM INGREDIENT", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("SELECT ID,NAME, TYPE FROM INGREDIENT WHERE ID=?", this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("INSERT INTO INGREDIENT (ID, NAME, TYPE) VALUES (?,?,?)",ingredient.getId(),ingredient.getName(),ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
            throws SQLException {
//        System.out.println(rs.getString("type"));
//        System.out.println(rs.getString("type"));
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
