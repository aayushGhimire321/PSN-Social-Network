package psnbackend.controller;

import psnbackend.entity.DoubleIdObjectEntity;
import psnbackend.entity.IdObjectEntity;
import psnbackend.entity.PostEntity;
import psnbackend.service.PostService;
import psnbackend.service.ResponseObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObjectService> createPost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<>(postService.insertPost(inputPost), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseObjectService> getPostsByUserId(@RequestBody IdObjectEntity inputUserId) {
        return new ResponseEntity<>(postService.findPostByUserId(inputUserId.getId()), HttpStatus.OK);
    }

    @PostMapping("/following")
    public ResponseEntity<ResponseObjectService> getPostsByFollowing(@RequestBody IdObjectEntity inputUserId) {
        return new ResponseEntity<>(postService.findPostByFollowing(inputUserId.getId()), HttpStatus.OK);
    }

    @PutMapping("/update/comment")
    public ResponseEntity<ResponseObjectService> updatePostByComment(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<>(postService.updatePostByComment(inputPost), HttpStatus.OK);
    }

    @PutMapping("/update/love")
    public ResponseEntity<ResponseObjectService> updatePostByLove(@RequestBody DoubleIdObjectEntity doubleId) {
        return new ResponseEntity<>(postService.updatePostByLove(doubleId), HttpStatus.OK);
    }

    @PutMapping("/update/share")
    public ResponseEntity<ResponseObjectService> updatePostByShare(@RequestBody DoubleIdObjectEntity doubleId) {
        return new ResponseEntity<>(postService.updatePostByShare(doubleId), HttpStatus.OK);
    }
}
