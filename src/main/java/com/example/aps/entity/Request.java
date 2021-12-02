package com.example.aps.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "request")
@NoArgsConstructor
public class Request extends BaseEntity implements Serializable {

    @Column(name = "created", columnDefinition = "timestamp default now()")
    private Date created = new Date();

    @Column(name = "status", nullable = false)
    private Condition status;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
//    @JoinColumn
//    @OnDelete(action = OnDeleteAction.CASCADE)
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
