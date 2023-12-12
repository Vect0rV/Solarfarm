package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.data.PanelRepositoryDouble;
import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class PanelServiceTest {

    PanelService service;

    @BeforeEach
    void setup() {
        PanelRepositoryDouble repository = new PanelRepositoryDouble();
        service = new PanelService(repository);
    }

    @Test
    void shouldFindOneBySection1() throws DataAccessException {
        List<Panel> actual = service.findBySection("Section 1");
        assertEquals(1, actual.size());
    }

    @Test
    void shouldNotFindByMissingSection() throws DataAccessException {
        List<Panel> actual = service.findBySection("None");
        assertEquals(0, actual.size());
    }

    @Test
    void shouldNotAddNullRow() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Ditch");
        panel.setColumn(3);
        panel.setInstallationYear(Year.of(2020));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("row"));
    }

    @Test
    void shouldNotAddNullColumn() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Ditch");
        panel.setRow(3);
        panel.setInstallationYear(Year.of(2020));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("column"));
    }

    @Test
    void shouldNotAddBlankSection() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("");
        panel.setRow(3);
        panel.setColumn(3);
        panel.setInstallationYear(Year.of(2020));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("section"));
    }

    @Test
    void shouldNotAddFutureInstillationYear() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Swamp");
        panel.setRow(3);
        panel.setColumn(3);
        panel.setInstallationYear(Year.of(2024));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("year"));
    }

    @Test
    void shouldNotAddNonListedMaterial() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Swamp");
        panel.setRow(3);
        panel.setColumn(3);
        panel.setInstallationYear(Year.of(2020));
        panel.setMaterialType(null);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("material"));
    }

    @Test
    void shouldNotAddDuplicate() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Section 1");
        panel.setRow(1);
        panel.setColumn(5);
        panel.setInstallationYear(Year.of(2020));
        panel.setMaterialType(MaterialType.ASI);
        panel.setIsTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
    }


    @Test
    void shouldUpdate() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Swamp");
        panel.setRow(100);
        panel.setColumn(50);
        panel.setInstallationYear(Year.of(2015));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals("Swamp", panel.getSection());
        assertEquals(100, panel.getRow());
        assertEquals(50, panel.getColumn());
        assertEquals(Year.of(2015), panel.getInstallationYear());
        assertEquals(MaterialType.CD_TE, panel.getMaterialType());
        assertFalse(panel.getIsTracking());
    }

    @Test
    void shouldNotUpdateMissingSection() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setSection(null);
        panel.setRow(10);
        panel.setColumn(50);
        panel.setInstallationYear(Year.of(2015));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("section"));
    }
    @Test
    void shouldNotUpdateRow251() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setSection("Section");
        panel.setRow(251);
        panel.setColumn(50);
        panel.setInstallationYear(Year.of(2015));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("row"));
    }

    @Test
    void shouldNotUpdateColumn251() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setSection("Section");
        panel.setRow(25);
        panel.setColumn(251);
        panel.setInstallationYear(Year.of(2015));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("column"));
    }

    @Test
    void shouldNotUpdateFutureInstillationYear() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setSection("Section");
        panel.setRow(25);
        panel.setColumn(50);
        panel.setInstallationYear(Year.of(2025));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("past"));
    }
}
