package btree.projetpro.backend.security;

import btree.projetpro.backend.security.dao.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN_ROLE")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/public/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .httpBasic();

        http.csrf().disable(); //TODO delete this line in production
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles(UserRole.USER_ROLE.toString())
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles(UserRole.USER_ROLE.toString())
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles(UserRole.ADMIN_ROLE.toString());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
