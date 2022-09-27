package cl.italosoft.nessfit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    /**
     * Inyección de origen de datos (base de datos)
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Bean para encriptar contraseñas con BCrypt
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



    /**
     * Configura el usuario y el rol para acceder al sistema
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.jdbcAuthentication().dataSource(dataSource)
                // Busca al usuario por el parámetro rut en la base de datos
                .usersByUsernameQuery("select rut, password, is_enabled from users where rut=?")
                // Busca el rol asociado al rut
                .authoritiesByUsernameQuery(
                        "select u.rut, r.name from users u inner join roles r on u.role_id = r.id where u.rut=?")
                .rolePrefix("ROLE_");
    }

    /**
     * Configura el filter Chain para acceso a las rutas
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.formLogin(form -> form.loginPage("/login").permitAll()).logout();
        http.authorizeRequests()
                // Los recursos estáticos no requieren autenticación
                .antMatchers("/build/**", "/css/**", "/images/**", "/js/**", "/vendors/**").permitAll()
                // Las vistas públicas no requieren autenticación
                .antMatchers("/login**").anonymous()
                // Las vistas con el subdominio administrador quedan protegidas al ROL
                // administrador
                .antMatchers("/administrador/**").hasAuthority("ADMINISTRADOR")
                // Todas las demás URLs de la Aplicación requieren autenticación
                .anyRequest().authenticated()
                // El formulario de Login redirecciona a la url /login
                .and().formLogin().loginPage("/login").usernameParameter("rut").passwordParameter("password")
                // Si las credenciales son válidas, utiliza el manejador de autenticación
                .successHandler(new AuthenticationSuccessHandler()
                {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException
                    {
                        // Tiempo máximo de sesión
                        request.getSession().setMaxInactiveInterval(0);
                        // Si la autenticación fue exitosa redirecciona a /home
                        response.sendRedirect("/home");
                    }
                })
                // Si las credenciales son inválidas utiliza el manejador de errores
                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        // Si el fallo es una instancia de la excepción BadCredential agrega el flag
                        // novalido
                        if (exception instanceof BadCredentialsException) {
                            super.setDefaultFailureUrl("/login?invalid");
                            // Si el fallo es una instancia de la excepción Disable agrega el flag
                            // noautorizado
                        } else if (exception instanceof DisabledException)
                        {
                            super.setDefaultFailureUrl("/login?unauthorized");
                        }
                        super.onAuthenticationFailure(request, response, exception);
                    }
                    // Si algun Match de url falla utiliza el manejador de excepciones
                }).and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler()
                {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response,
                                       AccessDeniedException accessDeniedException) throws IOException, ServletException
                    {
                        // Cualquiera sea el fallo redirecciona a /home
                        response.sendRedirect("/home");
                    }
                });
    }
}
