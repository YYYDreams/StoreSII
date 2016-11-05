package com.example.administrator.store3.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

	public static byte[] Bitmap2Bytes(Bitmap bm){
		       ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		       return baos.toByteArray();
		    }
	/**
     * 把字节数组保存为一个文件
     * @Author HEH
     * @EditTime 2010-07-19 上午11:45:56
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }



	public static int getResIDByResName(Activity activity,String iconName)
	{
		Resources resources = activity.getResources();
		int indentify = resources.getIdentifier(activity.getPackageName()+":drawable/"+iconName, null, null);
		return indentify;
	}

	public static Bitmap deCodeFromByte(byte[] data)
	{
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}

	public static Bitmap scaleBitmap(String filePath,int w,int h)
	{
		Options o = new Options();
		o.inJustDecodeBounds = true;
		Bitmap tmp = BitmapFactory.decodeFile(filePath, o);
		o.inJustDecodeBounds = false;
		int width = (int)Math.ceil(o.outWidth/(float)w);
		int height = (int)Math.ceil(o.outHeight/(float)h);
		if(width>1 && height>1)
		{
			if(height>width)
			{
				o.inSampleSize=height;
			}
			else
			{
				o.inSampleSize=width;
			}
		}
		tmp = BitmapFactory.decodeFile(filePath, o);

		return tmp;
	}

	public static boolean saveImg(Bitmap bitmap,File file)
	{
		try {
			FileOutputStream out = new FileOutputStream(file);
			if(bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out))
			{
				out.flush();
				out.close();
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static Bitmap getBmp(File file) throws FileNotFoundException {

		if (file == null)
			return null;
		Options o = new Options();
		o.inJustDecodeBounds = true;
		Bitmap tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), o);
		o.inJustDecodeBounds = false;

		int rate = (int)(o.outHeight / (float)o.outWidth);
	    if (rate <= 0)
	    {
	    	rate = 1;
	    }
	    o.inSampleSize = rate;
		o.inPurgeable = true;
		o.inInputShareable = true;

		tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), o);

		return tmp;
	}

	/**
	 * 获得bitmap
	 *
	 * @param url
	 * @return
	 */
//	public static Bitmap getBmp(String url) {
//		InputStream in = null;
//		try {
//
//				in = HttpUtils.createHttpURLConnectionPost(new URL(url))
//						.getInputStream();
//
//				if (in != null) {
//					Options o = new Options();
//					o.inPreferredConfig = Config.RGB_565;
////					o.inSampleSize = 2;
//					o.inPurgeable = true;
//					o.inInputShareable = true;
//
//					return BitmapFactory.decodeStream(in, null, o);
//				}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//					in = null;
//				}
//		}
//		return null;
//	}


	/**
	 * 返回圆角图片
	 *
	 * @author 左博云
	 * @param bitmap
	 *            需要转换的位图
	 * @param pixels
	 *            圆角半径
	 * @return
	 * **/
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		if (bitmap == null) {
			return bitmap;
		}

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap rotate(Bitmap bitmap,int degree)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(degree); /*翻转90度*/
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap createReflectedBitmap(Bitmap srcBitmap)
    {
        if (null == srcBitmap)
        {
            return null;
        }

        // The gap between the reflection bitmap and original bitmap.
        final int REFLECTION_GAP = 4;

        int srcWidth  = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int reflectionWidth  = srcBitmap.getWidth();
        int reflectionHeight = srcBitmap.getHeight() / 2;

        if (0 == srcWidth || srcHeight == 0)
        {
            return null;
        }

        // The matrix
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        try
        {
            // The reflection bitmap, width is same with original's, height is half of original's.
            Bitmap reflectionBitmap = Bitmap.createBitmap(
                    srcBitmap,
                    0,
                    srcHeight / 2,
                    srcWidth,
                    srcHeight / 2,
                    matrix,
                    false);

            if (null == reflectionBitmap)
            {
                return null;
            }

            // Create the bitmap which contains original and reflection bitmap.
            Bitmap bitmapWithReflection = Bitmap.createBitmap(
                    reflectionWidth,
                    srcHeight + reflectionHeight + REFLECTION_GAP,
                    Config.ARGB_8888);

            if (null == bitmapWithReflection)
            {
                return null;
            }

            // Prepare the canvas to draw stuff.
            Canvas canvas = new Canvas(bitmapWithReflection);

            // Draw the original bitmap.
            canvas.drawBitmap(srcBitmap, 0, 0, null);

            // Draw the reflection bitmap.
            canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP, null);

//            srcBitmap.recycle();
            reflectionBitmap.recycle();

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient shader = new LinearGradient(
                    0,
                    srcHeight,
                    0,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    0x70FFFFFF,
                    0x00FFFFFF,
                    TileMode.MIRROR);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

            // Draw the linear shader.
            canvas.drawRect(
                    0,
                    srcHeight,
                    srcWidth,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    paint);

            return bitmapWithReflection;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


}
