package com.epam.esm.controllers;

import com.epam.esm.dto.Mapper;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;
    private final Mapper mapper;

    public TagController(TagService tagService, Mapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/tag/{id}")
    public TagDTO showTag(@PathVariable int id) {
        return mapper.toTagDto(tagService.getTagById(id));
    }

    @GetMapping(value = "/tag/findByName/{name}")
    public TagDTO getGiftCertificatesByName(@PathVariable String name) {
        return mapper.toTagDto(tagService.getTagByName(name));
    }

    @GetMapping(value = "/tags")
    public List<TagDTO> showTags() {
        return tagService.getAllTags().stream().map(mapper::toTagDto).collect(Collectors.toList());
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
