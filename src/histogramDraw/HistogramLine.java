package histogramDraw;

import textGetter.PetDataType;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;

public class HistogramLine {

	private static float MaxValue = -1;
	private static float SpeedFactor = -1;
	private static final float SpeedPerPixel = (float) 0.0125;
	private static Paint textPaint = new Paint();
	
	private int iniX;
	private int Height;
	private float SelfMax;
	private Paint mPaint;
	private int BrushColor;
	private int BrushLineColor;
	private String text;
	private int SpeedUp = 1;
	private float INVERSTVALUE = 0;
	private float RealValue = 0;
	

	public static void setMaxValue(float maxValue) {
		MaxValue = maxValue;
	}

	public int getIniX() {
		return iniX;
	}

	public HistogramLine(int iniX, int SelfMax, Paint mPaint) {
		this.iniX = iniX;
		this.SelfMax = SelfMax;
		Height = 0;
		this.mPaint = mPaint;
	}

	public HistogramLine(int iniX, float SelfMax, Paint mPaint, int Color,
			String txt) {
		
		textPaint.setColor(android.graphics.Color.WHITE);
	    textPaint.setTextAlign(Align.CENTER);
	    textPaint.setTextSize(HistogramPanel.TextRealSize);
	    
	    Log.i("ASSDD",HistogramPanel.LineStrokeWidth+"");
		
		int Height = HistogramPanel.getViewHeight();
		float idelViewHeight = (float) HistogramPanel.HeightMarginFactor
				* (float) Height;

		SpeedFactor = Math.round(((float) idelViewHeight)*SpeedPerPixel);
		
		this.iniX = iniX;
		RealValue = SelfMax;
		this.Height = 0;
		this.mPaint = mPaint;
		BrushColor = Color;
		text = txt;
		this.SelfMax = (SelfMax / MaxValue) * (idelViewHeight);
		INVERSTVALUE = MaxValue / (HistogramPanel.HeightMarginFactor * Height);
		this.SpeedUp = (int) Math.round(this.SelfMax/idelViewHeight*SpeedFactor );
	}

	public void animate() {
		if (Height < SelfMax) {
			Height = Height + SpeedUp;
			
			float realCal = Height * INVERSTVALUE;
			
			if (Height >= SelfMax){
				Height = (int) Math.round(SelfMax);
				realCal = RealValue;
			}
			
			if(realCal <= PetDataType.RequireCalPerDay*0.5){
				BrushLineColor = Color.BLUE;
			}else if(realCal < PetDataType.RequireCalPerDay*0.75){
				BrushLineColor = Color.argb(255, 25, 255, 255);
			}else if(realCal >= PetDataType.RequireCalPerDay*1.5){
				BrushLineColor = Color.RED;
			}else if(realCal > PetDataType.RequireCalPerDay*1.25){
				BrushLineColor = Color.YELLOW;
			}else{
				BrushLineColor = Color.GREEN;
			}
		}
	}

	public void doDraw(Canvas canvas) {
		float printf = 0;
		/*
		 * set brush style before draw line
		 */
		mPaint.setStrokeWidth(HistogramPanel.LineStrokeWidth);
		mPaint.setColor(BrushLineColor);
		canvas.drawLine(iniX, HistogramPanel.defaultBasePosY, iniX,
				HistogramPanel.defaultBasePosY - Height, mPaint);
		
		textPaint.setColor(BrushColor);
		canvas.drawText(text, iniX - ((textPaint.descent() + textPaint.ascent()) / 2), HistogramPanel.defaultBasePosY
				+ HistogramPanel.TextRealSize*2, textPaint);
		
		textPaint.setColor(Color.WHITE);
		if (Height >= (int) SelfMax)
			printf = RealValue;
		else
			printf = Height * INVERSTVALUE;
		canvas.drawText(Math.round(printf) + "", iniX - ((textPaint.descent() + textPaint.ascent()) / 2) ,
				HistogramPanel.defaultBasePosY - Height
						- HistogramPanel.TextRealSize, textPaint);
	}

}
