package mx.com.gm.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post implements Serializable {

    @Id
    private int code;
    @Basic
    private boolean deleted = false;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String category;
    private Date date;
    @Lob
    private byte[] img;
    
  
   
}
