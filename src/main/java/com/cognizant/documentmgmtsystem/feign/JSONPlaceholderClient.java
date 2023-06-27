package com.cognizant.documentmgmtsystem.feign;

import com.cognizant.documentmgmtsystem.dto.CommentDTO;
import com.cognizant.documentmgmtsystem.dto.PostDTO;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "jsonplaceholder",
        url = "https://jsonplaceholder.typicode.com"
)
public interface JSONPlaceholderClient {
    @GetMapping("/posts/{id}")
    @Retry(name = "jsonplaceholder", fallbackMethod = "getPostByIdFallback")
    PostDTO getPostById(@PathVariable("id") Long id);

    @PostMapping("/posts")
    @Retry(name = "jsonplaceholder", fallbackMethod = "createPostFallback")
    PostDTO createPost(@RequestBody PostDTO postDTO);

    @PostMapping("/comments")
    @Retry(name = "jsonplaceholder", fallbackMethod = "createCommentFallback")
    CommentDTO createComment(@RequestBody CommentDTO commentDTO);

    default PostDTO getPostByIdFallback(Long id, Throwable ex) {
        // Fallback behavior for getPostById method
        // Return a default PostDTO or handle the fallback logic as per your requirement
        return PostDTO.builder().build();
    }

    default PostDTO createPostFallback(PostDTO postDTO, Throwable ex) {
        // Fallback behavior for createPost method
        // Return a default PostDTO or handle the fallback logic as per your requirement
        return PostDTO.builder().build();
    }

    default CommentDTO createCommentFallback(CommentDTO commentDTO, Throwable ex) {
        // Fallback behavior for createPost method
        // Return a default PostDTO or handle the fallback logic as per your requirement
        return CommentDTO.builder().build();
    }
}


