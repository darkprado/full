package com.example.back.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.back.dao.ImageModelDao;
import com.example.back.dao.PostDao;
import com.example.back.dao.UserDao;
import com.example.back.dto.PostDto;
import com.example.back.entity.ImageModel;
import com.example.back.entity.Post;
import com.example.back.entity.User;
import com.example.back.exception.PostNotFoundException;
import com.example.back.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostDao postDao;
    private final ImageModelDao imageModelDao;
    private final PostMapper postMapper;
    private final UserDao userDao;

    public Post save(PostDto postDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        LOG.info(String.format("Saving post for user with username %s", user.getUsername()));
        return postDao.save(postMapper.toPostCreate(postDto, user));
    }

    public List<Post> getAllPosts() {
        return postDao.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long id, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postDao.findPostByIdAndUser(id, user).orElseThrow(
                () -> new PostNotFoundException(String.format("Post with id %s from user with username %s not found", id, user.getUsername())));
    }

    public List<Post> getAllPostsByUser(Principal principal) {
        return postDao.findAllByUserOrderByCreatedDateDesc(getUserByPrincipal(principal));
    }

    public Post likePost(Long id, String username) {
        Post post = postDao.findById(id).orElseThrow(
                () -> new PostNotFoundException(String.format("Post with id %s not found", id))
        );

        Optional<String> userLike = post.getLikedUsers().stream().filter(u -> u.equals(username)).findAny();

        if (userLike.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }
        return postDao.save(post);
    }

    public void deletePost(Long id, Principal principal) {
        Post post = getPostById(id, principal);
        Optional<ImageModel> imageModel = imageModelDao.findByPostId(post.getId());
        postDao.delete(post);
        imageModel.ifPresent(imageModelDao::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userDao.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

}
