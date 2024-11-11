package learn.solarfarm.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.domain.PanelWebService;
import learn.solarfarm.domain.Result;
import learn.solarfarm.domain.ResultType;
import learn.solarfarm.models.Panel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/panels")
public class PanelController {

    private final PanelWebService service;

    public PanelController(PanelWebService service) {
        this.service = service;
    }

    @GetMapping
    public List<Panel> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{section}")
    public ResponseEntity<List<Panel>> findBySection(@PathVariable String section) throws DataAccessException {
        List<Panel> panels = service.findBySection(section);
        if (panels == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(panels, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Panel> add(@RequestBody Panel panel) throws DataAccessException {
        Result<Panel> result = service.add(panel);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{panelId}")
    public ResponseEntity<Void> update(@PathVariable("panelId") int panelId,@RequestBody Panel panel) throws DataAccessException {
        panel.setPanelId(panelId);

        if (panelId != panel.getPanelId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Result<Panel> result = service.update(panel);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{panelId}")
    public ResponseEntity<Void> delete(@PathVariable("panelId") int panelId) throws DataAccessException {
        if(service.delete(panelId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
