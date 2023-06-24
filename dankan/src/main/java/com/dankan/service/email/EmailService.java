package com.dankan.service.email;

import com.dankan.dto.request.email.EmailCodeRequestDto;

public interface EmailService {
    public String sendSimpleMessage(String to) throws Exception;
    public Boolean verifyCode(EmailCodeRequestDto emailCodeRequestDto);
}
