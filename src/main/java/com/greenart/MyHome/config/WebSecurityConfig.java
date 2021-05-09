package com.greenart.MyHome.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private DataSource dataSource;   // properity.yml 에서 설정한 dataSource 부분을 사용할수 있게 주입하는부분
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.csrf().disable()                       // csrf()은 보안을 위해서 security가 개발자와 별개로 키값을 전달하고 받아서 확인하는등 보안에 유리 
			.authorizeRequests()
				.antMatchers( "/", "/account/register" ,"/css/**" ,"/api/**","/board/**"  ).permitAll()
//				.antMatchers( "/", "/account/**" ,"/css/**" ,"/board/**" ,"/js/**"   ).hasAnyRole("ROLE_ADMIN","ROLE_USER","ROLE_MANAGER")
				.anyRequest().permitAll()
//				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/account/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
				.logout()
				.permitAll();

	}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                
                .passwordEncoder(passwordEncoder())              // 비빌번호 암호를 하는 부분
                
                .usersByUsernameQuery("select username, password, enabled "    // username, password 를  조회하는 쿼리부분          // Authentication    로그인관련(인증처리관련)
                        + "from User "                                                                                     // Authroization     권한에관한설정
                        + "where username = ?")                                  // 자동으로 ?에 username이 들어감
  
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join User u on ur.user_id = u.id "
                                          + "inner join Role r on ur.role_id = r.id "
                        + "where u.username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {        // 비밀번호 암호화 하게 하는 함수 실행부분
        return new BCryptPasswordEncoder();
    }
}