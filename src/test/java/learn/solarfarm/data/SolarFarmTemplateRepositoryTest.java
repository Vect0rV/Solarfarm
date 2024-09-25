package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SolarFarmJdbcTemplateRepositoryTest {

        SolarFarmJdbcTemplateRepository repository;

        public SolarFarmJdbcTemplateRepositoryTest() {
            ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
            repository = context.getBean(SolarFarmJdbcTemplateRepository.class);
        }

        @BeforeAll
        static void oneTimeSetup() {
            ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
            JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
            jdbcTemplate.update("call set_known_good_state();");
        }

        @Test
        void shouldFindAll() {
            List<Panel> all = repository.findAll();

            assertNotNull(all);
            assertTrue(all.size() >= 2);

            Panel expected = new Panel();
            expected.setPanelId(1);
            expected.setSection("Trap");
            expected.setRow(2);
            expected.setColumn(2);
            expected.setMaterialType(MaterialType.valueOf("CIGS"));
            expected.setInstallationYear(Year.parse("2021"));
            expected.setIsTracking(false);

            assertTrue(all.contains(expected)
            && all.stream().anyMatch(i -> i.getPanelId() == 2));
        }

        @Test
        void findBySection() {
            List<Panel> farm = repository.findBySection("farm");

            assertNotNull(farm);
            assertFalse(farm.isEmpty());

            Panel expected = new Panel();
            expected.setPanelId(2);
            expected.setSection("Farm");
            expected.setRow(1);
            expected.setColumn(1);
            expected.setMaterialType(MaterialType.valueOf("ASI"));
            expected.setInstallationYear(Year.parse("2022"));
            expected.setIsTracking(true);

            assertTrue(farm.contains(expected)
            && farm.stream().anyMatch(i -> i.getPanelId() == 3));


        }

        @Test
        void shouldAdd() {
            Panel panel = new Panel();
            panel.setSection("Bog");
            panel.setRow(1);
            panel.setColumn(1);
            panel.setMaterialType(MaterialType.valueOf("ASI"));
            panel.setInstallationYear(Year.parse("2024"));
            panel.setIsTracking(true);

            Panel actual = repository.add(panel);
            panel.setPanelId(4);

            assertNotNull(actual);
            assertEquals(panel, actual);
        }

        @Test
        void shouldUpdate() {
            Panel panel = new Panel();
            panel.setPanelId(2);
            panel.setSection("Forest");

            assertTrue(repository.update(panel));
            assertEquals(panel, repository.findBySection("Forest"));
        }

        @Test
        void shouldNotUpdateMissing() {
            Panel panel = new Panel();
            panel.setPanelId(200000);
            panel.setSection("Ditch");
            panel.setRow(1);
            panel.setColumn(1);
            panel.setMaterialType(MaterialType.valueOf("ASI"));
            panel.setInstallationYear(Year.parse("2024"));
            panel.setIsTracking(true);

            assertFalse(repository.update(panel));
        }
        @Test
        void deleteDeleteExisting() {
            assertTrue(repository.delete(3));
        }

        @Test
        void shouldNotDeleteMissing() {
            assertFalse(repository.delete(40000));

        }
    }


