package com.greenart.MyHome.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.greenart.MyHome.model.Board;



public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByContent(String content);
    List<Board> findByTitleOrContent(String title, String content);

	
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);


    
}