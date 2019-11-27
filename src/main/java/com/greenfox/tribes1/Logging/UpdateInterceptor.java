package com.greenfox.tribes1.Logging;

import com.greenfox.tribes1.Kingdom.Kingdom;
import com.greenfox.tribes1.Kingdom.KingdomService;
import com.greenfox.tribes1.Resources.Resource;
import com.greenfox.tribes1.Resources.ResourceService;
import com.greenfox.tribes1.Security.Model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class UpdateInterceptor extends HandlerInterceptorAdapter {

  private ResourceService resourceService;
  private KingdomService kingdomService;

  @Autowired
  public UpdateInterceptor(ResourceService resourceService, KingdomService kingdomService) {
    this.resourceService = resourceService;
    this.kingdomService = kingdomService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    return super.preHandle(request, response, handler);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Kingdom kingdom = kingdomService.getKindomFromAuth(authentication);
    List<Resource> resourceList = kingdom.getResources();
    for (Resource aResourceList : resourceList) {
      resourceService.refresh(aResourceList);
    }
    super.afterCompletion(request, response, handler, ex);
  }
}
