package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Group;
import jenya.gogacpypy.model.GroupsToQuestions;
import jenya.gogacpypy.repository.GroupRepository;
import jenya.gogacpypy.repository.GroupsToQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private GroupRepository GroupRepository;
    @Autowired
    private GroupsToQuestionsRepository GroupsToQuestionsRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @GetMapping("/groups")
    public String view_groups(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(GroupRepository.findAll());
    }

    @PostMapping("/group_by_id")
    public String group_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(GroupRepository.findById(id));
    }

    @PostMapping("/groups_by_collection_id")
    public String groups_by_collection_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(GroupRepository.findByCollectionId(id));
    }

    @PostMapping("/add_group")
    public String add_group(@RequestHeader("Authorization") String token,@RequestBody Group group,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
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
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        System.out.println(groupsToQuestions);
        GroupsToQuestionsRepository.save(groupsToQuestions);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_group")
    public String del_group(@RequestHeader("Authorization") String token,@RequestBody long id,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
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
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        List<GroupsToQuestions> a = GroupsToQuestionsRepository.findByGroupIdAndQuestionId(gtq.getGroupId(),gtq.getQuestionId());
        for (GroupsToQuestions i:
                a) {
            GroupsToQuestionsRepository.deleteById(i.getId());
        }
        return "{\"res\":\"Success\"}";
    }
}
