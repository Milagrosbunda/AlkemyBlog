/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Post;

/**
 *
 * @author Miriam
 */
public interface PostService {
    
    public List<Post> listPosts();
    public List<Post> listPostsByDate();
    public void savePost(Post post);
    public void deletePost(Post post);
    public Post findPost(Post post);
    public Post findPostByCode(int code);
    
}
