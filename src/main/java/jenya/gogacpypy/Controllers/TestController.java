package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Test;
import jenya.gogacpypy.model.TestsToGroups;
import jenya.gogacpypy.repository.TestRepository;
import jenya.gogacpypy.repository.TestsToGroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TestController {
    @Autowired
    private TestRepository TestRepository;
    @Autowired
    private TestsToGroupsRepository TestsToGroupsRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 1;
    private final ObjectMapper mapper;

    @GetMapping("/tests")
    public String view_groups(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(TestRepository.findAll());
    }

    @PostMapping("/test_by_id")
    public String test_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(TestRepository.findById(id));
    }

    @PostMapping("/tests_to_groups")
    public String view_tests_to_groups(@RequestHeader("Authorization") String token,@RequestBody Test test) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(TestsToGroupsRepository.findByTest(test));
    }

    @PostMapping("/tests_to_groups_by_test_id")
    public String view_tests_to_groups_by_test_id(@RequestHeader("Authorization") String token,@RequestBody long id) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(TestsToGroupsRepository.findByTestId(id));
    }

    @PostMapping("/add_test")
    public String add_test(@RequestHeader("Authorization") String token,@RequestBody Test test,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TestRepository.save(test);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping("/add_test_to_group")
    public String add_test_to_group(@RequestHeader("Authorization") String token,@RequestBody TestsToGroups testsToGroups,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TestsToGroupsRepository.save(testsToGroups);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_test")
    public String del_test(@RequestHeader("Authorization") String token,@RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TestRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_test_to_group")
    public String del_test_to_group(@RequestHeader("Authorization") String token,@RequestBody long id,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        TestsToGroupsRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
