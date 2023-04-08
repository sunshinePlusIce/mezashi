package com.arato.Mezashi.Mezashi;

import com.arato.Mezashi.Mezashi.exception.MezashiNotFoundException;
import com.arato.Mezashi.User.User;
import com.arato.Mezashi.User.UserRepository;
import com.arato.Mezashi.User.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class MezashiResource {
    private MezashiRepository mezashiRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(MezashiResource.class);

    public MezashiResource(
            MezashiRepository mezashiRepository,
            UserRepository userRepository
    ) {
        this.mezashiRepository = mezashiRepository;
        this.userRepository = userRepository;

        User user = new User(
                "arato",
                "password"
        );
        Mezashi m1 = new Mezashi(
                "find a girlfriend",
                null,
                CompleteCondition.MANUAL,
                "find a girlfriend, before the girls are not there anymore",
                user,
                null
        );
        Mezashi m2 = new Mezashi(
                "find a full-time summer internship",
                null,
                CompleteCondition.MANUAL,
                "girl matters, future matters much more",
                user,
                m1
        );
        Mezashi m3 = new Mezashi(
                "run",
                null,
                CompleteCondition.MANUAL,
                "run japan",
                user,
                m1
        );
        // m1.addChild(m2);
//        this.userRepository.save(user);
//        this.mezashiRepository.save(m1);
//        this.mezashiRepository.save(m2);
//        this.mezashiRepository.save(m3);
        // directly delete children, ok
//        this.mezashiRepository.delete(m3);
//        this.mezashiRepository.deleteMezashiById(m1.getId());
//        Mezashi m = mezashiRepository.findById(m1.getId()).get();
//        for (var child : m.getChildren()) {
//            Mezashi newChild = new Mezashi(
//                    child.getName(),
//                    child.getTags(),
//                    child.getCompleteCondition(),
//                    child.getDescription(),
//                    child.getUser(),
//                    null
//            );
//            newChild.setChildren(child.getChildren());
//            newChild.setId(child.getId());
//            this.mezashiRepository.save(newChild);
//        }
//        this.mezashiRepository.delete(m1);

//        var all = this.mezashiRepository.findAll();
        //Mezashi m = this.mezashiRepository.getMezashiById(1);
        // m.removeChild(m2);


        //m1.removeChild(m2);
//        this.mezashiRepository.save(m1);
//        this.mezashiRepository.save(m2);
//        this.mezashiRepository.delete(m1);

    }

    @GetMapping("/users/{userId}/mezashi")
    public Set<Mezashi> getMezashi(@PathVariable long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("user with id=" + userId + " is not found");
        return user.get().getMezashiList();
    }

    @GetMapping("/users/{userId}/mezashi/{mezashiId}")
    public Mezashi getMezashiById(
            @PathVariable long userId,
            @PathVariable long mezashiId
    ) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("user with id=" + userId + " is not found");
        var mezashi = user.get().getMezashiList().stream().filter(mezashi -> mezashi.getId() == mezashiId).findFirst();
        if (mezashi.isEmpty()) throw new MezashiNotFoundException("mezashi with id=" + mezashiId + " is not found");
        return mezashi.get();
    }

    @PostMapping("/users/{userId}/mezashi")
    public void createMezashi(
            @Valid @PathVariable long userId,
            @Valid @RequestBody Mezashi mezashi,
            @RequestParam @Min(0) Optional<Long> parentId
    ) {

        this.logger.debug("creating Mezashi: " + mezashi.toString());
       Optional<User> userOptional = this.userRepository.findById(userId);
       if (userOptional.isEmpty()) {
           throw new UserNotFoundException("NOT FOUND: User with id=" + userId);
       }
       if (parentId.isPresent()) {
           Optional<Mezashi> parentMezashi = this.mezashiRepository.findById(parentId.get());
           if (parentMezashi.isEmpty()) throw new MezashiNotFoundException("Mezashi with id=" + parentId.get() + " is not found.");

           this.logger.debug("get parent Mezashi: " + parentMezashi.get());
           mezashi.setParent(parentMezashi.get());
       }
       User user = userOptional.get();
       mezashi.setUser(user);
       this.mezashiRepository.save(mezashi);
    }



    @PutMapping("/users/{userId}/mezashi/{mezashiId}")
    public void updateMezashi(
            @PathVariable long userId,
            @PathVariable long mezashiId,
            @RequestBody MezashiUpdateInfomation mezashiUpdateInfomation,
            @RequestParam Optional<Long> parentId,
    ) {

    }



}
