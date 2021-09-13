package com.example.segundodesafio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Bola extends View implements SensorEventListener {     //extiendo de view para que sea una vista e implemento el listener para el acelerometro
    Paint paint = new Paint();
    int alto, ancho;
    int tamanio = 40;
    int borde = 12;
    float ejeX = 0, ejeY = 0, ejeZ = 0;
    String X, Y, Z;

    public Bola(Context interfaz){
        super(interfaz);
        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensorRotacion = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = getResources().getDisplayMetrics().widthPixels;
        alto = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejeX -= sensorEvent.values[0];
        X = Float.toString(ejeX);

        if (ejeX < (tamanio + borde)){
            ejeX = (tamanio + borde);
        }else if (ejeX > (ancho-(tamanio + borde))){
            ejeX=ancho-(tamanio + borde);
        }

        ejeY += sensorEvent.values[1];
        Y = Float.toString(ejeY);

        if (ejeY < (tamanio + borde)){
            ejeY = (tamanio + borde);
        }else if (ejeY > (alto - tamanio - 170)){
            ejeY=alto - tamanio - 170;
        }

        ejeZ = sensorEvent.values[2];
        Z = String.format("%.2f", ejeZ);
        invalidate();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onDraw(Canvas lienzo){
        paint.setColor(Color.MAGENTA);
        lienzo.drawCircle(ejeX, ejeY, ejeZ+tamanio, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(25);
        lienzo.drawText( "Moveme", ejeX-45, ejeY+3,paint);
    }
}

