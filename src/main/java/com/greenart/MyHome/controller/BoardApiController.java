package com.greenart.MyHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.MyHome.model.Board;
import com.greenart.MyHome.repository.BoardRepository;

@RestController
@RequestMapping("/api")
class BoardApiController {

  @Autowired
  private BoardRepository repository;


  @GetMapping("/boards")
  List<Board> all(@RequestParam(required=false, defaultValue="") String title
		  		,@RequestParam(required=false, defaultValue="") String content) {
	  if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){                                // 제목은 2자이상임
		  return repository.findAll();
	  }else {
		  return repository.findByTitleOrContent(title,content); //.findByTitleOrContent(title, content);
	  }
  }


  @PostMapping("/boards")
  Board newBoard(@RequestBody Board newBoard) {
    return repository.save(newBoard);
  }

 
  
  @GetMapping("/boards/{id}")
  Board one(@PathVariable Long id) {    
    return repository.findById(id).orElseThrow(null);
  }

  @PutMapping("/boards/{id}")
  Board replaceEmployee(@RequestBody Board newBoard, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(board -> {
    	  board.setTitle(newBoard.getTitle());
    	  board.setContent(newBoard.getContent());
        return repository.save(board);
      })
      .orElseGet(() -> {
        newBoard.setId(id);
        return repository.save(newBoard);
      });
  }

  @DeleteMapping("/boards/{id}")
  void deleteBoard(@PathVariable Long id) {
    repository.deleteById(id);
  }
}