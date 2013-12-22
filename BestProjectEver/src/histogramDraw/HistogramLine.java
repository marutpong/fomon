package histogramDraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HistogramLine {

	private static final int TEXTSHIFT1 = 5;
	private static final int TEXTSHIFT2 = 8;
	private static int mShift;
	private int iniX;
	private int Height;
	private int SelfMax;
	private Paint mPaint;
	private int BrushColor;
	private String text;
	private static Paint tPaint = new Paint();
	
	public int getIniX() {
		return iniX;
	}
	
	public HistogramLine(int iniX, int SelfMax, Paint mPaint) {
		this.iniX = iniX;
		this.SelfMax = SelfMax;
		Height = 0;
		this.mPaint = mPaint;
	}
	
	public HistogramLine(int iniX, int SelfMax, Paint mPaint, int Color, String txt) {
		this.iniX = iniX;
		this.SelfMax = SelfMax;
		Height = 0;
		this.mPaint = mPaint;
		BrushColor = Color;
		text = txt;
	}
	
	public void animate(){
		if(Height<SelfMax)
			Height++;
	}
	
	public void doDraw(Canvas canvas){
		/*
		 * set brush style before draw line
		 */
		mPaint.setStrokeWidth(HistogramPanel.LineStrokeWidth);
		mPaint.setColor(BrushColor);
		canvas.drawLine(iniX, HistogramPanel.defaultBasePosY, iniX, HistogramPanel.defaultBasePosY - Height, mPaint);
		tPaint.setColor(BrushColor);
		mShift = setShift(text);
		canvas.drawText(text, iniX-mShift, HistogramPanel.defaultBasePosY + HistogramPanel.TextMarginBaseLine, tPaint);
		tPaint.setColor(Color.WHITE);
		mShift = setShift(Height+"");
		canvas.drawText(Height+"", iniX-mShift, HistogramPanel.defaultBasePosY -Height -HistogramPanel.TextMarginTopLine, tPaint);
	}
	
	private int setShift(String txt){
		if(txt.length()<2)
			return TEXTSHIFT1;
		else
			return TEXTSHIFT2;
	}
	
}
