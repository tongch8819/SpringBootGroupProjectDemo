package hkmu.wadd.components;

import hkmu.wadd.models.CourseMaterial;
import hkmu.wadd.models.Poll;
import hkmu.wadd.models.PollOption;
import hkmu.wadd.repositories.CourseMaterialRepository;
import hkmu.wadd.repositories.PollRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CourseMaterialRepository materialRepository;
    private final PollRepository pollRepository;

    public DataSeeder(CourseMaterialRepository materialRepository, PollRepository pollRepository) {
        this.materialRepository = materialRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if database is already seeded to prevent duplicates on restarts
        if (materialRepository.count() == 0 && pollRepository.count() == 0) {
            seedCourseMaterials();
            seedPolls();
            System.out.println("✅ Database successfully seeded with dummy data!");
        }
    }

    private void seedCourseMaterials() {
        CourseMaterial lec1 = new CourseMaterial();
        lec1.setTitle("Lecture 1: Introduction to Web Architectures");
        lec1.setSummary("An overview of client-server models, HTTP protocols, and the Jakarta EE ecosystem.");
        lec1.setFilePath("/files/lec1.pdf"); // We will handle actual file uploads later

        CourseMaterial lec2 = new CourseMaterial();
        lec2.setTitle("Lecture 2: Spring Boot Fundamentals");
        lec2.setSummary("Deep dive into dependency injection, MVC patterns, and auto-configuration.");
        lec2.setFilePath("/files/lec2.pdf");

        materialRepository.saveAll(Arrays.asList(lec1, lec2));
    }

    private void seedPolls() {
        Poll poll1 = new Poll();
        poll1.setQuestion("Which framework feature do you find most difficult to grasp so far?");

        // Requirement check: Exactly 5 options per poll
        PollOption opt1 = new PollOption();
        opt1.setOptionText("Dependency Injection (IoC)");
        opt1.setPoll(poll1);

        PollOption opt2 = new PollOption();
        opt2.setOptionText("Spring Security Configuration");
        opt2.setPoll(poll1);

        PollOption opt3 = new PollOption();
        opt3.setOptionText("JPA and Hibernate Relationships");
        opt3.setPoll(poll1);

        PollOption opt4 = new PollOption();
        opt4.setOptionText("JSP and JSTL Tag Libraries");
        opt4.setPoll(poll1);

        PollOption opt5 = new PollOption();
        opt5.setOptionText("I understand everything perfectly!");
        opt5.setPoll(poll1);

        // Bind options to the poll
        poll1.setOptions(Arrays.asList(opt1, opt2, opt3, opt4, opt5));

        // Saving the poll will automatically save the options because we used CascadeType.ALL in the Entity
        pollRepository.save(poll1);
    }
}