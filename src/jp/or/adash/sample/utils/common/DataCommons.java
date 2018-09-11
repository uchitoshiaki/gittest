/**
 *
 */
package jp.or.adash.sample.utils.common;

import java.io.UnsupportedEncodingException;

/**
 * データを扱う際に使える共通クラス
 * @author T.Kawasaki
 *
 */
public class DataCommons {

	/**
	 * 文字列のバイト数を取得する
	 * @param str 文字列
	 * @return 文字列のバイト数（取得エラーの場合は、マイナス値を返す）
	 */
	public int getBytes(String str) {
		try {
			return str.getBytes("UTF-8").length;
		} catch(UnsupportedEncodingException e) {
			// 取得エラーの場合は、マイナス値を返す
			return -1;
		}
	}
}
