//package learn.solarfarm.controllers;
//
//import learn.solarfarm.data.DataAccessException;
//import learn.solarfarm.domain.*;
//import learn.solarfarm.domain.PanelService;
//import learn.solarfarm.models.Panel;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/Panel")
////@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://127.0.0.1:5500/"})
//public class PanelController {
//    private final PanelService service;
//
//    public PanelController(PanelService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<Panel> findAll() throws DataAccessException {
//        return service.findAll();
//    }
//
//    @GetMapping("/section/{section}")
//    public List<Panel> findBySection(@PathVariable String section) throws DataAccessException {
//        return service.findBySection(section);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Panel> findById(@PathVariable int id) throws DataAccessException {
//        Panel Panel = service.findById(id);
//        if (Panel == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(Panel, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody Panel Panel) throws DataAccessException {
//        PanelResult result = service.add(Panel);
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
//        }
//        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Panel Panel) throws DataAccessException {
//        if (id != Panel.getPanelId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
//        }
//
//        Result result = service.update(Panel);
//        if (!result.isSuccess()) {
//            if (result.getType() == ResultType.NOT_FOUND) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
//            } else {
//                return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST); // 400
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable int id) throws DataAccessException {
//        Result result = service.delete(id);
//        if (result.getType() == ResultType.NOT_FOUND) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
//    }
//}
//
