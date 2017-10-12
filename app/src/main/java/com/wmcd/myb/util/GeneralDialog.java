package com.wmcd.myb.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.wmcd.myb.R;

/**
 * The type General dialog.
 *
 * @author zxz
 */
public class GeneralDialog extends Dialog {
    /**
     * The Title.
     */
    private String title = "提示";
    /**
     * The Msg.
     */
    private String msg;
    /**
     * The Lp.
     */
    private LayoutParams lp;
    /**
     * The Alpha.
     */
    private float alpha = 0.97f;
    /**
     * The Dialog cancel.
     */
    private TextView dialog_cancel;
    /**
     * The Dialog confirm.
     */
    private TextView dialog_confirm;
    /**
     * The Dialog title.
     */
    private TextView dialog_title;
    /**
     * The Dialog msg.
     */
    private TextView dialog_msg;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Cancel listener.
     */
    private OnCancelListener cancelListener;
    /**
     * The Confirm listener.
     */
    private OnConfirmListener confirmListener;
    /**
     * The Button line.
     */
    private View button_line;

    /**
     * The interface On cancel listener.
     */
    public interface OnCancelListener {
        /**
         * Cancel.
         */
        void cancel();
    }

    /**
     * The interface On confirm listener.
     */
    public interface OnConfirmListener {
        /**
         * Confirm.
         */
        void confirm();
    }

    /**
     * Instantiates a new General dialog.
     *
     * @param context the context
     */
    public GeneralDialog(Context context) {
        super(context, R.style.add_dialog);
        this.context = context;
        init();
    }

    /**
     * Instantiates a new General dialog.
     *
     * @param context the context
     * @param theme   the theme
     */
    public GeneralDialog(Context context, int theme) {
        super(context, R.style.add_dialog);
        this.context = context;
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * Init.
     */
    private void init() {
        View view = View.inflate(context, R.layout.dialog_layout, null);
        setContentView(view);
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.5f;
        lp.y = -20;
        lp.alpha = alpha;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setAttributes(lp);
        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        dialog_msg = (TextView) view.findViewById(R.id.dialog_msg);
        dialog_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
        dialog_confirm = (TextView) view.findViewById(R.id.dialog_confirm);
        button_line = view.findViewById(R.id.button_line);
    }

    /**
     * Build.
     */
    private void build() {
        dialog_title.setText(title);
        dialog_msg.setText(msg);
        if (cancelListener == null) {
            dialog_cancel.setVisibility(View.GONE);
            button_line.setVisibility(View.GONE);
        }
        if (confirmListener == null) {
            dialog_confirm.setVisibility(View.GONE);
            button_line.setVisibility(View.GONE);
        }
    }

    /**
     * Sets cancel button.
     *
     * @param text           the text
     * @param cancelListener the cancel listener
     */
    public void setCancelButton(String text, final OnCancelListener cancelListener) {
        dialog_cancel.setText(text);
        if (cancelListener == null)
            this.cancelListener = new OnCancelListener() {
                @Override
                public void cancel() {

                }
            };
        else
            this.cancelListener = cancelListener;
        dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (cancelListener != null)
                    cancelListener.cancel();
                GeneralDialog.this.dismiss();
            }
        });
    }

    /**
     * Show.
     */
    @Override
    public void show() {
        // TODO Auto-generated method stub
        build();
        super.show();
    }

    /**
     * Sets confrim button.
     *
     * @param text            the text
     * @param confirmListener the confirm listener
     */
    public void setConfrimButton(String text, final OnConfirmListener confirmListener) {
        dialog_confirm.setText(text);
        this.confirmListener = confirmListener;
        dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                confirmListener.confirm();
                GeneralDialog.this.dismiss();
            }
        });
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        /**
         * The Dialog.
         */
        GeneralDialog dialog;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(Context context) {
            dialog = new GeneralDialog(context);
        }

        /**
         * Sets title.
         *
         * @param title the title
         * @return the title
         */
        public Builder setTitle(String title) {
            dialog.setTitle(title);
            return this;
        }

        /**
         * Sets msg.
         *
         * @param msg the msg
         * @return the msg
         */
        public Builder setMsg(String msg) {
            dialog.setMsg(msg);
            return this;
        }

        /**
         * Sets cancelable.
         *
         * @param flag the flag
         * @return the cancelable
         */
        public Builder setCancelable(boolean flag) {
            dialog.setCancelable(flag);
            return this;
        }

        /**
         * Sets confrim button.
         *
         * @param text            the text
         * @param confirmListener the confirm listener
         * @return the confrim button
         */
        public Builder setConfrimButton(String text, final OnConfirmListener confirmListener) {
            dialog.setConfrimButton(text, confirmListener);
            return this;
        }

        /**
         * Sets confrim button.
         *
         * @param text the text
         * @return the confrim button
         */
        public Builder setConfrimButton(String text) {
            dialog.setConfrimButton(text, new OnConfirmListener() {

                @Override
                public void confirm() {
                    // TODO Auto-generated method stub

                }
            });
            return this;
        }

        /**
         * Sets cancel button.
         *
         * @param text           the text
         * @param cancelListener the cancel listener
         * @return the cancel button
         */
        public Builder setCancelButton(String text, final OnCancelListener cancelListener) {
            dialog.setCancelButton(text, cancelListener);
            return this;
        }

        /**
         * Sets cancel button.
         *
         * @param text the text
         * @return the cancel button
         */
        public Builder setCancelButton(String text) {
            dialog.setCancelButton(text, new OnCancelListener() {

                @Override
                public void cancel() {
                    // TODO Auto-generated method stub

                }
            });
            return this;
        }

        /**
         * Build general dialog.
         *
         * @return the general dialog
         */
        public GeneralDialog build() {
            dialog.build();
            return dialog;
        }

        /**
         * Show.
         */
        public void show() {
            // TODO Auto-generated method stub
            dialog.show();
        }
    }
}
