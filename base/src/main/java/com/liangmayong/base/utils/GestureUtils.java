package com.liangmayong.base.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * GestureUtils
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class GestureUtils {

	private GestureUtils() {
	}

	/**
	 * get GestureDetector
	 * 
	 * @param context
	 *            context
	 * @param gestureListener
	 *            gestureListener
	 * @return GestureDetector
	 */
	public static GestureDetector getGestureDetector(Context context, final OnGestureListener gestureListener) {
		return new IBGestureExt(context, new IBGestureExt.OnGestureResult() {
			@Override
			public void onGestureResult(int direction) {
				if (gestureListener != null) {
					gestureListener.onTouch(new LGestureType(direction), direction);
				}
			}
		}).Buile();
	}

	/**
	 * IBGestureType
	 * 
	 * @author LiangMaYong
	 * @version 1.0
	 */
	public static class LGestureType {
		public static final int GESTURE_TOP = 0;
		public static final int GESTURE_BOTTOM = 1;
		public static final int GESTURE_LEFT = 2;
		public static final int GESTURE_RIGHT = 3;
		public int Type = -1;

		/**
		 * IBGestureType
		 * 
		 * @param direction
		 *            direction
		 */
		public LGestureType(int direction) {
			Type = direction;
		}

		/**
		 * is left
		 * 
		 * @return flag
		 */
		public boolean isLeft() {
			if (Type == 2) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * is right
		 * 
		 * @return flag
		 */
		public boolean isRight() {
			if (Type == 3) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * is bottom
		 * 
		 * @return flag
		 */
		public boolean isBottom() {
			if (Type == 1) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * is top
		 * 
		 * @return flag
		 */
		public boolean isTop() {
			if (Type == 0) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * get direction
		 * 
		 * @return direction
		 */
		public String getDirection() {
			String direction = "";
			switch (Type) {
			case 0:
				direction = "top";
				break;
			case 1:
				direction = "bottom";
				break;
			case 2:
				direction = "left";
				break;
			case 3:
				direction = "right";
				break;
			default:
				direction = "unkown";
				break;
			}
			return direction;
		}
	}

	/**
	 * OnGestureListener
	 * 
	 * @author LiangMaYong
	 * @version 1.0
	 */
	public static interface OnGestureListener {
		public void onTouch(LGestureType gestureType, int direction);
	}

	/**
	 * IBGestureExt
	 * 
	 * @author LiangMaYong
	 * @version 1.0
	 */
	private static class IBGestureExt {

		public static Screen getScreenPix(Context context) {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getMetrics(dm);
			return new Screen(dm.widthPixels, dm.heightPixels);
		}

		public static class Screen {

			public int widthPixels;
			public int heightPixels;

			public Screen(int widthPixels, int heightPixels) {
				this.widthPixels = widthPixels;
				this.heightPixels = heightPixels;
			}

			@Override
			public String toString() {
				return "(" + widthPixels + "," + heightPixels + ")";
			}

		}

		public static final int GESTURE_UP = 0;
		public static final int GESTURE_DOWN = 1;
		public static final int GESTURE_LEFT = 2;
		public static final int GESTURE_RIGHT = 3;
		private OnGestureResult onGestureResult;
		private Context mContext;

		public IBGestureExt(Context c, OnGestureResult onGestureResult) {
			this.mContext = c;
			this.onGestureResult = onGestureResult;
			screen = getScreenPix(c);
		}

		public GestureDetector Buile() {
			return new GestureDetector(mContext, onGestureListener);
		}

		private Screen screen;
		private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				try {
					float x = e2.getX() - e1.getX();
					float y = e2.getY() - e1.getY();
					float x_limit = screen.widthPixels / 4;
					float y_limit = screen.heightPixels / 4;
					float x_abs = Math.abs(x);
					float y_abs = Math.abs(y);
					if (x_abs >= y_abs) {
						// gesture left or right
						if (x > x_limit || x < -x_limit) {
							if (x > 0) {
								// right
								doResult(GESTURE_RIGHT);
							} else if (x <= 0) {
								// left
								doResult(GESTURE_LEFT);
							}
						}
					} else {
						// gesture down or up
						if (y > y_limit || y < -y_limit) {
							if (y > 0) {
								// down
								doResult(GESTURE_DOWN);
							} else if (y <= 0) {
								// up
								doResult(GESTURE_UP);
							}
						}
					}
					return true;
				} catch (Exception e) {
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		};

		public void doResult(int result) {
			if (onGestureResult != null) {
				onGestureResult.onGestureResult(result);
			}
		}

		public interface OnGestureResult {
			public void onGestureResult(int direction);
		}
	}
}
