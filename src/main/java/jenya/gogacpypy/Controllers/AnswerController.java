package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Answer;
import jenya.gogacpypy.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AnswerController {

    @Autowired
    private AnswerRepository AnswerRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @PostMapping("/answers_by_question")
    public String view_answers_by_question(@RequestHeader("Authorization") String token, @RequestBody long question_id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(AnswerRepository.findByQuestionId(question_id));
    }

    @PostMapping ("/add_answer")
    public String add_answer(@RequestHeader("Authorization") String token,@RequestBody Answer answer,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        AnswerRepository.save(answer);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_answer")
    public String del_answer(@RequestHeader("Authorization") String token,@RequestBody long id,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        AnswerRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
