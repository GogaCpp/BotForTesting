package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.Admin;
import jenya.gogacpypy.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminRepository AdminRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 2;
    private final ObjectMapper mapper;

    @GetMapping("/admins")
    public String view_admins(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(AdminRepository.findAll());
    }

    @PostMapping("/add_admin")
    public String add_admin(@RequestHeader("Authorization") String token,@RequestBody Admin admin,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
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
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        AdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
