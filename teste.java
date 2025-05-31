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

<repositories>
  <repository>
    <id>shibboleth-releases</id>
    <name>Shibboleth Releases Repository</name>
    <url>https://build.shibboleth.net/nexus/content/repositories/releases/</url>
  </repository>
  
  <repository>
    <id>shibboleth-snapshots</id>
    <name>Shibboleth Snapshots Repository</name>
    <url>https://build.shibboleth.net/nexus/content/repositories/snapshots/</url>
  </repository>
</repositories>
<repository>
  <id>shibboleth-releases</id>
  <url>https://build.shibboleth.net/nexus/content/repositories/releases/</url>
</repository>







<dependency>
  <groupId>dependencia.pai</groupId>
  <artifactId>artefato-pai</artifactId>
  <version>versao</version>
  
  <exclusions>
    <exclusion>
      <groupId>net.shibboleth.tool</groupId>
      <artifactId>xmlselector</artifactId>
    </exclusion>
  </exclusions>
</dependency>





    import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/fazerLogin")
public class FazerLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = "usuario";  // Troque pelo username desejado
        String password = "senha";    // Troque pela senha desejada
        
        // Montar a URL completa do j_security_check
        String urlLogin = "http://localhost:8080" + request.getContextPath() + "/j_security_check";
        
        // Criar a conexão
        URL url = new URL(urlLogin);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        
        // Montar os parâmetros do POST
        String parametros = "j_username=" + URLEncoder.encode(username, "UTF-8")
                          + "&j_password=" + URLEncoder.encode(password, "UTF-8");
        
        // Escrever os parâmetros no corpo da requisição
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }

        // Opcional: desconectar explicitamente (não obrigatório)
        conn.disconnect();

        // Responder ao cliente que o POST foi feito
        response.setContentType("text/plain");
        response.getWriter().write("POST para /j_security_check enviado com sucesso.");
    }
}

