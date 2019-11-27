package com.greenfox.tribes1.Progression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProgressionInterceptor extends HandlerInterceptorAdapter {

  private ProgressionService progressionService;

  @Autowired
  public ProgressionInterceptor(ProgressionService progressionService) {
    this.progressionService = progressionService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    progressionService.checkConstruction();
    return super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    progressionService.checkConstruction(); //TODO ONLY WORKS HERE
    super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    progressionService.checkConstruction();
    super.afterCompletion(request, response, handler, ex);
  }
}
