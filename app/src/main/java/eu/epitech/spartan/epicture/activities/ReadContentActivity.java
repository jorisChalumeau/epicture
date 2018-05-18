package eu.epitech.spartan.epicture.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.epitech.spartan.epicture.R;
import eu.epitech.spartan.epicture.modele.ImgurSettings;
import eu.epitech.spartan.epicture.modele.Picture;
import eu.epitech.spartan.epicture.modele.PicturesViewHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReadContentActivity extends AppCompatActivity {
    private final String SECTION = "/user"; // hot|top|user default: hot
    private final String SORT = "/rising";  // viral|top|time|rising default: viral
    private final String WINDOW = "";       // only if SECTION=top => day|week|month|year|all default: day
    private final String PAGE = "/0";       // integer-page number
//    private final Request hotPicturesRequest = new Request.Builder()
//            .url("https://api.imgur.com/3/gallery.json")
//            .header("Authorization", "Client-ID f4b1b225b0c412a")
//            .header("User-Agent", "MyApp")
//            .build();

    private final Request userPicturesRequest = new Request.Builder()
            .url("https://api.imgur.com/3/gallery" + SECTION + SORT + WINDOW + PAGE + ".json")
            .header("Authorization", "Client-ID " + ImgurSettings.IMGUR_CLIENT_ID)
            //.header("User-Agent", "MyApp")
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_content);
        RecyclerView rv = findViewById(R.id.rv_content);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter<PicturesViewHolder> adapter = new RecyclerView.Adapter<PicturesViewHolder>() {
            @NonNull
            @Override
            public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new PicturesViewHolder(new View(getApplicationContext()));
            }

            @Override
            public void onBindViewHolder(@NonNull PicturesViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        rv.setAdapter(adapter);
        fetchData();
    }

    private void fetchData() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        httpClient.newCall(userPicturesRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Error Fetching Data", "Error : failed to fetch imgur data " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("Success Fetching Data", "Succeeded fetching imgur data");

                // TODO : parse json response into Picture
                if (response.body() == null) {
                    System.out.println("nothing fetched");
                } else {
                    JSONObject data;
                    JSONArray items;
                    try {
                        data = new JSONObject(Objects.requireNonNull(response.body()).string());
                        items = data.getJSONArray("data");
                        final List<Picture> pictures = new ArrayList<>();

                        // for each post
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            Picture pict = new Picture();
                            pict.setTitle(item.getString("title"));

                            if (item.has("animated") && item.getBoolean("animated")) { //TODO: au cas ou y a un truc à faire
                                pict.setImage(item.getString("link"));
                            } else if (item.has("images")) {
                                pict.setImage(((JSONObject) item.getJSONArray("images").get(0)).getString("link"));
                            } else {
                                pict.setImage(item.getString("link"));
                            }
                            pict.setDateTime(item.getLong("datetime"));
                            pict.setScore(item.getInt("score"));
                            pict.setViews(item.getInt("views"));
                            pictures.add(pict); // Add picture with title, datetime, etc. to the list
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    render(pictures);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void render(final List<Picture> pictures) {
        final RecyclerView rv = findViewById(R.id.rv_content);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter<PicturesViewHolder> adapter = new RecyclerView.Adapter<PicturesViewHolder>() {
            @NonNull
            @Override
            public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                PicturesViewHolder vh = new PicturesViewHolder(getLayoutInflater().inflate(R.layout.read_content_item, rv, false));
                vh.picture = vh.itemView.findViewById(R.id.item_picture);
                vh.title = vh.itemView.findViewById(R.id.item_title);
                vh.description = vh.itemView.findViewById(R.id.item_description);
                vh.score = vh.itemView.findViewById(R.id.item_score);
                vh.views = vh.itemView.findViewById(R.id.item_views);
                vh.dateTime = vh.itemView.findViewById(R.id.item_dateTime);
                return vh;
            }

            @Override
            public void onBindViewHolder(@NonNull PicturesViewHolder holder, int position) {
                if (pictures.get(position).getImage().contains(".gif") || pictures.get(position).getImage().contains(".mp4")) {
                    Glide.with(ReadContentActivity.this).load(pictures.get(position).getImage()).asGif().into(holder.picture);
                    holder.picture.getLayoutParams().height = 700;
                    holder.picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    holder.picture.requestLayout();
                } else {
                    Picasso.with(ReadContentActivity.this).load(pictures.get(position).getImage()).into(holder.picture);
                    holder.picture.getLayoutParams().height = 700;
                    holder.picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    holder.picture.requestLayout();
                }
                holder.title.setText(pictures.get(position).getTitle());
                if (pictures.get(position).getDescription() == null || pictures.get(position).getDescription().equals("null"))
                    holder.description.setVisibility(View.GONE);
                else
                    holder.description.setText(pictures.get(position).getDescription());
                holder.views.setText(String.format(getResources().getString(R.string.view), pictures.get(position).getViews()));
                holder.score.setText(String.format(getResources().getString(R.string.score), pictures.get(position).getScore()));
                holder.dateTime.setText(pictures.get(position).getDatetime());
            }

            @Override
            public int getItemCount() {
                return pictures.size();
            }
        };
        rv.setAdapter(adapter);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                // margin between cards
                outRect.bottom = 2;
            }
        });
    }
}
