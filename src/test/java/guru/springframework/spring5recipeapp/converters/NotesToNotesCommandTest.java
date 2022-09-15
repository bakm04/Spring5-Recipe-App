package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesToNotesCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";

    NotesToNotesCommand converter;

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() {
        //  Given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //  When
        NotesCommand command = converter.convert(notes);

        //  Then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(RECIPE_NOTES, command.getRecipeNotes());
    }
}
