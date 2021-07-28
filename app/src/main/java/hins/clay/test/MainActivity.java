package hins.clay.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.main);

        Drawable d = getResources().getDrawable(R.drawable.test);
        //Drawable d = getResources().getDrawable(R.drawable.cam);
        Bitmap bitMapSource = ((BitmapDrawable) d).getBitmap();

        ImageView image = (ImageView) findViewById(R.id.imageSource);
        image.setImageBitmap(bitMapSource);

        int width = bitMapSource.getWidth();
        int height = bitMapSource.getHeight();

        int sourcePixels[] = new int[width * height];
        int destinationPixels[] = new int[width * height];

        bitMapSource.getPixels(sourcePixels, 0, width, 0, 0, width, height);

        // Building destination
        Bitmap bitMapDestionation = Bitmap.createBitmap(width, height,bitMapSource.getConfig());


        // Version Java //
        //bitMapDestionation.setPixels(javaGray(sourcePixels), 0, width, 0, 0, width, height);

        // Version C++ //
        nativeFilter(sourcePixels, destinationPixels, width, height);
        bitMapDestionation.setPixels(destinationPixels, 0, width, 0, 0, width, height);


        ImageView imageDestionation = (ImageView) findViewById(R.id.imageDestination);
        imageDestionation.setImageBitmap(bitMapDestionation);
    }

    private int[] javaGray(int sourcePixels[]) {
        return Arrays.stream(sourcePixels).map(
        value -> {
            final int a = (value>>24)&0xff ;
            final int r = (value>>16)&0xff ;
            final int g = (value>>8)&0xff ;
            final int b = value&0xff ;

            final int avg = (r + g + b)/3 ;
            return (a<<24) | (avg<<16) | (avg<<8) | avg ;
        }).toArray() ;
    }

    static {
        System.loadLibrary("filter");
    }

    private static native void nativeFilter(int src[], int dst[], int width, int height);
}