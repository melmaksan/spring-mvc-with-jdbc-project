package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/tag/{id}")
    public Tag showTag(@PathVariable int id) {
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/tag/findByName/{name}")
    public Tag getGiftCertificatesByName(@PathVariable String name) {
        return tagService.getTagByName(name);
    }

    @GetMapping(value = "/tags")
    public List<Tag> showTags() {
        return tagService.getAllTags();
    }

    @DeleteMapping(value = "/tags/{id}")
    public String deleteTag(@PathVariable int id) {
        int res = tagService.deleteTag(id);
        if (res > 0) {
            return "Tag with id = " + id + " was deleted";
        } else {
            return "Something wrong, please try again";
        }
    }

    @PostMapping(value = "/tags")
    public Tag createTag(@RequestBody Tag tag) {
        int id = tagService.addTag(tag);
        return tagService.getTagById(id);
    }


}
