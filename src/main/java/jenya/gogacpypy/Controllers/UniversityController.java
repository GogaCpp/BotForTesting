package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.University;
import jenya.gogacpypy.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UniversityController {
    @Autowired
    private jenya.gogacpypy.repository.UniversityRepository UniversityRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 2;
    private final ObjectMapper mapper;

    @GetMapping("/universities")
    public String view_universities(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(UniversityRepository.findAll());
    }

    @PostMapping("/add_university")
    public String add_university(@RequestHeader("Authorization") String token, @RequestBody University university,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
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
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        UniversityRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
