package com.dungnd.basemvp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.dungnd.basemvp.R;

public class DialogLoading {

    private boolean shown = false;

    private AlertDialog dialog = null;

    private static DialogLoading instance = null;

    private boolean hasActivityPermission = false;

    private Context context;

    public static DialogLoading getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new DialogLoading(context);
            return instance;
        }
    }

    private DialogLoading(Context context) {
        this.context = context;
        if (context != null && !shown) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater li = LayoutInflater.from(context);
            View dialogView = li.inflate(R.layout.dialog_loading, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            dialog = dialogBuilder.create();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    public void show() {
        if (!((Activity) context).isFinishing()) {
            if (!shown && dialog != null) {
                forceShown();
                dialog.show();
            }
        }
    }

    public void hidden() {
        if (shown && dialog != null && dialog.isShowing() && !hasActivityPermission) {
            initialize();
            dialog.dismiss();
        }
    }

    private void forceShown() {
        shown = true;
    }

    private void initialize() {
        shown = false;
    }

    public void destroyLoadingDialog() {
        if (instance != null && instance.dialog != null) {
            instance.dialog.dismiss();
        }
        instance = null;
    }
}
