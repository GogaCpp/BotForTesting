package jenya.gogacpypy.Controllers;

import io.jsonwebtoken.Claims;
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
    @GetMapping("/super_admins")
    public List<SuperAdmin> view_super_admins(@RequestHeader("Authorization") String token) {
        if (token != null && jwtProvider.validateAccessToken(token)) {
            final int role = jwtProvider.getAccessClaims(token).get("role",Integer.class);
            if(role<3){
                return null;
            }
        }
        return SuperAdminRepository.findAll();
    }

    @PostMapping("/add_super_admin")
    public String add_super_admin(@RequestHeader("Authorization") String token, @RequestBody SuperAdmin superAdmin,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "{\"res\":\"Have an error\"}";
        }
        if (token != null && jwtProvider.validateAccessToken(token)) {
            final int role = jwtProvider.getAccessClaims(token).get("role",Integer.class);
            if(role<3){
                return null;
            }
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
        if (token != null && jwtProvider.validateAccessToken(token)) {
            final int role = jwtProvider.getAccessClaims(token).get("role",Integer.class);
            if(role<3){
                return null;
            }
        }
        SuperAdminRepository.deleteById(id);
        return "{\"res\":\"Success\"}";
    }
}
