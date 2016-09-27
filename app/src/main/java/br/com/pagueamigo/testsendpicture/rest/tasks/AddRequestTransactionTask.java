package br.com.pagueamigo.testsendpicture.rest.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

import br.com.pagueamigo.testsendpicture.entities.Transaction;
import br.com.pagueamigo.testsendpicture.rest.OnTaskListener;
import br.com.pagueamigo.testsendpicture.rest.RetrofitAPI;
import br.com.pagueamigo.testsendpicture.rest.requestpojo.ApiError;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * This class represents the find user task.
 */
public class AddRequestTransactionTask extends AsyncTask<Transaction, Void, Object> {

    private Activity mActivity;
    private ProgressDialog mPDialog;
    private OnTaskListener mListener;

    /**
     * Constructor method.
     *
     * @param listener - Task listener.
     */
    public AddRequestTransactionTask(Activity activity, OnTaskListener listener) {
        mActivity = activity;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mPDialog = new ProgressDialog(mActivity);
        mPDialog.show();
    }

    @Override
    protected Object doInBackground(Transaction... params) {
        try {
            MultipartBody.Part filePart = null;
            if (!params[0].imagePath.equals("")) {
                File file = new File(params[0].imagePath);
                filePart = MultipartBody.Part.createFormData(
                        "file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
            }

            RequestBody friend = RequestBody.create(
                    MediaType.parse("application/json"),
                    params[0].toJsonString());

            Call<Void> call = new RetrofitAPI().client.addRequestTransaction(
                    "COLOCAR O TOKEN AQUI",
                    "COLOCAR O SEU ID AQUI",
                    friend,
                    filePart
            );


            Response<Void> response = call.execute();

            System.out.println(params[0].toJsonString() + "<<<<<<<<---------------<<<");

            switch (response.code()) {
                case 204:
                    return response.body();
                case 500:
                    return new ApiError(500, "400 Bad Request");
                default:
                    return response.body();
            }


        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        mPDialog.dismiss();

        if (this.isCancelled()) {
            Log.w(getClass().getSimpleName(), "onCancel()");
            if (mListener != null) {
                mListener.onCancel(this);
            }
        } else if (result instanceof ApiError || result instanceof Exception) {
            Log.w(getClass().getSimpleName(), "(onFailure)");
            if (mListener != null) {
                mListener.onFailure(this, result);
            }
        } else {
            Log.w(getClass().getSimpleName(), "onSuccess()");
            if (mListener != null) {
                mListener.onSuccess(this, result);
            }
        }
    }
}
