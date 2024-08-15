package com.arato.Mezashi.utils;

import com.arato.Mezashi.Mezashi.Mezashi;
import com.arato.Mezashi.Mezashi.MezashiRepository;
import com.arato.Mezashi.Mezashi.exception.MezashiNotFoundException;
import com.arato.Mezashi.Tag.Tag;
import com.arato.Mezashi.Tag.TagRepository;
import com.arato.Mezashi.Tag.exception.TagNotFoundException;
import com.arato.Mezashi.User.User;
import com.arato.Mezashi.User.UserRepository;
import com.arato.Mezashi.User.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class Utils {
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private MezashiRepository mezashiRepository;

    public Utils(UserRepository userRepository, TagRepository tagRepository, MezashiRepository mezashiRepository) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.mezashiRepository = mezashiRepository;
    }

    public User findUserByIdOrThrow(long userId) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user with id=" + userId + " is not found");
        }
        return userOptional.get();
    }

    public Mezashi findMezashiByUserIdAndMezashiIdOrThrow(
            long userId,
            long mezashiId
    ) throws UserNotFoundException, MezashiNotFoundException {
        var mezashi = this.mezashiRepository.findByIdAndUser_Id(mezashiId, userId);
        if (mezashi.isEmpty()) throw new MezashiNotFoundException("mezashi with id=" + mezashiId + " is not found");
        return mezashi.get();
    }

    public Mezashi findMezashiByIdOrThrow(
            long mezashiId
    ) throws MezashiNotFoundException {
        var mezashi = this.mezashiRepository.findById(mezashiId);
        if (mezashi.isEmpty()) throw new MezashiNotFoundException("mezashi with id=" + mezashiId + " is not found");
        return mezashi.get();
    }

    public Tag findTagByUserIdAndTagIdOrThrow(
            long userId,
            long tagId
    ) throws UserNotFoundException, TagNotFoundException {
        var tag = this.tagRepository.findByIdAndUser_Id(tagId, userId);
        if (tag.isEmpty()) throw new TagNotFoundException("tag with id=" + tagId + " is not found");
        return tag.get();
    }
}