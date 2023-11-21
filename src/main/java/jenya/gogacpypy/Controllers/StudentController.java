package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Student;
import jenya.gogacpypy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentRepository StudentRepository;

    @GetMapping("/students")
    public List<Student> view_students() {
        return StudentRepository.findAll();
    }

    @PostMapping("/add_student")
    public String add_student(@RequestBody Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        StudentRepository.save(student);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_student")
    public String del_student(@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        StudentRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
