package jp.or.adash.sample.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.or.adash.sample.entity.Item;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/sessiontest")
public class SessionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.セッション情報を取得（無い場合は自動作成）
		HttpSession session = request.getSession();

		// 2.セッションからデータを取得
		List<Item> items = (List<Item>)session.getAttribute("items");

		if(items == null) {
			items = new ArrayList<Item>();
		}

		// 3.商品を追加をする
		items.add(new Item(1,"りんご",120));

		// 4.セッションにデータを格納
		session.setAttribute("items", items);

		// 5.jspにフォワードする
		request.getRequestDispatcher("/test.jsp").forward(request, response);
	}

}
