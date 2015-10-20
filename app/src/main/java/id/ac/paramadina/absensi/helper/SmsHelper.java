package id.ac.paramadina.absensi.helper;

import android.telephony.SmsManager;

import id.ac.paramadina.absensi.reference.model.User;

public class SmsHelper {

    SmsManager manager;

    public SmsHelper() {
        this.manager = SmsManager.getDefault();
    }

    public boolean sendMessage(User user, String message) {
        if (message.trim().length() > 160 || message.trim().length() == 0) {
            return false;
        }
        String phoneNumber = user.getPhone();
        if (phoneNumber == null) {
            return false;
        }
        try {
            this.manager.sendTextMessage(phoneNumber, null, message.trim(), null, null);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

}
