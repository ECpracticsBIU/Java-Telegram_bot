package ru.lashin.tg.service.security;


public interface AccessChecker {

    boolean checkAccess(String userId);

}
