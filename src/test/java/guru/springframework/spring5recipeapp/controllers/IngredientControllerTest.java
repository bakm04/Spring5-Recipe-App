package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientSerivce;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new IngredientController(recipeService, ingredientSerivce, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //  Given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        //  When
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //  Then
        verify(recipeService, times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //  Given
        IngredientCommand command = new IngredientCommand();
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(new RecipeCommand());

        //  When
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //  Then
        verify(recipeService, times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //  Given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //  When
        when (ingredientSerivce.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //  Then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //  Given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //  When
        when(ingredientSerivce.saveIngredientCommand(any())).thenReturn(command);

        //  Then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //  Given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //  When
        when(ingredientSerivce.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //  Then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));
        verify(ingredientSerivce, times(1)).deleteById(anyLong(), anyLong());
    }
}
