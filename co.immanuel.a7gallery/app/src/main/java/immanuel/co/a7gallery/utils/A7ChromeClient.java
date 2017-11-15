package immanuel.co.a7gallery.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import immanuel.co.a7gallery.MainActivity;

/**
 * Created by Immanuel on 11/5/2017.
 */

public class A7ChromeClient extends WebChromeClient {

    MainActivity activity;

    public  A7ChromeClient(MainActivity act){
        activity = act;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(activity)
                .setTitle("Alert: Within 30")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do your stuff
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();
        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //Log.d("W30", view.getUrl() + " -- " + newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
}
