package com.dyason.ip_scan;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class AsyncTaskTestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynctest);

        //Starting the task. Pass an url as the parameter.
        new PostTask().execute("http://feeds.pcworld.com/pcworld/latestnews");
    }

    // The definition of our task class
    private class PostTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayProgressBar();
        }

        @Override
        protected String doInBackground(String... params) {
            String url=params[0];

            // Dummy code
            for (int i = 0; i <= 100; i += 5) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return "All Done!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissProgressBar();
        }

        protected void displayProgressBar(){
            ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar);
            bar.setVisibility(View.VISIBLE); //View.INVISIBLE, or View.GONE to hide it.
        }

        protected void updateProgressBar(Integer... values){
            ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar);
            bar.setProgress(values[0]);
        }

        protected void dismissProgressBar(){
            ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar);
            bar.setVisibility(View.VISIBLE); //View.INVISIBLE, or View.GONE to hide it.
        }
    }
}
