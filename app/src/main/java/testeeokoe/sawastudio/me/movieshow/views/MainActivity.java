package testeeokoe.sawastudio.me.movieshow.views;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActionBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import testeeokoe.sawastudio.me.movieshow.R;
import testeeokoe.sawastudio.me.movieshow.adapter.MovieListAdapter;
import testeeokoe.sawastudio.me.movieshow.entities.Movie;
import testeeokoe.sawastudio.me.movieshow.listener.OnListClickInteractionListener;
import testeeokoe.sawastudio.me.movieshow.utils.Network;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerMovie;
    private MovieListAdapter mvListAdapter;
    Movie mov; String query;
    private EditText tituloDigitado;
    private Button buscaFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        final OnListClickInteractionListener  listener = new OnListClickInteractionListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("data", mov);
                startActivity(intent);
            }
        };

        new AsyncTaskMovies(listener,2 ,null).execute();

        tituloDigitado = (EditText)findViewById(R.id.edt_busca);
        buscaFilme = (Button) findViewById(R.id.btn_busca);
        buscaFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = tituloDigitado.getText().toString().replace(" ","+");
                if(query.matches("")){
                    Toast.makeText(MainActivity.this,"Digite novamente", Toast.LENGTH_SHORT).show();
                }else{
                    AsyncTaskMovies task = new AsyncTaskMovies(listener,1,query);
                    task.execute();
                }
            }
        });



    }




    //AsyncTask
    public class AsyncTaskMovies extends AsyncTask<String,String,String> {
        String mQuery;
        int mFlag;
        private final  OnListClickInteractionListener  mlistener;
        public AsyncTaskMovies(OnListClickInteractionListener  listener, int flag, String query){
            mlistener =listener;
            mFlag = flag;
            mQuery = query;
        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonRecuperado = "";

            if(Network.networkStatus(MainActivity.this)){
                jsonRecuperado = Network.resgataJson(mFlag,mQuery); //1->Pesquisa   2->Filmes mais populares (lista padrão)
                return (jsonRecuperado);

            }else{
                Toast.makeText(MainActivity.this,"Conecte-se com a internet!",Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
         protected void onPostExecute(String result) {

             List<Movie> data = new ArrayList<>();

            try {

                JSONObject mainObject = new JSONObject(result);

                JSONArray resArray = mainObject.getJSONArray("results"); //Pega resultados do objeto
                for (int i = 0; i < resArray.length(); i++) {

                    JSONObject jsonObject = resArray.getJSONObject(i);

                    Movie movie = new Movie();

                    movie.setId(jsonObject.getInt("id"));
                    movie.setVoteAverage(jsonObject.getInt("vote_average"));
                    movie.setVoteCount(jsonObject.getInt("vote_count"));
                    movie.setOriginalTitle(jsonObject.getString("original_title"));
                    movie.setTitle(jsonObject.getString("title"));
                    movie.setPopularity(jsonObject.getDouble("popularity"));
                    movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                    movie.setOverview(jsonObject.getString("overview"));
                    movie.setReleaseDate(jsonObject.getString("release_date"));
                    movie.setPosterPath(jsonObject.getString("poster_path"));

                    data.add(movie);
                }


                // Seta adapter na lista
                mRecyclerMovie = (RecyclerView)findViewById(R.id.movie_list_view);
                mvListAdapter = new MovieListAdapter(data, MainActivity.this, mlistener);
                mRecyclerMovie.setAdapter(mvListAdapter);
                mRecyclerMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this));


            } catch (JSONException e) {
                e.printStackTrace();

            }

    }
}
}
