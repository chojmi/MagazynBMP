package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;

import michalchojnacki.magazynbmp.R;

public class LoadingDbDialog extends DialogFragment {

    private static ProgressDialog mProgressDialog;
    private static Context mContext;
    private static int valuesSaved;

    public void start(final Context context) {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mContext = context;
                    mProgressDialog = ProgressDialog.show(context,
                            context.getString(R.string.LoadingLabel),
                            context.getString(R.string.WaitWhileLoadingLabel), true);
                    valuesSaved = 0;
                }
            });
        }
    }

    public void nextValueSaved() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setMessage(mContext.getString(R.string.LoadedLabel) + ++valuesSaved + mContext.getString(R.string.SparePartsLabel));
            }
        });
    }

    public void stop() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

}
