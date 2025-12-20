package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.models.Panel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class PanelWebService {

    private final PanelRepository repository;

    public PanelWebService(PanelRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("permitAll()")
    public List<Panel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Panel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public Result<Panel> add (Panel panel) throws DataAccessException {
        Result<Panel> result = validate(panel);
        if (result.getType() != ResultType.SUCCESS) {
            return result;
        }

        Panel p = repository.add(panel);
        result.setPayload(p);
        return result;
    }

    public Result<Panel> update(Panel panel) throws DataAccessException {
        Result<Panel> result = new Result<>();

        if (!result.isSuccess()) {
            return result;
        }

        if (panel == null) {
            result.addMessage("Panel id is required for updating panel", ResultType.INVALID);
            return result;
        }
        if (panel.getPanelId() <= 0) {
            result.addMessage("Panel id cannot be 0", ResultType.INVALID);
            return result;
        }

        result = validate(panel);

        if (result.getType() == ResultType.INVALID) {
            return result;
        }

        if (result.getType() == ResultType.SUCCESS) {
            boolean success = repository.update(panel);
            if (success) {
                result.setType(ResultType.SUCCESS);
                result.setPayload(panel);
            } else {
                result.setType(ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public boolean delete(int panelId) throws DataAccessException {
        return repository.delete(panelId);
    }

    private Result<Panel> validate(Panel panel) {
        Result<Panel> result = new Result<>();

        if (panel == null) {
            result.addMessage("Panel cannot be null", ResultType.INVALID);
            return result;
        }

        if (panel.getSection() == null || panel.getSection().trim().length() == 0) {
            result.addMessage("section is required", ResultType.INVALID);
        }

        if (panel.getRow() <= 0 || panel.getRow() > 250) {
            System.out.println("Row must be between 1 and 250.");
            result.addMessage("Row is required and cannot be greater than 250", ResultType.INVALID);
        }

        if (panel.getColumn() <= 0 || panel.getColumn() > 250) {
            result.addMessage("Column is required and cannot be greater than 250", ResultType.INVALID);
        }

        if (panel.getMaterialType() == null) {
            result.addMessage("Material type is required", ResultType.INVALID);
        }

        if (panel.getInstallationYear() == null || Year.from(Year.now()).isBefore(panel.getInstallationYear())){
            result.addMessage("Instillation year is required and must be in the past", ResultType.INVALID);
        }

        return result;
    }

    public Panel findById(int id) throws DataAccessException {
        return repository.findById(id);
    }
}
