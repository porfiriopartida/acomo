package com.porfiriopartida.acomo.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.Arrays;

public class ContactsUtils {
    public static String retrieveContactName(Context ctx, String number) {
        String contactName = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        Cursor cursor =
                ctx.getContentResolver()
                        .query(uri, null, null, null,
                                null);

        int id = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);

        if (cursor.moveToFirst()) {
            contactName = cursor.getString(id);
        }
        cursor.close();
        return contactName;
    }

    public static boolean isContact(Context ctx, String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor =
                ctx.getContentResolver()
                        .query(uri, null, null, null,
                                null);

        boolean isContact = cursor.moveToFirst();

        cursor.close();

        return isContact;
    }
    public static boolean contains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
}
