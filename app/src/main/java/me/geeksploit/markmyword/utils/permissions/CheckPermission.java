package me.geeksploit.markmyword.utils.permissions;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

public class CheckPermission {

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean externalStorage(AppCompatActivity context) {
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            context.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PermissionRequestCodes.READ_WRITE);
            return false;
        }
    }

    public static Pair<Boolean, Boolean> onRequestRWResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean write = false;
        boolean read = false;
        if (requestCode == PermissionRequestCodes.READ_WRITE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                read = true;
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                write = true;
            }
        }
        return new Pair<>(read, write);
    }
}
