package hkmu.wadd.controllers;

import hkmu.wadd.models.CourseMaterial;
import hkmu.wadd.models.Poll;
import hkmu.wadd.repositories.CourseMaterialRepository;
import hkmu.wadd.repositories.PollRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final CourseMaterialRepository materialRepository;
    private final PollRepository pollRepository;

    // Spring automatically injects the repositories here
    public IndexController(CourseMaterialRepository materialRepository, PollRepository pollRepository) {
        this.materialRepository = materialRepository;
        this.pollRepository = pollRepository;
    }

    @GetMapping({"/", "/index"})
    public String showIndexPage(Model model) {
        // 1. Hardcode the course info as per basic requirements
        model.addAttribute("courseName", "COMP-3800SEF Web Application Design and Development");
        model.addAttribute("courseDescription", "Welcome to the course! Here you will find all lecture notes, materials, and weekly interactive polls.");

        // 2. Fetch all materials and polls from the database
        List<CourseMaterial> materials = materialRepository.findAll();
        List<Poll> polls = pollRepository.findAll();

        // 3. Add them to the model so the JSP can read them
        model.addAttribute("materials", materials);
        model.addAttribute("polls", polls);

        // 4. Return the name of the JSP file (without the .jsp extension)
        return "index"; 
    }
}