package comb.example.tito.moviesrating.Interface;

import comb.example.tito.moviesrating.Model.MovieResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Tito on 19/10/2017.
 */


    public interface MovieApiService {
        @GET("movie/top_rated")
        Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
    }

