package org.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blog.model.RespBean;
import org.blog.model.User;
import org.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //所有请求均拦截
                .anyRequest().authenticated()
                .and()
                //登录相关配置
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    User user = (User) authentication.getPrincipal();
                    user.setPassword(null);
                    ObjectMapper om = new ObjectMapper();
                    String s = om.writeValueAsString(RespBean.ok("登录成功"));
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) ->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespBean error = RespBean.error("登录失败");
                    if(e instanceof LockedException){
                        error.setMsg("账户被锁定，请联系管理员");
                    }else if (e instanceof DisabledException){
                        error.setMsg("账户被禁用，请联系管理员");
                    }else if (e instanceof CredentialsExpiredException){
                        error.setMsg("密码过期，请联系管理员");
                    }else if (e instanceof AccountExpiredException){
                        error.setMsg("账户过期，请联系管理员");
                    }else if (e instanceof BadCredentialsException){
                        error.setMsg("用户名或密码输入错误，请联系管理员");
                    }
                    ObjectMapper om = new ObjectMapper();
                    String s = om.writeValueAsString(error);
                    out.write(s);
                    out.flush();
                    out.close();
                } )
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) ->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespBean respBean = RespBean.ok("注销成功");
                    String s = new ObjectMapper().writeValueAsString(respBean);
                    out.write(s);
                    out.flush();
                    out.close();
                } )
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespBean error = RespBean.error("访问失败");
                    if (e instanceof InsufficientAuthenticationException){
                        error.setMsg("请求失败，请联系管理员");
                    }
                    String s = new ObjectMapper().writeValueAsString(error);
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }
}
