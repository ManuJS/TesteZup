package com.manu.projeto.testezup.util;

import android.content.Context;
import android.database.Cursor;

import com.manu.projeto.testezup.data.MovieContract;

/**
 * Created by emanu on 05/01/2017.
 */

public class Utility {

    public static int isFavorited(Context context, String id) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[] { id },
                null
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }
}
