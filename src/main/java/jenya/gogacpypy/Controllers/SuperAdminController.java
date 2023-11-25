package jenya.gogacpypy.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.model.SuperAdmin;
import jenya.gogacpypy.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SuperAdminController {

    @Autowired
    private SuperAdminRepository SuperAdminRepository;
    private final JWTProvider jwtProvider;
    private final int levelAccess = 3;
    private final ObjectMapper mapper;
    @GetMapping("/super_admins")
    public String view_super_admins(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        return mapper.writeValueAsString(SuperAdminRepository.findAll());
    }

    @PostMapping("/add_super_admin")
    public String add_super_admin(@RequestHeader("Authorization") String token, @RequestBody SuperAdmin superAdmin,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        SuperAdminRepository.save(superAdmin);
        return "{\"res\":\"Success\"}";
    }

    @PostMapping ("/del_super_admin")
    public String del_super_admin(@RequestHeader("Authorization") String token, @RequestBody long id,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (!jwtProvider.checkAccess(token,levelAccess)) {
            return "{\"res\":\"Access denied\"}";
        }
        SuperAdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
