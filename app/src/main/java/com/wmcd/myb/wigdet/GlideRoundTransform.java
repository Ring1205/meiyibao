package com.wmcd.myb.wigdet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 将图片转化为圆角 构造中第二个参数定义半径
 *
 * @author 鄧 E-mail:0731@aliyun.com
 */
public class GlideRoundTransform extends BitmapTransformation {

	/**
	 * The constant radius.
	 */
	private static float radius = 0f;

	/**
	 * Instantiates a new Glide round transform.
	 *
	 * @param context the context
	 */
	public GlideRoundTransform(Context context) {
		this(context, 4);
	}

	/**
	 * Instantiates a new Glide round transform.
	 *
	 * @param context the context
	 * @param dp      the dp
	 */
	public GlideRoundTransform(Context context, int dp) {
		super(context);
		GlideRoundTransform.radius = Resources.getSystem().getDisplayMetrics().density
				* dp;
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
		return roundCrop(pool, toTransform);
	}

	/**
	 * Round crop bitmap.
	 *
	 * @param pool   the pool
	 * @param source the source
	 * @return the bitmap
	 */
	private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
		if (source == null)
			return null;

		Bitmap result = pool.get(source.getWidth(), source.getHeight(),
				Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
					Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
				BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rectF, radius, radius, paint);
		return result;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return getClass().getName() + Math.round(radius);
	}
}