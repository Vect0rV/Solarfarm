package learn.solarfarm.data;

import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
}