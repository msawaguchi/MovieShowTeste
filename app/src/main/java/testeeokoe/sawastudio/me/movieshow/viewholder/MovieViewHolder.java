package testeeokoe.sawastudio.me.movieshow.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import testeeokoe.sawastudio.me.movieshow.R;
import testeeokoe.sawastudio.me.movieshow.entities.Movie;
import testeeokoe.sawastudio.me.movieshow.listener.OnListClickInteractionListener;
import testeeokoe.sawastudio.me.movieshow.utils.Network;

public class MovieViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    public static final String MOVIE_FOTO_URL="https://image.tmdb.org/t/p/w185";
    private TextView tituloFilme;
    private RelativeLayout list;
    ImageView capaFilme;



    public MovieViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.tituloFilme = (TextView) itemView.findViewById(R.id.txt_titulofilme);
        this.capaFilme = (ImageView) itemView.findViewById(R.id.list_item_thumbnail);
    }

    public void bindData(final Movie movie, final OnListClickInteractionListener listener) {


      this.tituloFilme.setText(movie.getTitle());

      pegaImagem(capaFilme,movie);

      this.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(movie.getId());
            }
        });
    }


    public View pegaImagem(ImageView capa, Movie mov){
        capa.setScaleType(ImageView.ScaleType.FIT_XY);
        capa.setAdjustViewBounds(true);
        Picasso.get().load(MOVIE_FOTO_URL + mov.getPosterPath()) .placeholder(R.drawable.videocamera) .into(capa);
        return capa;
    }
}
