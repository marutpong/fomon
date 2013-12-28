package histogramDraw;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class HistogramThread extends Thread {

	private static final int DEFAULT_SLEEP_FRAME = 10;
	public static final int FPS = 100;
	private HistogramPanel mPanel;
	private SurfaceHolder mHolder;
	private boolean Running = false;

	public HistogramThread(HistogramPanel panel) {
		mPanel = panel;
		mHolder = mPanel.getHolder();
	}

	public void setRunning(boolean running) {
		Running = running;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		long ticksPS = 1000;
		long startTime = System.currentTimeMillis();
		long sleepTime = 0;
		while (Running) {
			canvas = mHolder.lockCanvas();

			if (canvas != null) {
				mPanel.RunAnimate();
				mPanel.doDraw(canvas);
				mHolder.unlockCanvasAndPost(canvas);

				sleepTime = (ticksPS / FPS)
						- (System.currentTimeMillis() - startTime);
				try {
					if (sleepTime > 0)
						sleep(sleepTime);
					else
						sleep(DEFAULT_SLEEP_FRAME);
				} catch (Exception e) {
				}
			}
			startTime = System.currentTimeMillis();
		}
	}

}
