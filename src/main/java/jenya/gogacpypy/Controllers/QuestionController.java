package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Question;
import jenya.gogacpypy.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionRepository QuestionRepository;

    @GetMapping("/questions")
    public List<Question> view_questions() {
        return QuestionRepository.findAll();
    }

    @PostMapping("/question_by_id")
    public Optional<Question> question_by_id(@RequestBody long id) {
        return QuestionRepository.findById(id);
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
}
