/**
 *
 */
package jp.or.adash.sample.entity;

/**
 * 商品クラス
 * @author T.Kawasaki
 *
 */
public class Item {
	private int itemNo;
	private String itemName;
	private int unitPrice;

	/**
	 * コンストラクタ
	 * @param itemNo 商品番号
	 * @param itemName 商品名
	 * @param unitPrice 単価
	 */
	public Item(int itemNo, String itemName, int unitPrice) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}

	/**
	 * 商品番号を取得する
	 * @return itemNo 商品番号
	 */
	public int getItemNo() {
		return itemNo;
	}

	/**
	 * 商品名を取得する
	 * @return itemName 商品名
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 単価を取得する
	 * @return unitPrice 単価
	 */
	public int getUnitPrice() {
		return unitPrice;
	}

}
