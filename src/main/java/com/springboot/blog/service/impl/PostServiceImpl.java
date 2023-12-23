package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

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
    public List<PostDto> getAllPosts(){
        List<Post> res = postRepository.findAll();

        List<PostDto> postDtoList = new ArrayList<PostDto>();
        for(Post post: res){
            postDtoList.add(mapToDto(post));
        }

            return postDtoList;
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
