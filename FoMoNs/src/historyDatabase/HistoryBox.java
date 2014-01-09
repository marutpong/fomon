package historyDatabase;

import java.util.Date;

public class HistoryBox {

	private int id;
	private String PicturePath;
	private double Latitude;
	private double Longtitude;
	private int FoodType;
	private Date TimeStamp = null;

	public HistoryBox(int id, String picpath, double lat, double lon,
			int foodtype, String date, String time) {
		this.id = id;
		this.PicturePath = picpath;
		this.Latitude = lat;
		this.Longtitude = lon;
		this.FoodType = foodtype;
		this.TimeStamp = HistoryType.dateStringToDate(date, time);
	}

	/*
	 * public HistoryBox(String input) { String[] tmpData =
	 * input.split(HistoryDatabase.SPACE); if (isCorrectPattern(tmpData)) {
	 * PicturePath = tmpData[HistoryType.PicturePath]; Latitude =
	 * Double.parseDouble(tmpData[HistoryType.Latitude]); Longtitude =
	 * Double.parseDouble(tmpData[HistoryType.Longtitude]); FoodType =
	 * tmpData[HistoryType.FoodType]; KCalories =
	 * Float.parseFloat(tmpData[HistoryType.KCalories]); Protien =
	 * Float.parseFloat(tmpData[HistoryType.Protien]); Carbohydrate =
	 * Float.parseFloat(tmpData[HistoryType.Carbohydrate]); Fat =
	 * Float.parseFloat(tmpData[HistoryType.Fat]); try { TimeStamp =
	 * HistoryType.dateFormat.parse(tmpData[HistoryType.TimeStamp]); } catch
	 * (ParseException e) { //NOPE cause already check } } }
	 */
	/*
	 * public static boolean isCorrectPattern(String input) { String[] tmpData =
	 * input.split(HistoryDatabase.SPACE); if (tmpData.length ==
	 * HistoryType.DataSize) { try {
	 * Double.parseDouble(tmpData[HistoryType.Latitude]);
	 * Double.parseDouble(tmpData[HistoryType.Longtitude]);
	 * Float.parseFloat(tmpData[HistoryType.KCalories]);
	 * Float.parseFloat(tmpData[HistoryType.Protien]);
	 * Float.parseFloat(tmpData[HistoryType.Carbohydrate]);
	 * Float.parseFloat(tmpData[HistoryType.Fat]);
	 * HistoryType.dateFormat.parse(tmpData[HistoryType.TimeStamp]); } catch
	 * (Exception E) { return false; } return true; } return false; }
	 * 
	 * private boolean isCorrectPattern(String[] input){ if (input.length ==
	 * HistoryType.DataSize) { try {
	 * Double.parseDouble(input[HistoryType.Latitude]);
	 * Double.parseDouble(input[HistoryType.Longtitude]);
	 * Float.parseFloat(input[HistoryType.KCalories]);
	 * Float.parseFloat(input[HistoryType.Protien]);
	 * Float.parseFloat(input[HistoryType.Carbohydrate]);
	 * Float.parseFloat(input[HistoryType.Fat]);
	 * HistoryType.dateFormat.parse(input[HistoryType.TimeStamp]); } catch
	 * (Exception E) { return false; } return true; } return false; }
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicturePath() {
		return PicturePath;
	}

	public void setPicturePath(String picturePath) {
		PicturePath = picturePath;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongtitude() {
		return Longtitude;
	}

	public void setLongtitude(double longtitude) {
		Longtitude = longtitude;
	}

	public int getFoodType() {
		return FoodType;
	}

	public void setFoodType(int foodType) {
		FoodType = foodType;
	}

	public Date getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		TimeStamp = timeStamp;
	}

	public String getTimebyStringFormat() {
		return HistoryType.dateFormat.format(TimeStamp);
	}
}
