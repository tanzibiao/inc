//package com.inc.incadmin.filters;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//public class GlobalExceptionHanlderFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHanlderFilter.class);
//
//    public void handler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Throwable throwable) throws IOException {
//        String errmsg = throwable.getMessage();
//        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
//        if (stackTraceElements.length != 0){
//            StackTraceElement stackTraceElement = stackTraceElements[0];
//            errmsg += "\n" + "问题出处：" + stackTraceElement.toString();
//        }
//        logger.error(errmsg, throwable);
//        RestCode restCode = Exception2CodeRepo.getCode(throwable);
//        RestResponse<Object> response = RestResponse.error(restCode);
//
//        PrintWriterOut.writeOut(httpServletRequest, httpServletResponse, 200, response);
//    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//        }catch (Throwable e) {
//            this.handler(httpServletRequest, httpServletResponse, e);
//        }
//    }
//}
