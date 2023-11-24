package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Test;
import jenya.gogacpypy.model.TestsToGroups;
import jenya.gogacpypy.repository.TestRepository;
import jenya.gogacpypy.repository.TestsToGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TestController {
    @Autowired
    private TestRepository TestRepository;
    @Autowired
    private TestsToGroupsRepository TestsToGroupsRepository;

    @GetMapping("/tests")
    public List<Test> view_groups(@RequestHeader("Authorization") String token) {
        return TestRepository.findAll();
    }

    @PostMapping("/test_by_id")
    public Optional<Test> test_by_id(@RequestHeader("Authorization") String token,@RequestBody long id) {
        return TestRepository.findById(id);
    }

    @PostMapping("/tests_to_groups")
    public List<TestsToGroups> view_tests_to_groups(@RequestHeader("Authorization") String token,@RequestBody Test test) {
        return TestsToGroupsRepository.findByTest(test);
    }

    @PostMapping("/tests_to_groups_by_test_id")
    public List<TestsToGroups> view_tests_to_groups_by_test_id(@RequestHeader("Authorization") String token,@RequestBody long id) {
        return TestsToGroupsRepository.findByTestId(id);
    }

    @PostMapping("/add_test")
    public String add_test(@RequestHeader("Authorization") String token,@RequestBody Test test,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
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
        TestsToGroupsRepository.save(testsToGroups);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_test")
    public String del_test(@RequestHeader("Authorization") String token,@RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
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
        TestsToGroupsRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
