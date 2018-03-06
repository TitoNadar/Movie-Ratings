package comb.example.tito.moviesrating.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import comb.example.tito.moviesrating.Adapter.MoviesAdapter;
import comb.example.tito.moviesrating.Interface.MovieApiService;
import comb.example.tito.moviesrating.Model.Movie;
import comb.example.tito.moviesrating.Model.MovieResponse;
import comb.example.tito.moviesrating.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "API_KEY HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        connectAndGetApiData();
    }

    // This method create an instance of Retrofit
// set the base url
    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
          call.enqueue(new Callback<MovieResponse>() {
              @Override
              public void onResponse(Response<MovieResponse> response, Retrofit retrofit) {
                  List<Movie> movies = response.body().getResults();
                  recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                  Log.d(TAG, "Number of movies received: " + movies.size());
              }

              @Override
              public void onFailure(Throwable t) {
                  Log.e(TAG, t.toString());

              }
          });
    }
}

