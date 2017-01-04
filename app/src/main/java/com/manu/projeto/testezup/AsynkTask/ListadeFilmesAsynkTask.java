package com.manu.projeto.testezup.AsynkTask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by emanu on 01/09/2016.
 */
public class ListadeFilmesAsynkTask extends AsyncTask<String, Void, String[]> {
    private final String LOG_TAG = ListadeFilmesAsynkTask.class.getSimpleName();

    @Override
    protected String[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String listadefilmesJSON = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
//            final String LISTAFILMES_BASE_URL =
//                    "http://api.themoviedb.org/3/discover/movie?api_key=2ec29dd8d95f2933e1af2acb2f1e48e3&language=pt&include_image_language=pt,null";

            URL url = new URL("http://api.themoviedb.org/3/discover/movie?" +
                    "api_key=2ec29dd8d95f2933e1af2acb2f1e48e3&language=pt&include_image_language=pt,null");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            listadefilmesJSON = buffer.toString();

            Log.v(LOG_TAG, "Lista de Filmes: " + listadefilmesJSON);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error de conexao", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }
}
