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
 * 商品登録画面初期表示サーブレット
 */
@WebServlet("/item/display")
public class ItemRegistDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemRegistDisplayServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.1 リクエストから商品コードを取得
		int code = -1;
		if(!"".equals(request.getParameter("code")) && request.getParameter("code") != null) {
			code = Integer.parseInt(request.getParameter("code"));
		}

		// 1.2 商品コードがある場合、商品情報を取得
		Item item = null;
		if(code >= 0) {
			ItemService service = new ItemService();
			item = service.getItem(code);
		}
		// 1.3 リクエストに商品情報をセット
		request.setAttribute("item", item);

		// 1.4 JSPにフォワードする
		request.getRequestDispatcher("/input.jsp").forward(request, response);
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
