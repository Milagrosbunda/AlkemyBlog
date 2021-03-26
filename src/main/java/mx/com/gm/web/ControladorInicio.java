package mx.com.gm.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import mx.com.gm.domain.Post;
import mx.com.gm.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ControladorInicio {

    @Autowired
    private PostService postS;

    @GetMapping("/")
    public String inicio(Model model) {
        List<Post> posts = postS.listPostsByDate();
        List<Post> postsLimit = new ArrayList<>();
        int c = 0;
        if (posts.size() > 4) {
            while (c < 4) {
                postsLimit.add(posts.get(c));
                c++;
            }
            model.addAttribute("posts", postsLimit);
        } else {
            model.addAttribute("posts", posts);
        }
        return "index";
    }

 
    @GetMapping("/get/posts")
    public String morePage(Model model) {

        List<Post> posts = postS.listPostsByDate();
        model.addAttribute("posts", posts);
        return "morePosts";
    }

    @GetMapping("/newPost")
    public String newPost(Post post) {
        return "savePost";
    }

    @PostMapping("/patch")  //save post
    public String patch(Post post, @RequestParam("uploadImg") MultipartFile img) throws IOException {

        LocalDate localdate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localdate);
        post.setDate(date);

        if (img.isEmpty()) {
            post.setImg(null);
        } else {
            post.setImg(img.getBytes());
        }

        postS.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/patch/posts/{code}") //update
    public String patchPost(@PathVariable("code") int code, Post post, Model model) {
        post = postS.findPostByCode(code);
        model.addAttribute("post", post);
        return ("savePost");
    }

    @PostMapping("/get/posts")
    public String search(@RequestParam("searchCode") int code, Model model) {

        List<Post> posts = postS.listPosts();
        List<Post> postsLimit = new ArrayList<>();
        int c = 0;
        if (posts.size() >= 4) {
            while (c <= 4) {
                for (Post p : posts) {
                    postsLimit.add(p);
                    c++;
                }
            }
            model.addAttribute("posts", postsLimit);
        } else {
            model.addAttribute("posts", posts);
        }
        model.addAttribute("post", postS.findPostByCode(code));

        return ("searchPost");
    }

    @GetMapping("/more/{code}")
    public String fullPost(Post post, Model model) {

        post = postS.findPost(post);
        model.addAttribute("post", post);
        return ("fullPost");

    }

    @GetMapping("/display/{code}")
    @ResponseBody
    void showImg(@PathVariable("code") int code, HttpServletResponse response, Post post) throws IOException {

        post = postS.findPostByCode(code);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(post.getImg());
        response.getOutputStream().close();
    }

    @GetMapping("/delete/{code}")
    public String delete(Post p, Model model) {
        postS.deletePost(p);
        return ("redirect:/");

    }
}
