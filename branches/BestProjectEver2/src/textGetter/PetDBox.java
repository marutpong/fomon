package textGetter;

import java.text.ParseException;
import java.util.Date;

public class PetDBox {

	private String PicturePath;
	private double Latitude;
	private double Longtitude;
	private String FoodType;
	private float KCalories;
	private float Protien;
	private float Carbohydrate;
	private float Fat;
	private Date TimeStamp = null;

	public PetDBox(String input) {
		String[] tmpData = input.split(PetDataGet.SPACE);
		if (isCorrectPattern(tmpData)) {
			PicturePath = tmpData[PetDataType.PicturePath];
			Latitude = Double.parseDouble(tmpData[PetDataType.Latitude]);
			Longtitude = Double.parseDouble(tmpData[PetDataType.Longtitude]);
			FoodType = tmpData[PetDataType.FoodType];
			KCalories = Float.parseFloat(tmpData[PetDataType.KCalories]);
			Protien = Float.parseFloat(tmpData[PetDataType.Protien]);
			Carbohydrate = Float.parseFloat(tmpData[PetDataType.Carbohydrate]);
			Fat = Float.parseFloat(tmpData[PetDataType.Fat]);
			try {
				TimeStamp = PetDataType.dateFormat.parse(tmpData[PetDataType.TimeStamp]);
			} catch (ParseException e) {
				//NOPE cause already check
			}
		}
	}

	public static boolean isCorrectPattern(String input) {
		String[] tmpData = input.split(PetDataGet.SPACE);
		if (tmpData.length == PetDataType.DataSize) {
			try {
				Double.parseDouble(tmpData[PetDataType.Latitude]);
				Double.parseDouble(tmpData[PetDataType.Longtitude]);
				Float.parseFloat(tmpData[PetDataType.KCalories]);
				Float.parseFloat(tmpData[PetDataType.Protien]);
				Float.parseFloat(tmpData[PetDataType.Carbohydrate]);
				Float.parseFloat(tmpData[PetDataType.Fat]);
				PetDataType.dateFormat.parse(tmpData[PetDataType.TimeStamp]);
			} catch (Exception E) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean isCorrectPattern(String[] input){
		if (input.length == PetDataType.DataSize) {
			try {
				Double.parseDouble(input[PetDataType.Latitude]);
				Double.parseDouble(input[PetDataType.Longtitude]);
				Float.parseFloat(input[PetDataType.KCalories]);
				Float.parseFloat(input[PetDataType.Protien]);
				Float.parseFloat(input[PetDataType.Carbohydrate]);
				Float.parseFloat(input[PetDataType.Fat]);
				PetDataType.dateFormat.parse(input[PetDataType.TimeStamp]);
			} catch (Exception E) {
				return false;
			}
			return true;
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

	public Date getTimeStamp() {
		return TimeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		TimeStamp = timeStamp;
	}
	
	public String getTimebyStringFormat(){
		return PetDataType.dateFormat.format(TimeStamp);
	}

}
