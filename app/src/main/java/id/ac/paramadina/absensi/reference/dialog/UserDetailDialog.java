package id.ac.paramadina.absensi.reference.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.User;

public class UserDetailDialog extends DialogFragment {

    private User user;

    public interface UserDetailDialogListener {
        public void onCloseButtonClick(DialogFragment dialog);
        public void onRemoveButtonClick(DialogFragment dialog);
    }

    private UserDetailDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_user_detail, null);

        TextView txtMajor = (TextView) view.findViewById(R.id.user_major);
        TextView txtName = (TextView) view.findViewById(R.id.user_name);
        TextView txtIdNumber = (TextView) view.findViewById(R.id.user_id_number);

        if (this.user != null) {
            Major major = this.user.getMajor();
            if (major != null) {
                txtMajor.setText(major.getName());
            }
            else {
                txtMajor.setText("Umum");
            }
            txtName.setText(this.user.getName());
            txtIdNumber.setText(this.user.getIdNumber());
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
