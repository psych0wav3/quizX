package com.example.quizx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private String respostaCorreta;
    private int respostaCorretaContador;
    private int quizCount = 1;

    static final private int QUIZCOUNT = 3;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][]={
            {"Parana","Curitiba", "Londrina", "Arapongas", "Astorga"},
            {"São Paulo","São Paulo","Rancharia","Assis","Campinas"},
            {"Minas Gerais","Belo Horizonte","Ouro Preto","SRS","Itajubá"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i < quizData.length; i++){
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]);
            tmpArray.add(quizData[i][1]);
            tmpArray.add(quizData[i][2]);
            tmpArray.add(quizData[i][3]);
            tmpArray.add(quizData[i][4]);

            quizArray.add(tmpArray);
        }

        shownextQuiz();
    }

    public void shownextQuiz(){

        TextView contadorLabel = (TextView) findViewById(R.id.contadorLabel);
        contadorLabel.setText("Q"+quizCount);

        Random random = new Random();
        int randonNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randonNum);

        TextView perguntaLabel = (TextView) findViewById(R.id.perguntaLabel);
        perguntaLabel.setText(quiz.get(0));

        respostaCorreta = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        Button respostaBtn1 = (Button) findViewById(R.id.respostaBtn1);
        respostaBtn1.setText(quiz.get(0));
        Button respostaBtn2 = (Button) findViewById(R.id.respostaBtn2);
        respostaBtn2.setText(quiz.get(1));
        Button respostaBtn3 = (Button) findViewById(R.id.respostaBtn3);
        respostaBtn3.setText(quiz.get(2));
        Button respostaBtn5 = (Button) findViewById(R.id.respostaBtn5);
        respostaBtn5.setText(quiz.get(3));

        quizArray.remove(randonNum);
    }

    public void checkResposta(View view){
        Button respostaBtn = (Button) findViewById(view.getId());
        String btnText = respostaBtn.getText().toString();

        String alertTitle;

        if(btnText.equals(respostaCorreta)){
            alertTitle = "Correto! :) ";
            respostaCorretaContador++;
        }
        else{
            alertTitle = "Você Errou! :(";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle(alertTitle);
        builder.setMessage("Ressposta: "+ respostaCorreta + "\nPntuação: "+respostaCorretaContador);
        builder.setPositiveButton("PROX", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if(quizCount != QUIZCOUNT){
                   quizCount++;
                   shownextQuiz();
               }
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("DESISTIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (quizCount != QUIZCOUNT) {
                    quizCount++;
                    shownextQuiz();
                }
            }
            });
        builder.show();
    }

    public void resFinal(){
        String finalScore;
        if(respostaCorretaContador==QUIZCOUNT){
            finalScore = "Você acertou todas! PARABENS!!!";
        }
        else{
            finalScore = "Você não acertou todas :(";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RESULTADO FINAL");
        builder.setMessage(finalScore+"\nPontuação: "+ respostaCorretaContador);
        builder.setNeutralButton("RECOMEÇAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent restartIntent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(restartIntent);
            }
            });
        builder.setCancelable(false);
        builder.show();

    }
}