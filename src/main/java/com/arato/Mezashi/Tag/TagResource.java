package com.arato.Mezashi.Tag;

import com.arato.Mezashi.Tag.exception.TagCreationFailedException;
import com.arato.Mezashi.User.User;
import com.arato.Mezashi.User.UserRepository;
import com.arato.Mezashi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class TagResource {
    private TagRepository tagRepository;
    private UserRepository userRepository;
    private Utils utils;
    private Logger logger = LoggerFactory.getLogger(TagResource.class);

    public TagResource(
            TagRepository tagRepository,
            UserRepository userRepository,
            Utils utils
    ) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @CrossOrigin
    @PostMapping("/users/{userId}/tags")
    public ResponseEntity<Object> createTag(
            @PathVariable long userId,
            @RequestBody Tag tag
    ) {
        logger.debug("receiving tag: " + tag);

        User user = this.utils.findUserByIdOrThrow(userId);

        tag.setUser(user);
        try {
            this.tagRepository.save(tag);
        } catch(Exception e) {
            throw new TagCreationFailedException(
                    "Tag creation failed, check duplicated tags"
            );
        }

        var tags = user.getTags();
        tags.add(tag);
        user.setTags(tags);

        this.userRepository.save(user);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tag.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
