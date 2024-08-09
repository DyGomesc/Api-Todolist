package br.com.dygomesc.api_todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.dygomesc.api_todolist.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var ServletPath = request.getServletPath();

        if (ServletPath.startsWith("/tasks/")) {
            //        Pegar a autenticação (usuario e senha)
            var authorization = request.getHeader("Authorization"); // vem criptografado base64
            var authEncoded = authorization.substring("Basic".length()).trim(); //remove a palavra basic do inicio e remove espaços em branco
            byte[] authDecode = Base64.getDecoder().decode(authEncoded); // converte de base64 para bytes
            String auth = new String(authDecode); // converte de bytes para String
            String[] credentials = auth.split(":", 2); //separa a String em um array
            String username = credentials[0];
            String password = credentials[1];

//        Validar Usuario
            var user = this.userRepository.findByUserName(username);
            if (user == null) {
                response.sendError(401, "Usuário não autorizado!");
            } else {
//            Validar Senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    request.setAttribute("userId", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Usuário não autorizado!");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
