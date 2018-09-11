package jp.or.adash.sample.utils.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベーストランザクションクラス（ローカル接続）
 * @author T.Kawasaki
 *
 */
public class Transaction {

	/**
	 * データベース接続オブジェクト
	 */
	private Connection connection;

	/**
	 * トランザクションを開始する
	 * @throws TransactionException
	 */
	public void open() throws TransactionException {
		// データベース接続がないかどうか確認する（ある場合は何もしない）
		if (connection == null) {
			try {
				// 接続文字列を生成する
				StringBuffer connStrBuffer = new StringBuffer();
				connStrBuffer.append("jdbc:mysql://172.20.76.252:3306/uchi");
				connStrBuffer.append("?");
				connStrBuffer.append("useUnicode=true");
				connStrBuffer.append("&amp;characterEncoding=utf8");
				connStrBuffer.append("&amp;serverTimezone=JST");
				connStrBuffer.append("&amp;zeroDateTimeBehavior=convertToNull");
				connStrBuffer.append("&amp;useSSL=false");

				// データベース接続を開始する
				connection = DriverManager.getConnection(
						connStrBuffer.toString(), "uchi", "pgJav@1807");
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	/**
	 * トランザクションを開始する
	 * @throws TransactionException
	 */
	public void beginTrans() throws TransactionException {
		// データベース接続がないかどうか確認する（ある場合は何もしない）
		if (connection != null) {
			try {
				// トランザクションを開始する
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	/**
	 * トランザクションをコミットする
	 * @throws TransactionException
	 */
	public void commit() throws TransactionException {
		// データベース接続がないかどうか確認する（ある場合は何もしない）
		if (connection != null) {
			try {
				// トランザクションをコミットする
				connection.commit();
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	/**
	 * トランザクションをロールバックする
	 * @throws TransactionException
	 */
	public void rollback() throws TransactionException {
		if (connection != null) {
			try {
				// トランザクションをロールバックする
				connection.rollback();
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	/**
	 * データベース接続を終了する
	 * @throws TransactionException
	 */
	public void close() throws TransactionException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new TransactionException(e);
			} finally {
				connection = null;
			}
		}
	}

	/**
	 * データベース接続オブジェクトを取得する
	 * @return データベース接続オブジェクト
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * トランザクションが開始されているかどうかを返す
	 * @return トランザクションが開始されているかどうか(true or false)
	 * @throws TransactionException
	 */
	public boolean isActive() throws TransactionException {
		try {
			if (connection != null && connection.isClosed()) {
				connection = null;
				return false;
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}

		return true;
	}

	/**
	 * トランザクションに関する例外
	 * @author T.Kawasaki
	 *
	 */
	public static final class TransactionException extends RuntimeException{
		private static final long serialVersionUID = 1L;

		/**
		 * コンストラクタ
		 * @param e 例外オブジェクト
		 */
		public TransactionException(Exception e) {
			super(e);
		}
	}

}
