package hkmu.wadd.controllers;

import hkmu.wadd.models.AppUser;
import hkmu.wadd.models.Comment;
import hkmu.wadd.models.CourseMaterial;
import hkmu.wadd.repositories.AppUserRepository;
import hkmu.wadd.repositories.CommentRepository;
import hkmu.wadd.repositories.CourseMaterialRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CourseMaterialController {

    private final CourseMaterialRepository materialRepository;
    private final CommentRepository commentRepository;
    private final AppUserRepository userRepository;

    public CourseMaterialController(CourseMaterialRepository materialRepository, 
                                    CommentRepository commentRepository, 
                                    AppUserRepository userRepository) {
        this.materialRepository = materialRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 1. Display the Lecture Page
    @GetMapping("/material/{id}")
    public String viewMaterial(@PathVariable Long id, Model model) {
        CourseMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid material Id:" + id));
        
        model.addAttribute("material", material);
        return "material"; // Returns material.jsp
    }

    // 2. Handle New Comment Submissions
    @PostMapping("/material/{id}/comment")
    public String addComment(@PathVariable Long id, 
                             @RequestParam("content") String content, 
                             Principal principal) {
        
        // Find the lecture and the currently logged-in user
        CourseMaterial material = materialRepository.findById(id).orElseThrow();
        AppUser author = userRepository.findByUsername(principal.getName()).orElseThrow();

        // Create and save the new comment
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setCourseMaterial(material);
        
        commentRepository.save(comment);

        // Redirect back to the same lecture page so they can see their new comment
        return "redirect:/material/" + id;
    }
}