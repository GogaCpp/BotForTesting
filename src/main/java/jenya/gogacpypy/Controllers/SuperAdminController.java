package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.SuperAdmin;
import jenya.gogacpypy.repository.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SuperAdminController {

    @Autowired
    private SuperAdminRepository SuperAdminRepository;
    @GetMapping("/super_admins")
    public List<SuperAdmin> view_super_admins() {
        return SuperAdminRepository.findAll();
    }

    @PostMapping("/add_super_admin")
    public String add_super_admin(@RequestBody SuperAdmin superAdmin,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        SuperAdminRepository.save(superAdmin);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_super_admin")
    public String del_super_admin(@RequestBody long id,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        SuperAdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
