package com.example.aps.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "request")
@NoArgsConstructor
@JsonAutoDetect
public class Request extends BaseEntity {
    @Column(name = "created", columnDefinition = "timestamp default now()")
    private Date created = new Date();

    @Column(name = "status", nullable = false)
    private Condition status;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    public Request(Long id, Date created, Condition status, String description, User author) {
        super(id);
        this.created = created;
        this.status = status;
        this.description = description;
        this.author = author;
    }

    public Request(Long id, String description, User author) {
        this(id, new Date(), Condition.DRAFT, description, author);
    }
}
