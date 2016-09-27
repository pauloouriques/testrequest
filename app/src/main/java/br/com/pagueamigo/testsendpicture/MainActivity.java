package br.com.pagueamigo.testsendpicture;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.pagueamigo.testsendpicture.entities.Transaction;
import br.com.pagueamigo.testsendpicture.rest.OnTaskListener;
import br.com.pagueamigo.testsendpicture.rest.tasks.AddRequestTransactionTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AddRequestTransactionTask(
                this,
                new OnTaskListener() {
                    @Override
                    public void onSuccess(AsyncTask task, Object result) {

                    }

                    @Override
                    public void onFailure(AsyncTask task, Object error) {

                    }

                    @Override
                    public void onCancel(AsyncTask task) {

                    }
                }).execute(new Transaction());

    }

}
