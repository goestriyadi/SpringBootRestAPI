package com.goesmodul.springboot.firstrestapi.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
}
