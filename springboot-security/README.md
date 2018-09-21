### Spring Security 登录认证与授权流程

#### 登录认证
> * 首先被 AbstractAuthenticationProcessingFilter（UsernamePasswordAuthenticationFilter） 拦截
> * 执行 attemptAuthentication 方法
> * 执行 AuthenticationManager（ProviderManager） 的 authenticate 方法来获取用户的验证信息（不同的Provider调用的服务不同，因为这些信息可以是在数据库上，可以是在LDAP服务器上，可以是xml配置文件上等），如果验证通过后会将用户的权限信息封装一个User放到spring的全局缓存SecurityContextHolder中，以备后面访问资源时使用
> *
 
#### 授权
> * 访问 url 时，首先被 AbstractSecurityInterceptor（FilterSecurityInterceptor）拦截
> * 按顺序执行 doFilter ，invoke 方法
> * 然后通过 FilterInvocationSecurityMetadataSource 获取被拦截url所需的全部权限
> * 然后授权管理器 AccessDecisionManager，这个授权管理器会通过spring的全局缓存SecurityContextHolder获取用户的权限信息，还会获取被拦截的url和被拦截url所需的全部权限，然后根据所配的策略（有：一票决定，一票否定，少数服从多数等），如果权限足够，则返回，权限不够则报错并调用权限不足页面
> * 通过 AccessDecisionManager 里的 List<AccessDecisionVoter> 的进行能否访问的权限表决
> *


