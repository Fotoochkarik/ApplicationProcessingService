package com.example.aps.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "request")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Request extends BaseEntity {
    @Column(name = "created", columnDefinition = "timestamp default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NonNull
    private Date created = new Date();

    @Column(name = "status", nullable = false)
    @NonNull
    private Condition status = Condition.DRAFT;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    private User author;
}
