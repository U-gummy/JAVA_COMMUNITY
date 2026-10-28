package com.study.community.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member join fetch p.category")
    List<Post> findAllWithMemberAndCategory();

    @Query("select p from Post p join fetch p.member join fetch p.category where p.id = :id")
    Optional<Post> findByIdWithMemberAndCategory(@Param("id") Long id);
}
