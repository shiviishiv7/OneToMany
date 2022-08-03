package com.example.demo.controller;


import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllBlog(){
        return ResponseEntity.ok(blogRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable(value = "id")int id){
        return ResponseEntity.ok(blogRepository.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlogById(@PathVariable(value = "id")int id, @RequestBody Blog blog){
        Blog blog1 = blogRepository.findById(id).get();
        blog1.setDescription(blog.getDescription());
        blog1.setTitle(blog.getTitle());
        return ResponseEntity.ok(blogRepository.save(blog1));
    }
    @PostMapping("/")
    public ResponseEntity<?> addBlog(@RequestBody Blog blog){
        return ResponseEntity.ok(blogRepository.save(blog));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogById(@PathVariable(value = "id")int id){
        Map<String,Boolean> map = new HashMap<>();
        if(blogRepository.existsById(id)){
            map.put("state",true);
            return ResponseEntity.ok(map);
        }else {
            map.put("state",false);
           return ResponseEntity.badRequest().body(map);
        }
    }
    @GetMapping("/{id}/comment")
    public ResponseEntity<?> blogComments(@PathVariable(value = "id")int id){
        Map<String,Boolean> map = new HashMap<>();
        if(blogRepository.existsById(id)){
            map.put("state",true);
            return ResponseEntity.ok(commentRepository.findAllCommentsByBlogId(id));
        }else {
            map.put("state",false);
            return ResponseEntity.badRequest().body(map);
        }
    }

}
