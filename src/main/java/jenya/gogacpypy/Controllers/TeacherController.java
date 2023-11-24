package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Teacher;
import jenya.gogacpypy.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherRepository TeacherRepository;

    @GetMapping("/teachers")
    public List<Teacher> view_teachers(@RequestHeader("Authorization") String token) {
        return TeacherRepository.findAll();
    }

    @PostMapping("/add_teacher")
    public String add_teacher(@RequestHeader("Authorization") String token,@RequestBody Teacher teacher,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
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
        TeacherRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
