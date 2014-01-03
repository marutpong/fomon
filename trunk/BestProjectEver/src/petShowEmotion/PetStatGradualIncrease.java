package petShowEmotion;

public class PetStatGradualIncrease {

	public static final int MaxPETHP = 300;
	public static final int MaxPETATK = 100;
	public static final int MaxPETDEF = 100;
	public static final int MaxPETSPD = 50;

	private int upHp;
	private int upATK;
	private int upDEF;
	private int upSPD;
	private int curHP = 0;
	private int curATK = 0;
	private int curDEF = 0;
	private int curSPD = 0;

	public int getCurATK() {
		return curATK;
	}

	public int getCurDEF() {
		return curDEF;
	}

	public int getCurHP() {
		return curHP;
	}

	public int getCurSPD() {
		return curSPD;
	}

	public PetStatGradualIncrease(int HP, int ATK, int DEF, int SPD) {
		upHp = HP;
		upATK = ATK;
		upDEF = DEF;
		upSPD = SPD;
	}

	public interface OnMonsterStatChange {
		void OnMonsterStatHasChange(
				PetStatGradualIncrease petStatGradualIncrease);
	}

	private OnMonsterStatChange onMonsterStatChange;

	public void setOnMonsterStatChange(OnMonsterStatChange onMonsterStatChange) {
		this.onMonsterStatChange = onMonsterStatChange;
	}

	public void runIncrease() {
		if (curHP <= upHp)
			curHP++;
		if (curATK <= upATK)
			curATK++;
		if (curDEF <= upDEF)
			curDEF++;
		if (curSPD <= upSPD)
			curSPD++;
		NotifyStatChange();
	}

	private void NotifyStatChange() {
		if (onMonsterStatChange != null)
			onMonsterStatChange.OnMonsterStatHasChange(this);
	}

}
