package com.inc.admin.filters;

import com.inc.admin.context.FilterContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @ClassName TraceFilter
 * @Description TODO
 * @Author renh
 * @Date
 **/
@Component //将此Filter交给Spring容器管理
@WebFilter(urlPatterns = "/*", filterName = "TraceFilter")
@Order(1) //指定过滤器的执行顺序,值越大越靠后执行
@Slf4j
public class TraceFilter implements Filter
{
    /** 用于日志打印唯一标识 */
    private static final String UNIQUE_ID = "traceRootId";

    @Value("${server.logFlag:true}")
    private boolean serverLogFlag;

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        // 设置SessionId
        HttpServletRequest httpServletRequest =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = httpServletRequest.getHeader("sessionId");

        String requestURL = httpServletRequest.getRequestURL().toString();
        String requestURI = httpServletRequest.getRequestURI();
        String replace = requestURL.replace(requestURI, "");
        //设置访问host
        FilterContextHandler.setReqURL(replace);

        if (StringUtils.isEmpty(sessionId))
        {
            sessionId = UUID.randomUUID().toString();
        }

        MDC.put(UNIQUE_ID, sessionId);

        // 根据server.logFlag配置决定是否记录日志
        if (!serverLogFlag)
        {
            try
            {
                chain.doFilter(request, response);
            }
            finally
            {
                // 防止内存溢出
                MDC.remove(UNIQUE_ID);
            }
            return;
        }

        MyHttpServletRequestWrapper requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest)request);
        MyHttpServletResponseWrapper responseWrapper = new MyHttpServletResponseWrapper((HttpServletResponse)response);//转换成代理类

        String requestBody = null;
        Long requestTime = System.currentTimeMillis();
        String serviceName = httpServletRequest.getHeader("serviceName");
        try
        {
            requestBody = getRequestBody(requestWrapper);
            log.info("请求URI：{}，参数：{}", requestURI, requestBody);
            chain.doFilter(requestWrapper, responseWrapper);
        }
        finally
        {
            try
            {
                if (!requestURI.contains("/actuator/health"))
                {
//                    RServerLog rServerLog = SpringContextLocator.getBean(RServerLog.class);
//
//                    ServerLog serverLog = new ServerLog();
//                    serverLog.setSessionId(sessionId);
//                    serverLog.setServiceName(StringUtils.isNotBlank(serviceName) ? serviceName : "unkown");
//                    serverLog.setRequestMethod(requestWrapper.getMethod());
//                    serverLog.setRequestUrl(requestWrapper.getRequestURL().toString());
//                    serverLog.setRequestBody(requestBody);
//                    serverLog.setRequestTime(new Date(requestTime));
//                    Long responseTime = System.currentTimeMillis();
//                    serverLog.setResponseTime(new Date(responseTime));
//                    serverLog.setDurationMs((int)(responseTime - requestTime));
//                    rServerLog.save(serverLog);

                    String contentType = responseWrapper.getContentType();
                    log.info("contentType:{}", contentType);

                    //获取返回值
                    byte[] content = responseWrapper.getContent();
                    if (content.length > 0)
                    {
                        //返回值
                        if (StringUtils.isNotBlank(contentType) && !contentType.contains("octet-stream")
                            && !contentType.contains("application/javascript")
                            && !contentType.contains("application/font-woff2"))
                        {
                            String str = new String(content, StandardCharsets.UTF_8);

                            if(str.length() > 500) {
                                log.info("响应:{}", str.substring(0, 500));
                            }else {
                                log.info("响应:{}", str);
                            }

                            if(str.length() > 100000) {
                                str = str.substring(0, 100000);
                            }
                            //serverLog.setResponseBody(str);
                        }
                        //rServerLog.save(serverLog);
                        //把返回值输出到客户端
                        ServletOutputStream out = response.getOutputStream();
                        out.write(content);
                        out.flush();
                    }
                }

            }
            catch (Throwable e)
            {
                log.error("记录日志异常", e);
            }
            finally
            {
                // 防止内存溢出
                MDC.remove(UNIQUE_ID);
            }
        }
    }

    private String getRequestBody(MyHttpServletRequestWrapper requestWrapper)
    {
        String requestBody = requestWrapper.getBodyString();
        log.info("contentType:{}", requestWrapper.getContentType());
        if (StringUtils.isNotEmpty(requestWrapper.getContentType())
            )
        {
            if (requestWrapper.getContentType().contains("application/x-www-form-urlencoded")) {
                StringBuilder sb = new StringBuilder();
                java.util.Enumeration<String> reqParamName = requestWrapper.getParameterNames();
                while (reqParamName.hasMoreElements())
                {
                    String paramName = reqParamName.nextElement();
                    String[] values = requestWrapper.getParameterValues(paramName);
                    for (String value : values)
                    {
                        sb.append(paramName).append("=").append(value).append("&");
                    }
                }
                requestBody = sb.toString();
            }else if (requestWrapper.getContentType().contains("multipart/form-data")) {
                //上传了文件
                log.info("请求方式可能包含文件，暂不打印参数");
                //参考https://blog.csdn.net/funi16/article/details/8434157进行编写
            }

        }
        return requestBody;
    }

    @Override
    public void destroy()
    {

    }

}
