package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.data.PanelRepositoryDouble;
import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PanelServiceTest {

    @MockBean
    PanelRepository repository;

    @Autowired
    PanelWebService service;

//    @BeforeEach
//    void setup() {
//        PanelRepositoryDouble repository = new PanelRepositoryDouble();
//        service = new PanelWebService(repository);
//    }

    @Test
    void shouldAdd() throws DataAccessException {
        Panel panelIn = new Panel(0, "Rocks", 2, 2, MaterialType.CD_TE, Year.of(1999), true);
        Panel panelOut = new Panel(1, "Rocks", 2, 2, MaterialType.CD_TE, Year.of(1999), true);

        when(repository.add(panelIn)).thenReturn(panelOut);

        Result<Panel> result = service.add(panelIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(panelOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() throws DataAccessException {
         Result<Panel> result = service.add(null);
         assertEquals(ResultType.INVALID, result.getType());
         assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddEmptySection() throws DataAccessException {
        Panel panelIn = new Panel(0, "", 2, 2, MaterialType.CD_TE, Year.of(1999), true);
        Result<Panel> result = service.add(panelIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddZeroRow() throws DataAccessException {
        Panel panelIn = new Panel(0, "Rocks", 0, 2, MaterialType.CD_TE, Year.of(1999), true);
        Result<Panel> result = service.add(panelIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddBeyondMaxColumn() throws DataAccessException {
        Panel panelIn = new Panel(0, "Rocks", 1, 251, MaterialType.CD_TE, Year.of(1999), true);
        Result<Panel> result = service.add(panelIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullMaterialType() throws DataAccessException {
        Panel panelIn = new Panel(0, "Rocks", 1, 250, null, Year.of(1999), true);
        Result<Panel> result = service.add(panelIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddFutureYear() throws DataAccessException {
        Panel panelIn = new Panel(0, "Rocks", 1, 250, MaterialType.CD_TE, Year.of(2026), true);
        Result<Panel> result = service.add(panelIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Panel panelOut = new Panel(1, "Yard", 5, 1, MaterialType.MONO_SI, Year.of(2022), true);

        when(repository.update(panelOut)).thenReturn(true);

        Result<Panel> result = service.update(panelOut);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(panelOut, result.getPayload());

    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        Panel panelNotPresent = new Panel(100, "Yard", 5, 1,
                MaterialType.MONO_SI, Year.of(2022), true);

        when(repository.update(panelNotPresent)).thenReturn(false);

        Result<Panel> result = service.update(panelNotPresent);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotUpdateEmptyPanel() throws DataAccessException {
        Panel panelEmpty = new Panel();

        when(repository.update(panelEmpty)).thenReturn(false);

        Result<Panel> result = service.update(panelEmpty);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldDelete() throws DataAccessException {
        when(repository.delete(2)).thenReturn(true);
        assertTrue(service.delete(2));
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        when(repository.delete(200)).thenReturn(false);
        assertFalse(service.delete(200));

    }




    //
//    @Test
//    void shouldFindOneBySection1() throws DataAccessException {
//        List<Panel> actual = service.findBySection("Section 1");
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldNotFindByMissingSection() throws DataAccessException {
//        List<Panel> actual = service.findBySection("None");
//        assertEquals(0, actual.size());
//    }
//
//    @Test
//    void shouldNotAddNullRow() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Ditch");
//        panel.setColumn(3);
//        panel.setInstallationYear(Year.of(2020));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("row"));
//    }
//
//    @Test
//    void shouldNotAddNullColumn() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Ditch");
//        panel.setRow(3);
//        panel.setInstallationYear(Year.of(2020));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("column"));
//    }
//
//    @Test
//    void shouldNotAddBlankSection() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("");
//        panel.setRow(3);
//        panel.setColumn(3);
//        panel.setInstallationYear(Year.of(2020));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("section"));
//    }
//
//    @Test
//    void shouldNotAddFutureInstillationYear() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Swamp");
//        panel.setRow(3);
//        panel.setColumn(3);
//        panel.setInstallationYear(Year.of(2024));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("year"));
//    }
//
//    @Test
//    void shouldNotAddNonListedMaterial() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Swamp");
//        panel.setRow(3);
//        panel.setColumn(3);
//        panel.setInstallationYear(Year.of(2020));
//        panel.setMaterialType(null);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("material"));
//    }
//
//    @Test
//    void shouldNotAddDuplicate() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Section 1");
//        panel.setRow(1);
//        panel.setColumn(5);
//        panel.setInstallationYear(Year.of(2020));
//        panel.setMaterialType(MaterialType.ASI);
//        panel.setIsTracking(true);
//
//        PanelResult result = service.add(panel);
//
//        assertFalse(result.isSuccess());
//    }
//
//
//    @Test
//    void shouldUpdate() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setSection("Swamp");
//        panel.setRow(100);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2015));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.update(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Swamp", panel.getSection());
//        assertEquals(100, panel.getRow());
//        assertEquals(50, panel.getColumn());
//        assertEquals(Year.of(2015), panel.getInstallationYear());
//        assertEquals(MaterialType.CD_TE, panel.getMaterialType());
//        assertFalse(panel.getIsTracking());
//    }
//
//    @Test
//    void shouldNotUpdateMissingSection() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(1);
//        panel.setSection(null);
//        panel.setRow(10);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2015));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.update(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("section"));
//    }
//    @Test
//    void shouldNotUpdateRow251() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(1);
//        panel.setSection("Section");
//        panel.setRow(251);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2015));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.update(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("row"));
//    }
//
//    @Test
//    void shouldNotUpdateColumn251() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(1);
//        panel.setSection("Section");
//        panel.setRow(25);
//        panel.setColumn(251);
//        panel.setInstallationYear(Year.of(2015));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.update(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("column"));
//    }
//
//    @Test
//    void shouldNotUpdateFutureInstillationYear() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(1);
//        panel.setSection("Section");
//        panel.setRow(25);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2025));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.update(panel);
//
//        assertFalse(result.isSuccess());
//        assertEquals(1, result.getErrorMessages().size());
//        assertTrue(result.getErrorMessages().get(0).contains("past"));
//    }
//
//    @Test
//    void shouldDeleteId1() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(1);
//        panel.setSection("Section");
//        panel.setRow(25);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2022));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.delete(1);
//
//        assertTrue(result.isSuccess());
//    }
//
//    @Test
//    void shouldNotDeleteId100() throws DataAccessException {
//        Panel panel = new Panel();
//        panel.setPanelId(100);
//        panel.setSection("Section");
//        panel.setRow(25);
//        panel.setColumn(50);
//        panel.setInstallationYear(Year.of(2022));
//        panel.setMaterialType(MaterialType.CD_TE);
//        panel.setIsTracking(false);
//
//        PanelResult result = service.delete(100);
//
//        assertFalse(result.isSuccess());
//    }
}
