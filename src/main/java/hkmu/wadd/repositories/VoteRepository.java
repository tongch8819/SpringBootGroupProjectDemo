package hkmu.wadd.repositories;

import hkmu.wadd.models.AppUser;
import hkmu.wadd.models.Poll;
import hkmu.wadd.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    // Custom query to find if a specific user has already voted in a specific poll
    @Query("SELECT v FROM Vote v WHERE v.user = :user AND v.pollOption.poll = :poll")
    Optional<Vote> findByUserAndPoll(@Param("user") AppUser user, @Param("poll") Poll poll);
}