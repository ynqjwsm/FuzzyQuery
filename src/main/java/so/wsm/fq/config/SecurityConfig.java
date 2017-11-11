package so.wsm.fq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserConfig userConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/manage/**", "/images/**", "/weui/**").permitAll()
                .antMatchers("/","/index","/api/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login_process").usernameParameter("user").passwordParameter("pass").defaultSuccessUrl("/").permitAll()
                .and()
                .logout().logoutUrl("/logout").invalidateHttpSession(true);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        if(null == userConfig || userConfig.getUsers() == null || userConfig.getUsers().size() == 0){
            manager.createUser(User.withUsername("kmwxwy").password("wxwy123").roles("USER").build());
        }else {
             for(UserConfig.User user : userConfig.getUsers()){
                 manager.createUser(User.withUsername(user.getUsername()).password(user.getPassword()).roles("USER").build());
             }
        }

        return manager;
    }

}
