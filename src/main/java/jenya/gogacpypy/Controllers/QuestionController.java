package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Question;
import jenya.gogacpypy.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class QuestionController {

    @Autowired
    private QuestionRepository QuestionRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @GetMapping("/questions")
    public String view_questions(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(QuestionRepository.findAll());
    }

    @PostMapping("/question_by_id")
    public String question_by_id(@RequestHeader("Authorization") String token, @RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(QuestionRepository.findById(id));
    }

    @PostMapping ("/add_question")
    public String add_question(@RequestHeader("Authorization") String token,@RequestBody Question question,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        QuestionRepository.save(question);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_question")
    public String del_question(@RequestHeader("Authorization") String token,@RequestBody long id,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        QuestionRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
