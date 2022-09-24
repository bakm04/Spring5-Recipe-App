package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.IngredientRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe id not found.  Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for ID: " + command.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst();


        if (!savedIngredientOptional.isPresent()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

    }

    @Override
    public void deleteById(Long recipeId, Long idToDelete) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.debug("Recipe not found, ID: " + recipeId);
            return;
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(idToDelete))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            log.debug("found Ingredient to delete");
            Ingredient ingredientToDelete = ingredientOptional.get();
            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientOptional.get());
            recipeRepository.save(recipe);
        } else {
            log.debug("Ingredient not found, ID: " + idToDelete);
        }
    }
}
