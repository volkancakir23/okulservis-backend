package com.okulservis.service;

import java.util.List;

public interface SessionUser {

    Object getId();

    String getUsername();

    String getName();

    String getSurname();

    List getAuthorities();

}
