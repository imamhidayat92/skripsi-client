package id.ac.paramadina.absensi.reference.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import id.ac.paramadina.absensi.R;

public class SimplePromptDialog extends DialogFragment {

    private String message;

    public interface SimplePromptDialogListener {
        public void onPositiveButtonClick(DialogFragment dialog, String value);
        public void onNegativeButtonClick(DialogFragment dialog);
    }

    SimplePromptDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_simple_prompt, null);

        final EditText txtInput = (EditText) view.findViewById(R.id.txtInput);

        builder
            .setMessage(this.message)
            .setView(view)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (listener != null) {
                        listener.onPositiveButtonClick(SimplePromptDialog.this, String.valueOf(txtInput.getText()));
                    }
                }
            })
            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (listener != null) {
                        listener.onNegativeButtonClick(SimplePromptDialog.this);
                    }
                }
            })
        ;

        return builder.create();
    }

    public SimplePromptDialog setListener(SimplePromptDialogListener listener) {
        this.listener = listener;
        return this;
    }

    public SimplePromptDialog setMessage(String message) {
        this.message = message;
        return this;
    }
}
