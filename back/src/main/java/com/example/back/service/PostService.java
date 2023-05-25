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
        LOG.info("Saving post for user with username {}", user.getUsername());
        return postDao.save(postMapper.fromDto(postDto, user));
    }

    public List<Post> getAll() {
        return postDao.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postDao.findPostByIdAndUser(postId, user).orElseThrow(
                () -> new PostNotFoundException(String.format("Post with id %s from user with username %s not found", postId, user.getUsername())));
    }

    public List<Post> getAllByUser(Principal principal) {
        return postDao.findAllByUserOrderByCreatedDateDesc(getUserByPrincipal(principal));
    }

    public Post like(Long postId, String username) {
        Post post = postDao.findById(postId).orElseThrow(
                () -> new PostNotFoundException(String.format("Post with id %s not found", postId))
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

    public void delete(Long id, Principal principal) {
        Post post = getPostById(id, principal);
        postDao.delete(post);
        imageModelDao.findByPostId(post.getId()).ifPresent(imageModelDao::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userDao.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

}
