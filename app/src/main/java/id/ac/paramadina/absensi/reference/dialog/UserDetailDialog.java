package id.ac.paramadina.absensi.reference.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import id.ac.paramadina.absensi.R;
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

        builder.setView(view)
            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onCloseButtonClick(UserDetailDialog.this);
                }
            })
            .setPositiveButton("Hapus Data Kehadiran", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onRemoveButtonClick(UserDetailDialog.this);
                }
            });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setListener(UserDetailDialogListener listener) {
        this.listener = listener;
    }

    public UserDetailDialog setUser(User user) {
        this.user = user;
        return this;
    }
}
