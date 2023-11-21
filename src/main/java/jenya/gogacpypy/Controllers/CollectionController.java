package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Collection;
import jenya.gogacpypy.model.CollectionsToQuestions;
import jenya.gogacpypy.repository.CollectionRepository;
import jenya.gogacpypy.repository.CollectionsToQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CollectionController {

    @Autowired
    private CollectionRepository CollectionRepository;
    @Autowired
    private CollectionsToQuestionsRepository CollectionsToQuestionsRepository;
    @GetMapping("/collections")
    public List<Collection> view_collections() {
        return CollectionRepository.findAll();
    }

    @PostMapping("/collection_by_id")
    public Optional<Collection> collection_by_id(@RequestBody long id) {
        return CollectionRepository.findById(id);
    }

    @PostMapping("/add_collection")
    public String add_collection(@RequestBody Collection collection,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        CollectionRepository.save(collection);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_collection_to_question")
    public String add_collection_to_question(@RequestBody CollectionsToQuestions collectionsToQuestions,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        CollectionsToQuestionsRepository.save(collectionsToQuestions);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_collection")
    public String del_collection(@RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        CollectionRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_collection_to_question")
    public String del_collection_to_question(@RequestBody CollectionsToQuestions ctq,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
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