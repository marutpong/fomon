package petShowEmotion;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PetShowEmotionThread extends Thread{

	private static final int DEFAULT_SLEEP_FRAME = 10;
	public static final int FPS = 10;
	private PetShowEmotionPanel mPanel;
	private SurfaceHolder mHolder;
	private boolean Running = false;
	
	public PetShowEmotionThread(PetShowEmotionPanel panel){
		mPanel = panel;
		mHolder = mPanel.getHolder();
	}
	
	public void setRunning(boolean running) {
		Running = running;
	}
	
	@Override
	public void run() {
		try {
			sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Canvas canvas = null;
		long ticksPS = 1000 / FPS;
		long startTime = System.currentTimeMillis();
        long sleepTime;
		while(Running){
			canvas = mHolder.lockCanvas();
			
			if(canvas != null){
				mPanel.RunAnimate();
				mPanel.doDraw(canvas);
				mHolder.unlockCanvasAndPost(canvas);
				
				sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                try {
                       if (sleepTime > 0)
                              sleep(sleepTime);
                       else
                              sleep(DEFAULT_SLEEP_FRAME);
                } catch (Exception e) {}
			}
			startTime = System.currentTimeMillis();
		}
	}
	
}
