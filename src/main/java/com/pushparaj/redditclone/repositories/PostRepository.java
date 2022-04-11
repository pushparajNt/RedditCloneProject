package com.pushparaj.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pushparaj.redditclone.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
