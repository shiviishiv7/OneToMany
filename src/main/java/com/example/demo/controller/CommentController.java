package com.example.demo.controller;

import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.exception.BlogIdNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllComment(){
        return ResponseEntity.ok(commentRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable(value = "id")int id){
        return ResponseEntity.ok(commentRepository.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable(value = "id")int id, @RequestBody Comment comment){
        Comment comment1 = commentRepository.findById(id).get();
        comment1.setContent(comment.getContent());
        comment1.setBlog(comment.getBlog());
        return ResponseEntity.ok(commentRepository.save(comment1 ));
    }
    @PostMapping("/{blogId}")
    public ResponseEntity<?> addComment(@PathVariable int blogId, @RequestBody Comment comment) throws BlogIdNotExist {
        if(!blogRepository.existsById(blogId)) throw new BlogIdNotExist("Blog id not exist "+blogId);
        Blog blog = blogRepository.findById(blogId).get();
        comment.setBlog(blog);
        return ResponseEntity.ok(commentRepository.save(comment));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable(value = "id")int id){
        Map<String,Boolean> map = new HashMap<>();
        if(commentRepository.existsById(id)){
            map.put("state",true);
            return ResponseEntity.ok(map);
        }else {
            map.put("state",false);
            return ResponseEntity.badRequest().body(map);
        }
    }
}
