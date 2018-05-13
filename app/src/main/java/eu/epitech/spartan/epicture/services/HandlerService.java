package eu.epitech.spartan.epicture.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HandlerService extends IntentService {

    // imgur settings
    private static final String IMGUR_CLIENT_ID = "f4b1b225b0c412a";
    private static final String IMGUR_CLIENT_SECRET = "7928201b1864f682f151cf7e87862df938627915";

    // actions provided by this service
    private static final String ACTION_UPLOAD = "eu.epitech.spartan.epicture.services.action.UPLOAD";
    private static final String ACTION_FETCH = "eu.epitech.spartan.epicture.services.action.FETCH";

    // params for the ACTION_UPLOAD service
    private static final String UPLOAD_URI = "eu.epitech.spartan.epicture.services.extra.UPLOAD_URI";
    private static final String UPLOAD_TITLE = "eu.epitech.spartan.epicture.services.extra.UPLOAD_TITLE";
    private static final String UPLOAD_DESC = "eu.epitech.spartan.epicture.services.extra.UPLOAD_DESC";

    // TODO : params for the ACTION_FETCH service

    public HandlerService() {
        super("HandlerService");
    }

    /**
     * Starts this service to perform action Upload with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpload(Context context, Uri uri, String title, String desc) {
        Intent intent = new Intent(context, HandlerService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(UPLOAD_URI, uri.toString());
        intent.putExtra(UPLOAD_TITLE, title);
        intent.putExtra(UPLOAD_DESC, desc);
        //context.startService(intent);
    }

    /**
     * Starts this service to perform action Fetch with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFetch(Context context, String param1, String param2) {
        /*Intent intent = new Intent(context, HandlerService.class);
        intent.setAction(ACTION_FETCH);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);*/
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
            } else if (ACTION_FETCH.equals(action)) {
                // TODO : to FETCH data from imgur
                /*final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFetch(param1, param2);*/
            }
        }
    }

    /**
     * Handle action Upload in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload(Uri uri, String title, String description) {

        // TODO : upload picture to imgur


        // TODO : display toast message (or toolbar notification) "upload finished"
        // https://developer.android.com/guide/components/services#Notifications


        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Fetch in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetch(String param1, String param2) {
        // TODO: Handle action Fetch
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
