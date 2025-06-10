package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.TagRequest;
import com.lichenghsu.petshop.dto.TagResponse;
import com.lichenghsu.petshop.entity.Tag;
import com.lichenghsu.petshop.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponse> findAll() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    public TagResponse findById(Long id) {
        log.info("查詢標籤 by ID: {}", id);
        return tagRepository.findById(id)
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public TagResponse findByName(String name) {
        log.info("查詢標籤 by 名稱: {}", name);
        return tagRepository.findByName(name)
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Transactional
    public TagResponse create(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tag already exists");
        }

        Tag tag = new Tag();
        tag.setName(request.getName());
        Tag saved = tagRepository.save(tag);
        return new TagResponse(saved.getId(), saved.getName());
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
