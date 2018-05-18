package eu.epitech.spartan.epicture.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import eu.epitech.spartan.epicture.modele.ImgurSettings;
import eu.epitech.spartan.epicture.modele.Picture;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class HandlerService extends IntentService {

    // actions provided by this service
    private static final String ACTION_UPLOAD = "eu.epitech.spartan.epicture.services.action.UPLOAD";

    // params for the ACTION_UPLOAD service
    private static final String UPLOAD_URI = "eu.epitech.spartan.epicture.services.extra.UPLOAD_URI";
    private static final String UPLOAD_TITLE = "eu.epitech.spartan.epicture.services.extra.UPLOAD_TITLE";
    private static final String UPLOAD_DESC = "eu.epitech.spartan.epicture.services.extra.UPLOAD_DESC";

    public HandlerService() {
        super("HandlerService");
    }

    /**
     * Starts this service to perform action Upload with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpload(Context context, Uri uri, String title, String desc) {
        Intent intent = new Intent(context, HandlerService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(UPLOAD_URI, uri.toString());
        intent.putExtra(UPLOAD_TITLE, title);
        intent.putExtra(UPLOAD_DESC, desc);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD.equals(action)) {
                // to UPLOAD picture to imgur
                final Uri param1 = Uri.parse(intent.getStringExtra(UPLOAD_URI));
                final String param2 = intent.getStringExtra(UPLOAD_TITLE);
                final String param3 = intent.getStringExtra(UPLOAD_DESC);
                handleActionUpload(param1, param2, param3);
            }
        }
    }

    /**
     * Handle action Upload in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload(Uri uri, String title, String description) {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Callback uploadCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // start notification => upload failure
                Log.e("Error Uploading Data", "Error : failed to upload data to imgur " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // display notif => data sent
                Log.i("Success Fetching Data", "Succeeded fetching imgur data");

                if(response.body() == null) {
                    System.out.println("Nothing uploaded");
                } else {
                    JSONObject data;
                    JSONObject uploadedData;
                    try {
                        data = new JSONObject(Objects.requireNonNull(response.body().string()));
                        if(data.getBoolean("success")) {
                            uploadedData = data.getJSONObject("data");
                            Picture pic = new Picture();
                            pic.setTitle(uploadedData.getString("title"));
                            pic.setDescription(uploadedData.getString("description"));
                            pic.setDateTime(uploadedData.getLong("datetime"));
                            pic.setScore(0);
                            pic.setViews(uploadedData.getInt("views"));
                            pic.setImage(uploadedData.getString("link"));
                            Log.i("data uploaded : ", pic.toString());
                        } else
                            Log.e("upload status erro","error upload, status : "+data.getInt("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // TODO : display toast message (or toolbar notification) "upload finished"
        // https://developer.android.com/guide/components/services#Notifications

        //System.out.println("test avec url : "+uri.toString());
        if(toBase64(uri) != null) {
            RequestBody bodyUpload = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", toBase64(uri))
                    .addFormDataPart("title", title)
                    .addFormDataPart("description", description)
                    //.addFormDataPart("type", "URL")
                    .build();

            if (!ImgurSettings.isAuthenticated())
                httpClient.newCall(anonRequest(bodyUpload)).enqueue(uploadCallback);
            else
                httpClient.newCall(authedRequest(bodyUpload)).enqueue(uploadCallback);
        } else {
            Log.e("Invalid image", "Image can't be converted to Base64");
        }
    }

    private Request anonRequest(RequestBody bodyUpload) {
        return new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + ImgurSettings.IMGUR_CLIENT_ID)
                .post(bodyUpload)
                .build();
    }

    private Request authedRequest(RequestBody bodyUpload) {
        return new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + ImgurSettings.IMGUR_CLIENT_ID)
                .header("Bearer", ImgurSettings.getAccessToken())
                .post(bodyUpload)
                .build();
    }

    private String toBase64(Uri uri) {
        InputStream inputStream;
        byte[] bytes = null;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
            if(inputStream == null)
                return null;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            bytes = output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bytes != null)
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        return null;
    }
}
