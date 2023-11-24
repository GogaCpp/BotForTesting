package jenya.gogacpypy.Controllers;

import jenya.gogacpypy.model.Admin;
import jenya.gogacpypy.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminRepository AdminRepository;

    @GetMapping("/admins")
    public List<Admin> view_admins(@RequestHeader("Authorization") String token) {
        return AdminRepository.findAll();
    }

    @PostMapping("/add_admin")
    public String add_admin(@RequestHeader("Authorization") String token,@RequestBody Admin admin,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AdminRepository.save(admin);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_admin")
    public String del_admin(@RequestHeader("Authorization") String token, @RequestBody long id,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        AdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
