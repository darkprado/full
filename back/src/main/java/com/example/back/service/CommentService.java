package com.example.back.service;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.back.dao.CommentDao;
import com.example.back.dao.PostDao;
import com.example.back.dao.UserDao;
import com.example.back.dto.CommentDto;
import com.example.back.entity.Comment;
import com.example.back.entity.Post;
import com.example.back.entity.User;
import com.example.back.exception.PostNotFoundException;
import com.example.back.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final UserDao userDao;
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    public Comment save(Long postId, CommentDto commentDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = postDao.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s from user with username %s not found", postId, user.getUsername())));
        LOG.info("Saving comment for post with id {}", post.getId());
        return commentDao.save(commentMapper.fromDto(commentDto, post, user));
    }

    public List<Comment> getAllForPost(Long postId) {
        Post post = postDao.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", postId)));
        return commentDao.findAllByPost(post);
    }

    public void delete(Long commentId) {
        commentDao.findById(commentId).ifPresent(commentDao::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userDao.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

}
