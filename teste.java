@WebServlet("/doLogin")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        // Manipule a request: logs, validações, transformações
        System.out.println("Tentativa de login para: " + username);

        try {
            request.login(username, password);  // Chama JAAS normalmente
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (ServletException e) {
            // Falha na autenticação
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=true");
        }
    }
}
