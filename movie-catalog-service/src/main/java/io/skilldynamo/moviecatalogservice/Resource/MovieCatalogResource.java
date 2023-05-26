package io.skilldynamo.moviecatalogservice.Resource;

import io.skilldynamo.moviecatalogservice.model.CatalogItem;
import io.skilldynamo.moviecatalogservice.model.Movie;
import io.skilldynamo.moviecatalogservice.model.Rating;
import io.skilldynamo.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    //get all movie IDs
    //for each movie Id ,call movie info service and get details
    //hard coding the rating values


    @Autowired
    public RestTemplate restTemplate;


    @Autowired
    public WebClient.Builder webclient;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {


//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 3),
//                new Rating("2468", 5)
//        );

        UserRating userrating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
         List<Rating>ratings =  userrating.getRatings();


        return ratings.stream().map(rating->{
               Movie object = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
//            Movie object = webclient.build().
//                    get().
//                    uri("http://del1-lhp-n80329.synapse.com:8082/movies/"+rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class).block();
               return new CatalogItem(object.getName(),"machiness",rating.getRating());
        }).collect(Collectors.toList());

//
//        return Collections.singletonList(
//                new CatalogItem("Transformers", "test", 4)
//        );

    }
}
