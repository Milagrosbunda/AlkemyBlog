
package mx.com.gm.dao;

import mx.com.gm.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostDao extends JpaRepository<Post, Integer>{
    
}
