package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.University;
import jenya.gogacpypy.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UniversityController {
    @Autowired
    private jenya.gogacpypy.repository.UniversityRepository UniversityRepository;

    @GetMapping("/universities")
    public List<University> view_universities(@RequestHeader("Authorization") String token) {
        return UniversityRepository.findAll();
    }

    @PostMapping("/add_university")
    public String add_university(@RequestHeader("Authorization") String token, @RequestBody University university,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        UniversityRepository.save(university);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_university")
    public String del_university(@RequestHeader("Authorization") String token, @RequestBody long id,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        UniversityRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
