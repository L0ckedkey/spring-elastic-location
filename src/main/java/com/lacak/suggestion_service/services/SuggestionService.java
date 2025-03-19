package com.lacak.suggestion_service.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;
import com.lacak.suggestion_service.models.Suggestion;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class SuggestionService {

        @Autowired
        private ElasticsearchClient elasticsearchClient;

        public SuggestionService(ElasticsearchClient elasticsearchClient) {
                this.elasticsearchClient = elasticsearchClient;
        }

        public Map<String, List<Suggestion>> getSuggestion(String query, double latitude, double longitude) throws IOException {

                SearchResponse<Suggestion> response = elasticsearchClient.search(s -> s
                                .index("geonames_v2")
                                .query(q -> q
                                                .bool(b -> b
                                                                .minimumShouldMatch("1")
                                                                .should(m -> m.geoDistance(gd -> gd
                                                                                .field("location")
                                                                                .distance("100km")
                                                                                .location(l -> l.latlon(latlon -> latlon
                                                                                                .lat(latitude)
                                                                                                .lon(longitude)))
                                                                                .boost(5.0f)))
                                                                .should(m -> m.fuzzy(f -> f
                                                                                .field("name")
                                                                                .value(query)
                                                                                .fuzziness("2")
                                                                                .boost(1.0f)))))
                                .sort(sort -> sort
                                                .score(sc -> sc.order(SortOrder.Desc)))
                                .sort(sort -> sort
                                                .geoDistance(gd -> gd
                                                                .field("location")
                                                                .location(l -> l.latlon(latlon -> latlon.lat(latitude)
                                                                                .lon(longitude)))
                                                                .order(SortOrder.Asc)))
                                .source(src -> src
                                                .filter(f -> f.includes(
                                                                "name",
                                                                "asciiname",
                                                                "timezone",
                                                                "latitude",
                                                                "longitude",
                                                                "country_code"))),
                                Suggestion.class);

                List<Suggestion> suggestions = response.hits().hits().stream()
                                .map(hit -> {
                                        Suggestion suggestion = hit.source();
                                        if (suggestion != null) {
                                                Double score = (hit.score() - 0) / (1);
                                                suggestion.setScore(score);
                                        }
                                        return suggestion;
                                })
                                .collect(Collectors.toList());

                Map<String, List<Suggestion>> responseMap = new HashMap<>();
                responseMap.put("suggestions", suggestions);

                return responseMap;
        }

        public Map<String, List<Suggestion>> getSuggestion(String query) throws IOException {

                SearchResponse<Suggestion> response = elasticsearchClient.search(s -> s
                                .index("geonames_v2")
                                .query(q -> q
                                                .fuzzy(f -> f
                                                                .field("name")
                                                                .value(query)
                                                                .fuzziness("2")))
                                .source(src -> src
                                                .filter(f -> f.includes(
                                                                "name",
                                                                "asciiname",
                                                                "timezone",
                                                                "latitude",
                                                                "longitude",
                                                                "country_code"))),
                                Suggestion.class);

                List<Suggestion> suggestions = response.hits().hits().stream()
                                .map(hit -> {
                                        Suggestion suggestion = hit.source();
                                        if (suggestion != null) {
                                                Double score = (hit.score() - 0) / (1);
                                                suggestion.setScore(score);
                                        }
                                        return suggestion;
                                })
                                .collect(Collectors.toList());

                Map<String, List<Suggestion>> responseMap = new HashMap<>();
                responseMap.put("suggestions", suggestions);

                return responseMap;
        }

        public Map<String, List<Suggestion>> getSuggestion() throws IOException {

                SearchResponse<Suggestion> response = elasticsearchClient.search(s -> s
                                .index("geonames_v2")
                                .query(q -> q.matchAll(ma -> ma))
                                .size(1000)
                                .source(src -> src
                                                .filter(f -> f.includes(
                                                                "name",
                                                                "asciiname",
                                                                "timezone",
                                                                "latitude",
                                                                "longitude",
                                                                "country_code"))),
                                Suggestion.class);

                List<Suggestion> suggestions = response.hits().hits().stream()
                                .map(hit -> {
                                        Suggestion suggestion = hit.source();
                                        if (suggestion != null) {
                                                Double score = (hit.score() - 0) / (1);
                                                suggestion.setScore(score);
                                        }
                                        return suggestion;
                                })
                                .collect(Collectors.toList());

                Map<String, List<Suggestion>> responseMap = new HashMap<>();
                responseMap.put("suggestions", suggestions);

                return responseMap;
        }

}
