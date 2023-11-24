package jenya.gogacpypy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import jenya.gogacpypy.Utils.*;
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
    @Autowired
    private RefreshTokenRepository RefreshTokenRepository;

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
            RefreshToken refreshTokenEnt=new RefreshToken(0,refreshToken,user.getLogin());
            RefreshTokenRepository.deleteAllByLogin(user.getLogin());
            RefreshTokenRepository.save(refreshTokenEnt);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        Optional<Admin> a = AdminRepository.findFirstByLoginAndPass(JWTRequest.getLogin(), JWTRequest.getPassword());
        if(a.isPresent()){
            User user = new User(a.get().getLogin(),a.get().getPass(),2);
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            RefreshToken refreshTokenEnt=new RefreshToken(0,refreshToken,user.getLogin());
            RefreshTokenRepository.deleteAllByLogin(user.getLogin());
            RefreshTokenRepository.save(refreshTokenEnt);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        Optional<SuperAdmin> s = SuperAdminRepository.findFirstByLoginAndPass(JWTRequest.getLogin(), JWTRequest.getPassword());
        if(s.isPresent()){
            User user = new User(s.get().getLogin(),s.get().getPass(),3);
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            RefreshToken refreshTokenEnt=new RefreshToken(0,refreshToken,user.getLogin());
            RefreshTokenRepository.deleteAllByLogin(user.getLogin());
            RefreshTokenRepository.save(refreshTokenEnt);
            return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
        }
        return "{\"res\":\"Cant find user\"}";
    }

    @PostMapping("/update_token")
    public String update_token(@RequestBody String refreshToken,
                        BindingResult result) throws JsonProcessingException {

        refreshToken = refreshToken.replace("\"","");
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final Optional<RefreshToken> token = RefreshTokenRepository.findFirstByLogin(login);
            if(token.isEmpty()){
                return "{\"res\":\"No such user\"}";
            }
            final String saveRefreshToken = token.get().getToken();
            if (saveRefreshToken.equals(refreshToken)) {
                System.out.println("Try to refresh");
                Optional<Teacher> t = TeacherRepository.findFirstByLogin(login);
                if(t.isPresent()){
                    User user = new User(t.get().getLogin(),t.get().getPass(),1);
                    String accessToken = jwtProvider.generateAccessToken(user);
                    System.out.println("Refreshed");
                    return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
                }
                Optional<Admin> a = AdminRepository.findFirstByLogin(login);
                if(a.isPresent()){
                    User user = new User(a.get().getLogin(),a.get().getPass(),2);
                    String accessToken = jwtProvider.generateAccessToken(user);
                    System.out.println("Refreshed");
                    return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
                }
                Optional<SuperAdmin> s = SuperAdminRepository.findFirstByLogin(login);
                if(s.isPresent()){
                    User user = new User(s.get().getLogin(),s.get().getPass(),3);
                    String accessToken = jwtProvider.generateAccessToken(user);
                    System.out.println("Refreshed");
                    return mapper.writeValueAsString(new JWTResponse(accessToken, refreshToken));
                }
                return "{\"res\":\"Wrong token\"}";
            }
        }
        return "{\"res\":\"Token expired\"}";
    }

}
