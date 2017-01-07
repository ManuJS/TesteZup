package com.manu.projeto.testezup.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manu.projeto.testezup.R;
import com.manu.projeto.testezup.data.MovieDbHelper;

/**
 * Created by emanu on 05/01/2017.
 */

public class FavoritoActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, SimpleAdapter.ViewBinder {

    private static final String TAG = FavoritoActivity.class.getSimpleName();
    ListView listView;
    Context mContext;

    private MovieDbHelper helper;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new MovieDbHelper(this);
        new Task().execute((Void[]) null);
        ListView listView = (ListView) findViewById(R.id.favorito_list_view);

    }

    @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class Task extends AsyncTask<Void, Void, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            return listarFilmes();
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
        String[] de = {"poster","title",
                    "type",
                    "released"
                    };


            int[] para = {R.id.poster, R.id.title,
                    R.id.subtitle, R.id.description};

            SimpleAdapter adapter =
                    new SimpleAdapter(FavoritoActivity.this, result, R.layout.list_item_movie, de, para);

            adapter.setViewBinder(FavoritoActivity.this);
            setListAdapter(adapter);
        }
    }

    private List<Map<String, Object>> filmes;


//    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }

    private List<Map<String, Object>> listarFilmes() {


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT movie_id, title, poster, type, released FROM movie", null);

        cursor.moveToFirst();

        filmes = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < cursor.getCount(); i++) {

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "não filmes salvos", Toast.LENGTH_SHORT).show();
            } else {

                Map<String, Object> item = new HashMap<String, Object>();

                String movieId = cursor.getString(0);
                String title = cursor.getString(1);
                String type = cursor.getString(3);
                String released = cursor.getString(4);

                String poster = cursor.getString(2);

                item.put("movie_id", movieId);

                item.put("title", title);

                item.put("poster", android.R.drawable.ic_media_play);

                item.put("type", type);
                item.put("released", released);


                filmes.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return filmes;
    }
}
