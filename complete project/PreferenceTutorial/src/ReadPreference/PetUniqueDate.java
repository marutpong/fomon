package ReadPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PetUniqueDate {

	/*
	 * Please copy it with PrefDataType
	 * To use it, Please new object once to get Context from Activity
	 */
	private static final String PrefName = "PetPref";
	private Context mContext;
	private static SharedPreferences mainPref;
	private static SharedPreferences.Editor editPref;

	public PetUniqueDate(Context context) {
		setmContext(context);
		mainPref = context.getSharedPreferences(PetUniqueDate.PrefName,
				Context.MODE_PRIVATE);
		editPref = mainPref.edit();
		iniPref();
	}

	/*
	 * Initial Example
	 */
	private void iniPref(){
		if(getMonName() == null){
			SetMonName("Initial Monster Name");
		}
	}
	
	public static void SetMonName(String monName){
		editPref.putString(PrefDataType.MONNAME, monName);
		editPref.commit();
	}
	
	public static void SetMonHP(int HPChange){
		editPref.putInt(PrefDataType.MONHP, HPChange);
		editPref.commit();
	}
	
	public static void SetMonATK(int ATKChange){
		editPref.putInt(PrefDataType.MONATK, ATKChange);
		editPref.commit();
	}
	
	public static void SetMonDEF(int DEFChange){
		editPref.putInt(PrefDataType.MONDEF, DEFChange);
		editPref.commit();
	}
	
	public static void SetFacebookID(String facebookID){
		editPref.putString(PrefDataType.FBID, facebookID);
		editPref.commit();
	}
	
	public static String getMonName(){
		return mainPref.getString(PrefDataType.MONNAME, PrefDataType.NONE);
	}
	
	public static int getMonHP(){
		return mainPref.getInt(PrefDataType.MONHP, -1);
	}
	
	public static int getMonATK(){
		return mainPref.getInt(PrefDataType.MONATK, -1);
	}
	
	public static int getMonDEF(){
		return mainPref.getInt(PrefDataType.MONDEF, -1);
	}

	public static String getFacebookID(){
		return mainPref.getString(PrefDataType.FBID, PrefDataType.NONE);
	}
	
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

}
