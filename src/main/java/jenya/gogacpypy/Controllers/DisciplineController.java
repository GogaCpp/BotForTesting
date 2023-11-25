package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Discipline;
import jenya.gogacpypy.model.DisciplinesToCollections;
import jenya.gogacpypy.repository.DisciplineRepository;
import jenya.gogacpypy.repository.DisciplinesToCollectionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DisciplineController {

    @Autowired
    private DisciplineRepository DisciplineRepository;
    @Autowired
    private DisciplinesToCollectionsRepository DisciplinesToCollectionsRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @GetMapping("/disciplines")
    public String view_disciplines(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(DisciplineRepository.findAll());
    }

    @PostMapping("/discipline_by_id")
    public String discipline_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(DisciplineRepository.findById(id));
    }

    @PostMapping("/add_discipline")
    public String add_discipline(@RequestHeader("Authorization") String token,@RequestBody Discipline discipline,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        DisciplineRepository.save(discipline);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_discipline_to_collection")
    public String add_discipline_to_collection(@RequestHeader("Authorization") String token,@RequestBody DisciplinesToCollections disciplinesToCollections,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        DisciplinesToCollectionsRepository.save(disciplinesToCollections);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_discipline")
    public String del_discipline(@RequestHeader("Authorization") String token,@RequestBody long id,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        DisciplineRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_discipline_to_collection")
    public String del_discipline_to_collection(@RequestHeader("Authorization") String token,@RequestBody DisciplinesToCollections dtc,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        List<DisciplinesToCollections> a = DisciplinesToCollectionsRepository.findByCollectionIdAndDisciplineId(dtc.getCollectionId(), dtc.getDisciplineId());
        for (DisciplinesToCollections i:
                a) {
            DisciplinesToCollectionsRepository.deleteById(i.getId());
        }
        return "{\"res\":\"Success\"}";
    }
}
