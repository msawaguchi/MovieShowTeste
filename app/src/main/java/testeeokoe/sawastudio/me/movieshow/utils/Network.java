package testeeokoe.sawastudio.me.movieshow.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import testeeokoe.sawastudio.me.movieshow.entities.Movie;

public class Network {

    static final String API_KEY= "46aea4210e0e8481aab8a68a2c613a35";

    public static String resgataJson(int flag, String query){
        String url="";

        if (flag ==1) { url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=pt-BR&query="+query+"&page=1&include_adult=false";}
        else {url = "https://api.themoviedb.org/3/discover/movie?page=1&include_video=false&include_adult=false&sort_by=popularity.desc&language=pt-BR&api_key="+API_KEY;}

        String jsonResposta= "";

        ArrayList<Movie> movies = new ArrayList<Movie>();
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            jsonResposta = response.body().string();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResposta;
    }


    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = manager.getActiveNetworkInfo();

        if (net != null && net.isConnected()){
            return true;
        }
        return false;
    }
}
