package com.dankan.service.sms;

import com.dankan.domain.User;
import com.dankan.dto.request.sns.CertificationRequestDto;
import com.dankan.exception.user.PhoneNumberNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.UserRepository;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Random;

@Slf4j
public class SmsServiceImpl implements SmsService {
    @Value("${sms.key}")
    private String key;
    @Value("${sms.secret}")
    private String secret;
    @Value("${sms.phoneNumber}")
    private String ph;
    private static HashMap<String, String> userNum;
    private final Random random;
    private final UserRepository userRepository;

    public SmsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
        userNum = new HashMap<>();
        random = new Random();
    }

    @Override
    public void sendMessage(String phoneNum) {
        Message coolsms = new Message(key, secret);
        String cerNum = "";

        for(int i = 0; i < 6; i++) {
            cerNum += random.nextInt(10);
        }

        log.info("발신 번호: {}", ph);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("to", phoneNum);    // 수신전화번호
        params.put("from", ph); //발신 번호
        params.put("type", "SMS");
        params.put("text", "단칸 휴대폰인증 테스트 메시지 : 인증번호는" + "[" + cerNum + "]" + "입니다.");
        params.put("app_version", "dankan app 1.2"); // application name and version

        userNum.put(phoneNum, cerNum);

        //send message
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

    @Override
    public Boolean verifyNumber(CertificationRequestDto certificationRequestDto) {
        if(userNum.get(certificationRequestDto.getPhoneNumber()).equals(certificationRequestDto.getNumber().toString())) {
            User user = userRepository.findByPhoneNum(certificationRequestDto.getPhoneNumber()).orElseThrow(
                    () -> new PhoneNumberNotFoundException(certificationRequestDto.getPhoneNumber())
            );

            user.setPhoneNum(certificationRequestDto.getPhoneNumber());

            userRepository.save(user);

            return true;
        }

        return false;
    }
}
