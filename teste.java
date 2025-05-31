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


            import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class LoginSimulator {

    public static void main(String[] args) {
        try {
            String targetURL = "http://localhost:8080/SEU_CONTEXTO/j_security_check";
            String username = "usuario";
            String password = "senha";

            // Parâmetros como se fossem enviados por um form HTML
            String urlParameters = "j_username=" + URLEncoder.encode(username, "UTF-8")
                                 + "&j_password=" + URLEncoder.encode(password, "UTF-8");

            // Criar conexão
            URL url = new URL(targetURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setUseCaches(false);

            // Enviar parâmetros
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            // Ler resposta (opcional)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Resposta: " + response.toString());
            }
            
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.*;

public class FormAuthSimulator {

    private static final String PROTECTED_URL = "http://localhost:8080/app/protegido.jsp";
    private static final String LOGIN_URL = "http://localhost:8080/app/j_security_check";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "senha";

    public static void main(String[] args) throws Exception {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        // 1. Tenta acessar página protegida
        System.out.println("Acessando recurso protegido...");
        HttpURLConnection conn = (HttpURLConnection) new URL(PROTECTED_URL).openConnection();
        conn.setInstanceFollowRedirects(false);

        int responseCode = conn.getResponseCode();
        System.out.println("Código de resposta: " + responseCode);

        if (responseCode == 302 || responseCode == 303) {
            String location = conn.getHeaderField("Location");
            System.out.println("Redirecionado para: " + location);

            // 2. Acessa a página de login
            System.out.println("Acessando página de login...");
            HttpURLConnection loginPageConn = (HttpURLConnection) new URL(location).openConnection();
            readResponse(loginPageConn);

            // 3. Faz POST para j_security_check com credenciais
            System.out.println("Enviando POST para autenticação...");
            URL url = new URL(LOGIN_URL);
            HttpURLConnection postConn = (HttpURLConnection) url.openConnection();
            postConn.setInstanceFollowRedirects(false);
            postConn.setRequestMethod("POST");
            postConn.setDoOutput(true);
            postConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String urlParameters = "j_username=" + URLEncoder.encode(USERNAME, "UTF-8") +
                                   "&j_password=" + URLEncoder.encode(PASSWORD, "UTF-8");

            try (DataOutputStream wr = new DataOutputStream(postConn.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            int postResponseCode = postConn.getResponseCode();
            System.out.println("Código de resposta do POST: " + postResponseCode);

            String postLocation = postConn.getHeaderField("Location");
            System.out.println("Redirecionado para: " + postLocation);

            // 4. Acessa o recurso protegido já autenticado
            System.out.println("Acessando recurso protegido após autenticação...");
            HttpURLConnection finalConn = (HttpURLConnection) new URL(PROTECTED_URL).openConnection();
            finalConn.setInstanceFollowRedirects(true);

            readResponse(finalConn);
        } else {
            System.out.println("Recurso não protegido ou fluxo diferente.");
        }
    }

    private static void readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            System.out.println("Resposta:\n" + response);
        }
    }
}

