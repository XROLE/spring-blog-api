package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post newPost =  postRepository.save(post);

        return  mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy){
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Post> res = postRepository.findAll(pageable);
        List<Post> ListOfPosts = res.getContent();

        List<PostDto> postDtoList = new ArrayList<PostDto>();
        for(Post post: ListOfPosts){
            postDtoList.add(mapToDto(post));
        }

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(res.getNumber());
        postResponse.setPageSize(res.getSize());
        postResponse.setTotalElement(res.getTotalElements());
        postResponse.setTotalPages(res.getTotalPages());
        postResponse.setLast(res.isLast());

            return postResponse;
    }

    private PostDto mapToDto(Post post){
        PostDto newPostDto = new PostDto();
        newPostDto.setId(post.getId());
        newPostDto.setTitle(post.getTitle());
        newPostDto.setDescription(post.getDescription());
        newPostDto.setContent(post.getContent());

        return newPostDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }

    @Override
    public  PostDto getPostsById(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post res = postRepository.save(post);
        return mapToDto(res);
    }

    @Override
    public void deletePostById(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
