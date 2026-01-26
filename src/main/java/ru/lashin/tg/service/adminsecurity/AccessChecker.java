package ru.lashin.tg.service.adminsecurity;

public interface AccessChecker {

    boolean checkUserAccess(Long userId);

}
