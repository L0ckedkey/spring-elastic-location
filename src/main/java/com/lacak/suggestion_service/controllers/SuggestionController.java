package com.lacak.suggestion_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lacak.suggestion_service.models.Suggestion;
import com.lacak.suggestion_service.services.SuggestionService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/suggestions")

public class SuggestionController {
    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<Suggestion>>> getSuggestions(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) throws IOException {

        if (latitude == null && longitude == null &&  q != null){
            return ResponseEntity.ok(suggestionService.getSuggestion(q));
        }

        if (latitude != null && longitude != null && q != null) {
            return ResponseEntity.ok(suggestionService.getSuggestion(q, latitude, longitude));
        }
    
        return ResponseEntity.ok(suggestionService.getSuggestion());
    }

}
