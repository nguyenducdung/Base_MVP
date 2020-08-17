package com.dungnd.basemvp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.DialogConfirmBinding;

public class DialogConfirm {
    private static Dialog dialog;

    public void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            dialog.show();
        }
    }

    public void setOnKeyListener() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.setOnKeyListener((dialogInterface, keyCode, event) -> true);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            dialog.show();
        }
    }

    public DialogConfirm(Context context, String title, String message,
                         String negative, View.OnClickListener onNegativeClick,
                         String positive, View.OnClickListener onPositiveClick, boolean isCanceledOnTouchOutside) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        dialog = new Dialog(context);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        DialogConfirmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_confirm, null, false);
        dialog.setContentView(binding.getRoot());
        if (!TextUtils.isEmpty(message)) {
            binding.tvContent.setText(message);
        } else {
            binding.tvContent.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            binding.tvTitle.setText(title);
        } else {
            binding.tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(positive)) {
            binding.btnRetry.setText(positive);
        } else {
            binding.btnRetry.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(negative)) {
            binding.btnClose.setText(negative);
        } else {
            binding.btnClose.setVisibility(View.GONE);
        }
        binding.btnRetry.setOnClickListener(v -> {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (onPositiveClick != null) {
                onPositiveClick.onClick(v);
            }
        });

        binding.btnClose.setOnClickListener(v -> {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (onNegativeClick != null) {
                onNegativeClick.onClick(v);
            }
        });
    }
}
