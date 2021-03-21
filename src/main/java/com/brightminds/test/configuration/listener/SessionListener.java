package com.brightminds.test.configuration.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

@WebListener
public class SessionListener implements HttpSessionListener {

    private SessionRegistry sessionRegistry;

    public SessionListener(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionRegistry.removeSessionInformation(se.getSession().getId());
    }
}
