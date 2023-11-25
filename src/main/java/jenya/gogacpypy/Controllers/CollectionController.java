package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Collection;
import jenya.gogacpypy.model.CollectionsToQuestions;
import jenya.gogacpypy.repository.CollectionRepository;
import jenya.gogacpypy.repository.CollectionsToQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CollectionController {

    @Autowired
    private CollectionRepository CollectionRepository;
    @Autowired
    private CollectionsToQuestionsRepository CollectionsToQuestionsRepository;
    private final JWTProvider jwtProvider;
    private final ObjectMapper mapper;
    private final int levelAccess = 1;

    @GetMapping("/collections")
    public String view_collections(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(CollectionRepository.findAll());
    }

    @PostMapping("/collection_by_id")
    public String collection_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(CollectionRepository.findById(id));
    }

    @PostMapping("/add_collection")
    public String add_collection(@RequestHeader("Authorization") String token, @RequestBody Collection collection,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        CollectionRepository.save(collection);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_collection_to_question")
    public String add_collection_to_question(@RequestHeader("Authorization") String token,@RequestBody CollectionsToQuestions collectionsToQuestions,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        CollectionsToQuestionsRepository.save(collectionsToQuestions);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_collection")
    public String del_collection(@RequestHeader("Authorization") String token,@RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        CollectionRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_collection_to_question")
    public String del_collection_to_question(@RequestHeader("Authorization") String token,@RequestBody CollectionsToQuestions ctq,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        List<CollectionsToQuestions> a = CollectionsToQuestionsRepository.findByCollectionIdAndQuestionId(ctq.getCollectionId(), ctq.getQuestionId());
        for (CollectionsToQuestions i:
             a) {
            System.out.println(i.getId());
            CollectionsToQuestionsRepository.deleteById(i.getId());
        }
        return "{\"res\":\"Success\"}";
    }
}