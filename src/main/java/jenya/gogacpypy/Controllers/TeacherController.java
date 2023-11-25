package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Teacher;
import jenya.gogacpypy.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TeacherController {
    @Autowired
    private TeacherRepository TeacherRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 2;
    private final ObjectMapper mapper;

    @GetMapping("/teachers")
    public String view_teachers(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(TeacherRepository.findAll());
    }

    @PostMapping("/add_teacher")
    public String add_teacher(@RequestHeader("Authorization") String token,@RequestBody Teacher teacher,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TeacherRepository.save(teacher);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_teacher")
    public String del_teacher(@RequestHeader("Authorization") String token,@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TeacherRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
