package br.com.alura.forum.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	/* 
	 * Internacionalização de mensagem de erro
	 * Vai retornar a mensagem no idioma com base no header
	 * Accept-Language
	 *  */
	@Autowired
	private MessageSource messageSource;
	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)  // MethodArgumentNotValidException = Exeção de erro de validação de form
	public List<ErroFormDTO> handle(MethodArgumentNotValidException exception) {
		
		List<ErroFormDTO> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			
			ErroFormDTO erro = new ErroFormDTO(e.getField(), mensagem);
			
			dto.add(erro);
		});
		
		return dto;
	}
}
