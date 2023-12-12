package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/panels-seed.txt";
    static final String TEST_FILE_PATH = "./data/panels-test.txt";

    PanelFileRepository repository = new PanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

    }

    @Test
    void findAll() throws DataAccessException {
        List<Panel> actual = repository.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    void shouldFindBySection() throws DataAccessException {
        List<Panel> actual = repository.findBySection("Main Road");
        assertEquals(1, actual.size());

        assertEquals(2, actual.get(0).getPanelId());
    }

    @Test
    void shouldNotFindByMissingSection() throws DataAccessException {
        List<Panel> actual = repository.findBySection("Side Road");
        assertEquals(0, actual.size());

    }

    @Test
    void Add() throws DataAccessException {
        Panel panel = new Panel();
        panel.setSection("Swamp");
        panel.setRow(4);
        panel.setColumn(4);
        panel.setInstallationYear(Year.of(1999));
        panel.setMaterialType(MaterialType.CD_TE);
        panel.setIsTracking(false);

        Panel actual = repository.add(panel);
        assertEquals(4, panel.getPanelId());

        List<Panel> panels = repository.findAll();
        assertEquals(4, panels.size());

        actual = panels.get(3);
        assertEquals("Swamp", actual.getSection());
        assertEquals(4, actual.getColumn());
        assertEquals(4, actual.getRow());
        assertEquals(Year.of(1999), actual.getInstallationYear());
        assertEquals(MaterialType.CD_TE, actual.getMaterialType());
        assertFalse(actual.getIsTracking());
    }

    @Test
    void shouldUpdateExistingPanel() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setSection("Dorm");
        panel.setRow(66);
        panel.setColumn(44);
        panel.setInstallationYear(Year.of(1900));
        panel.setMaterialType(MaterialType.ASI);
        panel.setIsTracking(false);

        boolean actual = repository.update(panel);

        assertTrue(actual);
        assertEquals("Dorm", panel.getSection());
        assertEquals(66, panel.getRow());
        assertEquals(Year.of(1900), panel.getInstallationYear());
        assertEquals(MaterialType.ASI, panel.getMaterialType());
        assertFalse(panel.getIsTracking());

    }

    @Test
    void shouldNotUpdateMissingPanelId() throws DataAccessException {
        Panel panel = new Panel();
        panel.setPanelId(100);
        panel.setSection("Farm");

        boolean actual = repository.update(panel);
        assertFalse(actual);
    }

}