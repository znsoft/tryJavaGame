package com.znsoft.glsurfaceGame;

import java.nio.ByteBuffer;

public class TextureGenerator {
    public TextureGenerator() {
    }// / /

    // Generate an RGB8 checkerboard image
    //
    ByteBuffer genSurfaceImage(int width, int height, int checkSize) {
        int x, xMagnetic, yMagnetic, y, i;
        int Balls = 17;
        double Ambient = 900;
        int[] xCenters = new int[100];
        int[] yCenters = new int[100];
        int[] zCenters = new int[100];
        float[] pCenters = new float[100];
        for (i = 0; i < Balls; i++) {
            xCenters[i] = (int) Math.round(Math.random() * width);
            yCenters[i] = (int) Math.round(Math.random() * height);
            zCenters[i] = (int) Math.round(Math.random() * 255);
            pCenters[i] = (float) (1.0 + Math.random() * 3.0);

        }
        byte[] pixels = new byte[width * height * 3];
        double q = 0, z;
        for (y = 0; y < height; y++)
            for (x = 0; x < width; x++) {

                byte rColor = 0;
                byte bColor = 0;
                byte gColor = 0;
                z = 0.0;
                for (i = 0; i < Balls; i++) {
                    xMagnetic = xCenters[i] - x;
                    yMagnetic = yCenters[i] - y;
                    // q = Math.pow(Math.pow(xMagnetic,
                    // pCenters[i])+Math.pow(yMagnetic,
                    // pCenters[i]),1/pCenters[i]);
                    // q = Math.sqrt((double)(xMagnetic
                    // *xMagnetic+yMagnetic*yMagnetic));
                    q = Math.sqrt((double) (xMagnetic * xMagnetic + yMagnetic
                            * yMagnetic + zCenters[i]));
                    if (q != 0.0)
                        z = z + 1.0 / q;
                    else
                        z = z + 1;
                }
                int r = (int) Math.round(Ambient * z);
                if (r > 250)
                    r = 250;
                rColor = (byte) (r);
                bColor = rColor;
                gColor = rColor;

                // if ( ( x / checkSize ) % 2 == 0 )
                // {
                // bColor = (byte)(rColor * ( 1 - ( ( y / checkSize ) % 2 ) ));
                // }
                // else
                // {
                // bColor = (byte)(127 * ( ( y / checkSize ) % 2 ));
                // }

                pixels[(y * height + x) * 3] = rColor;
                pixels[(y * height + x) * 3 + 1] = gColor;// (byte) (bColor *
                // rColor * y * x);
                pixels[(y * height + x) * 3 + 2] = bColor;
            }

        // for ( y = 0; y < height; y++ )
        // for ( x = 0; x < width; x++ )
        // {

        ByteBuffer result = ByteBuffer.allocateDirect(width * height * 3);
        result.put(pixels).position(0);
        return result;
    }


    ///
    //  Generate an RGB8 checkerboard image
    //
    private ByteBuffer genCheckImage( int width, int height, int checkSize )
    {
        int x,xMagnetic,yMagnetic,
                y,i;
        int Balls = 17;
        double Ambient = 900;
        int[] xCenters = new int[100];
        int[] yCenters = new int[100];
        int[] zCenters = new int[100];
        float[] pCenters = new float[100];
        for(i=0;i<Balls;i++){
            xCenters[i]= (int) Math.round(Math.random()*width);
            yCenters[i]= (int) Math.round(Math.random()*height);
            zCenters[i]= (int) Math.round(Math.random()*255);
            pCenters[i]= (float) (1.0+Math.random()*3.0);

        }
        byte[] pixels = new byte[width * height * 3];
        double q = 0,z;
        for ( y = 0; y < height; y++ )
            for ( x = 0; x < width; x++ )
            {

                byte rColor = 0;
                byte bColor = 0;
                byte gColor = 0;
                z = 0.0;
                for(i=0;i<Balls;i++){
                    xMagnetic = xCenters[i] - x;
                    yMagnetic = yCenters[i] - y;
                    //q = Math.pow(Math.pow(xMagnetic, pCenters[i])+Math.pow(yMagnetic, pCenters[i]),1/pCenters[i]);
                    //           	q = Math.sqrt((double)(xMagnetic *xMagnetic+yMagnetic*yMagnetic));
                    q = Math.sqrt((double)(xMagnetic *xMagnetic+yMagnetic*yMagnetic+zCenters[i]));
                    if(q != 0.0 )z =z + 1.0/q; else z = z+1;
                }
                int r =(int) Math.round(Ambient * z);
                if(r>250)r=250;
                rColor = (byte) (r);
                bColor =  rColor;
                gColor =  rColor;


//	                if ( ( x / checkSize ) % 2 == 0 )
//	                {
//	                    bColor = (byte)(rColor * ( 1 - ( ( y / checkSize ) % 2 ) ));
//	                }
//	                else
//	                {
//	                    bColor = (byte)(127 * ( ( y / checkSize ) % 2 ));
//	                }




                pixels[(y * height + x) * 3] = rColor;
                pixels[(y * height + x) * 3 + 1] = gColor;//(byte) (bColor * rColor * y * x);
                pixels[(y * height + x) * 3 + 2] = bColor;
            }

//	        for ( y = 0; y < height; y++ )
//	            for ( x = 0; x < width; x++ )
//	            {

        ByteBuffer result = ByteBuffer.allocateDirect(width*height*3);
        result.put(pixels).position(0);
        return result;
    }
    // / /


}