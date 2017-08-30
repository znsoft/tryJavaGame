package com.znsoft.glsurfaceGame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

class GLESGameRenderer implements GLSurfaceView.Renderer {
	FloatBuffer quadVerts;
	int glBuf;
	private final float mTouchPositions[] = new float[40];
    private znResourceManager ResManager;

	public GLESGameRenderer(Context context) {
		mContext = context;
        ResManager = new znResourceManager(context);
		mTriangleVertices = ByteBuffer
				.allocateDirect(mTriangleVerticesData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangleVertices.put(mTriangleVerticesData).position(0);
	}

    ///
    // Create a 2D texture image
    //
    private int createTexture2D( )
    {
        // Texture object handle
        int[] textureId = new int[1];
        int    width = 256,
                height = 256;
//	        ByteBuffer pixels;
        mTextureID = textureId[0];
        //pixels = genCheckImage( width, height, 64 );

        // Generate a texture object
        GLES20.glGenTextures ( 1, textureId, 0 );

        // Bind the texture object
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, mTextureID );
        AssignBitmap(R.raw.dns,GLES20.GL_LINEAR);
        return textureId[0];
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
	// Generate an RGB8 checkerboard image
	//
	private ByteBuffer genSurfaceImage(int width, int height, int checkSize) {
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

	private void setRenderTexture(int frameBuf, int texture, int x, int y) {
		// if (frameBuf == 0)
		// GLES20.glViewport(0, 0, scrWidth, scrHeight);
		// else
		GLES20.glViewport(0, 0, x, y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuf);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
	}

	// ----------------------------------------------------------------------------
	public void onDrawFrame(GL10 glUnused) {
		GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glUseProgram(mProgram);
		checkGlError("glUseProgram");
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	    checkGlError("glActiveTexture");
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID1);
	    GLES20.glUniform1i(uSampler1Location, 0);
	    GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID2);
	    GLES20.glUniform1i(uSampler2Location, 1);
		
//		int uTouchPos = GLES20.glGetUniformLocation(mProgram, "uTouchPos");
//		// testing ball1
//		mTouchPositions[0] = 0.45f;// x
//		mTouchPositions[1] = 0.5f;// y
//		mTouchPositions[2] = 0.3f;// z высота игнорируетс€
//		mTouchPositions[3] = 0.4f;// а ”гол поворота
//		// -----ball2-----------------
//		mTouchPositions[4] = 0.5f;
//		mTouchPositions[5] = 0.6f;
//		mTouchPositions[6] = 0.5f;
//		mTouchPositions[7] = 0.6f;
//		GLES20.glUniform4fv(uTouchPos, 2, mTouchPositions, 0);
//		int uTP = GLES20.glGetUniformLocation(mProgram, "upo");

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
		quadVerts.position(0);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, quadVerts);
		checkGlError("glVertexAttribPointer maPosition");
		quadVerts.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		checkGlError("glEnableVertexAttribArray maPositionHandle");

		GLES20.glVertexAttribPointer(maTextureHandle1, 2, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, quadVerts);
		checkGlError("glVertexAttribPointer maTextureHandle");
		GLES20.glEnableVertexAttribArray(maTextureHandle1);

		checkGlError("glEnableVertexAttribArray maTextureHandle");

		long time = SystemClock.uptimeMillis();// % 9000L;

		GLES20.glVertexAttrib1f(maTimeHandle, (float) time / 800.f);
		// GLES20.glVertexAttrib3f(maLightHandle, 1.0f, 2.0f, 3.0f);

		float angle = 0.0190f;// * ((int) time);
		Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f);
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
		checkGlError("glDrawArrays");
	}

	// ----------------------------------------------
	int[] genbuf = new int[1];

	public void onSurfaceChanged(GL10 glUnused, int width, int height) {

		quadVerts = ByteBuffer.allocateDirect(mTriangleVerticesData.length * 4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();

		quadVerts.put(mTriangleVerticesData).position(0);

		GLES20.glGenBuffers(1, genbuf, 0);
		glBuf = genbuf[0];
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glBuf);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,
				mTriangleVerticesData.length * 4, quadVerts,
				GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	// +================================================
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		// Ignore the passed-in GL10 interface, and use the GLES20
		// class's static methods instead.
		mProgram = createProgram(mVertexShader, mFragmentShader);
		if (mProgram == 0) {
			return;
		}
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		checkGlError("glGetAttribLocation aPosition");
		if (maPositionHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aPosition");
		}

		maTimeHandle = GLES20.glGetAttribLocation(mProgram, "aTime");
		checkGlError("glGetAttribLocation aTime");
		if (maTimeHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aTime");
		}


		maTextureHandle1 = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
		checkGlError("glGetAttribLocation aTextureCoord");
		if (maTextureHandle1 == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aTextureCoord");
		}

		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		checkGlError("glGetUniformLocation uMVPMatrix");
		if (muMVPMatrixHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uMVPMatrix");
		}
		uSampler1Location = GLES20.glGetUniformLocation(mProgram, "sTexture");
	    uSampler2Location = GLES20.glGetUniformLocation(mProgram, "mTexture");
		// Texture object handle
		int[] textureId = new int[2];
		// Generate a texture object
		GLES20.glGenTextures(2, textureId, 0);
		// Bind the texture object
		GLES20.glActiveTexture( GLES20.GL_TEXTURE0 ); 
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
		UseTexture2D(genSurfaceImage(256, 256, 64),256,256);
		mTextureID1 =textureId[0];
		
		GLES20.glActiveTexture( GLES20.GL_TEXTURE1 ); 
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[1]);
		mTextureID2 = textureId[1];
		//UseTexture2D(genSurfaceImage(256, 256, 64),256,256);
		 GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, ResManager.loadBitmapResource(R.raw.lev1), 0);
         ResManager.recycleBitmap();
		 GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
		    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
	
		Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
	}

	// /
	// Create a 2D texture image
	//
	private int UseTexture2D(ByteBuffer pixels,int width ,int height) {

		// Load mipmap level 0
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, width,
				height, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, pixels);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);

		return 0;
	}

	private int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				Log.e(TAG, "Could not compile shader " + shaderType + ":");
				Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}

	private int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}

		int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (pixelShader == 0) {
			return 0;
		}

		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			checkGlError("glAttachShader");
			GLES20.glAttachShader(program, pixelShader);
			checkGlError("glAttachShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				Log.e(TAG, "Could not link program: ");
				Log.e(TAG, GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}


	private int loadProgram(String vs, String fs) throws Exception {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs);
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs);
		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			GLES20.glAttachShader(program, fragmentShader);
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS,
					linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				String error = GLES20.glGetProgramInfoLog(program);
				GLES20.glDeleteProgram(program);
				throw new Exception(error);
			}
		}
		return program;
	}


	private void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(TAG, op + ": glError " + error);
			throw new RuntimeException(op + ": glError " + error);
		}
	}

	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
	private final float[] mTriangleVerticesData = {
			// X, Y, Z, U, V
			-1.5f, -1.55f, 0.0f, 1.0f,1.0f,
			1.5f, -1.55f, 0.0f, 0.0f, 1.0f,
			-1.5f, 1.55f, 0.0f, 1.0f, 0.0f,
			1.5f, 1.55f, 0.0f, 0.0f, 0.0f

	};

	private FloatBuffer mTriangleVertices;

	private final String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n"
			+ "attribute float aTime;\n"
			+ "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n"
			+ "varying float vTime;\n"
			+ "varying float vPs;\n"
			+ "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n"
			+ "  vTime = aTime;\n"
			+ "   \n" + "}\n";

	private final String mFragmentShader = "precision mediump float;\n"
			+ "uniform vec4 uTouchPos[20];"
			+ "varying vec2 vTextureCoord;\n"
			+ "varying float vTime;\n"
			+ "uniform sampler2D sTexture;\n"
			+ "uniform sampler2D mTexture;\n"
			+ "void main() {\n"
			+ "vec2 cc = vec2(cos(0.25*vTime),sin(0.25*vTime*1.423));\n"
			+ "float dmin = 1000.0;\n" + "vec2 z; int yy = int(dmin);      \n"
			+ "vec2 xy =  vTextureCoord;\n"
			+ "vec4 o,l,h =(texture2D(mTexture, xy ));\n"
			+ "vec4 v= vec4(0);\n"
			+ "float u = 0.0;o=h;\n"
			+ "float g = 0.5;\n"
			+ "u =  0.005;\n"
			+ "z = cc*u;\n"
//			+ "for(int ii = 0;ii<2;ii++){;\n"
			+ "g = g+1.0;\n"
			+ "l =(texture2D(mTexture, xy+z*g ));\n"
			
			+ "if((l.r-v.r)<h.r){v = vec4(0.5);"
			//+"break;"
			+ "}\n"
			+ "v = v+ (h-l);\n"
	//		+ "}\n"
			+ " //texture2D(mTexture, xy )   \n"
			+ "  gl_FragColor = l;//texture2D(sTexture, xy );// - v ;\n"
			+ "             }";
	/*
			+ "  //gl_FragColor = gl_FragColor + texture2D(mTexture, xy );\n"
			+ "  gl_FragColor = gl_FragColor-v;\n"
			+ "        for(int ii =0;ii<2;ii++){        \n"
			+ "  vec2 t =   uTouchPos[ii].xy-xy;   "
			+ "          g= length(t) ;\n"
			+ "          if(g<0.151) { "
			+ "   t = t*cc*(0.05+g*10.0);  "
			+ "float a = atan(t.x,t.y);    "
			+ "    gl_FragColor = texture2D(mTexture, xy );  \n"
			+ "             } }}";
*/
	private float[] mMVPMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	private float[] mMMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	//private float[] mBMatrix = new float[256];
	// const int Balls = 17;
	private int mProgram;
	private int mTextureID1;
	private int mTextureID2;
	private int uSampler1Location;
	private int uSampler2Location;
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int maTextureHandle1;
	//private int maTextureHandle2;
	private int maTimeHandle;
	//private int maLightHandle;
	//private int maBallsHandle;
	private int resolution;
	private Context mContext;
	private static String TAG = "GLES20TriangleRenderer";
}
