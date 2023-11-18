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
    private AnswerRepository AnswerRepository;
    @Autowired
    private QuestionRepository QuestionRepository;

    @GetMapping("/students")
    public List<Student> view_students() {
        return StudentRepository.findAll();
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

    @PostMapping ("/add_student")
    public String add_student(@RequestBody Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        StudentRepository.save(student);
        return "Student saved";
    }

    @PostMapping ("/add_answer")
    public String add_answer(@RequestBody Answer answer,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        AnswerRepository.save(answer);
        return "Answer saved";
    }

    @PostMapping ("/add_question")
    public String add_question(@RequestBody Question question,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        QuestionRepository.save(question);
        return "Question saved";
    }

    @PostMapping ("/del_question")
    public String del_question(@RequestBody long id,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        QuestionRepository.deleteById(id);
        return "Question saved";
    }

    @PostMapping ("/del_answer")
    public String del_answer(@RequestBody long id,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "Have an error";
        }
        AnswerRepository.deleteById(id);
        return "Question saved";
    }

}
