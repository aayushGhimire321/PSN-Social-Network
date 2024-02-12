package psnbackend.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import psnbackend.entity.CommentEntity;
import psnbackend.entity.IdObjectEntity;
import psnbackend.entity.PostEntity;
import psnbackend.repository.CommentRepository;
import psnbackend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psnbackend.service.ResponseObjectService;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private PostService postService;

    public ResponseObjectService insertComment(CommentEntity inputComment, String inputPostId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<PostEntity> optPost = postRepo.findById(Long.valueOf(inputPostId));
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find target post id: " + inputPostId);
            responseObj.setPayload(null);
            return responseObj;
        } else {
            inputComment.setCreatedAt(Instant.now());
            commentRepo.save(inputComment);
            PostEntity targetPost = optPost.get();
            List<CommentEntity> commentList = targetPost.getComments();
            if (commentList == null) {
                commentList = new ArrayList<>();
            }
            commentList.add(inputComment);
            targetPost.setComments(commentList);
            postService.updatePostByComment(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(inputComment);
            return responseObj;
        }
    }

    public ResponseObjectService getComments(String inputPostId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<PostEntity> optTargetPost = postRepo.findById(Long.valueOf(inputPostId));
        if (optTargetPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("fail");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optTargetPost.get();
            List<CommentEntity> commentList = targetPost.getComments();
            if (commentList.size() > 0) {
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(commentList);
                return responseObj;
            } else {
                responseObj.setStatus("success");
                responseObj.setMessage("Post id " + inputPostId + " does not have any comment");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

}
