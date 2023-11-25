package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Student;
import jenya.gogacpypy.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentRepository StudentRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @GetMapping("/students")
    public String view_students(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(StudentRepository.findAll());
    }

    @PostMapping("/add_student")
    public String add_student(@RequestHeader("Authorization") String token,@RequestBody Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        StudentRepository.save(student);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_student")
    public String del_student(@RequestHeader("Authorization") String token,@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        StudentRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
