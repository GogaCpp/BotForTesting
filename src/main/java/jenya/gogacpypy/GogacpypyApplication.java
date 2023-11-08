package jenya.gogacpypy;

import jenya.gogacpypy.DB.Student;
import jenya.gogacpypy.DB.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GogacpypyApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/form")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("Student", new Student());
        return "form";
    }

    @GetMapping("/view")
    public String view(Model model) {
        String sql = "SELECT * from test";
        List<Student> data = jdbcTemplate.query(sql,new StudentRowMapper());
        model.addAttribute("students", data);
        return "view";
    }
    @PostMapping ("/form_submit")
    public String form_submit(@ModelAttribute("Student") Student student,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        String sql = "INSERT INTO test (name, email) VALUES ('"
                +student.getName() + "','" + student.getEmail() + "')";

        int rows = jdbcTemplate.update(sql);
        return "form";
    }

}
