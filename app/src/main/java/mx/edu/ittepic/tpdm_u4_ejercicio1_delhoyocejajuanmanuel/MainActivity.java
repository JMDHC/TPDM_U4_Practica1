package mx.edu.ittepic.tpdm_u4_ejercicio1_delhoyocejajuanmanuel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView ronda, jugador, ganador;
    TextView[] etiquetas, totales;
    ImageView[] dados;

    TextView etiquetaActual, totalActual;
    ImageView[] dadosActuales;
    int numeroActual;

    Button iniciar;

    Thread padre, hijo;

    int[] numeros;
    int[] total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ronda = findViewById(R.id.ronda);
        jugador = findViewById(R.id.jugador);
        ganador = findViewById(R.id.resultado);

        etiquetas = new TextView[12];

        etiquetas[0] = findViewById(R.id.j1t1);
        etiquetas[1] = findViewById(R.id.j2t1);
        etiquetas[2] = findViewById(R.id.j3t1);

        etiquetas[3] = findViewById(R.id.j1t2);
        etiquetas[4] = findViewById(R.id.j2t2);
        etiquetas[5] = findViewById(R.id.j3t2);

        etiquetas[6] = findViewById(R.id.j1t3);
        etiquetas[7] = findViewById(R.id.j2t3);
        etiquetas[8] = findViewById(R.id.j3t3);

        etiquetas[9] = findViewById(R.id.j1t4);
        etiquetas[10] = findViewById(R.id.j2t4);
        etiquetas[11] = findViewById(R.id.j3t4);

        totales = new TextView[3];

        totales[0] = findViewById(R.id.j1to);
        totales[1] = findViewById(R.id.j2to);
        totales[2] = findViewById(R.id.j3to);

        dados = new ImageView[6];

        dados[0] = findViewById(R.id.j1d1);
        dados[1] = findViewById(R.id.j1d2);

        dados[2] = findViewById(R.id.j2d1);
        dados[3] = findViewById(R.id.j2d2);

        dados[4] = findViewById(R.id.j3d1);
        dados[5] = findViewById(R.id.j3d2);

        dadosActuales = new ImageView[2];

        iniciar = findViewById(R.id.iniciar);

        numeros = new int[12];

        total = new int[3];

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarTodo();
                inicilizarPadre();
                padre.start();
            }
        });
    }

    private void inicilizarPadre() {
        padre = new Thread() {
            public void run() {
                for (int i = 0; i<numeros.length; i++) {
                    numeros[i] =  (int)(Math.random() * (13 - 2) + 2);
                }
                for (int i = 0, j = 0, p = 1; i < 12; i++, j+=2, p++){
                    if (j == 6) {
                        j = 0;
                    }
                    if (p == 4) {
                        p = 1;
                    }
                    inicializarHijo((i/3)+1, p);
                    numeroActual = numeros[i];
                    dadosActuales[0] = dados[j];
                    dadosActuales[1] = dados[j+1];
                    etiquetaActual = etiquetas[i];
                    totalActual = totales[p-1];
                    hijo.start();
                    try {
                        sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ronda.setText("0");
                        jugador.setText("0");
                    }
                });
                anunciarGanador();
            }
        };
    }

    private void inicializarHijo(final int nRonda, final int nJugador){
        hijo = new Thread(){
            public void run () {
                switch (nJugador) {
                    case 1:
                        total[0] += numeroActual;
                        break;
                    case 2:
                        total[1] += numeroActual;
                        break;
                    case 3:
                        total[2] += numeroActual;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jugador.setText(nJugador+"");
                        ronda.setText(nRonda+"");
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        establecerDados();
                    }
                });
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etiquetaActual.setText("Tiro "+ nRonda + ": "+ numeroActual);
                        totalActual.setText("Total: " + total[nJugador-1]+"");
                    }
                });
            }
        };
    }

    private void establecerDados () {
        switch (numeroActual) {
            case 2:
                dadosActuales[0].setImageResource(R.drawable.dice1);
                dadosActuales[1].setImageResource(R.drawable.dice1);
                break;

            case 3:
                dadosActuales[0].setImageResource(R.drawable.dice2);
                dadosActuales[1].setImageResource(R.drawable.dice2);
                break;

            case 4:
                dadosActuales[0].setImageResource(R.drawable.dice2);
                dadosActuales[1].setImageResource(R.drawable.dice2);
                break;

            case 5:
                dadosActuales[0].setImageResource(R.drawable.dice3);
                dadosActuales[1].setImageResource(R.drawable.dice2);
                break;

            case 6:
                dadosActuales[0].setImageResource(R.drawable.dice3);
                dadosActuales[1].setImageResource(R.drawable.dice3);
                break;

            case 7:
                dadosActuales[0].setImageResource(R.drawable.dice4);
                dadosActuales[1].setImageResource(R.drawable.dice3);
                break;

            case 8:
                dadosActuales[0].setImageResource(R.drawable.dice4);
                dadosActuales[1].setImageResource(R.drawable.dice4);
                break;

            case 9:
                dadosActuales[0].setImageResource(R.drawable.dice5);
                dadosActuales[1].setImageResource(R.drawable.dice4);
                break;

            case 10:
                dadosActuales[0].setImageResource(R.drawable.dice5);
                dadosActuales[1].setImageResource(R.drawable.dice5);
                break;

            case 11:
                dadosActuales[0].setImageResource(R.drawable.dice6);
                dadosActuales[1].setImageResource(R.drawable.dice5);
                break;

            case 12:
                dadosActuales[0].setImageResource(R.drawable.dice6);
                dadosActuales[1].setImageResource(R.drawable.dice6);
        }
    }

    private void anunciarGanador () {
        if (total[0] > total[1]) {
            if (total[0] > total[2]) {
                ganador.setText("Ganador: Jugador 1");
            } else {
                if (total[0] == total[2]) {
                    ganador.setText("Ganador: Jugador 1 y Jugador 3");
                } else {
                    ganador.setText("Ganador: Jugador 3");
                }
            }
        } else {
            if (total[0] == total[1]) {
                if (total[0] > total[2]) {
                    ganador.setText("Ganador: Jugador 1 y Jugador 2");
                } else {
                    if (total[0] == total[2]){
                        ganador.setText("Ganador: Jugador 1, Jugador 2 y Jugador 3");
                    } else {
                        ganador.setText("Ganador: Jugador 3");
                    }
                }
            } else {
                if (total[1] > total[2]) {
                    ganador.setText("Ganador: Jugador 2");
                } else {
                    if (total[1] == total[2]) {
                        ganador.setText("Ganador: Jugador 2 y Jugador 3");
                    } else {
                        ganador.setText("Ganador: Jugador 3");
                    }
                }
            }
        }

    }

    private void reiniciarTodo() {
        for (int i = 0; i < total.length; i++) {
            total[i] = 0;
        }
        for (int i = 0; i < etiquetas.length; i++) {
            etiquetas[i].setText(etiquetas[i].getText().toString().substring(0,7));
        }
        for (int i = 0; i < totales.length; i++) {
            totales[i].setText("Total: ");
        }
        ganador.setText("Ganador:   ");
    }
}
