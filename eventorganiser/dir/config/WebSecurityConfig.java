package eventorganiser.dir.config;

import eventorganiser.dir.DBMethods.DBRespositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthSuccessHandler authSuccessHandler(){
        return new AuthSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

//        http.csrf().disable();

        //"/api/get/*","/api","/api/*"
        http.authorizeRequests().antMatchers("*/*", "*", "*/*/*", "/api/*/*", "/api/*/*/*",  "/api/*/*/*/*", "/register", "/login", "/media/hodge-bank-logo.png", "/css/bootstrap/css/bootstrap.css", "static/css/eventPage.css", "./css/eventPage.css", "/css/header_footer.css", "/css/loginTemplate.css").permitAll();


        http.authorizeRequests().anyRequest().authenticated().and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/Calendar")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authSuccessHandler());


        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(passwordEncoder()).dataSource(dataSource);
    }
}