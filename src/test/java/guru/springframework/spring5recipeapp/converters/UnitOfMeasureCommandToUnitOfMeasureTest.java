package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final String DESCRIPTION = "description";
    public static final Long UOM_ID = 1L;

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //  Given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(UOM_ID);
        command.setDescription(DESCRIPTION);

        //  When
        UnitOfMeasure unitOfMeasure = converter.convert(command);

        //  Then
        assertNotNull(unitOfMeasure);
        assertEquals(UOM_ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }
}
