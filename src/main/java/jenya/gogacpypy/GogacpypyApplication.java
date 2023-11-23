package jenya.gogacpypy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jenya.gogacpypy.Utils.JWTProvider;
import jenya.gogacpypy.Utils.JWTRequest;
import jenya.gogacpypy.Utils.JWTResponse;
import jenya.gogacpypy.Utils.User;
import jenya.gogacpypy.model.*;
import jenya.gogacpypy.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class GogacpypyApplication {

    @Autowired
    private SuperAdminRepository SuperAdminRepository;
    @Autowired
    private AdminRepository AdminRepository;
    @Autowired
    private TeacherRepository TeacherRepository;

    private final JWTProvider jwtProvider;
    private final ObjectMapper mapper;

    @PostMapping("/login")
    public String login(@RequestBody JWTRequest JWTRequest,
                        BindingResult result) throws JsonProcessingException {
        Optional<Teacher> t = TeacherRepository.findFirstByLoginAndPass(JWTRequest.getLogin(), JWTRequest.getPassword());
        if(t.isPresent()){
            User user = new User(t.get().getLogin(),t.get().getPass(),1);
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        Optional<Admin> a = AdminRepository.findFirstByLoginAndPass(JWTRequest.getLogin(), JWTRequest.getPassword());
        if(a.isPresent()){
            User user = new User(a.get().getLogin(),a.get().getPass(),2);
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        Optional<SuperAdmin> s = SuperAdminRepository.findFirstByLoginAndPass(JWTRequest.getLogin(), JWTRequest.getPassword());
        if(s.isPresent()){
            User user = new User(s.get().getLogin(),s.get().getPass(),3);
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        return "{\"res\":\"Cant find user\"}";
    }

    @PostMapping("/claim")
    public String login(@RequestBody String string,
                        BindingResult result) throws JsonProcessingException {

        string = string.replace("\"","");
        System.out.println(mapper.writeValueAsString(jwtProvider.getAccessClaims(string)));
        return mapper.writeValueAsString(jwtProvider.getAccessClaims(string));
    }

}
