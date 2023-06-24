package com.dankan.service.sms;

import com.dankan.dto.request.sns.CertificationRequestDto;

public interface SmsService {
    public void sendMessage(String phoneNum);
    public Boolean verifyNumber(CertificationRequestDto certificationRequestDto);
}
