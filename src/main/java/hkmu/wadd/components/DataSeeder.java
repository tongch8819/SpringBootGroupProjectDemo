package hkmu.wadd.components;

import hkmu.wadd.models.AppUser;
import hkmu.wadd.models.CourseMaterial;
import hkmu.wadd.models.Poll;
import hkmu.wadd.models.PollOption;
import hkmu.wadd.repositories.AppUserRepository;
import hkmu.wadd.repositories.CourseMaterialRepository;
import hkmu.wadd.repositories.PollRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CourseMaterialRepository materialRepository;
    private final PollRepository pollRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Inject the user repository and password encoder
    public DataSeeder(CourseMaterialRepository materialRepository, 
                      PollRepository pollRepository,
                      AppUserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.materialRepository = materialRepository;
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed users first
        if (userRepository.count() == 0) {
            seedUsers();
        }

        // 2. Seed materials and polls
        if (materialRepository.count() == 0 && pollRepository.count() == 0) {
            seedCourseMaterials();
            seedPolls();
            System.out.println("✅ Database successfully seeded with dummy data and test users!");
        }
    }

    private void seedUsers() {
        // Create the requested Student Test Account
        AppUser student = new AppUser();
        student.setUsername("test");
        student.setPassword(passwordEncoder.encode("test")); // Must hash the password!
        student.setFullName("Test Student");
        student.setEmail("student@test.com");
        student.setPhoneNumber("12345678");
        student.setRole("ROLE_STUDENT");
        userRepository.save(student);

        // Create a Teacher Test Account for future admin testing
        AppUser teacher = new AppUser();
        teacher.setUsername("teacher");
        teacher.setPassword(passwordEncoder.encode("teacher")); 
        teacher.setFullName("Test Teacher");
        teacher.setEmail("teacher@test.com");
        teacher.setPhoneNumber("87654321");
        teacher.setRole("ROLE_TEACHER");
        userRepository.save(teacher);
    }

    private void seedCourseMaterials() {
        CourseMaterial lec1 = new CourseMaterial();
        lec1.setTitle("Lecture 1: Introduction to Web Architectures");
        lec1.setSummary("An overview of client-server models, HTTP protocols, and the Jakarta EE ecosystem.");
        lec1.setFilePath("/files/lec1.pdf"); 

        CourseMaterial lec2 = new CourseMaterial();
        lec2.setTitle("Lecture 2: Spring Boot Fundamentals");
        lec2.setSummary("Deep dive into dependency injection, MVC patterns, and auto-configuration.");
        lec2.setFilePath("/files/lec2.pdf");

        materialRepository.saveAll(Arrays.asList(lec1, lec2));
    }

    private void seedPolls() {
        Poll poll1 = new Poll();
        poll1.setQuestion("Which framework feature do you find most difficult to grasp so far?");

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

        poll1.setOptions(Arrays.asList(opt1, opt2, opt3, opt4, opt5));
        pollRepository.save(poll1);
    }
}