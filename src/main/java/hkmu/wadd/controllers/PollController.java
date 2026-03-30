package hkmu.wadd.controllers;

import hkmu.wadd.models.*;
import hkmu.wadd.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class PollController {

    private final PollRepository pollRepository;
    private final PollOptionRepository optionRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final AppUserRepository userRepository;

    public PollController(PollRepository pollRepository, PollOptionRepository optionRepository,
                          VoteRepository voteRepository, CommentRepository commentRepository,
                          AppUserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
        this.voteRepository = voteRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 1. Display the Poll Page
    @GetMapping("/poll/{id}")
    public String viewPoll(@PathVariable Long id, Model model, Principal principal) {
        Poll poll = pollRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid poll Id:" + id));
        
        AppUser user = userRepository.findByUsername(principal.getName()).orElseThrow();

        // Check if the user has already voted
        Optional<Vote> existingVote = voteRepository.findByUserAndPoll(user, poll);
        if (existingVote.isPresent()) {
            // Pass the ID of the option they selected so the JSP can pre-select the radio button
            model.addAttribute("userVoteId", existingVote.get().getPollOption().getId());
        }

        model.addAttribute("poll", poll);
        return "poll"; // Returns poll.jsp
    }

    // 2. Handle Voting (and changing votes)
    @PostMapping("/poll/{id}/vote")
    public String submitVote(@PathVariable Long id, @RequestParam("optionId") Long optionId, Principal principal) {
        AppUser user = userRepository.findByUsername(principal.getName()).orElseThrow();
        Poll poll = pollRepository.findById(id).orElseThrow();
        PollOption selectedOption = optionRepository.findById(optionId).orElseThrow();

        Optional<Vote> existingVote = voteRepository.findByUserAndPoll(user, poll);

        if (existingVote.isPresent()) {
            // If they already voted, update their existing vote to the new option
            Vote vote = existingVote.get();
            vote.setPollOption(selectedOption);
            voteRepository.save(vote);
        } else {
            // Otherwise, create a brand new vote
            Vote newVote = new Vote();
            newVote.setUser(user);
            newVote.setPollOption(selectedOption);
            voteRepository.save(newVote);
        }

        return "redirect:/poll/" + id;
    }

    // 3. Handle Poll Comments
    @PostMapping("/poll/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam("content") String content, Principal principal) {
        AppUser author = userRepository.findByUsername(principal.getName()).orElseThrow();
        Poll poll = pollRepository.findById(id).orElseThrow();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setPoll(poll); // Note: We attach it to the poll, not the course material

        commentRepository.save(comment);
        return "redirect:/poll/" + id;
    }
}