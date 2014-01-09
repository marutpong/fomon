package foodDatabase;

import java.util.Random;

public class FoodBox {
	private static Random randGen = new Random();
	private int ID;
	private String Name;
	private double Calories;
	private double Protein;
	private double Fat;
	private double Carbohydrate;
	private double Calcium;
	private double Magnesium;
	private double Potassium;
	private double Sodium;
	private double Phosphorus;
	private int randomHP[] = new int[2];
	private int randomATK[] = new int[2];
	private int randomDEF[] = new int[2];

	public FoodBox(int index, String name, double Cal, double Pro, double Fat,
			double Carb, double Calc, double Mag, double Pot, double So,
			double Phos, int randHP1, int randHP2, int randATK1, int randATK2,
			int randDEF1, int randDEF2) {
		this.ID = index;
		this.Name = name;
		this.Calories = Cal;
		this.Protein = Pro;
		this.Fat = Fat;
		this.Carbohydrate = Carb;
		this.Calcium = Calc;
		this.Magnesium = Mag;
		this.Potassium = Pot;
		this.Sodium = So;
		this.Phosphorus = Phos;
		this.randomHP[0] = randHP1;
		this.randomHP[1] = randHP2;
		this.randomATK[0] = randATK1;
		this.randomATK[1] = randATK2;
		this.randomDEF[0] = randDEF1;
		this.randomDEF[1] = randDEF2;
	}

	/*
	 * public boolean isName(String checkName) { return
	 * this.Name.equalsIgnoreCase(checkName); }
	 * 
	 * public boolean isClass(int Class){ return (Class==this.ID); }
	 */
	public int getID() {
		return ID;
	}

	public void setID(int index) {
		ID = index;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getCalories() {
		return Calories;
	}

	public double getProtein() {
		return Protein;
	}

	public double getFat() {
		return Fat;
	}

	public double getCarbohydrate() {
		return Carbohydrate;
	}

	public double getCalcium() {
		return Calcium;
	}

	public double getMagnesium() {
		return Magnesium;
	}

	public double getPotassium() {
		return Potassium;
	}

	public double getSodium() {
		return Sodium;
	}

	public double getPhosphorus() {
		return Phosphorus;
	}

	public int getMinrandHP() {
		return randomHP[0];
	}

	public int getMaxrandHP() {
		return randomHP[1];
	}

	public int getMinrandATK() {
		return randomATK[0];
	}

	public int getMaxrandATK() {
		return randomATK[1];
	}

	public int getMinrandDEF() {
		return randomDEF[0];
	}

	public int getMaxrandDEF() {
		return randomDEF[1];
	}

	public int getHPRandomValue() {
		return randomHP[0] + randGen.nextInt(randomHP[1]);
	}

	public int getATKRamdomValue() {
		return randomATK[0] + randGen.nextInt(randomATK[1]);
	}

	public int getDEFRandomValue() {
		return randomDEF[0] + randGen.nextInt(randomDEF[1]);
	}
}
