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
	private int dummyCounter = 0;
	private final int MaxDummyCounter = 3*8;

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
		void OnMonsterStatChangeComplete();
	}

	private OnMonsterStatChange onMonsterStatChange;

	public void setOnMonsterStatChange(OnMonsterStatChange onMonsterStatChange) {
		this.onMonsterStatChange = onMonsterStatChange;
	}

	public void runIncrease() {
		boolean stg1 = curHP <= upHp;
		boolean stg2 = curATK <= upATK;
		boolean stg3 = curDEF <= upDEF;
		boolean stg4 = curSPD <= upSPD;
		
		if (stg1)
			curHP++;
		if (stg2)
			curATK++;
		if (stg3)
			curDEF++;
		if (stg4)
			curSPD++;
		if(!stg1 && !stg2 && !stg3 && !stg4){
			if(dummyCounter > MaxDummyCounter)
				NotifyCompleteStatChange();
			else
				dummyCounter++;
		}
		else
			NotifyStatChange();
	}

	private void NotifyCompleteStatChange() {
		if(onMonsterStatChange != null)
			onMonsterStatChange.OnMonsterStatChangeComplete();
	}

	private void NotifyStatChange() {
		if (onMonsterStatChange != null)
			onMonsterStatChange.OnMonsterStatHasChange(this);
	}

}
