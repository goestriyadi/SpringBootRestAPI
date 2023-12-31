package com.goesmodul.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {
	
	private SurveyService surveyService;
	
	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	// /surveys
	
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		
		List<Survey> surveys = surveyService.retrieveAllSurvey();
		
		if (surveys == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return surveys; 
	}
	
	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		
		Survey survey = surveyService.retrieveSurveyById(surveyId);
		if(survey == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return survey;
	}
	
	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
		List<Question> surveyQuestions = surveyService.retrieveAllSurveyQuestions(surveyId);
		
		if(surveyQuestions == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return surveyQuestions;
	}
	
	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQustion(@PathVariable String surveyId,@PathVariable String questionId) {
		Question specificQuestion = surveyService.retrieveSpecificSurveyQustion(surveyId,questionId);
		if(specificQuestion == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return specificQuestion;
	}
	
	
	@RequestMapping(value="/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity  addNewSurveyQuestions(
			@PathVariable String surveyId,
			@RequestBody Question question) {
		String questionId = surveyService
				.addNewSurveyQuestion(surveyId, question);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{questionId}")
				.buildAndExpand(questionId).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
	public ResponseEntity  deleteSurveyQuestion(
			@PathVariable String surveyId,
			@PathVariable String questionId) {
		String deleteResult = surveyService.deleteSurveyQuestion(surveyId,questionId);
		
		if(deleteResult.equalsIgnoreCase(questionId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return ResponseEntity.noContent().build();
		
	}
	
	
	
}
