package com.greenart.MyHome.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.greenart.MyHome.model.Board;

@Component
public class BoardValidator implements Validator{

	@Override
	public boolean supports(Class<?> Class) {

		return Board.class.equals(Class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Board b = (Board)obj;
		if(StringUtils.isEmpty(b.getContent())) {
			errors.rejectValue("content", "key", "내용을 입력하세요");
		}
		
	}

	
}
