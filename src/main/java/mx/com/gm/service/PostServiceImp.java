/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.service;

import java.util.ArrayList;
import java.util.List;
import mx.com.gm.dao.PostDao;
import mx.com.gm.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService{
    
    @Autowired
    private PostDao postD;

    @Override
    public List<Post> listPosts() {
         List<Post> postsAll = postD.findAll();
         List<Post> postsSaved = new ArrayList<>();
         for(Post p: postsAll){
         if(!p.isDeleted()){
         postsSaved.add(p);
         }
         }
         return postsSaved;
    }

    @Override
    public void savePost(Post post) {
        postD.save(post);
    }

    @Override
    public void deletePost(Post post) {
        post = postD.findById(post.getCode()).orElse(null);
        post.setDeleted(true);
        postD.save(post);
    }

    @Override
    public Post findPost(Post post) {
        return postD.findById(post.getCode()).orElse(null);
    }

    @Override
    public Post findPostByCode(int code) {
        return postD.findById(code).orElse(null);
    }

    @Override
    public List<Post> listPostsByDate() {
          List<Post> posts = postD.findAll();

          for (int i = 0; i < posts.size() - 1; i++) {
            for (int j = 0; j < posts.size() - 1 - i; j++) {

                if (posts.get(i).getDate().after(posts.get(i + 1).getDate())) {

                    Post aux = posts.get(i);
                    posts.set(i, posts.get(i + 1));
                    posts.set(i + 1, aux);
                }
            }
        }
          return posts;
    }


    
}
