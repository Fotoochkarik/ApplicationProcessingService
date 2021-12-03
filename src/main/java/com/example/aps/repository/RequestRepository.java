package com.example.aps.repository;

import com.example.aps.entity.Condition;
import com.example.aps.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.author.id=:userId ORDER BY r.created DESC")
    List<Request> findAllByAuthorId(@Param("userId") Long userId);

    @Query("SELECT r FROM Request r WHERE r.status=:sent ORDER BY r.created DESC")
    List<Request> findAllByStatus(@Param("sent") Condition sent);
}
