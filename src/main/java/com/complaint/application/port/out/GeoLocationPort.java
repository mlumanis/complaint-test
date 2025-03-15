package com.complaint.application.port.out;

import com.complaint.domain.model.Country;

public interface GeoLocationPort {
    Country getCountryFromIp(String ipAddress);
} 