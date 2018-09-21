package testeeokoe.sawastudio.me.movieshow.views;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import testeeokoe.sawastudio.me.movieshow.R;
import testeeokoe.sawastudio.me.movieshow.entities.Movie;

public class DetailsActivity extends AppCompatActivity {

    private Movie mMovie;
    private ViewHolder mViewHolder = new ViewHolder();
    public static final String MOVIE_FOTO_URL="https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        this.mViewHolder.capaFilme = (ImageView) this.findViewById(R.id.img_capa_det);
        this.mViewHolder.titulo = (TextView) this.findViewById(R.id.txt_titulo);
        this.mViewHolder.tituloOriginal = (TextView) this.findViewById(R.id.txt_originaltitulo);
        this.mViewHolder.sinopse = (TextView) this.findViewById(R.id.txt_det_sinopse);
        this.mViewHolder.popularidade = (TextView) this.findViewById(R.id.txt_det_popularity);
        this.mViewHolder.votoMedia = (TextView) this.findViewById(R.id.txt_det_vote);
        this.mViewHolder.dataLancamento = (TextView) this.findViewById(R.id.txt_det_datalancamento);
        getDataFromActivity();
        setData();

    }

    private void setData() {
        pegaImagem(this.mViewHolder.capaFilme, this.mMovie);
        this.mViewHolder.titulo.setText(this.mMovie.getTitle());
        this.mViewHolder.tituloOriginal.setText(this.mMovie.getOriginalTitle());
        this.mViewHolder.sinopse.setText(this.mMovie.getOverview());
        this.mViewHolder.popularidade.setText(String.valueOf(this.mMovie.getPopularity()));
        this.mViewHolder.votoMedia.setText(String.valueOf(this.mMovie.getVoteAverage()));
        this.mViewHolder.dataLancamento.setText(this.mMovie.getReleaseDate());
    }


    private void getDataFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mMovie = (Movie) extras.get("data");
        }
    }

    public View pegaImagem(ImageView capa, Movie mov){
        capa.setScaleType(ImageView.ScaleType.FIT_XY); //posiciona a foto certo
        capa.setAdjustViewBounds(true);
        Picasso.get().load(MOVIE_FOTO_URL + mov.getPosterPath()) .placeholder(R.drawable.placeholder) .into(capa);
        return capa;
    }

    private static class ViewHolder {
        ImageView capaFilme;
        TextView titulo;
        TextView tituloOriginal;
        TextView sinopse;
        TextView popularidade;
        TextView votoMedia;
        TextView dataLancamento;
    }
}
