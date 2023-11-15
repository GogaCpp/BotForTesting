package jenya.gogacpypy;

import jenya.gogacpypy.model.*;
import jenya.gogacpypy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GogacpypyApplication {

    @Autowired
    private StudentRepository DBRepository;

    @GetMapping("/form")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "form";
    }

    @GetMapping("/view")
    public List<Student> view() {
        return DBRepository.findAll();
    }
    @PostMapping ("/form_submit")
    public String form_submit(@RequestBody Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        DBRepository.save(student);
        return "Student saved";
    }

}
