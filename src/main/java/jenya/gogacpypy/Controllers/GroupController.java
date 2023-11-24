package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Group;
import jenya.gogacpypy.model.GroupsToQuestions;
import jenya.gogacpypy.repository.GroupRepository;
import jenya.gogacpypy.repository.GroupsToQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class GroupController {
    @Autowired
    private GroupRepository GroupRepository;
    @Autowired
    private GroupsToQuestionsRepository GroupsToQuestionsRepository;

    @GetMapping("/groups")
    public List<Group> view_groups() {
        return GroupRepository.findAll();
    }

    @PostMapping("/group_by_id")
    public Optional<Group> group_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) {
        return GroupRepository.findById(id);
    }

    @PostMapping("/groups_by_collection_id")
    public List<Group> groups_by_collection_id(@RequestHeader("Authorization") String token,@RequestBody long id) {
        return GroupRepository.findByCollectionId(id);
    }

    @PostMapping("/add_group")
    public String add_group(@RequestHeader("Authorization") String token,@RequestBody Group group,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        GroupRepository.save(group);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_group_to_question")
    public String add_group_to_question(@RequestHeader("Authorization") String token,@RequestBody GroupsToQuestions groupsToQuestions,
                                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        GroupsToQuestionsRepository.save(groupsToQuestions);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_group")
    public String del_group(@RequestHeader("Authorization") String token,@RequestBody long id,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        GroupRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_group_to_question")
    public String del_group_to_question(@RequestHeader("Authorization") String token,@RequestBody GroupsToQuestions gtq,
                                               BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        List<GroupsToQuestions> a = GroupsToQuestionsRepository.findByGroupIdAndQuestionId(gtq.getGroupId(),gtq.getQuestionId());
        for (GroupsToQuestions i:
                a) {
            GroupsToQuestionsRepository.deleteById(i.getId());
        }
        return "{\"res\":\"Success\"}";
    }
}
