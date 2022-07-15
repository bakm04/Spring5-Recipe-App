package guru.springframework.spring5recipeapp.bootstrap;

import guru.springframework.spring5recipeapp.domain.*;
import guru.springframework.spring5recipeapp.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    RecipeRepository recipeRepository;
    IngredientRepository ingredientRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;
    NoteRepository noteRepository;
    CategoryRepository categoryRepository;


    public DataLoader(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository, NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading bootstrap data.");
        recipeRepository.saveAll(getRecipes());
    }

    public List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        Optional<UnitOfMeasure> teaspoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (teaspoonUomOptional.isEmpty()) {
            log.debug("Expected UOM not found");
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tablespoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (tablespoonUomOptional.isEmpty()) {
            log.debug("Expected Tablespoon UOM not found");
            throw new RuntimeException("Expected Tablespoon UOM not found");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (cupUomOptional.isEmpty()) {
            log.debug("Expected Cup UOM not found");
            throw new RuntimeException("Expected Cup UOM not found");
        }

        Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByDescription("Pinch");
        if (pinchUomOptional.isEmpty()) {
            log.debug("Expected Pinch UOM not found");
            throw new RuntimeException("Expected Pinch UOM not found");
        }

        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if (ounceUomOptional.isEmpty()) {
            log.debug("Expected Ounce UOM not found");
            throw new RuntimeException("Expected Ounce UOM not found");
        }

        Optional<UnitOfMeasure> wholeUomOptional = unitOfMeasureRepository.findByDescription("Whole");
        if (wholeUomOptional.isEmpty()) {
            log.debug("Expected Whole UOM not found");
            throw new RuntimeException("Expected Whole UOM not found");
        }

        Optional<UnitOfMeasure> bagUomOptional = unitOfMeasureRepository.findByDescription("Bag");
        if (bagUomOptional.isEmpty()) {
            log.debug("Expected Bag UOM not found");
            throw new RuntimeException("Expected Bag UOM not found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if (pintUomOptional.isEmpty()) {
            log.debug("Expected Pint UOM not found");
            throw new RuntimeException("Expected Pint UOM not found");
        }

        Optional<UnitOfMeasure> bunchUomOptional = unitOfMeasureRepository.findByDescription("Bunch");
        if (bunchUomOptional.isEmpty()) {
            log.debug("Expected Bunch UOM not found");
            throw new RuntimeException("Expected Bunch UOM not found");
        }

        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure pinchUom = pinchUomOptional.get();
//        UnitOfMeasure ounceUom = ounceUomOptional.get();
        UnitOfMeasure wholeUom = wholeUomOptional.get();
        UnitOfMeasure bagUom = bagUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure bunchUom = bunchUomOptional.get();


        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1) Cut Avocado. 2) Mash avocado 3) Add remaining ingredients to taste");
        guacRecipe.setPrepTime(10);
        guacRecipe.setServings(10);
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");


        Notes notes = new Notes();
        notes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. " + "" +
                "If no gloves are available, wash your hands thoroughly after handling, " +
                "and do not touch your eyes or the area near your eyes for several hours afterwards.");

        guacRecipe.setNotes(notes);
        guacRecipe.getCategories().add(categoryRepository.findByDescription("Mexican").get());

        guacRecipe.addIngredient(new Ingredient("Ripe Avocado", new BigDecimal(2), wholeUom));
        guacRecipe.addIngredient(new Ingredient("Kosher Salt", new BigDecimal(".25"), teaspoonUom));
        guacRecipe.addIngredient(new Ingredient("Lime or Lemon Juice", new BigDecimal(1), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("Minced Red Onion", new BigDecimal(3), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("Serrano (or Jalapeno) Chili's, stems and seeds removed", new BigDecimal(2), wholeUom));
        guacRecipe.addIngredient(new Ingredient("Cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("Freshly ground pepper", new BigDecimal(1), pinchUom));
        guacRecipe.addIngredient(new Ingredient("Ripe tomato, chopped (optional)", new BigDecimal(".50"), wholeUom));
        guacRecipe.addIngredient(new Ingredient("Red radish or jicama slices for garnish (optional)", new BigDecimal(1), wholeUom));
        guacRecipe.addIngredient(new Ingredient("Tortilla Chips", new BigDecimal(1), bagUom));

        recipes.add(guacRecipe);

        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setCookTime(15);
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("1) Prepare grill 2) Make the marinade and coat the chicken " +
                "3) Grill chicken 4) Warm tortillas 5) Assemble tacos");
        //tacosRecipe.setImage();
        tacosRecipe.setPrepTime(15);
        tacosRecipe.setServings(5);
        tacosRecipe.setSource("Simply Recipes");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        Notes notes1 = new Notes();
        notes1.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, " +
                "on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, " +
                "and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        tacosRecipe.setNotes(notes1);
        tacosRecipe.getCategories().add(categoryRepository.findByDescription("American").get());

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".50"), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Clove garlic, finely chopped", new BigDecimal(1), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Finely grated orange zest", new BigDecimal(1), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Fresh squeezed orange juice", new BigDecimal(3), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive oil", new BigDecimal(2), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Boneless chicken thighs", new BigDecimal(5), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Small corn tortillas", new BigDecimal(8), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Packed baby arugula (3oz)", new BigDecimal(3), cupUom));
        tacosRecipe.addIngredient(new Ingredient("Medium ripe avocados, sliced", new BigDecimal(2), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Radishes, thinly sliced", new BigDecimal(4), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("Red onion", new BigDecimal(".25"), wholeUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(1), bunchUom));
        tacosRecipe.addIngredient(new Ingredient("Sour cream thinned with 1/4 cup milk", new BigDecimal(".5"), cupUom));
        tacosRecipe.addIngredient(new Ingredient("Lime, cut into wedges", new BigDecimal(1), wholeUom));

        recipes.add(tacosRecipe);

        log.debug("Recipes initialized!");
        return recipes;
    }
}
