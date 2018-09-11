package jp.or.adash.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.or.adash.sample.entity.Item;
import jp.or.adash.sample.services.ItemService;

/**
 * Servlet implementation class ItemRegistServlet
 */
@WebServlet("/item/regist")
public class ItemRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemRegistServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.1 リクエストから値を取得する
		int code = Integer.parseInt(request.getParameter("code"));
		String hiddenCode = request.getParameter("hiddencode");
		String name = request.getParameter("name");
		int unitPrice = Integer.parseInt(request.getParameter("unitprice"));

		// 1.2 商品オブジェクトを作成
		Item item = new Item(code, name, unitPrice);

		// 1.3 入力チェック
		ItemService service = new ItemService();
		if(!service.check(item)) {
			// 1.4 入力チェックでエラーがあった場合、エラーメッセージをセット
			request.setAttribute("item", item);
			request.setAttribute("messages", service.getMessages());

			// 1.5 JSPにフォワード
			request.getRequestDispatcher("/input.jsp").forward(request, response);

			return;
		}

		if("".equals(hiddenCode)) {
			// 1.6 商品コードが無い場合、商品を登録する
			service.insertItem(item);
		}else {
			// 1.7 商品コードがある場合、商品を更新する
			service.updateItem(item);
		}
		// 処理結果メッセージをリクエストに格納する
		request.setAttribute("item", item);
		request.setAttribute("messages", service.getMessages());

		// 1.8 JSPにフォワード
		request.getRequestDispatcher("/input.jsp").forward(request, response);
	}

}
