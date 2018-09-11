package jp.or.adash.sample.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.or.adash.sample.dao.ItemDao;
import jp.or.adash.sample.entity.Item;
import jp.or.adash.sample.utils.common.DataCommons;
import jp.or.adash.sample.utils.common.MessageCommons;
import jp.or.adash.sample.utils.dao.Transaction;

/**
 * 商品に関する処理を定義するクラス
 * @author T.Kawasaki
 *
 */
public class ItemService {

	/**
	 * トランザクションオブジェクト
	 */
	private Transaction transaction;

	/**
	 * 処理結果メッセージを格納するリスト
	 */
	private List<String> messages;

	/**
	 * コンストラクタ
	 */
	public ItemService() {
		transaction = new Transaction();
		messages = new ArrayList<String>();
	}

	/**
	 * 処理結果メッセージを取得する
	 * @return 処理結果メッセージ
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * 商品コードを元に商品情報を取得する
	 * @param code 商品コード
	 * @return 商品情報
	 */
	public Item getItem(int code) {
		Item item = null;

		try {
			// データベース接続を開始する
			transaction.open();

			// 商品単価を取得する
			ItemDao dao = new ItemDao(transaction);
			item = dao.selectItem(code);

		} catch(IOException e) {
			// エラーメッセージをセットする

		} finally {
			// データベース接続をを終了する
			transaction.close();
		}

		return item;
	}
	/**
	 * 商品リストを取得する
	 * @return 商品リスト
	 */
	public List<Item> getItemList() {
		List<Item> itemList = new ArrayList<Item>();

		try {
			// データベース接続を開始する
			transaction.open();

			// 商品リストを取得する
			ItemDao dao = new ItemDao(transaction);
			itemList = dao.selectItemList();

		} catch(IOException e) {
			// エラーメッセージをセットする
			messages.add(MessageCommons.ERR_DB_CONNECT);
		} finally {
			// データベース接続を終了する
			transaction.close();
		}

		return itemList;
	}

	/**
	 * 商品価格を取得する
	 * @param itemNo 商品番号
	 * @return 商品価格（エラーの場合、-1をセット）
	 */
	public int getItemPrice(int itemNo) {
		// 商品単価（商品がなければ、0）
		int unitPrice = 0;

		try {
			// データベース接続を開始する
			transaction.open();

			// 商品単価を取得する
			ItemDao dao = new ItemDao(transaction);
			Item item = dao.selectItem(itemNo);
			unitPrice = item.getUnitPrice();

		} catch(IOException e) {
			// エラーメッセージをセットする
			unitPrice = -1;
		} finally {
			// データベース接続をを終了する
			transaction.close();
		}

		return unitPrice;
	}

	/**
	 * 商品データの内容をチェックする
	 * @param item 商品データ
	 * @return 処理結果（true:成功、false:失敗）
	 */
	public boolean check(Item item) {
		boolean result = true;		// チェック結果

		// 商品コードの値が正しいか
		if (item.getItemNo() <= 0) {
			messages.add("商品コードに0より小さい値は入力できません。");
			result = false;
		}

		// 商品名の長さが適切か
		DataCommons commons = new DataCommons();
		int length = commons.getBytes(item.getItemName());
		if (length <= 0 || length >= 100) {
			messages.add("商品名の文字数が多すぎます。");
			result = false;
		}

		// 単価の値が正しいか
		if (item.getUnitPrice() <= 0) {
			messages.add("単価は1以上で入力してください。");
			result = false;
		}

		return result;
	}

	/**
	 * 登録完了メッセージ
	 */
	private static final String MSG_ITEM_REGIST_COMPLETE = "商品登録が完了しました。";

	/**
	 * 登録失敗メッセージ
	 */
	private static final String MSG_ITEM_REGIST_FAILURE = "商品登録に失敗しました。";

	/**
	 * 商品データを登録する
	 * @param item 商品データ
	 * @return 処理結果（true:成功、false:失敗）
	 */
	public boolean registItem(Item item) {
		boolean result = false;

		// データベースに商品が既に存在するかどうか確認する
		if (exists(item.getItemNo())) {
			// 存在する場合は、商品データを更新する
			result = this.updateItem(item);
		} else {
			// 存在しなければ、商品データを登録する
			result = this.insertItem(item);
		}

		return result;
	}

	/**
	 * 商品コードがデータベースに既に存在するかどうかを確認する
	 * @param code 商品コード
	 * @return true:存在する、false:存在しない
	 */
	private boolean exists(int code) {
		boolean result = false;		// 確認結果

		// コードをキーにして、データベースを検索する
		int price = this.getItemPrice(code);

		// データが存在する場合、true	を返す
		if (price > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 商品データを更新する
	 * @param item 商品データ
	 * @return 処理結果（true:成功、false:失敗）
	 */
	public boolean updateItem(Item item) {
		boolean result = false;	// 処理結果

		try {
			// データベース接続を開始する
			transaction.open();

			// トランザクションを開始する
			transaction.beginTrans();

			// 商品単価を取得する
			ItemDao dao = new ItemDao(transaction);
			int count = dao.update(item);

			if (count > 0) {
				// 完了メッセージをセットする
				messages.add(MSG_ITEM_REGIST_COMPLETE);
				result = true;
			} else {
				// エラーメッセージをセットする
				messages.add(MSG_ITEM_REGIST_FAILURE);
			}

			// トランザクションをコミットする
			transaction.commit();

		} catch(IOException e) {
			// トランザクションをロールバックする
			transaction.rollback();

			// エラーメッセージをセットする
			messages.add(MessageCommons.ERR_DB_CONNECT);
		} finally {
			// データベース接続をを終了する
			transaction.close();
		}

		return result;
	}

	/**
	 * 商品データをデータベースに登録する
	 * @param item 商品データ
	 * @return 処理結果（true:成功、false:失敗）
	 */
	public boolean insertItem(Item item) {
		boolean result = false;		// 処理結果

		try {
			// データベース接続を開始する
			transaction.open();

			// トランザクションを開始する
			transaction.beginTrans();

			// 商品単価を取得する
			ItemDao dao = new ItemDao(transaction);
			int count = dao.insert(item);

			if (count > 0) {
				// 完了メッセージをセットする
				messages.add(MSG_ITEM_REGIST_COMPLETE);
				result = true;
			} else {
				// エラーメッセージをセットする
				messages.add(MSG_ITEM_REGIST_FAILURE);
				result = false;
			}

			// トランザクションをコミットする
			transaction.commit();

		} catch(IOException e) {
			// トランザクションをロールバックする
			transaction.rollback();

			// エラーメッセージをセットする
			messages.add(MessageCommons.ERR_DB_CONNECT);
		} finally {
			// データベース接続をを終了する
			transaction.close();
		}

		return result;
	}

}
