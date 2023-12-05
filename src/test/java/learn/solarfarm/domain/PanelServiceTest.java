package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.data.PanelRepositoryDouble;
import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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

}
