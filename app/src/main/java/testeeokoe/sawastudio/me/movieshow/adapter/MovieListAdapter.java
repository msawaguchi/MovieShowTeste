package testeeokoe.sawastudio.me.movieshow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import testeeokoe.sawastudio.me.movieshow.R;
import testeeokoe.sawastudio.me.movieshow.entities.Movie;
import testeeokoe.sawastudio.me.movieshow.listener.OnListClickInteractionListener;
import testeeokoe.sawastudio.me.movieshow.viewholder.MovieViewHolder;
import testeeokoe.sawastudio.me.movieshow.views.DetailsActivity;
import testeeokoe.sawastudio.me.movieshow.views.MainActivity;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Movie> mListMovies;
    private OnListClickInteractionListener mlistener;
    public static final String MOVIE_FOTO_URL="https://image.tmdb.org/t/p/w185";
    private LayoutInflater inflater;

    public MovieListAdapter(List<Movie> movies, Context context, OnListClickInteractionListener listener) {
        this.context = context;
        this.mListMovies = movies;
        this.mlistener = listener;
        inflater= LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Obtém o contexto
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Instancia o layout para manipulação dos elementos
        View movieView = inflater.inflate(R.layout.row_list_movie, parent, false);

        MovieHolder holder = new MovieHolder(movieView);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieHolder movieHolder = (MovieHolder) holder;
        final Movie movie = this.mListMovies.get(position);

        movieHolder.nomeFilme.setText(movie.getTitle());
        movieHolder.pegaImagem(movieHolder.capa,movie);

        movieHolder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("data", movie);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return this.mListMovies.size();
    }


    class MovieHolder extends RecyclerView.ViewHolder{

        TextView nomeFilme;
        ImageView capa;
        RelativeLayout list;

        // construtor
        public MovieHolder(View itemView) {
            super(itemView);
            nomeFilme= (TextView) itemView.findViewById(R.id.txt_titulofilme);
            capa= (ImageView) itemView.findViewById(R.id.list_item_thumbnail);
            this.list = (RelativeLayout) itemView.findViewById(R.id.listitem_movie);
        }

        public View pegaImagem(ImageView capa, Movie mov){
            capa.setScaleType(ImageView.ScaleType.FIT_XY);
            capa.setAdjustViewBounds(true);
            Picasso.get().load(MOVIE_FOTO_URL + mov.getPosterPath()) .placeholder(R.drawable.videocamera) .into(capa);
            return capa;
        }

    }
}
