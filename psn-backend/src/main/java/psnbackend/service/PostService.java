package psnbackend.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import psnbackend.entity.DoubleIdObjectEntity;
import psnbackend.entity.PostByFollowing;
import psnbackend.entity.PostEntity;
import psnbackend.entity.UserEntity;
import psnbackend.repository.PostRepository;
import psnbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    public ResponseObjectService insertPost(PostEntity inputPost) {
        ResponseObjectService responseObj = new ResponseObjectService();
        inputPost.setCreatedAt(Instant.now());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(postRepo.save(inputPost));
        return responseObj;
    }

    public ResponseObjectService findPostByUserId(String inputUserId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        List<PostEntity> userPosts = postRepo.findByUserIdOrderByCreatedAtDesc(inputUserId);
        if (userPosts.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId);
            responseObj.setPayload(null);
            return responseObj;
        } else {
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userPosts);
            return responseObj;
        }
    }

    public ResponseObjectService findPostByFollowing(String inputUserId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<UserEntity> optUser = userRepo.findById(Long.valueOf(inputUserId));
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId);
            responseObj.setPayload(null);
            return responseObj;
        } else {
            UserEntity user = optUser.get();
            if (user.getFollowing() != null) {
                List<String> followingIds = user.getFollowing();
                List<PostByFollowing> listPosts = new ArrayList<>();
                for (String followingId : followingIds) {
                    UserEntity followingUser = userRepo.findById(Long.valueOf(followingId)).orElse(null);
                    if (followingUser != null) {
                        followingUser.setPassword("");
                        List<PostEntity> followingPosts = postRepo.findByUserId(followingId);
                        if (followingPosts != null) {
                            for (PostEntity item : followingPosts) {
                                listPosts.add(new PostByFollowing(followingUser, item));
                            }
                        }
                    }
                }
                Collections.sort(listPosts, (o1, o2) -> o2.getPost().getCreatedAt().compareTo(o1.getPost().getCreatedAt()));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(listPosts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("user id: " + inputUserId + " has empty following list");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

    public ResponseObjectService updatePostByComment(PostEntity inputPost) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<PostEntity> optPost = postRepo.findById(inputPost.getId());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + inputPost.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            postRepo.save(inputPost);
            responseObj.setStatus("success");
            responseObj.setMessage("post is updated successfully");
            responseObj.setPayload(inputPost);
            return responseObj;
        }
    }

    public ResponseObjectService updatePostByLove(DoubleIdObjectEntity doubleId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<PostEntity> optPost = postRepo.findById(Long.valueOf(doubleId.getId1()));
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + doubleId.getId1());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> loveList = targetPost.getLove();
            if (loveList == null) {
                loveList = new ArrayList<>();
            }
            if (!loveList.contains(doubleId.getId2())) {
                loveList.add(doubleId.getId2());
            } else {
                loveList.remove(doubleId.getId2());
            }
            targetPost.setLove(loveList);
            postRepo.save(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("update love to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }

    public ResponseObjectService updatePostByShare(DoubleIdObjectEntity doubleId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<PostEntity> optPost = postRepo.findById(Long.valueOf(doubleId.getId1()));
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + doubleId.getId1());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> shareList = targetPost.getShare();
            if (shareList == null) {
                shareList = new ArrayList<>();
            }
            shareList.add(doubleId.getId2());
            targetPost.setShare(shareList);
            postRepo.save(targetPost);

            targetPost.setUserId(doubleId.getId2());
            targetPost.setId(null);
            targetPost.setContent("Shared a post: " + targetPost.getContent());
            targetPost.setLove(new ArrayList<>());
            targetPost.setShare(new ArrayList<>());
            targetPost.setComments(new ArrayList<>());
            postRepo.save(targetPost);

            responseObj.setStatus("success");
            responseObj.setMessage("add a share to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }
}
