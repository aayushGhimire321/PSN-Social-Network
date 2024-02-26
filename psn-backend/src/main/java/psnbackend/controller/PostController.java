package psnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psnbackend.entity.PostEntity;
import psnbackend.service.PostService;
import psnbackend.service.ResponseObjectService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObjectService> createPost(@RequestBody PostEntity inputPost) {
        ResponseObjectService response = postService.insertPost(inputPost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Define additional endpoints for other post-related operations as needed
    // Example: Update post, Delete post, Get posts by user ID, etc.
}
