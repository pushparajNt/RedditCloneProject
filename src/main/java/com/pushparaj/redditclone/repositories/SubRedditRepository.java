package com.pushparaj.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pushparaj.redditclone.model.SubReddit;
@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit, Long>{

}
