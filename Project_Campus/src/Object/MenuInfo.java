package Object;

public class MenuInfo {
	private static int MenuID;
	private static String MenuName;
	private static int MenuQuantity;
	private static int MenuPrice;
	private static int MenuTotal;
	
	public MenuInfo(int menuID, String menuName, int menuQuantity, int menuPrice) {
		MenuID = menuID;
		MenuName = menuName;
		MenuQuantity = menuQuantity;
		MenuPrice = menuPrice;
	}

	public MenuInfo(int cartID, String cartName, int cartQuantity, int cartPrice, int cartTotal) {
		MenuID = cartID;
		MenuName = cartName;
		MenuQuantity = cartQuantity;
		MenuPrice = cartPrice;
		MenuTotal = cartTotal;
	}
	
	public int getMenuID() {
		return MenuID;
	}

	public void setMenuID(int menuID) {
		MenuID = menuID;
	}

	public String getMenuName() {
		return MenuName;
	}

	public void setMenuName(String menuName) {
		MenuName = menuName;
	}

	public int getMenuQuantity() {
		return MenuQuantity;
	}

	public void setMenuQuantity(int menuQuantity) {
		MenuQuantity = menuQuantity;
	}

	public int getMenuPrice() {
		return MenuPrice;
	}

	public void setMenuPrice(int menuPrice) {
		MenuPrice = menuPrice;
	}

	public int getMenuTotal() {
		return MenuTotal;
	}

	public void setMenuTotal(int menuTotal) {
		MenuTotal = menuTotal;
	}
	
}
