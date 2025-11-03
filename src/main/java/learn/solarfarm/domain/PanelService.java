package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.models.Panel;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class PanelService {

    private final PanelRepository repository;

    public PanelService(PanelRepository repository){
        this.repository = repository;
    }

    public List<Panel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public List<Panel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Panel findById(int id) throws DataAccessException {
        return repository.findById(id);
    }

    public PanelResult add(Panel panel) throws DataAccessException {

        PanelResult result = new PanelResult();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set< ConstraintViolation<Panel>> violations = validator.validate(panel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Panel> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        return result;
    }

    public Result update(Panel panel) throws DataAccessException {
//        PanelResult result = validate(panel);
        Result result = new Result();

        if(panel.getPanelId() <= 0) {
            result.addMessage("Panel ID is required", ResultType.INVALID);
        }

        if(result.isSuccess()) {
            if(repository.update(panel)) {
                result.setPayload(panel);
            } else {
                String message = String.format("Panel ID %s not found", panel.getPanelId());
                result.addMessage(message, ResultType.NOT_FOUND);

            }
        }

        return result;
    }

    public Result delete(int panelId) throws DataAccessException {
        Result result = new Result();


        if(!repository.delete(panelId)) {
            String message = String.format("Panel ID %s not found", panelId);
            result.addMessage(message, ResultType.NOT_FOUND);
            }
        return result;
    }




}
