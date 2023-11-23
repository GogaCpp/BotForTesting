package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Answer;
import jenya.gogacpypy.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AnswerController {

    @Autowired
    private AnswerRepository AnswerRepository;

    @PostMapping("/answers_by_question")
    public List<Answer> view_answers_by_question(@RequestBody long question_id) {
        return AnswerRepository.findByQuestionId(question_id);
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
