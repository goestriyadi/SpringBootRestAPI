package com.goesmodul.springboot.firstrestapi.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SurveyService {
	
	private static List<Survey> surveys = new ArrayList<>();
	
	static {
		 Question question1 = new Question("Question1",
	                "Most Popular Cloud Platform Today", Arrays.asList(
	                        "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
	        Question question2 = new Question("Question2",
	                "Fastest Growing Cloud Platform", Arrays.asList(
	                        "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
	        Question question3 = new Question("Question3",
	                "Most Popular DevOps Tool", Arrays.asList(
	                        "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
	 
	        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
	                question2, question3));
	 
	        Survey survey = new Survey("Survey1", "My Favorite Survey",
	                "Description of the Survey", questions);
	 
	        surveys.add(survey);
	}

	

	public List<Survey> retrieveAllSurvey() {
		
		return surveys;
	}



	public Survey retrieveSurveyById(String surveyId) {
		
		
		Predicate<? super Survey> predicate =
				survey -> survey.getId().equalsIgnoreCase(surveyId);
		Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
	
		return optionalSurvey.get();
 
	}



	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		
		Predicate<? super Survey> predicate =
				survey -> survey.getId().equalsIgnoreCase(surveyId);
		Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
		
		
		if(optionalSurvey.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);	
		return optionalSurvey.get().getQuestions();
	}



	public Question retrieveSpecificSurveyQustion(String surveyId, String questionId) {
	
		
		List<Question> surveyQustions = retrieveAllSurveyQuestions(surveyId);
		
		if(surveyQustions == null) return null;
		
		Predicate<? super Question> predicate =
				question -> question.getId().equalsIgnoreCase(questionId);
		Optional<Question> optionalQuestion = surveyQustions.stream().filter(predicate).findFirst();

		if(optionalQuestion.isEmpty()) return null;
		return optionalQuestion.get();
		
	}



	public String addNewSurveyQuestion(String surveyId, Question question) {
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		question.setId(generateRandomId());
		questions.add(question);
		return question.getId();
		
	}



	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}



	public String deleteSurveyQuestion(String surveyId, String questionId) {

		List<Question> surveyQustions = retrieveAllSurveyQuestions(surveyId);
		
		if(surveyQustions == null) return null;
		
		Predicate<? super Question> predicate =
				question -> question.getId().equalsIgnoreCase(questionId);
		boolean isRemoved = surveyQustions.removeIf(predicate);

		if(!isRemoved) return null;
		
		return questionId;
	}
	
	

}
