package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final String DESCRIPTION = "description";
    public static final Long UOM_ID = 1L;

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //  Given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        // When
        UnitOfMeasureCommand command = converter.convert(unitOfMeasure);

        //  Then
        assertNotNull(command);
        assertEquals(UOM_ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}
