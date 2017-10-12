package com.wmcd.myb.wigdet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 将图片转化为圆形 构造中第二个参数定义半径
 *
 * @author 鄧 E-mail:0731@aliyun.com
 */
public class GlideCircleTransform extends BitmapTransformation {
	/**
	 * Instantiates a new Glide circle transform.
	 *
	 * @param context the context
	 */
	public GlideCircleTransform(Context context) {
		super(context);
	}

	/**
	 * Transform bitmap.
	 *
	 * @param pool        the pool
	 * @param toTransform the to transform
	 * @param outWidth    the out width
	 * @param outHeight   the out height
	 * @return the bitmap
	 */
	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
			int outWidth, int outHeight) {
		return circleCrop(pool, toTransform);
	}

	/**
	 * Circle crop bitmap.
	 *
	 * @param pool   the pool
	 * @param source the source
	 * @return the bitmap
	 */
	private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
		if (source == null)
			return null;

		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;

		// TODO this could be acquired from the pool too
		Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

		Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP,
				BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		float r = size / 2f;
		canvas.drawCircle(r, r, r, paint);
		return result;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return getClass().getName();
	}
}
