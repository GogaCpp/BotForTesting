package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Discipline;
import jenya.gogacpypy.model.DisciplinesToCollections;
import jenya.gogacpypy.repository.DisciplineRepository;
import jenya.gogacpypy.repository.DisciplinesToCollectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class DisciplineController {

    @Autowired
    private DisciplineRepository DisciplineRepository;
    @Autowired
    private DisciplinesToCollectionsRepository DisciplinesToCollectionsRepository;

    @GetMapping("/disciplines")
    public List<Discipline> view_disciplines() {
        return DisciplineRepository.findAll();
    }

    @PostMapping("/discipline_by_id")
    public Optional<Discipline> discipline_by_id(@RequestBody long id) {
        return DisciplineRepository.findById(id);
    }

    @PostMapping("/add_discipline")
    public String add_discipline(@RequestBody Discipline discipline,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        DisciplineRepository.save(discipline);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_discipline_to_collection")
    public String add_discipline_to_collection(@RequestBody DisciplinesToCollections disciplinesToCollections,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        DisciplinesToCollectionsRepository.save(disciplinesToCollections);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_discipline")
    public String del_discipline(@RequestBody long id,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        DisciplineRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_discipline_to_collection")
    public String del_discipline_to_collection(@RequestBody DisciplinesToCollections dtc,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        List<DisciplinesToCollections> a = DisciplinesToCollectionsRepository.findByCollectionIdAndDisciplineId(dtc.getCollectionId(), dtc.getDisciplineId());
        for (DisciplinesToCollections i:
                a) {
            DisciplinesToCollectionsRepository.deleteById(i.getId());
        }
        return "{\"res\":\"Success\"}";
    }
}
