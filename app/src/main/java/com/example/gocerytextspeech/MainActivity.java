package com.example.gocerytextspeech;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private TextToSpeech toSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        checkPermission();

        final EditText editText = findViewById(R.id.editText);
        final ArrayList<String> shoppingList ;
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    toSpeech.setLanguage(Locale.CANADA);
                }
            }
        });

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.i("Contol is here on error","onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i("Contol is here on error","onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float v) {
                Log.i("Contol is here on error","onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.i("Contol is here on error","onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.i("Contol is here on error","onEndOfSpeech");
            }

            @Override
            public void onError(int i) {
                Log.i("Contol is here on error","onError");
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList testList = new ArrayList();
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.i("Contol is here on ","onResults");
                //displaying the first match
                if (matches != null)
                    editText.setText(matches.get(0));
                testList.add(matches.get(0)) ;            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.i("Contol is here on ","onPartialResults");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.i("Contol is here on ","onEvent");
            }
        });

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");
                        String toSpeak = "What do you want to order";
                        toSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                        break;
                }
                return false;
            }
        });
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}