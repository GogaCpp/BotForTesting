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
    private AnswerRepository AnswerRepository;
    @Autowired
    private QuestionRepository QuestionRepository;

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
