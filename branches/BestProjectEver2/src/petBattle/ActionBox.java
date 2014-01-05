package petBattle;

public class ActionBox {

	private boolean isLeftSideAction;
	private int Damage;
	private int HPLeft;
	
	public ActionBox(boolean isLeftSideAction, int damage, int hPLeft) {
		super();
		this.isLeftSideAction = isLeftSideAction;
		Damage = damage;
		HPLeft = hPLeft;
	}

	public boolean isLeftSideAction() {
		return isLeftSideAction;
	}

	public void setLeftSideAction(boolean isLeftSideAction) {
		this.isLeftSideAction = isLeftSideAction;
	}

	public int getDamage() {
		return Damage;
	}

	public void setDamage(int damage) {
		Damage = damage;
	}

	public int getHPLeft() {
		return HPLeft;
	}

	public void setHPLeft(int hPLeft) {
		HPLeft = hPLeft;
	}
	
}
