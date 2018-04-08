package com.example.john.direccionip;


import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText EditMask;
    private TextView Broad, CantHost, PartRed, PartHost;
    private String mask;
    int Cant_de_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.buscar);
        EditMask = (EditText) findViewById(R.id.mascara);
        Broad = (TextView) findViewById(R.id.Broadcast);
        CantHost = (TextView) findViewById(R.id.Cant_Host);
        PartRed = (TextView) findViewById(R.id.ParteRed);
        PartHost = (TextView) findViewById(R.id.ParteHost);


        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                //Calculando Cantidad de Host
                //obtengo la mascara como cadena
                mask = EditMask.getText().toString();
                //calculando cantidad de host
                Cant_de_ip = 2 * (32 - Integer.parseInt(mask)) * 2 * (32 - Integer.parseInt(mask)) - 2;
                CantHost.setText(Cant_de_ip + "");


                //Calculando NetID
                //obteniendo direccion ip
                EditText EditIP = (EditText) findViewById(R.id.Ip);
                TextView NetID = (TextView) findViewById(R.id.net_id);
                String ip = EditIP.getText().toString();
                //dividiendo la direccion ip
                String[] parts = ip.split("[.]");
                int part1, part2, part3, part4;
                part1 = Integer.parseInt(parts[0]);
                part2 = Integer.parseInt(parts[1]);
                part3 = Integer.parseInt(parts[2]);
                part4 = Integer.parseInt(parts[3]);
                int[] array = new int[32];

                int mask1 = 0, mask2 = 0, mask3 = 0, mask4 = 0;
                int net1, net2, net3, net4;

                for (int i = 0; i < Integer.parseInt(mask); i++) {
                    array[i] = 1;
                }
                for (int i = Integer.parseInt(mask); i < 31; i++) {
                    array[i] = 0;
                }

                for (int i = 0; i < 8; i++) {
                    if (array[i] == 1) {
                        mask1 = mask1 + (int) Math.pow(2, 7 - i);
                    }
                    if (array[i + 8] == 1) {
                        mask2 = mask2 + (int) Math.pow(2, 7 - i);
                    }
                    if (array[i + 16] == 1) {
                        mask3 = mask3 + (int) Math.pow(2, 7 - i);
                    }
                    if (array[i + 24] == 1) {
                        mask4 = mask4 + (int) Math.pow(2, 7 - i);
                    }
                }

                net1 = part1 & mask1;
                net2 = part2 & mask2;
                net3 = part3 & mask3;
                net4 = part4 & mask4;
                NetID.setText("" + net1 + "." + net2 + "." + net3 + "." + net4);

                //Calculando Broadcast
                int bro1 = 0, bro2 = 0, bro3 = 0, bro4 = 0;
                int BroadC1, BroadC2, BroadC3, BroadC4;
                int[] arrayBroad = new int[32];

                for (int i = 0; i < 32; i++) {
                    if (array[i] == 0) {
                        arrayBroad[i] = 1;
                    } else if (array[i] == 1) {
                        arrayBroad[i] = 0;
                    }
                }

                for (int i = 0; i < 8; i++) {
                    if (arrayBroad[i] == 1) {
                        bro1 = bro1 + (int) Math.pow(2, 7 - i);
                    }
                    if (arrayBroad[i + 8] == 1) {
                        bro2 = bro2 + (int) Math.pow(2, 7 - i);
                    }
                    if (arrayBroad[i + 16] == 1) {
                        bro3 = bro3 + (int) Math.pow(2, 7 - i);
                    }
                    if (arrayBroad[i + 24] == 1) {
                        bro4 = bro4 + (int) Math.pow(2, 7 - i);
                    }
                }

                BroadC1 = part1 | bro1;
                BroadC2 = part2 | bro2;
                BroadC3 = part3 | bro3;
                BroadC4 = part4 | bro4;
                Broad.setText("" + BroadC1 + "." + BroadC2 + "." + BroadC3 + "." + BroadC4);

                //Calculando parte de Red y parte de Host
                PartRed.setText("" + net1 + "." + net2 + "." + net3 + "." + net4);

                int PartHost1, PartHost2, PartHost3, PartHost4;

                PartHost1 = part1 & bro1;
                PartHost2 = part2 & bro2;
                PartHost3 = part3 & bro3;
                PartHost4 = part4 & bro4;
                PartHost.setText(PartHost1 + "." + PartHost2 + "." + PartHost3 + "." + PartHost4);
            }
        });
    }
}