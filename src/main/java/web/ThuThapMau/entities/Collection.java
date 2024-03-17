package web.ThuThapMau.entities;

import jakarta.persistence.*;
import web.ThuThapMau.entities.Project;
import java.util.Date;

@Entity(name = "collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collection_id;
    private String collection_name;
    private Date collection_start;
    private Date collection_end;

    private String collection_image_url;

    private String collection_description;

    private Date collection_created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
