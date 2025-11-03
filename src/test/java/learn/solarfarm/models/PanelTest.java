package learn.solarfarm.models;

import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanelTest {

    @Test
    void emptyPanelShouldFailValidation() {
        Panel panel = new Panel();


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Panel>> violations = validator.validate(panel);

        assertEquals(5, violations.size());
    }

    @Test
    void missingSectionShouldFailValidation() {
        Panel panel = makeValidPanel();
        panel.setSection("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Panel>> violations = validator.validate(panel);

        assertEquals(1, violations.size());

        ConstraintViolation<Panel> first = violations.stream().findFirst().orElse(null);
        assert first != null;
        assertEquals("Panel section is required", first.getMessage());
    }

    Panel makeValidPanel() {
        Panel panel = new Panel();
        panel.setSection("Test section");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setInstallationYear(Year.of(2024));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(true);

        return panel;
    }
}
