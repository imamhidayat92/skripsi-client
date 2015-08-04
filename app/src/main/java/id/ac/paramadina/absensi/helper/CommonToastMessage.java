package id.ac.paramadina.absensi.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * A helper class to show*Message(...) using Toast.
 */
public class CommonToastMessage {

    public static void showErrorGettingDataFromServerMessage(Context context) {
        Toast.makeText(context, "Gagal mengambil data dari server.", Toast.LENGTH_LONG).show();
    }

}
