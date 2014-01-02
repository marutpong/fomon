package preferenceSetting;

import com.projnsc.bestprojectever.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PetUniqueDate {

	/*
	 * Please copy it with PrefDataType To use it, Please new object once to get
	 * Context from Activity
	 */
	private static final String PrefName = "PetPref";
	private static Context mContext;
	private static SharedPreferences mainPref;
	private static SharedPreferences.Editor editPref;

	/*
	 * Initial get Context and check for exist old preference
	 */
	public PetUniqueDate(Context context) {
		setContext(context);
	}

	/*
	 * It's only getter/setter pattern but we only call method :3 (Happy?) Don't
	 * forget to create NEW this object first to get context
	 */

	public static void SetMonName(String monName) {
		editPref.putString(PrefDataType.MONNAME, monName);
		editPref.commit();
	}

	public static void SetMonID(int monID) {
		editPref.putInt(PrefDataType.MONID, monID);
		editPref.commit();
	}

	public static void SetMonHP(int HPChange) {
		editPref.putInt(PrefDataType.MONHP, HPChange);
		editPref.commit();
	}

	public static void SetMonATK(int ATKChange) {
		editPref.putInt(PrefDataType.MONATK, ATKChange);
		editPref.commit();
	}

	public static void SetMonDEF(int DEFChange) {
		editPref.putInt(PrefDataType.MONDEF, DEFChange);
		editPref.commit();
	}

	public static void SetMonSPD(int SPDChange) {
		editPref.putInt(PrefDataType.MONSPD, SPDChange);
		editPref.commit();
	}

	public static void SetFacebookID(String facebookID) {
		editPref.putString(PrefDataType.FBID, facebookID);
		editPref.commit();
	}
	
	public static void SetMonsterBirthDay(String monBD){
		editPref.putString(PrefDataType.MONBIRTHDAY, monBD);
		editPref.commit();
	}

	public static String getMonName() {
		return mainPref.getString(PrefDataType.MONNAME, PrefDataType.NONE);
	}

	public static int getMonID() {
		return mainPref.getInt(PrefDataType.MONID, PrefDataType.NONEINT);
	}

	public static int getMonHP() {
		return mainPref.getInt(PrefDataType.MONHP, PrefDataType.NONEINT);
	}

	public static int getMonATK() {
		return mainPref.getInt(PrefDataType.MONATK, PrefDataType.NONEINT);
	}

	public static int getMonDEF() {
		return mainPref.getInt(PrefDataType.MONDEF, PrefDataType.NONEINT);
	}

	public static int getMonSPD() {
		return mainPref.getInt(PrefDataType.MONSPD, PrefDataType.NONEINT);
	}

	public static String getFacebookID() {
		return mainPref.getString(PrefDataType.FBID, PrefDataType.NONE);
	}
	
	public static String getMonsterBirthday() {
		return mainPref.getString(PrefDataType.MONBIRTHDAY, PrefDataType.NONE);
	}

	public Context getmContext() {
		return mContext;
	}

	public static void setContext(Context mContext) {
		PetUniqueDate.mContext = mContext;
		mainPref = mContext.getSharedPreferences(PetUniqueDate.PrefName,
				Context.MODE_PRIVATE);
		editPref = mainPref.edit();
	}

	public static int getPetResource() {
		Log.i(PetUniqueDate.class.getName(),getMonID()+"");
		switch (getMonID()) {
		case 1:
			return R.drawable.pet_s7;
		case 2:
			return R.drawable.pet_s6;
		default:
			return R.drawable.pet_s7;
		}
	}
}
