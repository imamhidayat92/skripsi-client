package id.ac.paramadina.absensi.reference.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.GlobalData;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.User;

public class UserDetailDialog extends DialogFragment {

    private User user;

    private String API_ADDRESS;

    public interface UserDetailDialogListener {
        public void onCloseButtonClick(DialogFragment dialog);
        public void onRemoveButtonClick(DialogFragment dialog);
    }

    private UserDetailDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        this.API_ADDRESS = preferences.getString(GlobalData.PREFERENCE_API_ADDRESS, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_user_detail, null);

        TextView txtMajor = (TextView) view.findViewById(R.id.user_major);
        TextView txtName = (TextView) view.findViewById(R.id.user_name);
        TextView txtIdNumber = (TextView) view.findViewById(R.id.user_id_number);
        TextView txtEmail = (TextView) view.findViewById(R.id.user_email);
        TextView txtPhone = (TextView) view.findViewById(R.id.user_phone);
        TextView txtAddress = (TextView) view.findViewById(R.id.user_address);
        ImageView imgDisplayPicture = (ImageView) view.findViewById(R.id.user_display_picture);

        if (this.user != null) {
            Major major = this.user.getMajor();
            if (major != null) {
                txtMajor.setText(major.getName());
                if (major.getColor() != null) {
                    txtMajor.setBackgroundColor(Color.parseColor(major.getColor()));
                }
            }
            else {
                // Should not be happened at all.
                txtMajor.setText("Tidak Diketahui");
            }
            txtName.setText(this.user.getName());
            txtIdNumber.setText(this.user.getIdNumber());
            txtEmail.setText(this.user.getEmail());
            txtPhone.setText(this.user.getPhone());
            txtAddress.setText(this.user.getAddress());

            String displayPicture = this.user.getDisplayPicture();
            if (displayPicture != null) {
                Glide.with(this.getActivity())
                        .load(this.API_ADDRESS + "/" + displayPicture)
                        .centerCrop()
                        .crossFade()
                        .into(imgDisplayPicture);
            }
        }

        builder.setView(view)
            .setNegativeButton("Hapus Data Kehadiran", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onRemoveButtonClick(UserDetailDialog.this);
                }
            })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onCloseButtonClick(UserDetailDialog.this);
                }
            });

        return builder.create();
    }

    public void setListener(UserDetailDialogListener listener) {
        this.listener = listener;
    }

    public UserDetailDialog setUser(User user) {
        this.user = user;
        return this;
    }
}
