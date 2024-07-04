package Object;

public class LoginInfo {

	private static String username,password,phone,gender,
						userID,transactionID,menuID,role;

	public static String getRole() {
		return role;
	}

	public static void setRole(String role) {
		LoginInfo.role = role;
	}

	public static String getPhone() {
		return phone;
	}

	public static void setPhone(String fullname) {
		LoginInfo.phone = fullname;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		LoginInfo.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		LoginInfo.password = password;
	}

	public static String getGender() {
		return gender;
	}

	public static void setGender(String gender) {
		LoginInfo.gender = gender;
	}

	public static String getUserID() {
		return userID;
	}

	public static void setUserID(String userID) {
		LoginInfo.userID = userID;
	}

	public static String getTransactionID() {
		return transactionID;
	}

	public static void setTransactionID(String transactionID) {
		LoginInfo.transactionID = transactionID;
	}

	public static String getMenuID() {
		return menuID;
	}

	public static void setMenuID(String menuID) {
		LoginInfo.menuID = menuID;
	}
	

}
