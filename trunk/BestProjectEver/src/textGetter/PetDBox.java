package textGetter;

public class PetDBox {

	private String PicturePath;
	private double Latitude;
	private double Longtitude;
	private String FoodType;
	private float KCalories;
	private float Protien;
	private float Carbohydrate;
	private float Fat;
	private int Day;
	private int Month;
	private int Year;
	private int Hour;
	private int Minuted;

	public PetDBox(String input) {
		String[] tmpData = input.split(PetDataGet.SPACE);
		if (isCorrectPattern(tmpData)) {
			PicturePath = tmpData[0];
			Latitude = Double.parseDouble(tmpData[1]);
			Longtitude = Double.parseDouble(tmpData[2]);
			FoodType = tmpData[3];
			KCalories = Float.parseFloat(tmpData[4]);
			Protien = Float.parseFloat(tmpData[5]);
			Carbohydrate = Float.parseFloat(tmpData[6]);
			Fat = Float.parseFloat(tmpData[7]);
			Day = Integer.parseInt(tmpData[8]);
			Month = Integer.parseInt(tmpData[9]);
			Year = Integer.parseInt(tmpData[10]);
			Hour = Integer.parseInt(tmpData[11]);
			Minuted = Integer.parseInt(tmpData[12]);
		}
	}

	public static boolean isCorrectPattern(String input) {
		String[] tmpData = input.split(PetDataGet.SPACE);
		if (tmpData.length == 13) {
			try {
				Double.parseDouble(tmpData[1]);
				Double.parseDouble(tmpData[2]);
				Float.parseFloat(tmpData[4]);
				Float.parseFloat(tmpData[5]);
				Float.parseFloat(tmpData[6]);
				Float.parseFloat(tmpData[7]);
				Integer.parseInt(tmpData[8]);
				Integer.parseInt(tmpData[9]);
				Integer.parseInt(tmpData[10]);
				Integer.parseInt(tmpData[11]);
				Integer.parseInt(tmpData[12]);
			} catch (Exception E) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean isCorrectPattern(String[] input){
		if (input.length == 13) {
			try {
				Double.parseDouble(input[1]);
				Double.parseDouble(input[2]);
				Float.parseFloat(input[4]);
				Float.parseFloat(input[5]);
				Float.parseFloat(input[6]);
				Float.parseFloat(input[7]);
				Integer.parseInt(input[8]);
				Integer.parseInt(input[9]);
				Integer.parseInt(input[10]);
				Integer.parseInt(input[11]);
				Integer.parseInt(input[12]);
			} catch (Exception E) {
				return false;
			}
			return true;
		}
		return false;
	}

	/*
	 * return true if this.date is EQUAL or MORE that in.date
	 */
	public boolean isBeforeDate(int Day, int Month, int Year) {
		if (this.Year >= Year) {
			if (this.Month >= Month) {
				if (this.Day >= Day)
					return true;
			}
		}
		return false;
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

	public String getFoodType() {
		return FoodType;
	}

	public void setFoodType(String foodType) {
		FoodType = foodType;
	}

	public float getKCalories() {
		return KCalories;
	}

	public void setKCalories(float kCalories) {
		KCalories = kCalories;
	}

	public float getProtien() {
		return Protien;
	}

	public void setProtien(float protien) {
		Protien = protien;
	}

	public float getCarbohydrate() {
		return Carbohydrate;
	}

	public void setCarbohydrate(float carbohydrate) {
		Carbohydrate = carbohydrate;
	}

	public float getFat() {
		return Fat;
	}

	public void setFat(float fat) {
		Fat = fat;
	}

	public int getDay() {
		return Day;
	}

	public void setDay(int day) {
		Day = day;
	}

	public int getMonth() {
		return Month;
	}

	public void setMonth(int month) {
		Month = month;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public int getHour() {
		return Hour;
	}

	public void setHour(int hour) {
		Hour = hour;
	}

	public int getMinuted() {
		return Minuted;
	}

	public void setMinuted(int minuted) {
		Minuted = minuted;
	}

}
