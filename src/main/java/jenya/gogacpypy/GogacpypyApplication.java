package jenya.gogacpypy;

import jenya.gogacpypy.model.*;
import jenya.gogacpypy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class GogacpypyApplication {

    @Autowired
    private SuperAdminRepository SuperAdminRepository;
    @Autowired
    private AdminRepository AdminRepository;
    @Autowired
    private TeacherRepository TeacherRepository;
    @Autowired
    private StudentRepository StudentRepository;
    @Autowired
    private UniversityRepository UniversityRepository;
    @Autowired
    private AnswerRepository AnswerRepository;
    @Autowired
    private QuestionRepository QuestionRepository;

    @GetMapping("/super_admins")
    public List<SuperAdmin> view_super_admins() {
        return SuperAdminRepository.findAll();
    }

    @GetMapping("/admins")
    public List<Admin> view_admins() {
        return AdminRepository.findAll();
    }

    @GetMapping("/teachers")
    public List<Teacher> view_teachers() {
        return TeacherRepository.findAll();
    }

    @GetMapping("/students")
    public List<Student> view_students() {
        return StudentRepository.findAll();
    }

    @GetMapping("/universities")
    public List<University> view_universities() {
        return UniversityRepository.findAll();
    }

    @PostMapping("/answers_by_question")
    public List<Answer> view_answers_by_question(@RequestBody long question_id) {
        return AnswerRepository.findByQuestionId(question_id);
    }

    @GetMapping("/questions")
    public List<Question> view_questions() {
        return QuestionRepository.findAll();
    }

    @PostMapping ("/question_by_id")
    public Optional<Question> question_by_id(@RequestBody long id) {
        return QuestionRepository.findById(id);
    }

    @PostMapping ("/add_super_admin")
    public String add_super_admin(@RequestBody SuperAdmin superAdmin,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        SuperAdminRepository.save(superAdmin);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_admin")
    public String add_admin(@RequestBody Admin admin,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AdminRepository.save(admin);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_teacher")
    public String add_teacher(@RequestBody Teacher teacher,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        TeacherRepository.save(teacher);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_student")
    public String add_student(@RequestBody Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        StudentRepository.save(student);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_university")
    public String add_university(@RequestBody University university,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        UniversityRepository.save(university);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_answer")
    public String add_answer(@RequestBody Answer answer,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AnswerRepository.save(answer);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/add_question")
    public String add_question(@RequestBody Question question,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        QuestionRepository.save(question);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_super_admin")
    public String del_super_admin(@RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        SuperAdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_admin")
    public String del_admin(@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_teacher")
    public String del_teacher(@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        TeacherRepository.deleteById(id);
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

    @PostMapping ("/del_university")
    public String del_university(@RequestBody long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        UniversityRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_question")
    public String del_question(@RequestBody long id,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        QuestionRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_answer")
    public String del_answer(@RequestBody long id,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AnswerRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

}
