package psnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psnbackend.entity.CommentEntity;
import psnbackend.repository.CommentRepository;
import psnbackend.service.ResponseObjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/")
    public ResponseEntity<ResponseObjectService> getAllComments() {
        List<CommentEntity> comments = commentRepository.findAll();
        return new ResponseEntity<>(new ResponseObjectService("success", "Retrieved all comments", comments), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObjectService> getCommentById(@PathVariable Long id) {
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(new ResponseObjectService("success", "Retrieved comment with id: " + id, comment.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObjectService("fail", "Comment not found with id: " + id, null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObjectService> createComment(@RequestBody CommentEntity comment) {
        CommentEntity createdComment = commentRepository.save(comment);
        return new ResponseEntity<>(new ResponseObjectService("success", "Comment created successfully", createdComment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObjectService> updateComment(@PathVariable Long id, @RequestBody CommentEntity comment) {
        if (!commentRepository.existsById(id)) {
            return new ResponseEntity<>(new ResponseObjectService("fail", "Comment not found with id: " + id, null), HttpStatus.NOT_FOUND);
        }
        comment.setId(id);
        CommentEntity updatedComment = commentRepository.save(comment);
        return new ResponseEntity<>(new ResponseObjectService("success", "Comment updated successfully", updatedComment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObjectService> deleteComment(@PathVariable Long id) {
        if (!commentRepository.existsById(id)) {
            return new ResponseEntity<>(new ResponseObjectService("fail", "Comment not found with id: " + id, null), HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(id);
        return new ResponseEntity<>(new ResponseObjectService("success", "Comment deleted successfully", null), HttpStatus.OK);
    }
}
