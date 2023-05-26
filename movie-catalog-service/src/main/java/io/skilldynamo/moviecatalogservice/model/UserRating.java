package io.skilldynamo.moviecatalogservice.model;

import java.util.List;

public class UserRating {

    List<Rating>ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
